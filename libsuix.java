import java.io.IOException;
import java.net.Socket;

//Library for holding all the client commands and shit {WIP}

public class libsuix
{

  //Socket to the host server
  public static Socket connection;

  //Get the current username.
  //Returns the username else `null` if some really bad shit happened.
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

  //Send the given message `message`.
  //Returns "MESSAGE_SENT" on successful sending of message.
  //"MESSAGE_SEND_FAILED" if not successful.
  //And if some really bad shit happened then `null`.
  public static String sendMessage(String message)
  {
    IO.sendCommand(connection.getOutputStream(),"SEND_MESSAGE");
    IO.sendCommand(connection.getOutputStream(),message);
    String errorReturn = IO.readCommand(connection.getInputStream());
    return errorReturn;
  }



}
