import java.io.*;
import java.net.*;

public class Client {

    public static int add(int lhs, int rhs) {
        return (int) sendRequest("add", lhs, rhs);
    }

    public static int divide(int num, int denom) {
        return (int) sendRequest("divide", num, denom);
    }

    public static String echo(String message) {
        return (String) sendRequest("echo", message);
    }

    private static Object sendRequest(String methodName, Object... args) {
        try (Socket socket = new Socket("localhost", 10314);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
                System.out.println(socket.getInetAddress());
            out.writeObject(methodName);
            out.writeObject(args);

            Object result = in.readObject();
            if (result instanceof Throwable) {
                throw (Throwable) result;
            }
            return result;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: Unable to connect to the server or deserialize the response.");
            return null;
        } catch (Throwable t) {
            System.out.println("Error: The server returned an error: " + t.getMessage());
            return null;
        }
    }


    // Do not modify any code below this line
    // --------------------------------------
    String server = "localhost";
    public static final int PORT = 10314;

    public static void main(String... args) {
        // All of the code below this line must be uncommented
        // to be successfully graded.
        System.out.print("Testing... ");

        if (add(2, 4) == 6)
            System.out.print(".");
        else
            System.out.print("X");

        try {
            divide(1, 0);
            System.out.print("X");
        }
        catch (ArithmeticException x) {
            System.out.print(".");
        }

        if (echo("Hello").equals("You said Hello!"))
            System.out.print(".");
        else
            System.out.print("X");
        
        System.out.println(" Finished");
    }
}