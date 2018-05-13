//Represents a message to be sent to a channel

public class Message
{
  //The actual message
  private String messageText;

  //The channel to which the message is to be sent to
  private Channel channel;

  public Message(Channel channel, String messageText)
  {
    this.messageText = messageText;
    this.channel = channel;
  }
}
