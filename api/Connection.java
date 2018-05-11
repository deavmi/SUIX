public class Connection extends Thread
{

  private Socket socket;

    public Client(Socket socket)
    {
        this.socket = socket;
        start();
    }

public void run()
{

}
}
