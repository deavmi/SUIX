import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server
{

  //All connections
  public static DynamicArray<ServerConnection> connections;

  public static void startServer(int port, int max_user)
  {
	PrettyPrint.out("server", "Starting server on port " + port + " ...");

    connections = new DynamicArray<ServerConnection>(0);

    try
    {
      // The server socket
      ServerSocket servSock = new ServerSocket(port);

      while (true)
      {
        PrettyPrint.out("server", "Waiting for a connection...");

        // Incoming client socket
        Socket clientSock = servSock.accept();

        PrettyPrint.out("server", "Connection received, setting up data structures...");

        //Create a new `ServerConnection` which represents a connection
        ServerConnection connection = new ServerConnection(clientSock);

        connections.append(connection);

        PrettyPrint.out("server", "Connection object created.");
      }

    }
    catch (IOException err)
    {
      PrettyPrint.out("server", "There was an error with the server socket.");

    }
  }

}
