import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class CalcServer {

    private DatagramSocket socket;
    private boolean running;
    private byte[] buf = new byte[256];

    public CalcServer() throws SocketException {
        socket = new DatagramSocket(4445);
    }

    public void run() throws IOException {
        running = true;
        System.out.println("running");
        while (running) {
            DatagramPacket packet
                    = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());

            if (received.equals("end")) {
                running = false;
                continue;
            }
            System.out.println(received);
            int[] input = parseExpression(received);

            String result = "Your result is : " + calculate(input[0],input[1],input[2]==0);

            packet = new DatagramPacket(result.getBytes(), result.getBytes().length, address, port);

            socket.send(packet);
        }
        socket.close();
    }

    public static int calculate(int a, int b, boolean plus) {
        if (plus) {
            return a + b;
        } else {
            return a - b;
        }
    }

    public static int[] parseExpression(String expression) {
        String[] parts = expression.split(" ");
        int number1 = Integer.parseInt(parts[0]);
        int number2 = Integer.parseInt(parts[1]);
        int operator;
        if (parts[2].charAt(0)=='A' || parts[2].charAt(0)=='a') {
            operator = 0;
        } else {
            operator = 1;
        }
        return new int[] {number1, number2, operator};
    }

    public static void main(String args[]) throws IOException {
        CalcServer server = new CalcServer();
        while(true){
            server.run();
        }
    }
}
