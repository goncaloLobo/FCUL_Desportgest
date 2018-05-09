package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 * Uma fase
 *	
 * @author css013
 * 
 */

@Entity
public class Fase {

	@Id @GeneratedValue
	private int id;

	@Column(nullable = false)
	private int numFase;

	@JoinColumn
	private Prova prova;
	
	/**
	 * Constructor needed by JPA.
	 */
	protected Fase() {
	}

	/**
	 * Cria uma nova fase com o seu numero da fase e a prova a que pertence
	 * @param numFase Numero da fase
	 * @param prova Prova a que pertence
	 */
	public Fase(int numFase, Prova prova) {
		this.numFase = numFase;
	}

	/**
	 * @return the numFase
	 */
	public int getFase() {
		return numFase;
	}
	
	/**
	 * 
	 * @return the id
	 */
	public int getId(){
		return id;
	}

}
