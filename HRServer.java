
// Import the necessary classes from the java.rmi and java.rmi.server packages.
import java.rmi.*;
import java.rmi.server.*;

// Create a class HRServer.
class HRServer {
    // Create the constructor of the HRServer class.
    public HRServer() {
        try {
            // Create an HRImpl object.
            HRImpl obj = new HRImpl();
            // Publish the HRImpl object to the RMI registry with the name "HRSvc".
            Naming.rebind("rmi://localhost:2501/HRSvc", obj);
            // Display a message indicating that the server is ready for operations.
            System.out.println("HR Server is ready for operations.");
        } catch (Exception e) {
            // Display an error message if an error occurs.
            System.out.println("Trouble: " + e);
        }
    }

    // Create the main method of the application.
    public static void main(String[] args) {
        // Create an HRServer object.
        new HRServer();
    }
}