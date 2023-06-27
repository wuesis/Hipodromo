package ServerRMI;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class MatrixResolverHub {

    public static void main(String[] args) {

        try {
            String serverIp = InetAddress.getLocalHost().getHostAddress();
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            System.setProperty("java.rmi.server.hostname", serverIp);
            Naming.rebind("//" + serverIp + ":" + Integer.parseInt(args[0] + "//RMIServer"),new MatrixResolverEntity());
            System.out.println("Servidor en linea "+serverIp+":"+args[0]);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
