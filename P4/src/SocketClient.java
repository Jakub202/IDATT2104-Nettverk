import java.io.*;
import java.net.*;
import java.security.spec.RSAOtherPrimeInfo;
import java.sql.SQLOutput;
import java.util.InputMismatchException;
import java.util.Scanner;

class SocketClient {
    public static void main(String[] args) throws IOException {
        final int PORTNR = 1250;

        /* Bruker en scanner til � lese fra kommandovinduet */
        Scanner leserFraKommandovindu = new Scanner(System.in);
        System.out.print("Oppgi navnet p� maskinen der tjenerprogrammet kj�rer: ");
        String tjenermaskin = leserFraKommandovindu.nextLine();

        /* Setter opp forbindelsen til tjenerprogrammet */
        Socket forbindelse = new Socket(tjenermaskin, PORTNR);
        System.out.println("N� er forbindelsen opprettet.");

        /* �pner en forbindelse for kommunikasjon med tjenerprogrammet */
        InputStreamReader leseforbindelse = new InputStreamReader(forbindelse.getInputStream());
        BufferedReader leseren = new BufferedReader(leseforbindelse);
        PrintWriter skriveren = new PrintWriter(forbindelse.getOutputStream(), true);

        /* Leser innledning fra tjeneren og skriver den til kommandovinduet */
        String innledning1 = leseren.readLine();
        String innledning2 = leseren.readLine();
        System.out.println(innledning1 + "\n" + innledning2);

        /* Leser tekst fra kommandovinduet (brukeren) */

        Scanner sc = new Scanner(System.in);
        boolean run = true;
        while (run) {
            try {
                System.out.print("Enter the first number: ");
                String a = sc.next();
                System.out.print("Enter the second number: ");
                String b = sc.next();
                System.out.print("Do you want to add or subtract (A/S)? ");
                String operation = sc.next();
                while(!operation.equalsIgnoreCase("A") && !operation.equalsIgnoreCase("S")){
                    System.out.println("Operation must be A or S");
                    operation = sc.next();
                }


                skriveren.println(a + " " + b + " " + operation);
                System.out.println(leseren.readLine());
                System.out.print("Do you want to continue (Y/N)? ");
                String answer = sc.next();
                run = answer.equalsIgnoreCase("Y");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input");
            }

            /* Lukker forbindelsen */

        }
        skriveren.println("exit");
        leseren.close();
        skriveren.close();
        forbindelse.close();

    }
}
