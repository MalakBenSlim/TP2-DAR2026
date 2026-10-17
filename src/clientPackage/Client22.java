package clientPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client22 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        System.out.println("Je suis un client pas encore connecté ... ");
        try (Socket socket = new Socket("localhost", 1234);
             // d'abord le flux de sortie, puis le flux d'entrée (sinon blocage)
             OutputStream os = socket.getOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(os);
             InputStream is = socket.getInputStream();
             ObjectInputStream in = new ObjectInputStream(is);
             Scanner sc = new Scanner(System.in)) {


            System.out.println("je suis un client connecté");
            while (true) {
                System.out.println("Donner l'opérande 1 (ou fin pour quitter): ");
                String o1 =sc.next();
                if (o1.equalsIgnoreCase("fin")) {
                    out.writeObject(new Operation(0, "FIN", 0));
                    out.flush();
                    break;
                }
                System.out.println("Donner l'opérateur (+ - * /): ");
                String op = sc.next();
                System.out.println("Donner l'opérande 2: ");
                String o2 = sc.next();

                // vérification de la syntaxe avant l'envoi
                if (!(op.equals("+") || op.equals("-") || op.equals("*") || op.equals("/"))) {
                    System.out.println("Opérateur incorrect, réessayez.");
                    continue;
                }
                double a, b;
                try {
                    a = Double.parseDouble(o1);
                    b = Double.parseDouble(o2);
                } catch (NumberFormatException e) {
                    System.out.println("Opérande invalide, réessayez.");
                    continue;
                }

                // créer l'objet et l'envoyer au serveur
                Operation operation = new Operation(a, op, b);
                out.writeObject(operation);
                out.flush();

                // recevoir le résultat
                String res = (String) in.readObject();
                System.out.println("Resultat: " + res);
            }
            System.out.println("Fin de la communication");
        }
    }
}