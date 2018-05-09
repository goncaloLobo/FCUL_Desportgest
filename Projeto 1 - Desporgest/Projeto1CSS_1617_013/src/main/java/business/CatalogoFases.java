package business;

import javax.persistence.EntityManager;

/**
 * Catalogo de fases
 * 
 * @author css013
 *
 */
public class CatalogoFases {

	private EntityManager em;

	/**
	 * Constroi um catalogo de arbitros dado um entity manager
	 */
	public CatalogoFases(EntityManager em) {
		this.em = em;
	}
	
	/**
	 * Adiciona uma fase dado o seu numero e a prova correspondente
	 * @param numFase Numero da fase
	 * @param prova Prova a que pertence
	 * @return Fase criada
	 */
	public Fase addFase(int numFase, Prova prova){
		Fase f = new Fase(numFase, prova);
		em.persist(f);
		return f;
	}
}
