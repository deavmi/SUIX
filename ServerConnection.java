import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerConnection extends Thread {

	//All connections


	//Socket to the client host
	private Socket sock;

	//The Sopcket's InputStream
	private InputStream inStream;

	//The Socket's OutputStream
	private OutputStream outStream;



	//The channel we are currently in (default: #welcome)
	private Channel channel = new Channel("welcome", "default channel");

	/*The username should be unique and since our objects are we can use that as a default SET_USERNAME.
	We will use this uniqeness provided by the hashcode.*/
	private String username = ""+this.hashCode();


	//Returns the username asociated with this user
	public String getUsername()
	{
		return username;
	}





	public void setUsername(String username)
	{
		output("User attempting to change username");
		output("Username has been updated from \"" + this.username + "\" to \"" + username + "\"");
		this.username = username;
	}

	public ServerConnection(Socket sock) {
		this.sock = sock;
		start();
	}

	//Join a channel
	public void joinChannel(String channelName)
	{
		output("Joining channel \"" + channelName + "\"");
		channel = channelName;
		output("Joined channel \"" + channelName + "\"");
	}

	//Whether the user is joined to a channel or not
	public boolean isJoinedChannel()
	{
		return channel != null;
	}

	//Get the current channel
	public String getChannel()
	{
		return channel;
	}

	//Send a message to the current channel
	public void sendMessage(String message)
	{

		output("message : \"" + message+ "\"");

		//we good?
		IO.sendCommand(outStream, "MESSAGE_SENT");
	}

	//Lists all the channels on this server
	public Channel[] listChannels()
	{
		Channel[] channels = null;
		//Work in progress @deavmi
		return channels;
	}

	//Leave a channel
	public void leaveChannel()
	{
		output("Leaving channel \"" + channel + "\"");
		//WIP: Assigned to @deavmi
		//Send a message to the current channel when leaving

		//Leave the channel
		channel = null;

		output("Left channel \"" + channel + "\"");
	}

	public void setupStreams()
	{
		output("Setting up streams...");
		try
		{
			output("Setting up input stream...");
			inStream = sock.getInputStream();
			output("Input stream has been setup.");

			output("Setting up output stream...");
			outStream = sock.getOutputStream();
			output("Output stream has been setup.");
		}
		catch (IOException err)
		{
			output("Error setting up stream: " + err.getMessage());
		}
		output("Streams setup.");
	}

	// Is the byte a Linefeed
	public boolean isLineFeed(byte charCode)
	{
		if (charCode == 10)
		{
			return true;
		}
		else
		{
			return false;
		}
	}



	//Setup routines
	public void setup()
	{
		// Setup the input and output streams
		setupStreams();
	}




	//This thread's main routine
	public void run()
	{
		//TODO: Add a welcome statement

		output("New Connection object created");

		//Run setup routines
		setup();


		String command = "";
		boolean running = true;
		while (running)
		{
			// Read from the client
			command = IO.readCommand(inStream);

			//If there was an error whilst reading from the stream
			if(command == null)
			{
				output("There was an error with the client, terminating...");
				running = false;
				continue;
			}
			//Else, we continue interpreting the commands
			else
			{
				output("command recieved: \"" + command + "\"");
				//If the command is to set the currently logged in user's username
				if(command.equals("SET_USERNAME"))
				{
					String newUsername = IO.readCommand(inStream);
					setUsername(newUsername);
				}
				else if(command.equals("GET_USERNAME"))
				{
					IO.sendCommand(outStream, username);
				}
				//If the command is to join a channel
				else if(command.equals("JOIN_CHANNEL"))
				{
					//Set the current channel (and leave the other WIP)
					String userRequestedChannel = IO.readCommand(inStream);

					//Leave the current channel
					leaveChannel();

					//Join the requested channel
					joinChannel(userRequestedChannel);
				}
				else if(command.equals("LEAVE_CHANNEL"))
				{
					//Leave the current channel
					leaveChannel();
				}
				else if(command.equals("SEND_MESSAGE"))
				{
					String message = IO.readCommand(inStream);
					output("Ello naai: "+message);
					sendMessage(message);
				}
				else if(command.equals("LIST_CHANNELS"))
				{
					//Get all the channels
					Channel[] channels = listChannels();

					//Send the amount of channels to the user so he knows how many of
					//my messages are channels
					IO.sendCommand(outStream, channels.length);

					//Now send the channel names to him
					for(Channel channel: channels)
					{
						IO.sendCommand(outStream, channel.getChannelName());
					}
				}
				else
				{
					output("Invalid command \""+command + "\"");
				}
			}
		}
		output("Connection object thread ending");
	}

	//Output text to the `stdout` file descriptor (a.k.a. the terminal screen) with a useful debugging information
	public void output(String message)
	{
		PrettyPrint.out("Connection (" +username +")","[LA: " + sock.getLocalAddress() + ", LP: " + sock.getLocalPort() + ", RA: " + sock.getInetAddress() + ", RP: " + sock.getPort() + "]: " + message);
	}

}
