import java.io.IOException;
import java.net.Socket;

//Library for holding all the client commands and shit

public class libsuix
{

  //Socket to the host server
  public static Socket connection;

  //Get the current username
	public static String getUsername()
	{
		IO.sendCommand(connection.getOutputStream(), "GET_USERNAME");
		String username = IO.readCommand(connection.getInputStream());
		return username;
	}

  //Set the username
  public static void setUsername(String username)
  {
    IO.sendCommand(connection.getOutputStream(),"SET_USERNAME");
    IO.sendCommand(connection.getOutputStream(),username);
  }

}
