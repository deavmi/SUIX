import java.net.Socket;
import java.io.IOException;

public class suixy
{

	public static void out(String message)
	{
		PrettyPrint.out("client",message);
	}

	public static void main(String[] args)
	{
		if(args.length < 2)
		{
			out("Need an IP Address and port number");
		}
		else
		{
			String IPAddress = args[0];
			int port;
			try
			{
				port = Integer.parseInt(args[1]);
				startClient(IPAddress, port);
			}
			catch(NumberFormatException err)
			{
				out("Format error");
			}
		}
	}

	public static void startClient(String IPAddress, int port)
	{
		try
		{
			out("Trying to connect to server at " + IPAddress + " on port " + port + " ...");
			Socket connection = new Socket(IPAddress, port);


			String command = "";
			boolean running = true;

			//Get the username
			IO.sendCommand(connection.getOutputStream(), "GET_USERNAME");
			String username = IO.readCommand(connection.getInputStream());
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
					//get the requested username
					username = IO.readCommand(System.in);
					//Set username
					IO.sendCommand(connection.getOutputStream(),"SET_USERNAME");
					IO.sendCommand(connection.getOutputStream(),username);

				}
				//Anything else is a message
				else
				{
					IO.sendCommand(connection.getOutputStream(),"SEND_MESSAGE");
					IO.sendCommand(connection.getOutputStream(),command);
					String errorReturn = IO.readCommand(connection.getInputStream());
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
