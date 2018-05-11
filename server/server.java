import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server
{


public static Connection[] connections;

  public static void startServer(int port, int max_user)
  {
	PrettyPrint.out("server", "Starting server on port " + port + " ...");

    connections= new Connection[max_user];

    try
    {
      // The server socket
      ServerSocket servSock = new ServerSocket(port);

      int connectionCount = 0;
      while (true)
      {
        PrettyPrint.out("server", "Waiting for a connection...");

        // Incoming client socket
        Socket clientSock = servSock.accept();

        PrettyPrint.out("server", "Connection received, setting up data structures...");

        Connection connection = new Connection(clientSock);

        connections[connectionCount++] = connection;

        PrettyPrint.out("server", "Connection object created.");
      }

    }
    catch (IOException err)
    {
      PrettyPrint.out("server", "There was an error with the server socket.");

    }
  }

}
