import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection extends Thread {

	private Socket sock;
	private InputStream inStream;
	private OutputStream outStream;

	/*The username should be unique and since our objects are we can use that as a default SET_USERNAME.
		We will use this uniqeness provided by the hashcode.
	*/
	private String username = ""+this.hashCode();

	public Connection(Socket sock) {
		this.sock = sock;
		start();
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

	// Encode a message with a trailing linefeed as our delimiter
	public byte[] encode(String message)
	{
		byte[] characterBytes = new byte[message.length() + 1];

		for (int i = 0; i < message.length(); i++)
		{
			characterBytes[i] = (byte) message.charAt(i);
		}

		// Ending character (in this case a linefeed)
		characterBytes[message.length() + 1] = 10;

		return characterBytes;
	}

	public void setup()
	{
		// Setup the input and output streams
		setupStreams();
	}

	public void run()
	{
//TODO: Add a welcome statement

		output("New Connection object created");

		//Run setup routines
		setup();

		try
		{
			String command = "";

			boolean running = true;
			while (running)
			{
				// Read from client
				int byteRep = inStream.read();

				//check -1
				if(byteRep == -1)
				{
					//error here
				}
				//Read an actual byte
				else
				{
					byte theByte = (byte)byteRep;

					//Linefeed flush out
					if(theByte == 10)
					{
						//Command recieved
						output("Command received \"" + command+"\"");

						//Run the command

						//Set the username of the connected user
						if(command.equals("SET_USERNAME"))
						{
								output("User is requesting to set a username");
						}
						//Join an existing channel
						else if(command.equals("JOIN_CHANNEL"))
						{

						}
						//Creates a new channel
						else if(command.equals("CREATE_CHANNEL"))
						{

						}
						//Leaves the channel
						else if(command.equals("LEAVE_CHANNEL"))
						{

						}
						//Send a message to a given channel
						else if(command.equals("SEND_MESSAGE"))
						{

						}
						//Change the topic of a given channel
						else if(command.equals("CHANGE_TOPIC"))
						{

						}
						//Disconnect from the server
						else if(command.equals("BYE"))
						{

						}
						//Handle anything else
						else
						{
							//TODO: work in progress
						}


						//Flush out
						command = "";
					}
					//Append to `command` string
					else
					{
						command = command + (char)(  (byte)theByte );
					}
				}

			}
		}
		catch (IOException err)
		{
			output("Error with client: " + err.getMessage());
		}

		output("Connection object thread ending");
	}

//Output text to the `stdout` file descriptor (a.k.a. the terminal screen) with a useful debugging information
	public void output(String message)
	{
		System.out.println("[LA: " + sock.getLocalAddress() + ", LP: " + sock.getLocalPort() + ", RA: " + sock.getInetAddress() + ", RP: " + sock.getPort() + "]: " + message);
	}

}
