# Task 3

## Purpose

Simple thread/event mix message queue framework to send and receive messages

## Requirements

[JDK 17 or greater](https://www.oracle.com/java/technologies/downloads/)

### QueueBroker

#### Constructor

```java
QueueBroker(String name);
```
Param :
    - name (String) : Name of the broker for communication management

#### Methods

```java
String name();
```
Description : Get the name of the broker
Return : String representing the broker name

```java
public boolean bind(int port, AcceptListener listener);
```
Description : Listening for connection demands through port number port
Param :
    - port (integer) : Port number where the QueueBroker is listening on
    - listener (AcceptListener) : The callback method to trigger when an accept is done
Return : A boolean to confirm or not that the port asked is bind

```java
public boolean unbind(int port);
```
Description : Remove the port binding for the queue broker
Param :
    - port (integer) : Port number the QueueBroker is binded with
Return : A boolean to confirm or not that the port asked is unbind

```java
public boolean connect(String name, int port, ConnnectListener listener);
```
Description : Connect the QueueBroker to the desired remote QueueBroker identified by the values name/port
Param :
    - name (String) : Name of the remote QueueBroker
    - port (integer) : Port number of the remote QueueBroker
    - listener (ConnectListener) : Callback method to trigger when the connect is done
Return : A boolean to confirm or not that the QueueBroker is connected with the remote instance

### MessageQueue

#### Methods

```java
public boolean send(byte[] bytes);
public boolean send(byte[] bytes, int offset, int length);
```
Description : Send bytes from byte array with starting offset and message length to the remote broker
Param :
    - bytes (byte[]) : Byte buffer with the message content
    - offset (integer) : Start to send the message at the position specified by the offset
    - length (integer) : Number of bytes to send from the byte buffer
Return : A boolean to confirm that the message has been sent fully

```java
public void setListener(Listener l);
```
Description : Set the message queue message listener
Param :
    - listener(Listener) : Callback method to define message queue behavior

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
static Task getTask();
```
Description : static method to get the current running Task
Return : The current thread casted into a Task