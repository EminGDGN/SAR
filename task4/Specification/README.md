# Task 4

## Purpose

Simple event based message queue framework to send and receive messages

## Requirements

[JDK 17 or greater](https://www.oracle.com/java/technologies/downloads/)

## Overview

Communication framework based on events. Using a full-duplex message queue, this local communication layer permits connection between brokers to open a message queue channel. From this channel, you can send and receive messages. Because it is fully event based, the framework is only using non-blocking methods. Provide a listener to your message queue to define a behavior when receiving a message. Call send as you wish, messages are send 1 by 1 FIFO lossless.

### QueueBroker

#### Constructor

```java
QueueBroker(String name);
```
- Param :
    - name (String) : Name of the broker for communication management

#### Methods

```java
String name();
```
- Description : Get the name of the broker
- Return : String representing the broker name

```java
public boolean bind(int port, AcceptListener listener);
```
- Description : Listening for connection demands through port number port
- Param :
    - port (integer) : Port number where the QueueBroker is listening on
    - listener (AcceptListener) : The callback method to trigger when an accept is done
- Return : A boolean to confirm or not that the port asked is bind

```java
public boolean unbind(int port);
```
- Description : Remove the port binding for the queue broker
- Param :
    - port (integer) : Port number the QueueBroker is binded with
- Return : A boolean to confirm or not that the port asked is unbind

```java
public boolean connect(String name, int port, ConnnectListener listener);
```
- Description : Connect the QueueBroker to the desired remote QueueBroker identified by the values name/port
- Param :
    - name (String) : Name of the remote QueueBroker
    - port (integer) : Port number of the remote QueueBroker
    - listener (ConnectListener) : Callback method to trigger when the connect is done
- Return : A boolean to confirm or not that a connection with the remote Queuebroker is possible

### MessageQueue

#### Methods

```java
public boolean send(byte[] bytes);
public boolean send(byte[] bytes, int offset, int length);
```
- Description : Send bytes from byte array with starting offset and message length to the remote broker
- Param :
    - bytes (byte[]) : Byte buffer with the message content
    - offset (integer) : Start to send the message at the position specified by the offset
    - length (integer) : Number of bytes to send from the byte buffer
- Return : A boolean to confirm that the message can be send

```java
public void setListener(Listener l);
```
- Description : Set the message queue message listener
- Param :
    - listener(Listener) : Callback method to define message queue behavior

```java
void close();
```
- Description : Stop communication pipe

```java
boolean closed();
```
- Description : Get the message queue status
- Return : True if the message queue is close. Otherwise false

### Listener

#### Methods

```java
public void received(byte[] msg);
```
- Description : Method called by the message queue when a message has been received
- Param :
    - msg (byte[]) : the received message

```java
public void closed();
```
- Description : Method called by the message queue when it shutdowns successfully

### AcceptListener

#### Methods

```java
public void accepted(MessageQueue queue);
```
- Description : Method called by the queuebroker when a connection demand has been accepted
- Param :
    - queue (MessageQueue) : the created message queue with the remote QueueBroker

### ConnectListener

#### Methods

```java
public void connected(MessageQueue queue);
```
- Description : Method called by the queuebroker when the connection demand has been accepted
- Param :
    - queue (MessageQueue) : the created message queue with the remote QueueBroker

```java
public void refused();
```
- Description : Method called by the queuebroker when the connection demand has been refused
