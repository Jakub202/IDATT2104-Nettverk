import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    public static void main(String[] args) throws IOException {
        final int PORTNR = 1251;

        ServerSocket tjener = new ServerSocket(PORTNR);
        System.out.println("Logg for tjenersiden. N� venter vi...");
        int id = 1;
        while (true) {
            Socket forbindelse = tjener.accept();
            //change WebServerThread to ServerThread
            WebServerThread serverThread = new WebServerThread(forbindelse, id);
            serverThread.start();
            id++;
        }
    }
}
