package business;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * Um participante
 *	
 * @author css013
 * 
 */

@Entity
@NamedQuery(name = Participante.FIND_BY_ID_PROVA, query = "SELECT p FROM Participante p WHERE p.prova = :"
		+ Participante.PROVA)
public class Participante {

	public static final String FIND_BY_ID_PROVA = "Prova.findByProva";
	public static final String PROVA = "prova";

	@Id
	@GeneratedValue
	private int id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@OneToOne
	@JoinColumn
	private Prova prova;

	/**
	 * Cria um novo participante com o seu nome e a prova a que pertence
	 * @param nome Nome do participante
	 * @param prova Prova Prova a que pertence
	 */
	public Participante(String nome, Prova prova) {
		this.nome = nome;
		this.prova = prova;
	}

	/**
	 * Constructor needed by JPA.
	 */
	protected Participante() {
	}
	
	/**
	 * 
	 * @return the id
	 */
	public int getId(){
		return id;
	}

	/**
	 * 
	 * @return the name
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return the prova
	 */
	public Prova getProva() {
		return prova;
	}
}
