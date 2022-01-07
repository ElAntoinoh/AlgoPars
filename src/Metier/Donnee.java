package AlgoPars.Metier;

import AlgoPars.Metier.Types.*;

import java.util.ArrayList;

public class Donnee {
    private ArrayList<Typable> donnees;

    private final String[][] tabRegex = new String[][] {
            { "'.'", "\"[^\\\"]*\"", "\\d,\\d+", "vrai", "faux", "\\d+" },
            { "caractere", "chaine", "reel", "booleen", "entier" }
    };

    Typable var;

    /**
     * Constructeur de la classe Donnee
     */
    public Donnee() {
        donnees = new ArrayList<Typable>();
    }

    /**
     * Méthode qui instancie une variable
     * 
     * @param nom
     * @param type
     */
    public void add(String nom, String type) {
        switch (type) {
            case "booleen": {
                this.donnees.add(new Booleen(nom, true, false));
                break;
            }
            case "caractere": {
                this.donnees.add(new Caractere(nom, true, ' '));
                break;
            }
            case "chaine": {
                this.donnees.add(new Chaine(nom, true, ""));
                break;
            }
            case "entier": {
                this.donnees.add(new Entier(nom, true, 0));
                break;
            }
            case "reel": {
                this.donnees.add(new Reel(nom, true, 0.0));
                break;
            }
            // case "tableau" : { this.donnees.add(new Reel (nom, true, 0.0 )); break; }
            default:
                break;
        }
    }

    /**
     * Méthode qui instancie une constante
     * 
     * @param nom
     * @param type
     * @param valeur
     */
    public void add(String nom, String type, String valeur) {
        if (type == null)
            for (int i = 0; i < tabRegex[0].length; i++)
                if (valeur.matches(tabRegex[0][i])) {
                    add(nom, tabRegex[1][i], valeur);
                    return;
                }

        switch (type) {
            case "booleen": {
                this.donnees.add(new Booleen(nom, false, valeur.equals("vrai") ? true : false));
                break;
            }
            case "caractere": {
                this.donnees.add(new Caractere(nom, false, valeur.charAt(1)));
                break;
            }
            case "chaine": {
                this.donnees.add(new Chaine(nom, false, valeur));
                break;
            }
            case "entier": {
                this.donnees.add(new Entier(nom, false, Integer.parseInt(valeur)));
                break;
            }
            case "reel": {
                valeur = valeur.replaceAll(",", ".");
                this.donnees.add(new Reel(nom, false, Double.parseDouble(valeur)));
                break;
            }
            // case "tableau" : { this.donnees.add(new Reel (nom, true , 0.0 )); break; }
            default:
                break;
        }

    }

    /**
     * Méthode de recherche d'un Typable par son nom
     * 
     * @param nom
     * @return
     */
    public Typable rechercheParNom(String nom) {
        for (Typable t : donnees)
            if (t.getNom().equals(nom))
                return t;

        return null;
    }

    /**
     * Méthode qui affecte une valeur à une variable en se basant sur le nom de
     * cette variable
     * 
     * @param nom
     * @param valeur
     */
    public void affecterValeur(String nom, String valeur) {
        this.var = rechercheParNom(nom);

        if (valeur.matches("'.'")) {
            ((Caractere) (this.var)).setValeur(valeur.charAt(1));
            return;
        }
        if (valeur.matches("\"[^\\\"]*\"")) {
            ((Chaine) (this.var)).setValeur(valeur.substring(1, valeur.length() - 1));
            return;
        }
        if (valeur.matches(",")) {
            ((Reel) (this.var)).setValeur(Double.parseDouble(valeur));
            return;
        }
        if (valeur.matches("vrai") || valeur.matches("faux")) {
            ((Booleen) (this.var)).setValeur(valeur.matches("vrai"));
            return;
        }
        if (valeur.matches("\\d+")) {
            ((Entier) (this.var)).setValeur(Integer.parseInt(valeur));
            return;
        }

        // if (valeur.matches("\w+(\w*)"))
        //

    }
}