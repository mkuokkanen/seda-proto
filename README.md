seda-proto
==========

Simple application prototype to store key-value pairs. 
Build with staged event-driven architecture (SEDA) principles.
It combines few technologies I have researched recently, purpose is to help me remember later on what I have learned.

Key features:
* Client-Server architechture, multiple clients and one (logical) server
* Client must work in web browser for universal access
 * Java clients etc binary blobs are so old school 
* Communication between client and server must happen asynchronously and efficiently, with data flowing in both directions
 * Request/response is so old school
* Server must handle operations (events) in strongly serialized way.
 * Traditional web services that scale at the servlet level and serialize in db transactions are so old school
* Build for performance
 * As serialization requirement forbids horizontal parallelisation, target vertical parallel processing, aka SEDA
 * Try to minimize Garbage Collection
* Build for fail safety (High Availability)
 * Probably out of scope for this little demo
* Documentation at level that is relevant to me
* Easy runnability, so that I can return to this later and still understand how to make this work
* Some automated tests (unit, performance) would be nice
* Switchable technologies to compare performance would be nice, but probably too hard in this scope

All still work in progress.

Specifics
----------

### Client

* HTML5 with JavaScript
 * Served as static pages, no need for dynamic page creation
 * Google Dart would be interesting, but JavaScript provides enough challenge
 * JQuery might make some parts easier
* Twitter Bootstrap 2.3.x for UI elements
 * Bootstrap 3 RC1 is available right now, might upgrade to it later
* WebSockets for sending and receiving data
 * What alternatives there would be? E.g. ZeroMQ and RabbitMQ would be hard to make work with web browsers.
 * Would SPDY be option? Needs investigation.
 
### Server

* Java code, build with Maven
* Embedded Jetty 9 for WebSocket implementation
 * Alternatives? Even Jetty seems to be still work in progress, with major rewrites even inside version 9.
* Disruptor 3 for implementing SEDA (queues, stages)
 * Alternative might be multiple java.util.concurrent.ArrayBlockingQueue objects with self-managed threads.
* JSON for moving data between client and server
 * Multiple json libraries available, still sorting this out
* Javolution for storing data
 * Minimize GC by preallocating needed ByteBuffers.
 * Alternative might be storing data outside Java managed heap.
* Logback for logging purposes. 


Running instructions
-------------

Still work in progress.
