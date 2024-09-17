# Task 1

## Purpose

Simple multithreaded communication layer library

## Requirements

[JDK 17 or greater](https://www.oracle.com/java/technologies/downloads/)

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

#### Constructor

```java
Broker(String name)
```
Param :
    - name (String) : Name of the Broker

#### Methods

```java
Channel accept(int port)
```
Description : Listening for connection demands through port number port
Param :
    - port (integer) : Port number where the broker is listening on
Return : Channel object representing the communication pipe with the client

```java
Channel connect(String name, int port)
```
Description : Connect the broker to the desired broker identified by the values name/port
Param :
    - name (String) : Name of the remote Broker
    - port (integer) : Port number of the remote Broker
Return : Channel object representing the communication pipe with the remote Broker

### Channel

#### Methods

```java
int read(bytes[] bytes, int offset, int length)
```
Description : Read and store bytes into bytes with starting offset and message length
Param :
    - bytes (byte[]) : Byte buffer where the message content will be stored
    - offset (integer) : Store the message in the byte buffer starting at the position specified by the offset
    - length (integer) : Number of bytes to store inside the byte buffer
Return : Message length or -1 for channel error

```java
int write(bytes[] bytes, int offset, int length)
```
Description : Write bytes for byte array bytes with starting offset and message length
Param :
    - bytes (byte[]) : Byte buffer with the message content
    - offset (integer) : Start to send the message at the position specified by the offset
    - length (integer) : Number of bytes to send from the byte buffer
Return : Number of bytes sent or -1 for channel error

```java
void disconnect()
```
Description : Stop communication channel with the remote Channel

```java
boolean disconnected()
```
Description : Stop communication channel with the remote Channel
Return : True if the channel is down. Otherwise false

### Task

#### Constructor

```java
Task(Broker b, Runnable r)
```
Param :
    - b (Broker) : The broker managed by the Task
    - r (Runnable) : A runnable method defining the Task behavior for Broker management

```java
static Broker getBroker()
```
Description : static method to find the broker linked to the current thread (Thread.currentThread())
Return : The Broker object linked to the current Thread