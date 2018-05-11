import java.io.IOException;
import java.net.Socket;

public class libsuix
{
  //Connect to the remote host and return a connection object or `null` if failed
    public static Client newConnection(String IPAddress, int port)
    {
      Connection connection = null;
        try
        {
          Socket socket = new Socket(IPAddress, port);
          Connection connection = new Connnection(socket);
        }
        catch (IOException err)
        {
          System.out.println("Error with connecting to server");
        }
        return connnection;
    }
}
