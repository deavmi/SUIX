# SUIX

> **SUIX** is a piece of shit written in the world's most socially awkward language ~~ur mom gay~~ Java!
James Gosling circa 1999

## Protocol specification

The server reads bytes until the byte, in decimal `10`, is found. All preceding bytes are converted to their respective characters via the ASCII character codes and are placed into a String that represents the command sent from the client.

## Server implementation

The chat server a.k.a. daemon is named `suixd` and its code cna be found in the `server` directory.

## Client

The client is named `suixy` and can be found under the `client` directory.
