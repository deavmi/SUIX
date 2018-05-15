public class DynamicArray<Type>
{

  //All the data
  Object data;

  private void out(String message)
  {
    kak.out("DynamicArray", message)
  }

  public DynamicArray(int size)
  {
    //Create a new array of the given size `size`
    data = new Object[size];

    //Debugging
    out("Dynamic array of size \"" + size + "\" created.");
  }

  private void expandAndAppend(Type element)
  {
    //debugging
    out("Expansion occurring from size \"" + data.length + "\" top size \"" + (data.length+1) + "\"...");

    //New array of size n+1 (where n is `data`'s size')
    Object[] newData = new Object[data.length+1];

    //Copy all the data from the array referenced by `data` to the
    //array referenced by `newData`
    for(int i = 0; i < data.length; i++)
    {
      newData[i] = data[i];
    }

    //Append the new element
    newData[data.length] = element;

    //Update the reference held by `data` to now point to the array refrenced by `newData`
    data = newData;

    //Debugging
    out("Expansion finshed.");
  }

  //Append a new element to the array
  public void append(Type element)
  {
    expandAndAppend(element);
  }

  //Get an element at index `index`
  public Type get(int index)
  {
    return (Type)data[index];
  }

  //Get an array of all the data
  public Type[] getArray()
  {
    return (Type[])data;
  }

  //Remove an element at the given index `index`
  public void remove(int index)
  {
    Object[] newData = new Object[data.length-1];

    //Index count
    int indexPointer = 0;

    //Iterate over the array and add all elemnets from `data` to `newData` besides the one at index `index`
    for(int i = 0; i < data.length; i++)
    {
      if(i == index)
      {
        //Do nothing
      }
      else
      {
        newData[indexPointer] = data[i];
        indexPointer++;
      }
    }

    //Update the reference held by `data` to point to a new object pointed to the object that is referenced by `newData`
    data = newData;
  }

}
