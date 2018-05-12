import java.io.IOException;
import java.net.Socket;

public class libsuix
{
  //Connect to the remote host and return a connection object or `null` if failed
    public static ClientConnection newConnection(String IPAddress, int port)
    {
      ClientConnection connection = null;
        try
        {
          Socket socket = new Socket(IPAddress, port);
          connection = new ClientConnection(socket);
        }
        catch (IOException err)
        {
          System.out.println("Error with connecting to server");
        }
        return connection;
    }
}
