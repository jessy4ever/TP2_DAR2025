package serverPackage;

import java.io.*;
import java.net.*;
import objectPackage.Operation;

public class Server{
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Serveur en attente de connexion...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connecté !");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            while (true) {
                Operation op = (Operation) in.readObject();

                double resultat = 0;
                switch (op.getOperateur()) {
                    case '+': resultat = op.getOperande1() + op.getOperande2(); break;
                    case '-': resultat = op.getOperande1() - op.getOperande2(); break;
                    case '*': resultat = op.getOperande1() * op.getOperande2(); break;
                    case '/': 
                        if (op.getOperande2() != 0)
                            resultat = op.getOperande1() / op.getOperande2();
                        else
                            System.out.println("Division par zéro !");
                        break;
                    default:
                        System.out.println("Opérateur non reconnu !");
                        break;
                }

                out.writeDouble(resultat);
                out.flush();
                System.out.println("Opération reçue : " + op.getOperande1() + " " + op.getOperateur() + " " + op.getOperande2() + " = " + resultat);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}