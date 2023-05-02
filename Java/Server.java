import java.io.*;
import java.lang.reflect.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(10314)) {
            while (true) {
                try (Socket socket = serverSocket.accept();
                
                     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                        

                    String methodName = (String) in.readObject();
                    Object[] methodArgs = (Object[]) in.readObject();
                    Method method = Server.class.getMethod(methodName, getArgTypes(methodArgs));

                    try {
                        Object result = method.invoke(null, methodArgs);
                        out.writeObject(result);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        out.writeObject(e.getCause());
                    }
                } catch (IOException | ClassNotFoundException | NoSuchMethodException e) {
                    System.out.println("Error: Unable to process the client request.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to start the server.");
        }
    }

    private static Class<?>[] getArgTypes(Object[] args) {
        Class<?>[] argTypes = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        return argTypes;
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