import java.rmi.Remote; // Import the Remote interface from the java.rmi package.

// Import the RemoteException class from the java.rmi package. This is necessary because the notify method in the NotificationListener interface can throw a RemoteException.
import java.rmi.RemoteException;


// This interface extends the Remote interface, which means it can be used to create remote objects.
public interface NotificationListener extends Remote
{
    // This method is used to notify the client when a room becomes available.
    void notify(String message) throws RemoteException;
}