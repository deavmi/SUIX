//Represents a message to be sent to a channel

public class Message
{
  //The actual message
  private String messageText;

  //The channel to which the message is to be sent to
  private Channel channel;

  //Constructor for a new Message object
  public Message(Channel channel, String messageText)
  {
    this.channel = channel;
    this.messageText = messageText;
  }

  //Returns the channel this message is to be sent to
  public Channel getChannel()
  {
    return channel;
  }
}
