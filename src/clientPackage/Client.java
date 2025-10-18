package clientPackage;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import objectPackage.Operation;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000)) {
            System.out.println("Connecté au serveur !");
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner sc = new Scanner(System.in);

            boolean continuer = true;
            while (continuer) {
                System.out.print("Entrez le premier nombre : ");
                double op1 = sc.nextDouble();

                System.out.print("Entrez l’opérateur (+, -, *, /) : ");
                char operateur = sc.next().charAt(0);

                System.out.print("Entrez le deuxième nombre : ");
                double op2 = sc.nextDouble();

                Operation operation = new Operation(op1, operateur, op2);
                out.writeObject(operation);
                out.flush();

                double resultat = in.readDouble();
                System.out.println("Résultat reçu : " + resultat);

                System.out.print("Voulez-vous continuer ? (1 = oui / 0 = non) : ");
                int rep = sc.nextInt();
                if (rep == 0) continuer = false;
            }

            sc.close();
            System.out.println("Client terminé.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}