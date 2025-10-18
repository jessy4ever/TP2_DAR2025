package clientPackage;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            Socket socket = new Socket("localhost", 5000);
            System.out.println("Connecté au serveur !");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            boolean continuer = true;
            while (continuer) {
                System.out.print("Entrez une opération (ex: 55 * 25) ou 'exit' pour quitter : ");
                String operation = sc.nextLine();

                out.println(operation);

                if (operation.equalsIgnoreCase("exit")) {
                    continuer = false;
                    break;
                }

                String reponse = in.readLine();
                System.out.println("Réponse du serveur → " + reponse);
            }

            socket.close();
            sc.close();
            System.out.println("Client terminé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
