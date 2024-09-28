# Task 2

## Purpose

Simple multithreaded message queue framework to send and receive messages

## Requirements

[JDK 17 or greater](https://www.oracle.com/java/technologies/downloads/)

### Server side

Within your server, start a Broker with a specific name.
Give the broker to a QueueBroker for connection handling.
Listen for connections with the accept method on a specific port number.
For every connection demands, open a channel with the client.
Use this channel to start a message queue and handle message-based communication.
The broker name is unique and the port number is specific for every broker.

### Client side

For clients, start a Broker with a specific name and give it to a QueueBroker.
Use the connect method with the server name and server port number to open a connection.
The connect method will return the message queue based on a communication channel with the server.
Use the message queue to receive and send messages.

### Concurrency

A task is a thread managing 1 or several brokers/QueueBrokers.
Each task is linked to a specific channel/message queue from a broker.
The task is responsible of reading/writing messages from the communication framework.

### QueueBroker

#### Constructor

```java
QueueBroker(Broker broker);
```
Param :
    - broker (Broker) : Broker instance for communication management

#### Methods

```java
String name();
```
Description : Get the name of the broker
Return : String representing the broker name

```java
MessageQueue accept(int port);
```
Description : Listening for connection demands through port number port
Param :
    - port (integer) : Port number where the QueueBroker is listening on
Return : MessageQueue object representing the communication pipe with the client

```java
MessageQueue connect(String name, int port);
```
Description : Connect the QueueBroker to the desired remote QueueBroker identified by the values name/port
Param :
    - name (String) : Name of the remote QueueBroker
    - port (integer) : Port number of the remote QueueBroker
Return : MessageQueue object representing the communication pipe with the remote QueueBroker

### MessageQueue

#### Methods

```java
byte[] receive();
```
Description : Read message sent through the message queue
Return : byte array representing the message received

```java
void send(byte[] bytes, int offset, int length);
```
Description : Send bytes from byte array with starting offset and message length to the remote broker
Param :
    - bytes (byte[]) : Byte buffer with the message content
    - offset (integer) : Start to send the message at the position specified by the offset
    - length (integer) : Number of bytes to send from the byte buffer

```java
void close();
```
Description : Stop communication pipe

```java
boolean closed();
```
Description : Get the message queue status
Return : True if the message queue is close. Otherwise false

### Task

#### Constructor

```java
Task(Broker b, Runnable r);
```
Param :
    - b (Broker) : The broker managed by the Task
    - r (Runnable) : A runnable method defining the Task behavior for Broker management

```java
Task(QueueBroker b, Runnable r);
```
Param :
    - b (QueueBroker) : The QueueBroker managed by the Task
    - r (Runnable) : A runnable method defining the Task behavior for QueueBroker management

```java
static Broker getBroker()
```
Description : static method to find the broker linked to the current thread
Return : The Broker object linked to the current Thread

```java
QueueBroker getQueueBroker();
```
Description : static method to find the QueueBroker linked to the current thread
Return : The QueueBroker object linked to the current Thread

```java
static Task getTask();
```
Description : static method to get the current running Task
Return : The current thread casted into a Task