import java.io.IOException;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CalcClient {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public CalcClient() throws SocketException, UnknownHostException {
        socket = new DatagramSocket();
        address = InetAddress.getByName("localhost");
    }

    public String sendCalc(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(new byte[256], new byte[256].length);
        socket.receive(packet);
        String received = new String(packet.getData(), packet.getOffset(), packet.getLength());
        return received;
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        CalcClient client = new CalcClient();
        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while(run){
            try {
                System.out.print("Enter the first number: ");
                String a = sc.next();
                if(!a.matches("\\d+")){
                    throw new InputMismatchException();
                }
                System.out.print("Enter the second number: ");
                String b = sc.next();
                if(!b.matches("\\d+")){
                    throw new InputMismatchException();
                }
                System.out.print("Do you want to add or subtract (A/S)? ");
                String operation = sc.next();
                while(!operation.equalsIgnoreCase("A") && !operation.equalsIgnoreCase("S")){
                    System.out.println("Operation must be A or S");
                    operation = sc.next();
                }

                String output = client.sendCalc(a + " " + b + " " + operation);
                System.out.println(output);
                System.out.print("Do you want to continue (Y/N)? ");
                String answer = sc.next();
                run = answer.equalsIgnoreCase("Y");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }


        }

    }
}
