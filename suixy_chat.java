import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;

public class suixy_chat
{

	public static void chatConsole(String IPAddress, int port)
	{
		try
		{
			suixy.out("Trying to connect to server at " + IPAddress + " on port " + port + " ...");
			Socket socket = new Socket(IPAddress, port);

			//Setup input and output streams
			InputStream inStream = socket.getInputStream();
			OutputStream outStream = socket.getOutputStream();

			String command = "";
			boolean running = true;

			//Set the Streams
			libsuix.outStream = outStream;
			libsuix.inStream = inStream;

			//Get the username
			String username = libsuix.getUsername();
			//Get the channel
			String channel = libsuix.getChannel();

			while(running)
			{
				System.out.print("["+username+"/"+channel+"]>>> ");
				command = IO.readCommand(System.in);

				if(command.equals("quit"))
				{
					return;
				}
				//Set my username
				else if(command.equals("/nick"))
				{
					//Get the requested username
					username = IO.readCommand(System.in);

					//Set username
          libsuix.setUsername(username);
				}
				//Anything else is a message
				else
				{
					String errorReturn = libsuix.sendMessage(command);
					if(errorReturn.equals("MESSAGE_SENT"))
					{
						//message has been sent then
					}
					else
					{
						//error
						System.out.print("Error sending message");
					}
				}


			}
		}
		catch(IOException err)
		{
			suixy.out("Error: " + err.getMessage());
		}
	}

}
