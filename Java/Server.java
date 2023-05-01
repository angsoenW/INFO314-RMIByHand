import java.io.*;
import java.net.*;

public class Server {

    public static final int PORT = 10314;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running on port " + PORT);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    String methodName = (String) in.readObject();
                    Object result;

                    switch (methodName) {
                        case "add":
                            int lhs = in.readInt();
                            int rhs = in.readInt();
                            result = add(lhs, rhs);
                            break;
                        case "divide":
                            int num = in.readInt();
                            int denom = in.readInt();
                            try {
                                result = divide(num, denom);
                            } catch (ArithmeticException e) {
                                out.writeObject(e);
                                continue;
                            }
                            break;
                        case "echo":
                            String message = (String) in.readObject();
                            result = echo(message);
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid method name: " + methodName);
                    }

                    out.writeObject(result);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Do not modify any code below tihs line
    // --------------------------------------
    public static String echo(String message) { 
        return "You said " + message + "!";
    }
    public static int add(int lhs, int rhs) {
        return lhs + rhs;
    }
    public static int divide(int num, int denom) {
        if (denom == 0)
            throw new ArithmeticException();

        return num / denom;
    }
}