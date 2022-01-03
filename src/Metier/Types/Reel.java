package AlgoPars.Metier.Types;

public class Reel extends Typable
{
	private double valeur;

	public Reel(String nom , boolean modifiable, double valeur)
	{
		super(nom, modifiable);

		this.valeur=valeur ;
	}

	public Double getValeur() {
		return this.valeur;
	}

	public void setValeur(double v) {
		if( this.modifiable )
			this.valeur = v;
		else
		{//si une valeur n'a pas été encore attribué
			if(this.valeur == null)
				this.valeur = v;
			else
				throw new RuntimeException("La valeur d'une constante n'est pas modifiable");
		}
	}
}