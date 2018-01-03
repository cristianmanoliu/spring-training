# spring-training
## spring-training-basic
### Requirements
- Application will receive a single argument: "messageCount". 
E.g. MainApp 5

- Define a BusinessService class that have the following methods:
process(Message message) : void

- Define MessageRepository interface that has the following method:
store(Message message) : void

- Provide two implementations of MessageRepository: InMemoryStoreRepository, FileStoreRepository. Both implementations should just log the received message.
The concrete implementation of MessageRepository should be decided on application context start-up based on a value in the application properties file. 
(E.g. application.properties containing repositoryType=IN_MEMORY - will add InMemoryStoreRepository in Spring container)

- Define the following domain objects as Spring managed beans (Message and MessageId POJOs)
1. public class Message { private MessageId messageId; }
Domain model that is passed to the BusinessService. It contains just a MessageId instance.

2. public class MessageId {  private String id = UUID.randomUUID().toString(); }
Primary key for Message model. It contains just a id, which by default is initialized using UUID.

- Each Message should contain an unique MessageId instance.

- Based on messageCount argument, create <messageCount> Message domain objects, and send them to BusinessService for processing.
Hint: applicationContext.getBean