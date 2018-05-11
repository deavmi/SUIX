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

	//Get the command sent from the host (returns `null` on error (when an IOException occurs)
	public String readCommand()
	{
		String message = "";
try
{
		boolean awaitingLineFeed = true;
		while(awaitingLineFeed)
		{
			// Read from client
			int byteRep = inStream.read();

			//check -1
			if(byteRep == -1) //look into me @assigned to deavmi
			{
				//error here
			}
			//Read an actual byte
			else			
			{
				// The actual byte
				byte theByte = (byte)byteRep;

				//Linefeed flush out
				if(theByte == 10)
				{
					//Command recieved
					output("Data received \"" + message+"\"");

					//We are now done and can return the message
					awaitingLineFeed = false;
				}
				//If not a linefeed then append to the `message` String
				else
				{
					message = message + (char)(  (byte)theByte );
				}

			}
		}
}
catch(IOException err)
{
	//On error set `message` to `null`
	message = null;
}
		return message;
	}

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
				command = readCommand();

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
						output("user attempting to change username");
						String newUsername = readCommand();
						output("Username has been updated from \"" + username + "\" to \"" + newUsername + "\"");
						username = newUsername;
					}
					//If the command is to join a channel
					else if(command.equals("JOIN_CHANNEL"))
					{
						
					}
				}
			}

		output("Connection object thread ending");
	}

//Output text to the `stdout` file descriptor (a.k.a. the terminal screen) with a useful debugging information
	public void output(String message)
	{
		System.out.println("[LA: " + sock.getLocalAddress() + ", LP: " + sock.getLocalPort() + ", RA: " + sock.getInetAddress() + ", RP: " + sock.getPort() + "]: " + message);
	}

}
