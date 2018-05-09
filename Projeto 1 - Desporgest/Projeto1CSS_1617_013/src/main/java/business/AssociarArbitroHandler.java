package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.ApplicationException;

/**
 * Controla o caso de uso de associar um arbitro a um encontro
 * 
 * @author css013
 *
 */
public class AssociarArbitroHandler {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	private EntityManagerFactory emf;

	/**
	 * Cria um handler para o caso de uso de associar um arbitro a um encontro
	 * 
	 * @param emf
	 *            The entity manager factory of the application
	 */
	public AssociarArbitroHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Visualiza os encontros da prova com a designacao
	 * @param designacao Designacao da prova
	 * @throws ApplicationException
	 *             No caso de nao existir uma prova com essa designacao
	 */
	public List<String> getEncontros(String designacao) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		CatalogoProvas catalogoProvas = new CatalogoProvas(em);
		List<String> encontros = new ArrayList<>(); //Lista com os encontros que nao tem informacao completa sobre os arbitros
		try {
			//Ir buscar a prova com a designacao
			Prova p = catalogoProvas.getProva(designacao);
			//Encontros dessa mesma prova
			List<Encontro> encontrosProva = p.getEncontros();
			for (Encontro encontro : encontrosProva) {
				//Se o numero de arbitros atribuidos for diferente do numero total de arbitros
				if (encontro.getArbitros().size() != p.getNumArbitrosEncontro()) {
					encontros.add(encontro.infoEncontro());
				}
			}
			return encontros;
		} catch (Exception e) {
			throw e;
		} finally {
			em.close();
		}
	}

	/**
	 * Associa o arbitro a um encontro
	 * 
	 * @param idEncontro
	 *            Id do encontro escolhido
	 * @param numFederativo
	 *            Numero federativo do arbitro a associar
	 * @throws ApplicationException
	 *             No caso de o encontro ou arbitro nao existirem, caso o
	 *             arbitro arbitre ambas as maos de um encontro, caso o arbitro
	 *             tenha um encontro no mesmo dia ou arbitre outro jogo na mesma
	 *             fase
	 */
	public void associarArbitro(int idEncontro, int numFederativo) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		CatalogoArbitros catalogoArbitros = new CatalogoArbitros(em);
		CatalogoEncontros catalogoEncontros = new CatalogoEncontros(em);
		boolean check = true;
		try {
			em.getTransaction().begin();
			Encontro e = catalogoEncontros.getEncontro(idEncontro); //Encontro com o id
			Arbitro a = catalogoArbitros.getArbitro(numFederativo); //Arbitro com o numFederativo
			Date dataEncontro = e.getData();
			List<Encontro> listaEncontrosArbitro = a.getCalendarioEncontros(); //Encontros do arbitro
			String tipo = e.getTipo(); //Tipo da prova
			while (check == true) {
				for (Encontro encontro : listaEncontrosArbitro) {
					//Se a prova for do tipo campeonato
					if (tipo.equals("campeonato")) {
						List<Participante> listaParticipantes = e.getParticipantes();
						Participante p1 = listaParticipantes.get(0);
						Participante p2 = listaParticipantes.get(listaParticipantes.size() - 1);
						List<Participante> listaAux = encontro.getParticipantes();
						//Se o arbitro ja arbitrar outro jogo com os mesmos participantes
						if (listaAux.contains(p1) && listaAux.contains(p2)) {
							check = false;
						}
					}
					//Se o arbitro ja tiver outro encontro na mesma data ou arbitrar outro jogo na mesma fase
					if (encontro.getData().equals(dataEncontro) || encontro.getFase().equals(e.getFase())) {
						check = false;
					}
				}
				check = false;
			}
			if (!check) {
				throw new Exception(" ");
			}
			e.addArbitro(a);
			a.addEncontro(e);
			em.getTransaction().commit();
		} catch (Exception e) {
			if (em.getTransaction().isActive())
				em.getTransaction().rollback();
			throw new ApplicationException("Erro a associar o arbitro", e);
		} finally {
			em.close();
		}
	}
}
