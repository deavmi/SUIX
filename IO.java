import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IO
{

	// Encode a message with a trailing linefeed as our delimiter
	public static byte[] encode(String message)
	{
		byte[] characterBytes = new byte[message.length() + 1];

		for (int i = 0; i < message.length(); i++)
		{
			characterBytes[i] = (byte) message.charAt(i);
		}

		// Ending character (in this case a linefeed)
		characterBytes[message.length()] = 10;

		return characterBytes;
	}

	//Send the bytes specified by `bytes` to the outputStream given
	private static boolean sendCommandInternal(OutputStream outStream, byte[] bytes)
	{
		try
		{
			//Send each byte at a time
			for(byte theByte: bytes)
			{
				outStream.write(theByte);
			}
		}
		catch(IOException err)
		{
			return false;
		}
		return true;
	}

	public static boolean sendCommand(OutputStream outStream, String message)
	{
		return sendCommandInternal(outStream, encode(message));
	}

	//Get the command sent from the host (returns `null` on error (when an IOException occurs)
	public static String readCommand(InputStream inStream)
	{
		String message = "";
		try
		{
			boolean awaitingLineFeed = true;
			while(awaitingLineFeed)
			{
				// Read from client
				int byteRep = inStream.read();
	
				//check -1
				if(byteRep == -1) //look into me @assigned to deavmi
				{
					//error here
				}
				//Read an actual byte
				else			
				{
					// The actual byte
					byte theByte = (byte)byteRep;
	
					//Linefeed flush out
					if(theByte == 10)
					{
						//Command recieved
						PrettyPrint.out("IO","Data received \"" + message+"\"");
	
						//We are now done and can return the message
						awaitingLineFeed = false;
					}
					//If not a linefeed then append to the `message` String
					else
					{
						message = message + (char)(  theByte );
					}
	
				}
			}
		}
		catch(IOException err)
		{
			//On error set `message` to `null`
			message = null;
		}

		return message;
	}

}
