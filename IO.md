`IO` Library
============

As requested by Skippy I have created a document on how to use the `IO` library.


## Reading a `String` from a given InputStream

To read a `String` from a given `InputStream` (say now `xIn`) use the following code:

````java
String response = IO.readCommand(xIn);
````
## Writing a `String` to a given OutputStream

To write a `String` (say now `xString`) to a given `OutputStream` (say now `xOut`) use the following code:

````java
IO.sendCommand(xOut, xString)
````
