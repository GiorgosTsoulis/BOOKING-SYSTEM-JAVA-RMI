// Import the necessary classes from the java.net, java.rmi, java.util, java.io packages.
import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// Create a class HRClient that extends UnicastRemoteObject and implements NotificationListener.
class HRClient extends UnicastRemoteObject implements NotificationListener {

    // Create the constructor of the HRClient class.
    public HRClient() throws RemoteException {
        super();
    }

    // Implement the notify method that notifies the client when a room becomes available.
    @Override
    public void notify(String message) {
        System.out.println(message);
    }

    // Create the main method of the application.
    public static void main(String[] args) {
        try {
            // Connect to the server.
            HRInterface c = (HRInterface) Naming.lookup("rmi://localhost:2501/HRSvc");

            // Check if the user provided a command.
            if (args.length == 0) { // If no command is given, display the available commands.
                System.out.println("Usage:"); // Display the usage instructions.
                System.out.println("java HRClient list <hostname>"); // Display the command for listing rooms.
                System.out.println("java HRClient book <hostname> <roomType> <number> <username>"); // Display the command for booking a room.
                System.out.println("java HRClient guests <hostname>"); // Display the command for listing guests.
                System.out.println("java HRClient cancel <hostname> <roomType> <number> <username>"); // Display the command for canceling a booking.
                return; // Terminate the program.
            }

            // Execute the command provided by the user.
            switch (args[0]) {
                case "list":
                    // Display the list of rooms.
                    System.out.println(c.list());
                    break;
                case "book":
                    // Book a room for the client.
                    String result = (c.book(args[2], Integer.parseInt(args[3]), args[4])); // Call the book method of the object c with the correct arguments from the command line.
                    System.out.println(result);
                    // Check if the booking was successful.
                    if (result.contains("Not enough rooms")) // If the booking was not successful, display an error message.
                    {
                        // Ask the client if they want to register for notifications.
                        System.out.println("Register for notifications when a room is available? (yes/no)");
                        Scanner scanner = new Scanner(System.in); // Create a new scanner to read the client's response.
                        String response = scanner.nextLine(); // Read the client's response.
                        if (response.equalsIgnoreCase("yes")) // If the client's response is "yes", continue.
                        {
                            // Register the client for notifications.
                            HRClient client = new HRClient(); // Create a new HRClient object.
                            c.registerForNotification(client, args[2]); // Call the registerForNotification method of the object c with the correct arguments from the command line.
                            System.out.println("Enter how many seconds to stay registered:"); // Display a message for the time the client wants to stay registered.
                            String timeDuration = scanner.nextLine(); // Read the time the client wants to stay registered.
                            int time = Integer.parseInt(timeDuration); // Convert the time to an integer.
                            try {
                                // Wait for the specified time.
                                Thread.sleep(time * 1000);
                            } catch (InterruptedException ex) { // If an error occurs, display an error message.
                                // The wait time has expired.
                            }
                            // Unregister the client for notifications.
                            c.unregisterForCallback(client);
                            System.out.println("Unregistered for callback.");
                            System.exit(0); // Terminate the program.
                        }
                        scanner.close(); // Close the scanner.
                    }
                    break;
                case "guests":
                    // Display the list of guests.
                    System.out.println(c.guests());
                    break;
                case "cancel":
                    // Cancel a booking for the client.
                    System.out.println(c.cancel(args[2], Integer.parseInt(args[3]), args[4])); // Call the cancel method of the object c with the correct arguments from the command line.
                    break;
                default:
                    // Display an error message if the command is invalid.
                    System.out.println("Invalid command");
                    break;
            }
        }
        // Display the respective error messages if an error occurs.
        catch (MalformedURLException murle) {
            System.out.println();
            System.out.println("MalformedURLException");
            System.out.println(murle);
        }
        catch (RemoteException re) {
            System.out.println();
            System.out.println("RemoteException");
            System.out.println(re);
        }
        catch (NotBoundException nbe) {
            System.out.println();
            System.out.println("NotBoundException");
            System.out.println(nbe);
        }
        catch (java.lang.ArithmeticException ae) {
            System.out.println();
            System.out.println("java.lang.ArithmeticException");
            System.out.println(ae);
        }
        catch (Exception aInE)
        {
            System.out.println(aInE);
        }
    }
}