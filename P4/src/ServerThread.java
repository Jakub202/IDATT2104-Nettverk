import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.InputMismatchException;

public class ServerThread extends Thread{

    public Socket socket;
    public int clientID;

    public ServerThread(Socket socket, int id){
        this.socket = socket;
        this.clientID = id;
    }

    public void run(){
        // venter inntil noen tar kontakt

        /* �pner str�mmer for kommunikasjon med klientprogrammet */
        InputStreamReader leseforbindelse = null;
        try {
            leseforbindelse = new InputStreamReader(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = null;
        try {
            skriveren = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        /* Sender innledning til klienten */
        skriveren.println("Hei, du har kontakt med tjenersiden!");
        skriveren.println("Skriv inn to tall du vil addere eller subtrahere");

        /* Mottar data fra klienten */
        String enLinje = null;  // mottar en linje med tekst
/*        try {
            enLinje = leseren.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        boolean run = true;
        while (run) {
            try {
                String line = leseren.readLine();
                if(line.equals("exit")){
                    break;
                }
                int[] expression = parseExpression(line);
                int result = calculate(expression[0], expression[1], expression[2]==0);
                skriveren.println("The result is: " + result);

            }  catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("client exit");

        /* Lukker forbindelsen */
        try {
            leseren.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        skriveren.close();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        if (parts[2].equalsIgnoreCase("A")) {
            operator = 0;
        } else {
            operator = 1;
        }
        return new int[] {number1, number2, operator};
    }

}
