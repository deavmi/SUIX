import java.net.Socket;
import java.io.IOException;

public class suixy_chat
{

	public static void chatConsole(String IPAddress, int port)
	{
		try
		{
			out("Trying to connect to server at " + IPAddress + " on port " + port + " ...");
			Socket connection = new Socket(IPAddress, port);


			String command = "";
			boolean running = true;

      //Set the connection
      libsuix.connection = connection;

			//Get the username
			String username = libsuix.getUsername();
			while(running)
			{
				System.out.print("["+username+"]>>> ");
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
			out("Error: " + err.getMessage());
		}
	}

}
