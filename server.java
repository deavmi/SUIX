import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server
{

  //All connections
  public static DynamicArray<ServerConnection> connections;

  //Output with pretty print and this module's name
  private static void out(String message)
  {
    PrettyPrint.out("server", message)
  }

  public static void startServer(int port)
  {
	  out("Starting server on port " + port + " ...");

    connections = new DynamicArray<ServerConnection>(0);

    try
    {
      // The server socket
      ServerSocket servSock = new ServerSocket(port);

      while (true)
      {
        out("Waiting for a connection...");

        // Incoming client socket
        Socket clientSock = servSock.accept();

        out("Connection received, setting up data structures...");

        //Create a new `ServerConnection` which represents a connection
        ServerConnection connection = new ServerConnection(clientSock);

        //Append the `ServerConnection` object to the array
        connections.append(connection);

        out("Connection object created.");
      }

    }
    catch (IOException err)
    {
      out("There was an error with the server socket.");
    }
  }

}
