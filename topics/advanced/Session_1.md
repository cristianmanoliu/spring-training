# Introduction
## What is Spring?
Spring is an open source framework that was created to address the complexity of enterprise application development.
It empowered old Java objects (POJOs) with powers previously only available using EJB and other enterprise Java specifications.
Over time, EJB started offering a simple POJO oriented programming model of its own. Now EJB employs ideas such as DI and AOP< arguably inspired by the success of Spring.

Classic EJB example:
http://www-inf.int-evry.fr/cours/CORBA_gb/Site/course/lectureNotes/EJB-example-hello.pdf

To back up its attack on Java complexity, Spring employs four key strategies:
1. Lightweight and minimally invasive development with POJOs
Spring avoids (as much as possible) littering your application code with its API. Spring almost never forces you to implement Spring-specific interface or extends a Spring-specific class.
2. Loose coupling through DI and interface orientation
Traditionally, each object is responsible for obtaining its own references to the objects it collaborates with (its dependencies). This can lead to highly coupled and hard-to-test code.
3. Declarative programming through aspects
AOP is a technique that promotes separation of concerns in a software system.
4. Eliminating boilerplate code with aspects and templates
Spring seeks to eliminate boilerplate code by encapsulating it in templates. Spring’s JdbcTemplate makes it possible to perform database operations without all the ceremony required by traditionally JDBC.

How does spring handle DI internally? (@Autowired, @Qualifier) – Annotation post processor

# Hands-on exercise
## Requirements
1. Define a MessagingService interface with a single method sendMessage(String message).
2. Define a property key/value in application.properties with following possible values (e.g. messagingSystem=JMS; messagingSystem=KAFKA).
3. Define a custom service that has an reference to a KafkaMessagingService or JmsMessagingService based on above property value
- Bean has to have the scope prototype.
4. Exercise must be implemented using TDD approach

# Take-away (Recap)
## Spring
In a nutshell: Spring is a lightweight dependency injection and aspect oriented container and framework.
- Lightweight: a couple of MB
- DI
Dependency: attribute, method argument
Injection: Spring calling a setter method, a constructor with some arguments, etc.
Metadata (@Autowired, @Qualifer) describes the actual classes and services that we want, Spring Container wires everything together and manages the lifecycle (create the bean – we rarely instantiate objects).
- AOP: separating application business logic from system services (auditing, transaction management) – also known as cross cutting concerns
Application objects should not be responsible (or even aware) of system concerns, such as logging and transactional support.
- Container: Manages the lifecycle and configuration of application objects. (spring-context, spring-core, spring-beans)
- Framework: Configure and compose complex applications from simpler components. (transaction management, persistence framework integration, etc.) (spring-jms, spring-tx, spring-jdbc, …)

## Key annotations
@Configuration – indicates that a class declares one or more @Bean methods that may be processed by the Spring container to generate bean definitions and service requests for those beans at runtime.
@Component – Indicates that annotated class is a spring-component. Such classes are considered candidates for auto-detection when using annotated-based configuration and class path scanning.
-	@Repository – it makes the unchecked exceptions (Thrown from DAO methods) eligible for translation into Spring DataAccessException.
-	@Service – no additional behaviour over @Component
-	@Controller – makes it possible to use @RequestMapping at method level as well.
@Autowired – Marks a constructor, field, setter method as to be autowired by Spring Container.
@Qualifier – Used together with Autowired when multiple beans of a type exists in the context, and you want to specify exactly which one you want to be injected by Spring container.
@Bean – Indicates that a method produces a bean that needs to be managed by Spring container.
@PropertySource – To be used in conjunction with @Configuration classes. Convenient and declarative mechanism for adding a PropertySource to Spring’s Environment.
-	PropertySource: abstract class representing a source of name/value property pairs.
-	Environment: interface representing the environment in which the current application is running.
@ComponentScan – To be used with Configuration in order to define which packages should be scanned.
@Value – Annotation at field level or method/constructor parameter, that indicates a default value expression for that field.
@Scope – Used in conjunction with Component, defining the lifecycle of an instance: singleton, prototype.