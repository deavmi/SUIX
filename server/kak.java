public class kak
{

	public static String getLekker(String name, String message)
	{
		return "(" + name + ") " + message;
	}

	public static void out(String name, String message)
	{
		System.out.println(getLekker(name, message));
	}

}
