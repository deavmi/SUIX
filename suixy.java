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
				suixy_chat.chatConsole(IPAddress, port);
			}
			catch(NumberFormatException err)
			{
				out("Format error");
			}
		}
	}

}
