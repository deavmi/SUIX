public class suixd
{

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			kak.out("suixd","Needs a port number argument and a max user number argument");
		}
		else
		{
			try
			{
				server.startServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
			catch (NumberFormatException err)
			{
				kak.out("suixd","A valid port number and max user number is required");
			}
		}
	}


}
