package serverPackage;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            int port = 5000;
            InetAddress ip = InetAddress.getByName("0.0.0.0");
            ServerSocket serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(ip, port));
            System.out.println("Serveur en attente sur " + InetAddress.getLocalHost().getHostAddress() + ":" + port);

            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("Client connecté !");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                boolean continuer = true;
                while (continuer) {
                    String operation = in.readLine(); // lire l’opération envoyée
                    if (operation == null || operation.equalsIgnoreCase("exit")) {
                        System.out.println("Client a quitté la session.");
                        break;
                    }

                    System.out.println("Opération reçue : " + operation);
                    String resultat;

                    try {
                        resultat = calculer(operation);
                    } catch (Exception e) {
                        resultat = "Erreur : opération invalide (" + e.getMessage() + ")";
                    }

                    out.println(resultat);
                    System.out.println("Résultat envoyé : " + resultat);
                }
            }

            serverSocket.close();
            System.out.println("Serveur fermé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction de calcul
    private static String calculer(String operation) throws Exception {
        operation = operation.trim();

        String[] tokens = operation.split(" ");
        if (tokens.length != 3)
            throw new Exception("Format attendu : [nombre opérateur nombre]");

        double a = Double.parseDouble(tokens[0]);
        String op = tokens[1];
        double b = Double.parseDouble(tokens[2]);
        double res;

        switch (op) {
            case "+": res = a + b; break;
            case "-": res = a - b; break;
            case "*": res = a * b; break;
            case "/":
                if (b == 0) throw new Exception("Division par zéro");
                res = a / b;
                break;
            default:
                throw new Exception("Opérateur non reconnu");
        }

        return "Résultat = " + res;
    }
}
