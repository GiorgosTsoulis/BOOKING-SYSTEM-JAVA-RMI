// Import the Remote interface from the java.rmi package.
import java.rmi.*;

// This interface extends the Remote interface, which means it can be used to create remote objects.
public interface HRInterface extends Remote
{

    // This method is used to get the list of available rooms.
    String list() throws RemoteException;

    // This method is used to make a booking for a customer.
    String book(String type, int number, String name) throws RemoteException;

    // This method is used to get the list of guests who have made a booking.
    String guests() throws RemoteException;

    // This method is used to cancel a booking for a customer.
    String cancel(String type, int number, String name) throws RemoteException;

    // This method is used to register a customer for notifications when a room becomes available.
    void registerForNotification(NotificationListener client, String roomType) throws RemoteException;

    // This method is used to remove a customer from the notification list.
    void unregisterForCallback(NotificationListener client) throws RemoteException;
}