package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.ApplicationException;

/**
 * Controla o caso de uso de visualizar o calendario de encontros de um arbitro
 * 
 * @author css013
 *
 */
public class CalendarioArbitroHandler {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	private EntityManagerFactory emf;

	private Arbitro arbitro; //Arbitro atual

	/**
	 * Cria um handler para o caso de uso de visualizar o calendario de encontros de um arbitro
	 * 
	 * @param emf
	 *            The entity manager factory of the application
	 */
	public CalendarioArbitroHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Visualiza o calendario do arbitro com o numero federativo
	 * @param numFederativo Numero federativo do arbitro
	 * @throws ApplicationException Caso o arbitro nao exista
	 */
	public List<String> visualizarCalendario(int numFederativo) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		CatalogoArbitros catalogoArbitros = new CatalogoArbitros(em);
		List<String> calendario = new ArrayList<>(); //Informacao dos encontros do arbitro
		try {
			arbitro = catalogoArbitros.getArbitro(numFederativo); //Arbitro com o numero federativo
			for (Encontro encontro : arbitro.getCalendarioEncontros()) {
				calendario.add(encontro.infoCalendario());
			}
			return calendario;
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro a obter o calendario", e);
		} finally {
			em.close();
		}
	}

	/**
	 * Visualiza lista de arbitros do encontro do arbitro escolhido
	 * @param data Data do encontro escolhido
	 * @throws ApplicationException Caso nao hajam arbitros atribuidos nesse encontro
	 */
	public List<Integer> escolheCalendario(Date data) throws ApplicationException {
		List<Arbitro> listaArbitros = new ArrayList<>();
		List<Encontro> listaEncontros = arbitro.getCalendarioEncontros(); //Encontros do arbitro
		List<Integer> arbitros = new ArrayList<>(); //Informacao dos arbitros do encontro com essa data
		for (Encontro encontro : listaEncontros) {
			if (encontro.getData().equals(data)) {
				listaArbitros = encontro.getArbitros();
				break;
			}
		}
		//Caso o encontro nao tenha arbitros atribuidos
		if (listaArbitros.size() == 0) {
			throw new ApplicationException("Este encontro nao tem arbitros atribuidos");
		} else {
			for (Arbitro a : listaArbitros) {
				arbitros.add(a.getNum_federativo());
			}
		}

		return arbitros;
	}
}
