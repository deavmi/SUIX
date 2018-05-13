import java.io.OutputStream;
import java.io.InputStream;

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

  //Get the current channel.
  //Returns the username else `null` if some really bad shit happened.
	public static String getChannel()
	{
		IO.sendCommand(outStream, "GET_CHANNEL");
		String channel = IO.readCommand(inStream);
		return channel;
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
