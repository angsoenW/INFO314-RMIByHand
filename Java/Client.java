import java.io.*;
import java.net.*;

public class Client {

    public static final int PORT = 10314;
    public static final String SERVER_ADDRESS = "localhost";

    /**
     * This method name and parameters must remain as-is
     */
    public static int add(int lhs, int rhs) {
        return (int) executeRemoteCall("add", lhs, rhs);
    }

    /**
     * This method name and parameters must remain as-is
     */
    public static int divide(int num, int denom) {
        return (int) executeRemoteCall("divide", num, denom);
    }
    
    /**
     * This method name and parameters must remain as-is
     */
    public static String echo(String message) {
        return (String) executeRemoteCall("echo", message);

    }

    private static Object executeRemoteCall(String methodName, Object... args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(methodName);
            for (Object arg : args) {
                out.writeObject(arg);
            }

            Object result = in.readObject();
            if (result instanceof Throwable) {
                throw (Throwable) result;
            }
            return result;

        } catch (IOException | ClassNotFoundException | Throwable e) {
            System.err.println("Error: " + e.getMessage());
            return null;
        }
    }


    // Do not modify any code below this line
    // --------------------------------------

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