package business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import facade.exceptions.ApplicationException;

/**
 * Controla o caso de uso de adicionar uma nova prova.
 * 
 * @author css013
 *
 */
public class AdicionaProvaHandler {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	private EntityManagerFactory emf;

	/**
	 * Cria um handler para o caso de uso de criar uma prova dada uma
	 * entitymanagerfactory
	 * 
	 * @param emf
	 *            The entity manager factory of the application
	 */
	public AdicionaProvaHandler(EntityManagerFactory emf) {
		this.emf = emf;
	}

	/**
	 * Adiciona uma nova prova.
	 * 
	 * @param tipo
	 *            Tipo da prova ("taca" ou "campeonato")
	 * @param designacao
	 *            Designacao da prova
	 * @param dataInicio
	 *            Data de inicio da prova
	 * @param numParticipantes
	 *            Numero de participantes da prova
	 * @param numArbitrosEncontro
	 *            Numero de arbitro do encontro
	 * @param periocidade
	 *            Periodicidade (SEMANAL, MENSAL, SEMESTRAL)
	 * @throws ApplicationException
	 *             Caso ja exista uma prova com a mesma designacao, caso a data
	 *             de inicio nao seja sabado ou domingo ou caso o num de
	 *             participantes na taca nao seja potencia de 2 e no campeonato
	 *             nao seja par
	 */
	public void adicionaProva(String tipo, String designacao, Date dataInicio, int numParticipantes,
			int numArbitrosEncontro, Periodicidade periocidade) throws ApplicationException {
		EntityManager em = emf.createEntityManager();
		CatalogoProvas catalogoProvas = new CatalogoProvas(em);
		CatalogoParticipantes catalogoParticipantes = new CatalogoParticipantes(em);
		CatalogoEncontros catalogoEncontros = new CatalogoEncontros(em);
		CatalogoFases catalogoFases = new CatalogoFases(em);

		try {
			em.getTransaction().begin();
			// Aceder as provas com esta designacao
			List<Prova> listaProvas = catalogoProvas.getProvas(designacao);
			// Se nao houver outra prova com essa designacao
			if (listaProvas.isEmpty()) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dataInicio);
				// Se o dia da data de inicio for um sabado ou domingo
				if (cal.get(Calendar.DAY_OF_WEEK) == 7 || cal.get(Calendar.DAY_OF_WEEK) == 8) {
					// Se o tipo da prova for taca e o numero de participantes
					// for potencia de 2
					if (tipo.equals("taca") && powerOf2(numParticipantes)) {
						Prova p = catalogoProvas.adicionaProva(tipo, designacao, dataInicio, numParticipantes,
								numArbitrosEncontro, periocidade);
						List<Participante> listaParts = new ArrayList<Participante>();
						// Criar participantes sequencialmente
						for (int i = 1; i <= numParticipantes; i++) {
							Participante part = catalogoParticipantes.addParticipante("part " + i, p);
							listaParts.add(part);
						}
						Random random = new Random();
						Random rnd = new Random();
						// Criar fase
						Fase fase = catalogoFases.addFase(1, p);
						// Formar varios pares de participantes aleatoriamente
						// Criar o respetivo encontro e adiciona-lo a prova
						for (int i = 1; i <= numParticipantes / 2; i++) {
							int aleatorio1 = random.nextInt(listaParts.size());
							Participante p1 = listaParts.get(aleatorio1);
							listaParts.remove(aleatorio1);
							int aleatorio2 = rnd.nextInt(listaParts.size());
							Participante p2 = listaParts.get(aleatorio2);
							Encontro e1 = catalogoEncontros.addEncontro(p.getTipo(), dataInicio, periocidade, fase, p1,
									p2);
							p.addEncontro(e1);
							listaParts.remove(aleatorio2);
						}
						// Se a prova for do tipo campeonato e o numero de
						// participantes for par
					} else if (tipo.equals("campeonato") && numParticipantes % 2 == 0) {
						Prova p = catalogoProvas.adicionaProva(tipo, designacao, dataInicio, numParticipantes,
								numArbitrosEncontro, periocidade);
						List<Participante> listaParts = new ArrayList<Participante>();
						// Criar participantes sequencialmente
						for (int i = 1; i <= numParticipantes; i++) {
							Participante part = catalogoParticipantes.addParticipante("part " + i, p);
							listaParts.add(part);
						}

						// Lista com os pares de participantes das primeiras
						// fases
						List<Par<Participante, Participante>> listaPares = new ArrayList<Par<Participante, Participante>>();
						for (int i = 0; i < listaParts.size(); i++) {
							for (int j = i + 1; j < listaParts.size(); j++) {
								Par<Participante, Participante> par = new Par<Participante, Participante>(
										listaParts.get(i), listaParts.get(j));
								listaPares.add(par);
							}
						}
						int numFase = 1;
						Fase fase;
						int inverso = listaPares.size() - 1;
						// Criar os dois encontros de cada fase com os
						// respetivos participantes agrupados e adiciona-los a
						// prova
						for (int i = 0; i <= (listaPares.size() / 2) - 1; i++) {
							fase = catalogoFases.addFase(numFase, p);
							Encontro e1 = catalogoEncontros.addEncontro(p.getTipo(), dataInicio, periocidade, fase,
									listaPares.get(i).primeiro(), listaPares.get(i).segundo());
							Encontro e2 = catalogoEncontros.addEncontro(p.getTipo(), dataInicio, periocidade, fase,
									listaPares.get(inverso).primeiro(), listaPares.get(inverso).segundo());
							p.addEncontro(e1);
							p.addEncontro(e2);
							inverso--;
							numFase++;
						}

						// Inverter a lista de participantes para fazer as maos inversas
						Collections.reverse(listaParts);

						// Lista com os pares de participantes das ultimas fases
						List<Par<Participante, Participante>> listaParesUltimos = new ArrayList<Par<Participante, Participante>>();
						for (int i = 0; i < listaParts.size(); i++) {
							for (int j = i + 1; j < listaParts.size(); j++) {
								Par<Participante, Participante> par = new Par<Participante, Participante>(
										listaParts.get(i), listaParts.get(j));
								listaParesUltimos.add(par);
							}
						}

						int inverso2 = listaParesUltimos.size() - 1;
						// Criar os dois encontros de cada fase com os
						// respetivos participantes agrupados e adiciona-los a
						// prova
						for (int i = 0; i <= (listaParesUltimos.size() / 2) - 1; i++) {
							fase = catalogoFases.addFase(numFase, p);
							Encontro e1 = catalogoEncontros.addEncontro(p.getTipo(), dataInicio, periocidade, fase,
									listaPares.get(i).segundo(), listaPares.get(i).primeiro());
							Encontro e2 = catalogoEncontros.addEncontro(p.getTipo(), dataInicio, periocidade, fase,
									listaPares.get(inverso2).segundo(), listaPares.get(inverso2).primeiro());
							p.addEncontro(e1);
							p.addEncontro(e2);
							inverso2--;
							numFase++;
						}
					//Caso o numero de participantes nao corresponda ao correto
					} else {
						throw new Exception(" ");
					}
				//Se o dia da data de inicio nao for sabado ou domingo
				} else {
					throw new Exception(" ");
				}
				em.getTransaction().commit();
			//Caso ja existe uma prova com a mesma designacao
			} else {
				throw new Exception(" ");
			}
		} catch (Exception e) {
			throw new ApplicationException("Erro a adicionar a prova", e);
		} finally {
			em.close();
		}

	}

	/**
	 * Verifica se um numero eh potencia de 2
	 * 
	 * @param number
	 *            numero a avaliar
	 * @return true caso seja potencia de 2 false caso contrario
	 */
	private static boolean powerOf2(int number) {
		if (number <= 0) {
			return false;
		}

		while (number > 1) {
			if (number % 2 != 0) {
				return false;
			}
			number = number / 2;
		}
		return true;
	}
}
