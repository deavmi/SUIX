import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Connection extends Thread {

	private Socket sock;
	private InputStream inStream;
	private OutputStream outStream;

	public Connection(Socket sock) {
		this.sock = sock;
		start();
	}

	public void setupStreams() {
		output("Setting up streams...");
		try {
			output("Setting up input stream...");
			inStream = sock.getInputStream();
			output("Input stream has been setup.");

			output("Setting up output stream...");
			outStream = sock.getOutputStream();
			output("Output stream has been setup.");
		} catch (IOException err) {
			output("Error setting up stream: " + err.getMessage());
		}
		output("Streams setup.");
	}

	// Is the byte a Linefeed
	public boolean isLineFeed(byte charCode) {
		if (charCode == 10) {
			return true;
		} else {
			return false;
		}
	}

	// Encode a message with a trailing linefeed as our delimiter
	public byte[] encode(String message) {
		byte[] characterBytes = new byte[message.length() + 1];

		for (int i = 0; i < message.length(); i++) {
			characterBytes[i] = (byte) message.charAt(i);
		}

		// Ending character (in this case a linefeed)
		characterBytes[message.length() + 1] = 10;

		return characterBytes;
	}

	public void run() {
		output("New Connection object created");

		// Setup the input and output streams
		setupStreams();

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
						else if(command.equals("SEND_MESSAGE"))
						{

						}
						else if(command.equals("CHANGE_TOPIC"))
						{

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

	public void output(String message) {
		System.out.println("[LA: " + sock.getLocalAddress() + ", LP: " + sock.getLocalPort() + ", RA: "
				+ sock.getInetAddress() + ", RP: " + sock.getPort() + "]: " + message);
	}

}
