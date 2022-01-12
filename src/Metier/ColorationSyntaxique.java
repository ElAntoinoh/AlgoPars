package AlgoPars.Metier;

import iut.algo.Console;
import iut.algo.CouleurConsole;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.File;


public class ColorationSyntaxique
{
	private static HashMap<String, String > couleurs;
	private static HashMap<String, Pattern> regPatterns;

	private static String couleurCommentaire;
	private static boolean commMultiLignes = false;

	private static final int longueurLignes = 75;


	/**
	 * Méthode qui charge la couleur des textes via un fichier xml
	 */
	public static void chargerCouleurs()
	{
		couleurs    = new HashMap<String, String >();
		regPatterns = new HashMap<String, Pattern>();

		Element racine = null;

		try {
			Document doc = new SAXBuilder().build( new File( "../utilisateur/coloration.xml" ) );
			racine = doc.getRootElement();
		}
		catch( Exception e ) { e.printStackTrace(); }

		for( Element e : racine.getChildren() )
		{
			for( Element child : e.getChildren() )
			{
				if( child.getText().equals( "//" ) )
					couleurCommentaire = getCouleurFromId( e.getAttribute( "idCoul" ).getValue() );
				
				couleurs.put( child.getText(), colorierMot( 
					child.getText(),  
					e.getAttribute( "idCoul" ).getValue(),  
					Boolean.parseBoolean( e.getAttribute( "poids" ).getValue() )
				) );

				regPatterns.put( child.getText(), Pattern.compile(
					"\\b" + child.getText() + "\\b(?![^\"]*\"[^\"]*(?:\"[^\"]*\"[^\"]*)*$)"
				) );
			}
		}
	}


	/**
	 * Méthode qui colorie la ligne passée en paramètre
	 * @param ligne Ligne à colorier
	 * @param ajouterBlanc Nécessité de compenser les caractères ansii
	 * @return String
	 */
	public static String colorierLigne( String ligne, boolean ajouterBlanc )
	{
		// Cas d'une ligne vide ( rien à colorier )
		if( ligne.isBlank() ) return ligne;

		int longueurLigneInitiale = ligne.length();

		/*--------------------------*/
		/* Commentaires multilignes */
		/*--------------------------*/

		// Entrée dans un commentaire multiligne
		if( ligne.contains( "/*" ) )
		{
			String debutLigne = colorierLigne( ligne.substring( 0, ligne.indexOf( "/*" ) ), false );

			// Si fin de commentaire
			if( ligne.contains( "*/" ) )
				return debutLigne
					  + couleurCommentaire
					  + ligne.substring( ligne.indexOf( "/*" ), ligne.indexOf( "*/" ) + 2 )
					  + "\033[0m"
					  + colorierLigne( ligne.substring( ligne.indexOf( "*/") + 2, ligne.length() ), true )
					  + " ".repeat( longueurLignes - longueurLigneInitiale );

			commMultiLignes = true;

			// Pas de fin de commentaires, coloration en vert
			return debutLigne
				  + couleurCommentaire
				  + ligne.substring( ligne.indexOf( "/*" ), ligne.length() )
				  + "\033[0m"
				  + " ".repeat( longueurLignes - longueurLigneInitiale );
		}

		// Si un commentaire multiligne a été démarré mais pas encore fini
		if( commMultiLignes )
		{
			// Si fin du commentaire
			if( ligne.contains( "*/" ) )
			{
				// Nous ne sommes plus dans un commentaire
				commMultiLignes = false;

				return couleurCommentaire
					  + ligne.substring( 0, ligne.indexOf( "*/" ) + 2 )
					  + "\033[0m"
					  + colorierLigne( ligne.substring( ligne.indexOf( "*/" ) + 2, ligne.length() ), true )
					  + " ".repeat( longueurLignes - longueurLigneInitiale );
			}

			// Pas fin de commentaire, coloration en vert
			return couleurCommentaire
				  + ligne
				  + "\033[0m"
				  + " ".repeat( longueurLignes - longueurLigneInitiale );
		}

		/*----------------------*/
		/* Commentaires simples */
		/*----------------------*/
		if( ligne.contains( "//" ) )
		{
			return colorierLigne( ligne.substring( 0, ligne.indexOf( "//" ) ), false )
					+ couleurCommentaire 
					+ ligne.substring( ligne.indexOf( "//" ), ligne.length() ) 
					+ "\033[0m"
					+ " ".repeat( longueurLignes - longueurLigneInitiale ) ;
		}

		/*-----------------------*/
		/* Mots clés d'une ligne */
		/*-----------------------*/

		Matcher matcher = null;

		for( String mot : couleurs.keySet() )
		{
			matcher = regPatterns.get( mot ).matcher( ligne );

			if( matcher.find() )
			{
				if( mot.equals( "a" ) && ligne.indexOf( "a" ) < ligne.indexOf( "faire" ) )
					ligne = ligne.replaceFirst( mot, couleurs.get( mot ) );
				else
					ligne = ligne.replace( mot, couleurs.get( mot ) );
			}
		}

		/*------------------------------------*/
		/* Besoin de compenser des couleurs ? */
		/*------------------------------------*/

		if( ajouterBlanc ) 
			ligne += " ".repeat( longueurLignes - longueurLigneInitiale );
		
		return ligne;
	}


	/**
	 * Méthode qui colorie le mot passé en paramètre.
	 * @param mot Le mot à colorier.
	 * @param couldId L'identifiant de sa couleur.
	 * @param gras Un booléen indiquant si le mot doit être mis en gras.
	 * @return Une String contenant le mot avec les séquences ANSII pour le mettre en couleur.
	 */
	private static String colorierMot( String mot, String coulId, boolean gras )
	{
		       /* Font de la couleur */   /* Traitement du gras  */         /* Reset */
		return getCouleurFromId(coulId) + ( gras ? "\033[1m" : "" ) + mot + "\033[0m";
	}


	/**
	 * Méthode qui renvoit une couleur grâce à son identifiant
	 * @param id
	 * @return String
	 */
	public static String getCouleurFromId( String id )
	{
		String[] tabCoul = new String[] {
			CouleurConsole.BLANC.getFont(),
			CouleurConsole.BLEU .getFont(),
			CouleurConsole.CYAN .getFont(),
			CouleurConsole.JAUNE.getFont(),
			CouleurConsole.VERT .getFont(),
			CouleurConsole.ROUGE.getFont(),
			CouleurConsole.MAUVE.getFont()
		};

		return tabCoul[Integer.parseInt(id)];
	}
}