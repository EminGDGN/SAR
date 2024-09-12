# Task 1

## Purpose

Simple communication layer between tasks within the same process

### Server side

Within your server, start a Broker with a specific name.
Listen for connections with the accept method on a specific port number.
For every connection demands, open a channel with the client.
The broker name is unique and the port number is specific for every broker.

### Client side

For clients, start a Broker with a specific name.
Use the connect method with the server name and server port number to open a connection.
The connect method will return the communication channel with the server.
Use the channel to read/write messages.

### Concurrency

A task is a thread managing 1 or several brokers.
Each task is linked to a specific channel from a broker.
The task is responsible of reading/writing messaging with the channel.

### Broker

```java
Broker(String name); // Broker constructor
Channel accept(int port); // Listening for connection demands through port number port
Channel connect(String name, int port) // Connect the broker to the desired broker name on distant port port
```

### Channel

```java
int read(bytes[] bytes, int offset, int length); // Read and store bytes into bytes with starting offset and message length. Return message length or -1 for channel error
int write(bytes[] bytes, int offset, int length); // Write bytes for byte array bytes with starting offset and message length. Return message length or -1 for channel error
void disconnect(); // Stop communication channel
boolean disconnected(); // Boolean to see if channel is still operational.
```

### Task

```java
Task(Broker b, Runnable r); // Task constructor. Give the linked broker and the method to run.
static Broker getBroker(); // Return the broker of the current Thread.
```