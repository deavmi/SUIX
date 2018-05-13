import java.io.IOException;
import java.net.Socket;

//Library for holding all the client commands and shit {WIP}

public class libsuix
{

  //OutputStream for the socket
  public static OutputStream outStream;

  //InputStream for the socket
  public static InputStream inStream;


  //Get the current username.
  //Returns the username else `null` if some really bad shit happened.
	public static String getUsername()
	{
		IO.sendCommand(outStream, "GET_USERNAME");
		String username = IO.readCommand(inStream);
		return username;
	}

  //Set the username
  public static void setUsername(String username)
  {
    IO.sendCommand(outStream,"SET_USERNAME");
    IO.sendCommand(outStream,username);
  }

  //Send the given message `message`.
  //Returns "MESSAGE_SENT" on successful sending of message.
  //"MESSAGE_SEND_FAILED" if not successful.
  //And if some really bad shit happened then `null`.
  public static String sendMessage(String message)
  {
    IO.sendCommand(outStream,"SEND_MESSAGE");
    IO.sendCommand(outStream,message);
    String errorReturn = IO.readCommand(inStream);
    return errorReturn;
  }



}
