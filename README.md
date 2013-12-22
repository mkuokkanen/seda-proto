## Purpose

Simple client-server application prototype. 
Build with asynchronous messaging and [staged event-driven architecture (SEDA)](http://en.wikipedia.org/wiki/Staged_event-driven_architecture) principles.
It combines few technologies I have researched recently,
like html5, websockets, Disruptor, and so on. 
Purpose is to help me remember later what I have learned.

## Running instructions

### Program

1. Go to root folder
* type "mvn clean install exec:exec"
* open web browser at localhost:8080
* click "Connect"
* write some values to push fields and click "Send"

### Source code analysis

1. mvn clean site
* open target/site/index.html in browser
* Check Project Reports from bottom left link div.

## Metadata  

### Some requirements

* Client-server architechture, multiple clients and one server, with some simple functionality, like storing key-value pairs.
* Server must handle requests with FIFO principle, request order must not change.
* Clients must get information of changed state on server at the same time.
* Provide believable try for good performance.

### Technology selections

#### Protocol

*WebSockets* as protocol between client and server. 
It makes asyncronous message delivery possible
and works with modern browsers, 
which is nice.
 * Alt: [HTTP polling with long timeout](http://stackoverflow.com/questions/1406580/jquery-ajax-polling-for-json-response-handling-based-on-ajax-result-or-json-con)? Probably not as efficient. Kind of kludge.
 * Alt: [ZeroMQ](http://zeromq.org)? Interesting, but hard to make work in web browsers.
 * Alt: [RabbitMQ](http://www.rabbitmq.com)? Hard to make work in web browsers, needs dedicated server app.
 * Alt: [SPDY](http://en.wikipedia.org/wiki/SPDY)? Not enough knowledge to say anything.


#### Message format

*JSON* as message format between client and server. Works well enough, especially with web browsers.
* Alt: XML? Could work. Bigger msg size than JSON and unwelcome complexity with namespaces etc.
* Alt: [Google Protobuf](http://code.google.com/p/protobuf/)? Interesting, but hard to make work in web browsers.
* Alt: [Apache Thrift](http://thrift.apache.org/tutorial/js/)? Might work, but wants to mess with protocol layer too, documentation lacking.
* Alt: [Apache avro](http://avro.apache.org)? Does anybody use this?

#### Client

*HTML5* and *Javascript* with some selected libraries. Easy and fast to build PoC. 
* Alt: Java Swing or JavaFX Client? Binary blobs are so old school. OTOH, would make other protocols and message formats possible.
* [JQuery](http://jquery.com) for some random Javascript tasks.
 * Alt: AngularJS, ... no need in simple project.
 * Alt: Google Dart? Interesting, but not relevant.
* [Twitter Bootstrap 2](http://getbootstrap.com/2.3.2/) for UI elements
 * Alt: Bootstrap 3? Might upgrade to it later
 
#### Server

Standalone *Java* code with selected libraries, build with *Maven* 
* [Embedded Jetty 9](http://www.eclipse.org/jetty/) for WebSocket implementation
 * Alt?: Even Jetty implementation seems to be still work in progress, with major rewrites even inside version 9.
* [Disruptor 3](http://lmax-exchange.github.io/disruptor/) for implementing SEDA (queues, stages)
 * Alt: Maybe multiple java.util.concurrent.ArrayBlockingQueue objects or some other Java List objects with self-managed threads.
 * [The LMAX Architecture - Martin Fowler](http://martinfowler.com/articles/lmax.html)
* [json-simple](http://code.google.com/p/json-simple/) for handling json
 * [comparison blog post](http://www.rojotek.com/blog/2009/05/07/a-review-of-5-java-json-libraries/)
 * Alt: [minimal-json](http://eclipsesource.com/blogs/2013/04/18/minimal-json-parser-for-java/) seems nice, but no maven artifact to be found. [https://github.com/ralfstx/minimal-json](blog)
* [Javolution](http://javolution.org) for storing data in preallocated ByteBuffers to minimize GC
 * idea is from this [ticketing demo](https://github.com/mikeb01/ticketing)
 * Alt: Store data outside heap [blog](http://vanillajava.blogspot.fi/2013/07/openhft-java-lang-project.html)
* [Logback](http://logback.qos.ch) and [SLF4j](http://www.slf4j.org) for logging purposes. 
 * Alt: Log4j 1.x, old school by now
 * Alt: [Log4j 2.x](http://logging.apache.org/log4j/2.x/), new interesting stuff, but too little too late? But! "Asynchronous Loggers based on the LMAX Disruptor library".
