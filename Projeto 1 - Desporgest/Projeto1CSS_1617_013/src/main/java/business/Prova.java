package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Uma prova
 *	
 * @author css013
 * 
 */

@Entity
@NamedQuery(name = Prova.FIND_BY_DESIGNATION, query = "SELECT p FROM Prova p WHERE p.designacao = :" + Prova.DESIGNACAO)
public class Prova {

	// Named query name constants
	public static final String FIND_BY_DESIGNATION = "Prova.findByDesignation";
	public static final String DESIGNACAO = "designacao";

	@Id @GeneratedValue
	private int id;

	@Column(name = "tipo", nullable = false)
	private String tipo;

	@Column(name = "designacao", nullable = false, unique = true)
	private String designacao;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(nullable = false)
	private int numParticipantes;

	@Enumerated(EnumType.STRING)
	private Periodicidade periodicidade;

	@Column(nullable = false)
	private int numArbitrosEncontro;

	@OneToMany
	@JoinColumn
	private List<Encontro> encontrosProva;

	/**
	 * Constructor needed by JPA.
	 */
	protected Prova() {

	}

	/**
	 * Cria uma nova prova com o seu tipo, designacao, data de inicio, numero de participantes, numero de arbitros e periodicidade
	 * @param tipo Tipo da prova
	 * @param designacao Designacao da prova
	 * @param dataInicio Data de inicio da prova
	 * @param numParticipantes Numero de participantes
	 * @param numArbitrosEncontro Numero de arbitros por encontro
	 * @param periodicidade Periodicidade
	 */
	public Prova(String tipo, String designacao, Date dataInicio, int numParticipantes, int numArbitrosEncontro,
			Periodicidade periodicidade) {
		this.tipo = tipo;
		this.designacao = designacao;
		this.dataInicio = dataInicio;
		this.numParticipantes = numParticipantes;
		this.numArbitrosEncontro = numArbitrosEncontro;
		this.periodicidade = periodicidade;

		encontrosProva = new ArrayList<Encontro>();
	}

	/**
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return the designacao
	 */
	public String getDesignacao() {
		return designacao;
	}

	/**
	 * @return the dataInicio
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * @return the numParticipantes
	 */
	public int getNumParticipantes() {
		return numParticipantes;
	}

	/**
	 * @return the numArbitrosEncontro
	 */
	public int getNumArbitrosEncontro() {
		return numArbitrosEncontro;
	}

	/**
	 * 
	 * @return the periodicidade
	 */
	public Periodicidade getPeriocidade() {
		return periodicidade;
	}

	/**
	 * Adiciona um encontro a lista de encontros da prova
	 * @param e Encontro a adicionar
	 */
	public void addEncontro(Encontro e) {
		encontrosProva.add(e);
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
	 * @return Encontros
	 */
	public List<Encontro> getEncontros(){
		return encontrosProva;
	}
}
