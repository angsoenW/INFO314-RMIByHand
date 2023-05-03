import java.io.*;
import java.net.*;


public class Server {



    public static void main(String[] args) {

    int port = 10314;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server listening on port " + port);

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream())) {

                    String methodName = (String) ois.readObject();
                    Object[] methodArgs = (Object[]) ois.readObject();

                    Object result;

                    if (methodName.equals("add")) {
                        result = add((int) methodArgs[0], (int) methodArgs[1]);
                    } else if (methodName.equals("divide")) {
                        try {
                            result = divide((int) methodArgs[0], (int) methodArgs[1]);
                        } catch (ArithmeticException e) {
                            result = -1;
                        }
                        
                    } else if (methodName.equals("echo")) {
                        result = echo((String) methodArgs[0]);
                    } else {
                        result = "Error: unknown method";
                    }

                    oos.writeObject(result);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
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