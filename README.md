seda-proto
==========

Simple application prototype to store key-value pairs. 
Build with asynchronous messaging and staged event-driven architecture (SEDA) principles.
It combines few technologies I have researched recently, 
purpose is to help me remember later what I have learned.

Some requirements
------

* Client-server architechture, multiple clients and one server, with some simple functionality, like storing key-value pairs.
* Server must handle requests with FIFO principle, request order must not change.
* Clients must get information of changed state on server at the same time.
* Provide believable try for good performance.

Technology selections
----------

### Protocol

*WebSockets* as protocol between client and server. 
It makes asyncronous message delivery possible
and works with modern browsers, 
which is nice.
 * [HTTP polling with long timeout](http://stackoverflow.com/questions/1406580/jquery-ajax-polling-for-json-response-handling-based-on-ajax-result-or-json-con)? Probably not as efficient. Kind of kludge.
 * [ZeroMQ](http://zeromq.org)? Interesting, but hard to make work in web browsers.
 * [RabbitMQ](http://www.rabbitmq.com)? Hard to make work in web browsers, needs dedicated server app.
 * [SPDY](http://en.wikipedia.org/wiki/SPDY)? Not enough knowledge to say anything.


### Message format

*JSON* as message format between client and server. Works well enough, especially with web browsers.
* XML? Could work. Bigger msg size than JSON and unwelcome complexity with namespaces etc.
* [Google Protobuf](http://code.google.com/p/protobuf/)? Interesting, but hard to make work in web browsers.
* [Apache Thrift](http://thrift.apache.org/tutorial/js/)? Might work, but wants to mess with protocol layer too, documentation lacking.


### Client

*HTML5* and *Javascript* with some selected libraries. Easy and fast to build PoC. Java Swing or JavaFX client would also be possbile, but binary blobs are so old school. OTOH, would make other protocols and message formats possible.
* [JQuery](http://jquery.com) for some random Javascript tasks.
 * Alternatives: AngularJS, ... no need in simple project.
 * Google Dart? Interesting, but not relevant.
* [Twitter Bootstrap 2](http://getbootstrap.com/2.3.2/) for UI elements
 * Bootstrap 3? Might upgrade to it later
 
### Server

Standalone *Java* code with selected libraries, build with *Maven* 
* [Embedded Jetty 9](http://www.eclipse.org/jetty/) for WebSocket implementation
 * Alternatives? Even Jetty implementation seems to be still work in progress, with major rewrites even inside version 9.
* [Disruptor 3](http://lmax-exchange.github.io/disruptor/) for implementing SEDA (queues, stages)
 * Alternative might be multiple java.util.concurrent.ArrayBlockingQueue objects or some other Java List objects with self-managed threads.
 * [The LMAX Architecture - Martin Fowler](http://martinfowler.com/articles/lmax.html)
* [json-simple](http://code.google.com/p/json-simple/) for handling json
 * [comparison blog post](http://www.rojotek.com/blog/2009/05/07/a-review-of-5-java-json-libraries/)
 * [minimal-json](http://eclipsesource.com/blogs/2013/04/18/minimal-json-parser-for-java/) seems nice, but no maven artifact to be found. [https://github.com/ralfstx/minimal-json](blog)
* [Javolution](http://javolution.org) for storing data in preallocated ByteBuffers to minimize GC
 * Alt: Store data outside heap [blog](http://vanillajava.blogspot.fi/2013/07/openhft-java-lang-project.html)
* [Logback](http://logback.qos.ch) and [SLF4j](http://www.slf4j.org) for logging purposes. 
 * Alt: Log4j 1.x, old school by now
 * Alt: [Log4j 2.x](http://logging.apache.org/log4j/2.x/), new interesting stuff, but too little too late? But! "Asynchronous Loggers based on the LMAX Disruptor library".


Running instructions
-------------

1. Go to root folder
* type "mvn exec:exec"
* open web browser at localhost:8080
* click "Connect"
* write some values to push fields and click "Send"
