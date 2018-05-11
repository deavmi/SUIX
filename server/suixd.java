import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class suixd
{



	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			System.out.println("Needs a port number argument and a max user number argument");
		}
		else
		{
			try
			{
				server.startServer(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
			}
			catch (NumberFormatException err)
			{
				System.out.println("A valid port number and max user number is required");
			}
		}
	}


}
