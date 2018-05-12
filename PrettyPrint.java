

public class PrettyPrint
{

	public static String getPrettyString(String name, String message)
	{
		return "(" + name + ") " + message;
	}

	public static void out(String name, String message)
	{
		System.out.println(getPrettyString(name, message));
	}

	//WIP: use me
	public static String userfy(String username)
	{
		return "@"+username;
	}

	//WIP: use me
	public static String channelfy(String channelName)
	{
		return "#"+channelName;
	}

}
