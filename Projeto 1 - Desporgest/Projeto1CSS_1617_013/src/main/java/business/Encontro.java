package business;

import java.util.ArrayList;
import java.util.Comparator;
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
 * Um encontro
 *	
 * @author css013
 * 
 */

@Entity
@NamedQuery(name = Encontro.FIND_BY_ID, query = "SELECT e FROM Encontro e WHERE e.id = :" + Encontro.ID)
public class Encontro {

	// Named query name constants
	public static final String FIND_BY_ID = "Encontro.FIND_BY_ID";
	public static final String ID = "id";

	@Id
	@GeneratedValue
	private int id;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(name = "tipo", nullable = false)
	private String tipo;

	@JoinColumn(nullable = false)
	private Fase fase;

	@Enumerated(EnumType.STRING)
	private Periodicidade periocidade;

	@JoinColumn(nullable = false)
	private Participante part1;

	@JoinColumn(nullable = false)
	private Participante part2;

	@OneToMany
	private List<Arbitro> listaArbitros;

	private List<Participante> listaParticipantes;

	/**
	 * Cria um novo encontro com o seu tipo, data, periodicidade, fase e participantes
	 * @param tipo Tipo da prova
	 * @param data Data do encontro
	 * @param periocidade Periodicidade do encontro
	 * @param fase Fase do encontro
	 * @param part1 Participante 1
	 * @param part2 Participante 2
	 */
	public Encontro(String tipo, Date data, Periodicidade periocidade, Fase fase, Participante part1,
			Participante part2) {
		this.tipo = tipo;
		this.data = data;
		this.periocidade = periocidade;
		this.fase = fase;
		this.part1 = part1;
		this.part2 = part2;

		this.listaArbitros = new ArrayList<Arbitro>();
		this.listaParticipantes = new ArrayList<Participante>();
		this.listaParticipantes.add(part1);
		this.listaParticipantes.add(part2);
	}

	/**
	 * Constructor needed by JPA.
	 */
	protected Encontro() {
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the data
	 */
	public Date getData() {
		return data;
	}

	/**
	 * 
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @return periodicidade
	 */
	public Periodicidade getPeriocidade() {
		return periocidade;
	}

	/**
	 * 
	 * @return Arbitros
	 */
	public List<Arbitro> getArbitros() {
		return listaArbitros;
	}

	/**
	 * 
	 * @return informacao do encontro 
	 */
	public String infoEncontro() {
		return "Id: " + id + " Data: " + data.toString() + " Participantes: " + part1.getNome() + " e "
				+ part2.getNome();
	}

	/**
	 * 
	 * @return Informacao do calendario
	 */
	public String infoCalendario() {
		return "Data: " + data.toString() + " Designacao: " + part1.getProva().getDesignacao() + " Fase: " + getFase().getFase() + " Participantes: "
				+ part1.getNome() + " e " + part2.getNome();
	}

	/**
	 * 
	 * @return fase
	 */
	public Fase getFase() {
		return fase;
	}

	/**
	 * 
	 * @return participantes
	 */
	public List<Participante> getParticipantes() {
		return listaParticipantes;
	}

	/**
	 * Adiciona um arbitro a lista de arbitros do encontro
	 * @param a
	 */
	public void addArbitro(Arbitro a) {
		listaArbitros.add(a);
	}

	/**
	 * Compara dois encontros tendo em conta a sua data
	 * @return Comparator dos dois encontros
	 */
	public static Comparator<Encontro> getCompByDate() {
		Comparator<Encontro> comp = new Comparator<Encontro>() {
			@Override
			public int compare(Encontro s1, Encontro s2) {
				return s1.getData().compareTo(s2.getData());
			}
		};
		return comp;
	}
}
