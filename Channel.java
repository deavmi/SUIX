//Represents a channel

public class Channel
{

  //The channel's name
  private String channelName;

  //The channel's description
  private String channelDescription;

  //Constructor for a new Channel object
  public Channel(String channelName, String channelDescription)
  {
    this.channelName = channelName;
    this.channelDescription = channelDescription;
  }

  //Returns the name of the channel
  public String getChannelName()
  {
    return channelName;
  }

  //Returns the channel's description
  public String getChannelDescription()
  {
    return channelDescription;
  }

  //Override the toString method with the channel's name
  public String toString()
  {
    return channelName;
  }
}
