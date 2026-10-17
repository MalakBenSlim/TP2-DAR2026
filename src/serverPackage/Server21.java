package serverPackage;
import java.io .*;
import java.net .*;

public class Server21 {
    public static void main(String[] args) throws IOException {
        try (ServerSocket socketserveur = new ServerSocket(1234)) {
            System.out.println("Je suis un serveur en attente de connexion d'un client");
            try (Socket socket = socketserveur.accept();
                 InputStream is = socket.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader in = new BufferedReader(isr);

                 OutputStream os = socket.getOutputStream();
                 PrintWriter out = new PrintWriter(os, true);) {
                System.out.println("Un client est connecté");
                while (true) {
                    String o1 = in.readLine();
                    if (o1 == null) {
                        System.out.println("Le client s'est déconnecté");
                        break;
                    }
                    if (o1.equalsIgnoreCase("FIN")) {
                        System.out.println("Fin de la communication");
                        break;
                    }
                    String op = in.readLine();
                    String o2 = in.readLine();
                    String res;
                    try {
                        // on va convertir o1 et o2 de chaines de caracters au réel pour calculer
                        double a = Double.parseDouble(o1);
                        double b = Double.parseDouble(o2);
                        if (op.equals("+")) {
                            res = "" + (a + b);
                        } else if (op.equals("-")) {
                            res = "" + (a - b);
                        } else if (op.equals("*")) {
                            res = "" + (a * b);
                        } else if (b == 0) {
                            res = "Erreur : impossible division par 0";
                        } else {
                            res = "" + (a / b);
                        }
                        System.out.println("Recu: " + a + " " + op + " " + b + "= " + res);
                    }catch(NumberFormatException e){
                        res="Erreur: opérande invalide";
                    }
                    out.println(res); // envoyer le resultat au client


                }
            }
        }
    }}
