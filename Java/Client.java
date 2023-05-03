import java.io.*;
import java.net.*;

public class Client {

    public static int add(int lhs, int rhs) {
        try {
            return (int) sendRequest("add", lhs, rhs);
        } catch (Exception e) {
            throw new ArithmeticException();
        }
        

    }

    public static int divide(int num, int denom) {
        try {
            int temp = (int) sendRequest("divide", num, denom);
            if (temp == -1) {
                throw new ArithmeticException();
            }
            return temp;
        } catch (Exception e) {
            throw new ArithmeticException();
        }

    }

    public static String echo(String message) {
        return (String) sendRequest("echo", message);
    }

    private static Object sendRequest(String methodName, Object... args) {
        String server = "localhost";
        int port = 10314;

        try (Socket socket = new Socket(server, port);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            oos.writeObject(methodName);
            oos.writeObject(args);

            Object result = ois.readObject();

            if (result instanceof Throwable) {
                throw (Throwable) result;
            }

            return result;

        } catch (Throwable e) {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
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
        // System.out.print("Testing... ");

        // if (add(2, 4) == 6)
        //     System.out.print(".");
        // else
        //     System.out.print("X");

        // try {
        // divide(1, 0);
        // System.out.print("X");
        // }
        // catch (ArithmeticException x) {
        // System.out.print(".");
        // }

        // if (echo("Hello").equals("You said Hello!"))
        // System.out.print(".");
        // else
        // System.out.print("X");

        // System.out.println(" Finished");
    }
}