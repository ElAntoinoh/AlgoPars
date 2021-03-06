package AlgoPars.Metier;

import AlgoPars.AlgoPars;
import AlgoPars.Metier.Types.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import java.io.FileInputStream;

public class Programme {
	private AlgoPars ctrl;
	private Primitives primitives;

	private int ligneActive;
	private ArrayList<String> lignesFichier;
	private ArrayList<String> lignesFichierColorie;

	private Donnee donnees;
	private ArrayList<String> listeVarSuivies;
	private ArrayList<Instruction> listeInstructions;

	private ArrayList<Integer> listeBreakPoints;
	private ArrayList<Boolean> alSi;

	private boolean executionActive;
	private boolean commMultiLignes;
	private boolean bConstante;
	private boolean bVariable;
	private boolean bSi;

	private int nombreSi;
	private int siImbrique;

	/**
	 * Constructeur de la classe Programme
	 * 
	 * @param ctrl
	 * @param cheminFichier
	 */
	public Programme(AlgoPars ctrl, String cheminFichier) {
		// Important car cela permet de charger le fichier XML des couleurs.
		ColorationSyntaxique.chargerCouleursXML();

		this.ctrl = ctrl;
		this.primitives = new Primitives(this.ctrl);

		this.lignesFichier = new ArrayList<String>();
		this.lignesFichierColorie = new ArrayList<String>();
		this.ligneActive = 0;
		this.executionActive = true;

		this.donnees = new Donnee();
		this.listeVarSuivies = new ArrayList<String>();
		this.listeInstructions = new ArrayList<Instruction>();

		this.alSi = null;
		this.nombreSi = -1;
		this.siImbrique = 0;
		this.bSi = true;

		this.listeBreakPoints = new ArrayList<Integer>();

		this.commMultiLignes = false;
		this.bConstante = false;
		this.bVariable = false;

		try {
			// Lecture du programme.
			Scanner sc = new Scanner(new FileInputStream("../utilisateur/" + cheminFichier + ".algo"), "UTF-8");

			String ligne = "";
			while (sc.hasNextLine()) {
				ligne = sc.nextLine().replace("\t", "    ");
				this.lignesFichier.add(ligne);
				this.lignesFichierColorie.add(ColorationSyntaxique.colorierLigne(ligne, true));
				this.listeInstructions.add(new Instruction(this.ctrl, this.primitives, ligne));
			}

			sc.close();

			// Lecture du fichier contenant les variables ?? suivre.
			sc = new Scanner(new FileInputStream("../utilisateur/" + cheminFichier + ".var"), "UTF-8");

			while (sc.hasNextLine())
				this.listeVarSuivies.add(sc.next().strip());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Accesseur de BConstante
	 * 
	 * @return booleen indiquant si l'on est dans la zone de d??claration des
	 *         constantes
	 */
	public boolean getBConstante() {
		return this.bConstante;
	}

	/**
	 * Accesseur de BVariable
	 * 
	 * @return booleen indiquant si l'on est dans la zone de d??claration des
	 *         variables
	 */
	public boolean getBVariable() {
		return this.bVariable;
	}

	/**
	 * Accesseur de bSi
	 * 
	 * @return booleen indiquant si l'on est dans un si
	 */
	public boolean getBSi() {
		return this.bSi;
	}

	/**
	 * Fonction changeant la valeur de BConstante
	 * 
	 * @param bConstante
	 */
	public void setBConstante(boolean bConstante) {
		this.bConstante = bConstante;
	}

	/**
	 * Fonction changeant la valeur de BConstante
	 * 
	 * @param bVariable
	 */
	public void setBVariable(boolean bVariable) {
		this.bVariable = bVariable;
	}

	/**
	 * Accesseur de AkSi
	 * 
	 * @return ArrayList<Boolean>
	 */
	public ArrayList<Boolean> getAlSi() {
		return this.alSi;
	}

	/**
	 * Fonction changeant la valeur de alSi
	 * 
	 * @param alSi
	 */
	public void setAlSi(ArrayList<Boolean> alSi) {
		this.alSi = alSi;
	}

	/**
	 * Accesseur de ligneActive
	 * 
	 * @return indice de la ligne active
	 */
	public int getLigneActive() {
		return this.ligneActive;
	}

	/**
	 * Accesseur de lignesFichier
	 * 
	 * @return ArrayList contenant les lignes du fichier
	 */
	public ArrayList<String> getLignesFichier() {
		return this.lignesFichier;
	}

	/**
	 * Accesseur de lignesFichierColorie
	 * 
	 * @return ArrayList contenant les lignes du fichier color??es
	 */
	public ArrayList<String> getLignesFichierColorie() {
		return this.lignesFichierColorie;
	}

	/**
	 * Accesseur de listeVarSuivies
	 * 
	 * @return ArrayList contenant les variables suivies
	 */
	public ArrayList<String> getVariablesSuivies() {
		return this.listeVarSuivies;
	}

	/**
	 * Accesseur de listeBreakPoints
	 * 
	 * @return ArrayList contenant la liste des breakpoints
	 */
	public ArrayList<Integer> getListeBreakPoints() {
		return this.listeBreakPoints;
	}

	/**
	 * M??thode changeant bsi
	 * 
	 * @param bSi
	 */
	public void setBSi(boolean bSi) {
		this.bSi = bSi;
	}

	/**
	 * M??thode changeant nbSi
	 * 
	 * @param nbSi
	 */
	public void setNbSi(int nbSi) {
		this.nombreSi = nbSi;
	}

	/**
	 * M??thode changeant siImbrique
	 * 
	 * @param nbSi
	 */
	public void setSiImbrique(int nbSi) {
		this.siImbrique = nbSi;
	}

	/**
	 * Accesseur du nombre de si
	 * 
	 * @return nombre de si
	 */
	public int getNbSi() {
		return this.nombreSi;
	}

	/**
	 * Accesseur de valeur
	 * 
	 * @param nom
	 * @return valeur sous forme de string
	 */
	public String getValeur(String nom) {
		if (this.donnees.rechercheParNom(nom) == null)
			return null;

		Typable var = this.donnees.rechercheParNom(nom);

		if (var.getValeur() != "true" && var.getValeur() != "false")
			return var.getValeur();

		return var.getValeur() == "true" ? "vrai" : "faux";
	}

	/**
	 * Renvoit la valeur d'une variable par son nom
	 * 
	 * @param nom
	 * @return valeur en String
	 */
	public String getString(String nom) {
		Typable var = this.donnees.rechercheParNom(nom);

		if (var != null)
			return var.toString();

		return null;
	}

	/**
	 * Fonction changeant la valeur de valeur
	 * 
	 * @param nom
	 * @param valeur
	 */
	public void affecterValeur(String nom, String valeur) {
		this.donnees.affecterValeur(nom, valeur);
	}

	/**
	 * Ex??cution de l'algorithme.
	 */
	public void executerAlgo() {
		Scanner sc = null;
		do {
			this.ctrl.afficher();

			try {
				sc = new Scanner(System.in); // ouverture du Scanner

				String msg = sc.nextLine();

				if (this.lignesFichier.get(this.ligneActive).contains("/*")) {
					if (!this.lignesFichier.get(this.ligneActive).contains("*/"))
						commMultiLignes = true;
				}

				if (commMultiLignes) {
					while (!this.lignesFichier.get(this.ligneActive + 1).contains("*/"))
						this.ligneActive++;
					commMultiLignes = false;
					this.listeInstructions.get(this.ligneActive).interpreterLigne();
				}

				else if (this.alSi != null) {
					if (!this.bSi || (!this.alSi.get(this.nombreSi)
							&& this.listeInstructions.get(this.ligneActive).getInstruction().equals("si"))) {
						this.siImbrique = 0;
						while (!this.bSi || (this.siImbrique > -1 && !this.alSi.get(this.nombreSi))) {
							this.siImbrique += this.listeInstructions.get(++this.ligneActive)
									.interpreterLigne(this.siImbrique);
						}
						if (this.nombreSi == -1) {
							this.nombreSi++;
							--ligneActive;
						} else if (this.siImbrique == -1) {
							this.nombreSi++;
							--ligneActive;
						}
					} else if ((this.alSi.get(this.nombreSi)
							&& this.listeInstructions.get(this.ligneActive).getInstruction().equals("sinon"))
							&& this.bSi) {
						this.siImbrique = 0;
						while (this.siImbrique > -1 && this.alSi.get(this.nombreSi)) {
							this.siImbrique += this.listeInstructions.get(++this.ligneActive)
									.interpreterLigne(this.siImbrique);
						}
						if (this.nombreSi == -1) {
							this.nombreSi++;
							--ligneActive;
						} else if (this.siImbrique == -1) {
							this.nombreSi++;
							--ligneActive;
						}

					}
				}
				// M??thode : "L" + numLigne + Entr??e ( aller ?? la ligne numLigne )
				else if (msg.matches("^L\\d+")) {
					int ecart = this.ligneActive - Integer.parseInt(msg.substring(1));

					int i = Math.abs(ecart);

					int x = i == 0 ? 0 : ecart / i;

					for (int cpt = 0; cpt < i; cpt++) {
						this.ligneActive = this.ligneActive - x;

						if (this.ligneActive == this.lignesFichier.size())
							break;

						if (x < 0)
							this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}
				// M??thode : "+ bk" + numLigne ( ajout d'un point d'arr??t )
				else if (msg.matches("^\\+ bk \\d+")) {
					int val = Integer.parseInt(msg.substring(5));

					if (!this.listeBreakPoints.contains(val) && val < this.lignesFichier.size())
						this.listeBreakPoints.add(val);

					this.listeBreakPoints.sort(null); // tri
				}
				// M??thode : "- bk" + numLigne ( suppression d'un point d'arr??t )
				else if (msg.matches("^\\- bk \\d+")) {
					int indice = this.listeBreakPoints.indexOf(Integer.parseInt(msg.substring(5)));
					this.listeBreakPoints.remove(indice);
				}
				// M??thode : "go bk" ( d??placement jusqu'au prochain point d'arr??t )
				else if (msg.equals("go bk")) {
					int prochainBK = this.lignesFichier.size() - 1;

					for (int i : this.listeBreakPoints)
						if (i > this.ligneActive) {
							prochainBK = i;
							break;
						}

					if (prochainBK == this.lignesFichier.size() - 1)
						System.out.println(
								"Pas de point d'arr??t trouv?? apr??s votre position. Vous ??tes envoy??s au bout du programme");

					for (int cpt = this.ligneActive; cpt < prochainBK; cpt++)
						this.listeInstructions.get(++this.ligneActive).interpreterLigne();
				}

				switch (msg) {
					case "b": { // M??thode : "b" + Entr??e ( reculer d'une ligne )
						this.ligneActive = this.ligneActive == 0 ? 0 : this.ligneActive - 1;
						break;
					}
					case "": { // M??thode : Entr??e ( avancer d'une ligne )
						if (++this.ligneActive < this.listeInstructions.size())
							this.listeInstructions.get(this.ligneActive).interpreterLigne();
					}
				}

				// sc.close(); // fermeture du Scanner
			} catch (NoSuchElementException e) {
				sc.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (this.executionActive && this.ligneActive < this.lignesFichier.size());
	}

	public void add(String nom, String type, String valeur) {
		this.donnees.add(nom, type, valeur);
	}

	public void add(String nom, String type) {
		this.donnees.add(nom, type);
	}
}
