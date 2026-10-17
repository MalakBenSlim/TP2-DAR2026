package serverPackage;

import commonPackage.Operation;
import java.io.*;
import java.net.*;

public class Server22 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        try (ServerSocket socketserveur = new ServerSocket(1234)) {
            System.out.println("Je suis un serveur en attente de connexion d'un client");
            try (Socket socket = socketserveur.accept();
                 // d'abord le flux de sortie, puis le flux d'entrée (sinon blocage)
                 OutputStream os =socket.getOutputStream();
                 ObjectOutputStream out =new ObjectOutputStream(os);

                 InputStream is =socket.getInputStream();
                 ObjectInputStream in =new ObjectInputStream(is)) {

                System.out.println("Un client est connecté");
                while (true) {
                    Operation op;
                    try {
                        // le serveur reçoit l'objet envoyé par le client
                        op =(Operation) in.readObject();
                    } catch (EOFException e) {
                        System.out.println("Le client s'est déconnecté");
                        break;
                    }
                    if (op.getOperateur().equals("FIN")) {
                        System.out.println("Fin de la communication");
                        break;
                    }

                    // récupérer les données de l'objet
                    double a =op.getOperande1();
                    String o =op.getOperateur();
                    double b =op.getOperande2();
                    String res;
                    if (o.equals("+")) {
                        res = "" +(a + b);
                    } else if (o.equals("-")) {
                        res = "" +(a - b);
                    } else if (o.equals("*")) {
                        res = "" +(a * b);
                    } else if (o.equals("/")) {
                        if (b ==0) {
                            res ="Erreur : impossible de diviser par 0";
                        } else {
                            res ="" + (a / b);
                        }
                    } else {
                        res = "Erreur : opérateur invalide";
                    }
                    System.out.println("Reçu : " + a + " " + o + " " + b + " = " + res);

                    // envoyer le résultat au client
                    out.writeObject(res);
                }
            }
        }
    }
}