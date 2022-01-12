package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Programme;
import java.util.ArrayList;
import AlgoPars.Metier.Types.*;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.util.Scanner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.lang.reflect.Method;

public class Primitives {
    private AlgoPars ctrl;
    public Method[] listePrimitives;

    public Primitives(AlgoPars ctrl) {
        this.ctrl = ctrl;

        // tableau des méthodes primitives
        this.listePrimitives = this.getClass().getDeclaredMethods();
    }

    public void lire(String nom) {
        System.out.println("Lire " + nom + " : ");

        String valeur = new Scanner(System.in).nextLine();

        this.ctrl.affecterValeur(nom, valeur);
    }

    public void ecrire(String msg) {
        String result = "";

        Pattern ptrn = Pattern.compile("(\"(.+?)\")|(\\d+(\\.\\d*)*)|(\\w+)");
        Matcher matcher = ptrn.matcher(msg);

        String param = "";
        while (matcher.find()) {
            param = matcher.group();

            if (param.contains("\""))
                result += param.replace("\"", "") + " ";
            else if (param.equals("vrai") || param.equals("faux"))
                result += param + " ";
            else if (this.ctrl.getValeur(param) != null)
                result += this.ctrl.getValeur(param) + " ";
            else
                result += param + " ";
        }

        this.ctrl.ajouterTraceExecution(result);
    }

    public void si(String msg) {
        ArrayList<Boolean> result = new ArrayList<Boolean>();
        System.out.println(msg + " 3");
        if (this.ctrl.getAlSi() != null) {
            switch (msg) {
                case "vrai", "true":
                    break;
                case "faux", "false":
                    break;
            }
        } else {
            switch (msg) {
                case "vrai", "true":
                    break;
                case "faux", "false":
                    break;
            }

        }
    }

    // retourne le caractère correspondant à sa valeur dans la table ASCII
    public static String car(String entier) {
        return String.valueOf(Integer.parseInt(entier));
    }

    // retourne la valeur ASCII d'un caractère
    public static String ord(String caractere) {
        return String.valueOf((int) caractere.charAt(0));
    }

    // convertit un réel en chaine
    public static String enChaine(String str) {
        return "\"" + str + "\"";
    }

    // convertit une chaine en entier
    public static String enEntier(String chaine) {
        return chaine;
    }

    // convertit une chaine en réel
    public static String enReel(String chaine) {
        return chaine;
    }

    // retourne l'entier le plus proche d'un réel vers le bas
    public static String plancher(String reel) {
        return String.valueOf(Math.floor(Double.parseDouble(reel)));
    }

    // retourne l'entier le plus proche d'un réel vers le haut
    public static String plafond(String reel) {
        return String.valueOf(Math.ceil(Double.parseDouble(reel)));
    }

    // retourne l'entier le plus proche d'un réel ( par convention, arrondi de x,5
    // vaut x+1 )
    public static String arrondi(String reel) {
        return String.valueOf(Math.round(Double.parseDouble(reel)));
    }

    // retourne sous forme de chaine la date du jour au format jj/mm/aaaa
    public static String aujourdhui() {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return (date.format(format));
    }

    // retourne la partie jour d'une chaine correspondant à une date au format
    // jj/mm/aaaa
    public static String jour(String chaine) {
        return String.valueOf(chaine.substring(0, 2));
    }

    // retourne la partie mois d'une chaine correspondant à une date au format
    // jj/mm/aaaa
    public static String mois(String chaine) {
        return String.valueOf(chaine.substring(3, 5));
    }

    // retourne la partie année d'une chaine correspondant à une date au format
    // jj/mm/aaaa
    public static String annee(String chaine) {
        return String.valueOf(chaine.substring(6, 10));
    }

    // indique si la chaine peut être convertie en réel
    public static String estReel(String chaine) {
        return chaine.matches("^\\d+\\.\\d+$") == true ? "vrai" : "faux";
    }

    // indique si la chaine peut être convertie en entier
    public static String estEntier(String chaine) {
        return chaine.matches("^\\d+$") == true ? "vrai" : "faux";
    }

    // retourne une valeur entière prise au hasard sur l'intervalle [ 0; paramètre ]
    public static String hasard(String entier) {
        return String.valueOf(Math.random() * Integer.parseInt(entier));
    }

    public void addValAlSi(Boolean val) {
        this.ctrl.addValAlSi(val);
    }
}