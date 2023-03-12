import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebServerThread extends Thread {
    private Socket forbindelse;
    private int id;

    public WebServerThread(Socket forbindelse, int id) {
        this.forbindelse = forbindelse;
        this.id = id;
    }

    public void run() {
        try {
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(forbindelse.getInputStream()));
            String clientRequest = inFromClient.readLine();
            System.out.println("Client request: " + clientRequest);

            String httpResponse = "HTTP/1.1 200 OK\nContent-Type: text/html; charset=utf-8\n\n<HTML><BODY>\n" +
                    "<H1> Hilsen. Du har koblet deg opp til min enkle web-tjener </h1>\n" +
                    "Header fra klient er:\n" +
                    "<UL>\n" +
                    "<LI> ...... </LI>\n" +
                    "</UL>\n" +
                    "</BODY></HTML>";
            DataOutputStream outToClient = new DataOutputStream(forbindelse.getOutputStream());
            outToClient.writeBytes(httpResponse);
            forbindelse.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
