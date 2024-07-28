# BOOKING-SYSTEM-JAVA-RMI
This repository contains a Java RMI-based booking system, which demonstrates the use of Java Remote Method Invocation (RMI) for distributed computing. This system includes a server that handles booking requests and a client that interacts with the server.

## Contents
- "HRClient.java": The client application that connects to the RMI server to make booking requests.
- "HRImpl.java": The implementation of the booking service that runs on the server.
- "HRInterface.java": The remote interface that defines the methods that can be invoked remotely by clients.
- "HRServer.java": The server application that registers the booking service and listens for client requests.
- "NotificationListener.java": An interface for listening to notifications, possibly used for callback purposes.

## Requirements
- Java Development Kit (JDK) 8 or higher
- A working network environment where the client and server can communicate

## Setup and Running the Program
Follow these steps to set up and run the booking system:

**1. Compile all Java files:**

```console
javac *.java
```

**2. Generate stub and skeleton files for the remote object:**

```console
rmic HRImpl
```

Leave this terminal window open.

**3. Start the RMI registry:**

```console
rmiregistry 2501
```

**4. Run the server:**

In a new terminal window, start the server by running:

```console
java HRServer
```

This will instantiate the server object and bind it to the RMI registry, making it available for clients to connect.

**5. Run the Client**

Open a new terminal for the client and use the following commands as per your requirement:

**- List all available rooms:**

```console
java HRClient list <hostname>
```

Replace 'hostname' with the server's hostname or IP address.

**- Book rooms:**

```console
java HRClient book <hostname> <roomType> <number> <username>
```

- 'hostname': The server's hostname or IP address.
- 'roomType': The type of room you want to book.
- 'number': The number of rooms you want to book.
- 'username': Your username.

If the booking fails due to insufficient rooms, the client will ask if you want to register for notifications when rooms become available.

Example:

```console
java HRClient book localhost Single 2 john_doe
```

**- View all guests:**

```console
java HRClient guests <hostname>
```

Replace 'hostname' with the server's hostname or IP address.

**- Cancel a booking:**

```console
java HRClient cancel <hostname> <roomType> <number> <username>
```

- 'hostname': The server's hostname or IP address.
- 'roomType': The type of room you want to cancel.
- 'number': The number of rooms you want to cancel.
- 'username': Your username.

Example:

```console
java HRClient cancel localhost Single 2 john_doe
```

## Notifications Feature
When a booking attempt fails due to insufficient available rooms, the client is prompted to register for notifications. If the user chooses to register, they will be notified when the requested room type becomes available.

For example, if user Lydia tries to book 2 rooms of type B but only one is available, they will be asked:

```console
Not enough rooms
Register for notifications when a room is available? (yes/no)
```

If Lydia responds with "yes", she will remain registered until a room becomes available or until a specified time duration elapses.

## How It Works
- HRInterface.java: This interface defines the remote methods that the client can call on the server.
- HRImpl.java: This class implements the HRInterface and contains the business logic for handling bookings.
- HRServer.java: This class sets up the RMI registry, creates an instance of HRImpl, and binds it to the registry.
- HRClient.java: This class looks up the remote service in the RMI registry and invokes its methods.
- NotificationListener.java: This interface may be used for callback purposes, allowing the server to notify clients of certain events.
