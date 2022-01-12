package AlgoPars;

import AlgoPars.Metier.Programme;
import AlgoPars.Metier.Types.Typable;
import AlgoPars.CUI.Affichage;

import java.util.ArrayList;

public class AlgoPars {
    private Programme prgm;
    private Affichage cui;

    /**
     * Constructeur de la classe AlgoPars
     * 
     * @param chemin nom du fichier passé en paramètre
     */
    public AlgoPars(String chemin) {
        this.cui = new Affichage(this);
        this.prgm = new Programme(this, chemin);
        this.cui.initialiserVariablesSuivies();
        this.prgm.executerAlgo();
    }

    /**
     * Méthode d'accès à l'ArrayList contenant les lignes du programme interprété
     * colorées
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getLignesFichierColorie() {
        return this.prgm.getLignesFichierColorie();
    }

    /**
     * Méthode renvoyant l'indice de la ligne courante
     * 
     * @return int
     */
    public int getLigneActive() {
        return this.prgm.getLigneActive();
    }

    public ArrayList<String> getVariablesSuivies() {
        return this.prgm.getVariablesSuivies();
    }

    public ArrayList<Integer> getListeBreakPoints() {
        return this.prgm.getListeBreakPoints();
    }

    /**
     * Méthode qui nettoie la console et affiche la nouvelle étape de
     * l'interprétation
     */
    public void afficher() {
<<<<<<< HEAD
        System.out.print("\033[H\033[2J"); // Réinitialisation de l'affichage de la console
        System.out.flush();
=======

        System.out.print("\033[H\033[2J"); // Réinitialisation de l'affichage de la console
        System.out.flush();

>>>>>>> 1e9e6b02d93ac6e30ea5d1a80899f295f1aefba7
        this.cui.afficher();
    }

    /**
     * Méthode qui ajoute une nouvelle ligne à la trace d'execution
     * 
     * @param trace ligne à ajouter
     */
    public void ajouterTraceExecution(String trace) {
        this.cui.ajouterTraceExecution(trace);
    }

    /*
     * public Object executerFonction(String nomFonction, Typable[] parametre) {
     * return this.prgm.executerFonction(nomFonction, parametre);
     * }
     */

    /**
     * Méthode qui appelle le constructeur de constantes
     * 
     * @param nom    nom de la constante
     * @param type   type de la constante
     * @param valeur une constante prend sa valeur à sa création
     */
    public void add(String nom, String type, String valeur) {
        this.prgm.add(nom, type, valeur);
    }

    /**
     * Méthode qui appelle le constructeur de variables
     * 
     * @param nom  nom de la variable
     * @param type type de la variable
     */
    public void add(String nom, String type) {
        this.prgm.add(nom, type);
    }

    /**
     * Méthode appelant le modificateur des valeurs de variables
     * 
     * @param nom    nom de la variable à modifier
     * @param valeur nouvelle valeur de la variable
     */
    public void affecterValeur(String nom, String valeur) {
        this.prgm.affecterValeur(nom, valeur);
    }

    public String getValeur(String nom) {
        return this.prgm.getValeur(nom);
    }

    public String getString(String nom) {
        return this.prgm.getString(nom);
    }

    public boolean getBConstante() {
        return this.prgm.getBConstante();
    }

    public boolean getBVariable() {
        return this.prgm.getBVariable();
    }

    public void setBConstante(boolean bConstante) {
        this.prgm.setBConstante(bConstante);
    }

    public void setBVariable(boolean bVariable) {
        this.prgm.setBVariable(bVariable);
    }

    public void setAlSi(ArrayList<Boolean> alSi) {
        this.prgm.setAlSi(alSi);
    }

    public void setNbSi(int nbSi) {
        this.prgm.setNbSi(nbSi);
    }

    public void setSiImbrique(int nbSi) {
        this.prgm.setSiImbrique(nbSi);
    }

    public void setBSi(boolean bSi) {
        this.prgm.setBSi(bSi);
    }

    public void setBSinon(boolean bSinon) {
        this.prgm.setBSinon(bSinon);
    }

    public void addValAlSi(Boolean val) {
        this.prgm.addValAlSi(val);
    }

    public int getNbSi() {
        return this.prgm.getNbSi();
    }

    public ArrayList<Boolean> getAlSi() {
        return this.prgm.getAlSi();
    }

    public void setNom(String nom) {
        this.prgm.setNom(nom);
    }

    /*
     * public Typable getTypable(String s) {
     * return prgm.getTypable(s);
     * }
     */

    public static void main(String[] args) {
        new AlgoPars(args[0]);
    }

}