public class suixd
{

	public static final String VERSION = "0.0.1 fokkin' alpha";

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			PrettyPrint.out("suixd","Needs a port number argument and a max user number argument");
		}
		else
		{
			try
			{
				server.startServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
			catch (NumberFormatException err)
			{
				PrettyPrint.out("suixd","A valid port number and max user number is required");
			}
		}
	}


}
