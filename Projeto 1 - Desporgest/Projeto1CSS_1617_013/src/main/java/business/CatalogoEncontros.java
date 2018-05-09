package business;

import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import facade.exceptions.ApplicationException;

/**
 * Catalogo de encontros
 * 
 * @author css013
 *
 */
public class CatalogoEncontros {

	private EntityManager em;

	/**
	 * Constroi um catalogo de encontros dado um entity manager
	 */
	public CatalogoEncontros(EntityManager em) {
		this.em = em;
	}

	/**
	 * Cria um novo encontro dado o tipo da prova, a data de inicio, a periodicidade, fase e os participantes
	 * @param tipoProva Tipo da prova (taca ou campeonato)
	 * @param dataInicio Data de inicio da prova
	 * @param periocidade Periodicidade da prova (SEMANAL, MENSAL ou SEMESTRAL)
	 * @param fase Fase da prova
	 * @param p1 Participante 1
	 * @param p2 Participante 2
	 * @return Encontro criado
	 */
	public Encontro addEncontro(String tipoProva, Date dataInicio, Periodicidade periocidade, Fase fase, Participante p1,
			Participante p2) {
		Encontro e = new Encontro(tipoProva, dataInicio, periocidade, fase, p1, p2);
		em.persist(e);
		return e;
	}

	/**
	 * Encontra um encontro dado o seu id
	 * @param id Id do encontro
	 * @return Encontro correspondente a esse id
	 * @throws ApplicationException Caso o encontro nao exista
	 */
	public Encontro getEncontro(int id) throws ApplicationException {
		TypedQuery<Encontro> query = em.createNamedQuery(Encontro.FIND_BY_ID, Encontro.class);
		query.setParameter(Encontro.ID, id);
		try {
			if(query.getSingleResult() != null)
				return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("O encontro nao foi encontrado", e);
		}
		return null;
	}
}