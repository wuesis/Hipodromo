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
            int serverPort = Integer.parseInt(args[0]);
            String serverIp = InetAddress.getLocalHost().getHostAddress();
            LocateRegistry.createRegistry(serverPort);
            System.setProperty("java.rmi.server.hostname", serverIp);
            Naming.rebind("//" + serverIp + ":" + serverPort + "//RMIServer",new MatrixResolverEntity());
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
