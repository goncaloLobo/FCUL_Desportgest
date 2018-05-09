package business;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;

/**
 * Um arbitro
 *	
 * @author css013
 * 
 */
@Entity
@NamedQuery(name = Arbitro.FIND_BY_NUM_FEDERATIVO, query = "SELECT a FROM Arbitro a WHERE a.num_federativo = :"
		+ Arbitro.NUM_FEDERATIVO)
public class Arbitro {

	// Named query name constants
	public static final String FIND_BY_NUM_FEDERATIVO = "Prova.findByNumFederativo";
	public static final String NUM_FEDERATIVO = "num_federativo";

	@Id
	private int id;

	@Column(nullable = false)
	private int num_federativo;

	@ManyToMany
	private List<Encontro> listaEncontros;

	/**
	 * Cria um novo arbitro com o seu numero federativo
	 * 
	 * @param num_federativo Numero federativo do arbitro
	 */
	public Arbitro(int num_federativo) {
		this.num_federativo = num_federativo;
		this.listaEncontros = new ArrayList<Encontro>();
	}

	/**
	 * Constructor needed by JPA.
	 */
	protected Arbitro() {
	}

	/**
	 * @return the num_federativo
	 */
	public int getNum_federativo() {
		return num_federativo;
	}
	
	/**
	 * 
	 * @return lista de encontros
	 */
	public List<Encontro> getCalendarioEncontros() {
		return listaEncontros;
	}

	/**
	 * Adiciona um encontro e a sua lista de encontros por ordem
	 * @param e Encontro a adicionar
	 */
	public void addEncontro(Encontro e) {
		listaEncontros.add(e);
		Collections.sort(listaEncontros, Encontro.getCompByDate());
	}
}
