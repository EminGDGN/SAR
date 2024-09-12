# Task 1

## Purpose

Simple communication layer between tasks within the same process

### Server side

Within your server, start a Broker with a specific name.
Listen for connections with the accept method on a specific port number.
For every connection demands, open a channel with the client.

### Client side

For clients, start a Broker with a specific name.
Use the connect method with the server name and server port number to open a connection.
The connect method will return the communication channel with the server.
Use the channel to read/write messages.