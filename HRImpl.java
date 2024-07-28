// Import the necessary classes from the java.rmi and java.util packages.
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

// Create a class HRImpl that extends UnicastRemoteObject and implements HRInterface.
public class HRImpl extends UnicastRemoteObject implements HRInterface
{
    // Declare the variables that will be used for managing rooms and bookings.
    private String[] roomType = {"A", "B", "C", "D", "E"}; // Define the different types of rooms.
    private int[] roomCount = {40, 35, 25, 30, 20}; // Define the number of available rooms for each type.
    private int[] roomPrice = {75, 110, 120, 150, 200}; // Define the price of each room.
    private Map<String, List<String>> bookings = new HashMap<>(); // Create a Map to store customer bookings.
    private Map<String, List<NotificationListener>> notifications = new HashMap<>(); // Create a Map to store customers registered for notifications.

    // Create the constructor of the HRImpl class.
    public HRImpl() throws RemoteException
    {
        super();
    }

    // Implement the list method that returns the list of available rooms.
    public String list() throws RemoteException
    {
        String result = ""; // Create a variable result to store the results.
        for (int i = 0; i < roomType.length; i++) // Iterate over each room type.
        {
            result += roomCount[i] + " rooms of type " + roomType[i] + " - price: " + roomPrice[i] + " Euros per night\n"; // Add the number of rooms, room type, and price to the result.
        }
        return result; // Return the result.
    }

    // Implement the book method that makes a booking for a customer.
    public String book(String type, int number, String name) throws RemoteException
    {
        // Check if the room type exists in the roomType array.
        int index = -1;
        for (int i = 0; i < roomType.length; i++) // Iterate over each room type.
        {
            if (roomType[i].equals(type))
            {
                index = i; // If the room type exists in the roomType array, store its position in the index variable.
                break;
            }
        }

        // Check if the room type exists in the roomType array.
        if (index == -1) {
            return "Room type not found.";
        }

        // Check if there are enough rooms of the requested type available.
        if (number > roomCount[index])
        {
            return "Not enough rooms of type " + type + " available.";
        }

        roomCount[index] -= number; // Subtract the number of rooms booked by the customer from the total rooms of the requested type.
        List<String> userBookings = bookings.getOrDefault(name, new ArrayList<>()); // Retrieve the customer's bookings from the bookings Map.
        for (int i = 0; i < number; i++) // Iterate over each booking.
        {
            userBookings.add(type); // Add the room type to the customer's bookings.
        }
        bookings.put(name, userBookings); // Store the customer's bookings in the bookings Map.
        return "Booking successful. Total cost: " + (number * roomPrice[index]) + " Euros"; // Return a success message and the total cost.
    }

    // Implement the guests method that returns the list of guests who have made a booking.
    public String guests() throws RemoteException
    {
        String result = "";
        for (Map.Entry<String, List<String>> entry : bookings.entrySet()) // Iterate over each guest.
        {
            int numberOfBookings = entry.getValue().size(); // Calculate the number of bookings for the guest.
            result += "Guest " + entry.getKey() + " booked " + numberOfBookings + " rooms\n"; // Add the number of bookings for the guest to the result.
        }
        return result; // Return the result.
    }

    // Implement the registerForNotification method that registers a customer for notifications when a room becomes available.
    public void registerForNotification(NotificationListener client, String roomType) throws RemoteException
    {
        List<NotificationListener> clients = notifications.getOrDefault(roomType, new ArrayList<>()); // Retrieve the customers registered for notifications for the room type.
        clients.add(client); // Add the customer to the notification list.
        notifications.put(roomType, clients); // Store the notification list in the notifications Map.
    }

    // Implement the unregisterForCallback method that removes a customer from the notification list.
    public void unregisterForCallback(NotificationListener client) throws RemoteException
    {
        for (List<NotificationListener> clients : notifications.values()) { // Iterate over each notification list.
            clients.remove(client); // Remove the customer from the notification list.
        }
    }

    // Implement the cancel method that cancels a booking for a customer.
    public String cancel(String type, int number, String name) throws RemoteException
    {
        List<String> userBookings = bookings.get(name); // Retrieve the customer's bookings from the bookings Map.
        if(userBookings == null) // Check if the customer has booked rooms.
        {
            return "No bookings found for user " + name; // Return a message indicating no bookings were found for the customer.
        }

        // Check if the customer has booked the requested number of rooms.
        for(int i = 0; i < number; i++ )
        {
            if(!userBookings.remove(type)) // If the customer has not booked the requested number of rooms, return an error message.
            {
                return "No bookings found for user " + name + " of type " + type; // Return an error message.
            }
        }

        // Check if the room type exists in the roomType array.
        int index = -1;
        for (int i = 0; i < roomType.length; i++) {
            if (roomType[i].equals(type))
            {
                index = i;
                break;
            }
        }
        roomCount[index] += number; // Add the number of canceled rooms to the total rooms of the canceled type.

        List<NotificationListener> clients = notifications.get(type); // Retrieve the customers registered for notifications for the room type.
        if (clients != null) { // Check if there are customers registered for notifications.
            for (NotificationListener client : clients) { // Iterate over each customer.
                client.notify("Room type " + type + " is now available."); // Notify the customer that the room type is available.
            }
        }

        return "Cancellation successful. Remaining bookings for user " + name + ": " + userBookings.toString(); // Return a success message and the remaining bookings for the customer.
    }
}