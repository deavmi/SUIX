import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ServerConnection extends Thread
{

	//Socket to the client host
	private Socket socket;

	//The Sopcket's InputStream
	private InputStream inStream;

	//The Socket's OutputStream
	private OutputStream outStream;


	//Channels we are currently in
	private Channel channels = {new Channel("welcome","default channel")};

	//Soon to be deprecated data structure
	private DynamicArray<Channel> channels = new DynamicArray<Channnel>(0);

	//Join the default or welcoming channel
	private void joinDefaultChannel()
	{
		channels.append(new Channel("welcome","idk")); //FIXME: idk and how channels descriptions are going to work
	}

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
		out("User attempting to change username");
		out("Username has been updated from \"" + this.username + "\" to \"" + username + "\"");
		this.username = username;
	}

	public ServerConnection(Socket socket) {
		this.socket = socket;
		start();
	}

	//Send the given message `message` to the given channel `channel`
	private void broadcastToChannel(Message message)
	{
		//Debugging
		out("Broadcasting to channel \"" + message.getChannel().channel.getChannelName() + "\"...");

		//Iterate over each connection and send each user on the given channel the new message
		for(ServerConnection connection: server.connections.getArray())
		{
			out("Broadcasting message to \"" + connection + "\"...");

			//Skip myself
			if(connection != this)
			{
				connection.receiveMessage(message); //add to miss self (@deavmi)
			}

			out("Broadcast sent");
		}
	}

	//Receive a message from a broadcast
	public void receiveMessage(Message message)
	{
		//Write it to the user host
		IO.sendCommand("RECEIVED_MESSAGE");

		//Tell the user the channel the message originated from
		IO.sendCommand(message.getChannel().getChannelName());

		//Provide the actual message text to the user
		IO.sendCommand(message.getMessageText());
	}

	//Join a channel
	private void joinChannel(Channel channel)
	{
		out("Joining channel \"" + channel.getChannelName() + "\"");
		this.channel = channel;
		out("Joined channel \"" + channel.getChannelName() + "\"");
	}

	//Whether the user is joined to a channel or not
	public boolean isJoinedChannel()
	{
		return channels.length != 0;
	}

	//Send a message `message` to the channel specified
	public void sendMessage(Message message)
	{

		out("message : \"" + message+ "\"");

		//Broadcast the message to the given channel
		broadcastToChannel(message);

		//we good?
		IO.sendCommand(outStream, "MESSAGE_SENT");
	}

	//Lists all the channels on this server
	public Channel[] listChannels()
	{
		return channels.getArray();
		//Work in progress @deavmi
		return channels;
	}

	//Leave channel `channel`
	public void leaveChannel(Channel channel)
	{
		out("Leaving channel \"" + channel.getChannelName() + "\"");
		//WIP: Assigned to @deavmi
		//Send a message to the current channel when leaving

		//Leave the channel
		//WIP

		out("Left channel \"" + channel.getChannelName() + "\"");
	}

	public void setupStreams()
	{
		out("Setting up streams...");
		try
		{
			out("Setting up input stream...");
			inStream = sock.getInputStream();
			out("Input stream has been setup.");

			out("Setting up output stream...");
			outStream = sock.getOutputStream();
			out("Output stream has been setup.");
		}
		catch (IOException err)
		{
			out("Error setting up stream: " + err.getMessage());
		}
		out("Streams setup.");
	}

	//Setup routines
	public void setup()
	{
		//Debugging
		out("Setup is taking place...");
		out("Setting up streams...")

		// Setup the input and output streams
		setupStreams();

		//Debugging
		out("Setting up streams... [done]");
		out("Joining default channel...");

		//Join the default channel (a.k.a. the `welcome` channel)
		joinDefaultChannel();

		//Debugging
		out("Joining default channel... [done]");
		out("Setup is taking place... [done]");
	}

	//This thread's main routine
	public void run()
	{
		//TODO: Add a welcome statement
		//Announce server version to the client
		//IO.sendCommand(outStream, suixd.VERSION);

		out("New Connection object created");

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
				out("There was an error with the client, terminating...");
				running = false;
				continue;
			}
			//Else, we continue interpreting the commands
			else
			{
				out("command recieved: \"" + command + "\"");
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
					out("Ello naai: "+messageText);

					Channel channel = new Channel(channelForMessage, "wip");
					Message message = new Message(channel, messageText);
					sendMessage(message);
				}
				else if(command.equals("VERSION"))
				{
					out("Sending version number to client...");
					IO.sendCommand(outStream, suixd.VERSION);
					out("Version number sent.");
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
					out("Invalid command \""+command + "\"");
				}
			}
		}
		out("Connection object thread ending");
	}

	//Output text to the `stdout` file descriptor (a.k.a. the terminal screen) with a useful debugging information
	public void out(String message)
	{
		PrettyPrint.out("Connection (" +username +")","[LA: " + sock.getLocalAddress() + ", LP: " + sock.getLocalPort() + ", RA: " + sock.getInetAddress() + ", RP: " + sock.getPort() + "]: " + message);
	}

}
