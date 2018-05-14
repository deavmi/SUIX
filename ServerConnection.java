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


	//Channels we are currently in
	private Channel channels = {new Channel("welcome","default channel")};

	/*The username should be unique and since our objects are we can use that as a default SET_USERNAME.
	We will use this uniqeness provided by the hashcode.*/
	private String username = ""+this.hashCode();


	//Returns the username asociated with this user
	public String getUsername()
	{
		return username;
	}

	//Set the userame
	private void setUsername(String username)
	{
		output("User attempting to change username");
		output("Username has been updated from \"" + this.username + "\" to \"" + username + "\"");
		this.username = username;
	}

	public ServerConnection(Socket sock) {
		this.sock = sock;
		start();
	}

	//Send the given message `message` to the given channel `channel`
	private void broadcastToChannel(Channel channel, Message message)
	{
		output("Broadcasting to channel \"" + channel.getChannelName() + "\"...");
		//
		output("");
	}

	//Join a channel
	public void joinChannel(Channel channel)
	{
		output("Joining channel \"" + channel.getChannelName() + "\"");
		this.channel = channel;
		output("Joined channel \"" + channel.getChannelName() + "\"");
	}

	//Whether the user is joined to a channel or not
	public boolean isJoinedChannel()
	{
		return channels.length != 0;
	}

	//Send a message `message` to the channel `channel`
	public void sendMessage(Channel channel, Message message)
	{

		output("message : \"" + message+ "\"");

		//Broadcast the message to the given channel
		broadcastToChannel(channel, message);

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

	//Leave channel `channel`
	public void leaveChannel(Channel channel)
	{
		output("Leaving channel \"" + channel.getChannelName() + "\"");
		//WIP: Assigned to @deavmi
		//Send a message to the current channel when leaving

		//Leave the channel
		//WIP

		output("Left channel \"" + channel.getChannelName() + "\"");
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

					Channel channelToJoin = new Channel(userRequestedChannel, "tbd"); //FIXME: assigned to @deavmi

					//Join the requested channel
					joinChannel(channelToJoin);
				}
				else if(command.equals("LEAVE_CHANNEL"))
				{
					//Get the channel the user is requesting to leave
					String channelToLeave = IO.readCommand(inStream);

					//First check if it is possible
					boolean isPossible = true;//WIP

					if(isPossible)
					{
						//Leave the channel
						leaveChannel(new Channel(channelToLeave,"idk"));

						//Send a response saying the leave was successful
						IO.sendCommand(outStream, "LEAVE_SUCCESS");
					}
					else
					{
						//Send a response saying the leave was unsucccessful
						IO.sendCommand(outStream, "LEAVE_ERROR");
					}
				}
				else if(command.equals("SEND_MESSAGE"))
				{
					//Get the channel of which to send the message to
					String channelForMessage = IO.readCommand(inStream);
					//Get the message's text
					String messageText = IO.readCommand(inStream);
					output("Ello naai: "+messageText);

					Channel channel = new Channel(channelForMessage, "wip");
					Message message = new Message(channel, messageText);
					sendMessage(message);
				}
				else if(command.equals("LIST_CHANNELS"))
				{
					//Get all the channels
					Channel[] channels = listChannels();

					//Send the amount of channels to the user so he knows how many of
					//my messages are channels
					IO.sendCommand(outStream, ""+channels.length);

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
