package business;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

/**
 * Catalogo de partiicipantes
 * 
 * @author css013
 *
 */
public class CatalogoParticipantes {

	private EntityManager em;
	
	/**
	 * Constroi um catalogo de arbitros dado um entity manager
	 */
	public CatalogoParticipantes(EntityManager em){
		this.em = em;
	}
	
	/**
	 * Cria um novo participante dado o seu nome e a prova em que participa
	 * @param nome Nome do participante
	 * @param prova Prova em que participa
	 */
	public Participante addParticipante(String nome, Prova prova){
		Participante p = new Participante(nome, prova);
		em.persist(p);
		return p;
	}
	
	/**
	 * Encontra os participantes dado uma prova
	 * @param p Prova
	 * @return Os participantes de uma prova
	 * @throws ApplicationException Caso nao exista nenhum participante associado
	 */
	public List<Participante> getParticipantesProva(Prova p) throws ApplicationException {
		TypedQuery<Participante> query = em.createNamedQuery(Participante.FIND_BY_ID_PROVA, Participante.class);
		query.setParameter(Participante.PROVA, p);
		try {
			return query.getResultList();
		} catch (PersistenceException e) {
			throw new ApplicationException("A prova com essa designacao nao foi encontrada", e);
		}
	}
}
