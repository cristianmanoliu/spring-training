# What is JMS (Java Message Service)?

JMS is not a messaging system itself; it’s an abstraction of the interfaces and classes needed by messaging clients when communicating with messaging systems. 
In the same way that JDBC abstracts access to relational databases.

If a vendor provides a compliant service provider for JMS, the JMS API can be used to send and receive messages to that vendor. 
For example, you can use the same JMS API to send messages using SonicMQ that you would using IBM’s WebSphere MQ.

## Main actors in messaging

1. **Messaging system:** application-to-application messaging systems, when used in business systems, are generically referred to as enterprise messaging systems, or Message-Oriented Middleware (MOM). Enterprise messaging systems allow two or more applications to exchange information in the form of messages.

2. **Destinations:** in all modern enterprise messaging systems, applications exchange messages through virtual channels called destinations.
  When a message is sent, it’s addressed to a destination (i.e., queue or topic), not a specific application.

Two types of destinations:

2.1.  QUEUES (point to point)

P2P - One sender - One receiver (one message will only be picked up by a single receiver)
P2P - Request Reply (using JMSCorrelationID plus message selector)

2.2. TOPICS (publish-subscribe)

PUBSUB - one sender - multiple receiver. 

When someone publishes a message to a topic, all listeners (subscribers) receive a copy of that message.

##	The Advantages of Messaging

- The communication and integration of heterogeneous platforms is perhaps the most classic use case for messaging. 

Examples of these messaging systems include ActiveMQ (open source) and IBM WebSphere MQ (commercial). 

Both of these messaging systems support JMS, but they also expose a native API for use by non-Java messaging clients (such as C and C++). 

The key point here is that, depending on the vendor, it is possible to use JMS to communicate to non-Java or non-JMS messaging clients.

- System and application bottlenecks occur whenever you have a process that cannot keep up with the rate of requests made to that process. 

- Much in the same way that messaging reduces system bottlenecks, it can also be used to increase the overall scalability and throughput of a system, effectively reducing the response time as well. Scalability in messaging systems is achieved by introducing multiple message receivers that can process different messages concurrently.

- Client is coupled to server interface without a JMS. if server interface changes, cleint need to adapt as well (message oriented and decoupled)

- Client is coupled to server location as well. If network topology changes, client need to adapt as well (location indepenence)

- Client is coupled to server availability. If server is down, client is crippled.

  ##Main components in JMS

  ​

- Message
  Message Body types:
1. TextMessage - String
2. MapMessage: String, primitives
3. Bytes Message: Stream of uninterpreted bytes
4. StreamMessage: Stream of primitive values
5. ObjectMessage: Serializable object
6. Message: Noting. This message type is useful when a message body is not required.
- Header (JMSMessageID, JMSDestination, JMSTimestamp, JMSDeliveryMode, JMSCorrelationID, JmsReplyTo, JMSMessageId, etc)
  The message headers provide metadata describing who or what created the message, when it was created, how long its data is valid, etc.
  - Properties
    Message properties are additional headers that can be assigned to a message. They provide the application developer or JMS vendor with the ability to attach more information to a message.
- JMS Provider - messaging system
- JMS ConnectionFactory
  Set of connection configuration parameters.
- JMS Connection (tcp/ip socket between client/server)
  Client active connection to JMS Provider.
- JMS Session (can be seen as JDBC Transaction);
  A session provides a transactional context for the messaging.
  A session may be specified as transacted. 
  Each transacted session supports a single series of transactions. Each transaction groups a set of messages sends and set of messages received into an atomic unit of work.
  - Transacted: JMSContext.SESSION_TRANSACTED, JMSContext.CLIENT_ACKNOWLEDGE, JMSContext.AUTO_ACKNOWLEDGE and JMSContext.DUPS_OK_ACKNOWLEDGE.
    CLIENT_ACKNOWLEDGE: for every msg client is responsible to acknowledge the message(s).
    AUTO_ACKNOWLEDGE - acknowledge the message(s) instantly after send/receive.
    DUPS_OK_ACKNOWLEDGE - faster but there is a risk that the messaging system can send same message twice, or better said at least twice.
- Destination
- Message Consumer
- Message Producer
- Topic Subscriber
- Message Listener

Of these general interfaces, the ConnectionFactory and Destination must be obtained from the provider using JNDI (per the JMS specification).
Java Naming Directory Interface (JNDI) - Internet applications must use naming and directory services to access network resources. You can share information related to users, machines, remote applications, and other network services with these services. JNDI is an API that is a standard Java library that is used for accessing naming and directory services such as Lightweight Directory Access Protocol (LDAP), Domain Naming Service (DNS), and Remote Method Invocation (RMI). You can even access the local file system (directory structure) on your computer.
The factory is then placed in a publicly available administered object store, where you can access it by name using the Java Naming and Directory Interface (JNDI) API. This arrangement has several benefits:
- It allows the administrator to control the properties of client connections to the provider, ensuring that they are properly configured.
- It enables the administrator to tune performance and improve throughput by adjusting configuration settings even after an application has been deployed.
- By relying on the predefined connection factory to handle the configuration details, it helps keep client code provider-independent and thus more easily portable from one JMS provider to another.

# Spring JMS
##	About & Advantages
-  Avoid writing verbose and repetitive code.

-  Avoid being force to catch JMSException.

-  Avoid being forced to control connection, session lifecycle

  ##Main components
  - JmsTemplate - Spring’s answer to verbose and repetitive JMS code. JmsTemplate takes care of creating a connection, obtaining a session, and ultimately sending or receiving messages. This leaves you to focus your development efforts on constructing the message to send or processing the messages that are received.

  What’s more, JmsTemplate can handle any clumsy JMSException that may be thrown along the way. If a JMSException is thrown in the course of working with JmsTemplate, JmsTemplate will catch it and rethrow it as one of the unchecked subclasses of Spring’s own JmsException.
  In fairness to the JMS API, JMSException does come with a rich and descriptive set of subclasses that give you a better sense of what went wrong. Nevertheless, all of these subclasses of JMSException are checked exceptions and thus must be caught. Jms-Template will attend to that for you by catching those exceptions and rethrowing an appropriate unchecked subclass of JmsException.

  - SimpleMessageConverter - A simple message converter which is able to handle TextMessages, BytesMessages, MapMessages, and ObjectMessages. Used as default conversion strategy by JmsTemplate, for convertAndSend and receiveAndConvert operations.
  - DefaultMessageListenerContainer - This is a simple but nevertheless powerful form of message listener container. Message listener container variant that uses plain JMS client APIs, specifically a loop of MessageConsumer.receive() calls that also allow for transactional reception of messages (registering them with XA transactions).
  - MessageListener - A MessageListener object is used to receive asynchronously delivered messages. **async**
  - Connection Factory (Single connection factory; Caching connection factory)