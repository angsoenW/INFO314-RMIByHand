import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote {

    public String echoo(String message) throws RemoteException;
    
    public int addd(int lhs, int rhs) throws RemoteException;
      
    public int dividee(int num, int denom) throws RemoteException;
        
}