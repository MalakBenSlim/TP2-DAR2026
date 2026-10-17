package clientPackage;
import java.io .*;
import java.net .*;
import java.util.Scanner;

public class Client21 {
    public static void main (String[] args) throws IOException {
        System.out.println("Je suis un client pas encore connecté ... ");
        try (Socket socket = new Socket("localhost", 1234);
             OutputStream os = socket.getOutputStream();
             PrintWriter out = new PrintWriter(os, true);

             InputStream is = socket.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader in = new BufferedReader(isr);
             Scanner sc = new Scanner(System.in)
        ) {
            System.out.println("je suis un client connecté");
            while (true) {
                System.out.println("Donner l'opérande 1 (ou fin pour quitter): ");
                // ici on n'utilise pas sc.nextInt pourtant il s'agit d'un entier : car l'utilisateur peut saisir le mot fin
                String o1 =sc.next();
                if (o1.equalsIgnoreCase("fin")) {
                    out.println("FIN");
                    break;
                }
                System.out.println("Donner l'opérateur (+ - * /): ");
                String op = sc.next();
                System.out.println("Donner l'opérande 2: ");
                String o2 = sc.next();

                //avant envoyer au serveur , on vérifie le syntaxe
                if (!(op.equals("+") || (op.equals("-") || (op.equals("*") || (op.equals("/")))))) {
                    System.out.println("Opérateur incorrect,réessayez.");
                    continue;
                }
                // maintenant on peut envoyer au serveur
                out.println(o1);
                out.println(op);
                out.println(o2);
                // recoit le resultat depuis serveur
                String res = in.readLine();
                System.out.println("Resultat: " + res);
            }
            System.out.println("Fin de la communication");

        }
    }
}
