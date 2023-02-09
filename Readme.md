# Python to Java and vice versa POC

First order of business should be to get Redis up and running via a Docker container.
1. Install Docker  

2. Run this command on the terminal:
```
docker run -p 6379:6379 -d redis/redis-stack:latest
```
3. Check whether the container is up:
```
docker ps
```
You should see something similar: (It's alright if container ID and container name is different)
```
CONTAINER ID   IMAGE                                              COMMAND                  CREATED        STATUS        PORTS                              NAMES
001281377d56   redis/redis-stack:latest                           "/entrypoint.sh"         17 hours ago   Up 17 hours   0.0.0.0:6379->6379/tcp, 8001/tcp   cool_boyd
```
### Java Client
The Java client uses vanilla HTTP REST methods 
provided in the box with the JDK. To use it,
simply compile it and start following instructions 
on the command line.

#### Endpoints exposed:
- [host:port]/chat  
Request Body Sample:  


### Python Server
The python server uses Flask to serve HTTP RESTful requests.
It uses a WSGI server library to serve production-grade requests.
The Python server relies on Redis to store and retrieve clients' 
IP addresses against their identifiers.

#### Endpoints exposed:
- [host:port]/chat-with-client  
  Request Body Sample:
```json
{
  "clientId": "123",
  "text": "heyy"
}
```

- [host:port]/chat-with-server  
-   Request Body Sample:
```json
{
  "clientId": "123",
  "text": "This is a sample message",
  "host": "127.0.0.1"
}
```