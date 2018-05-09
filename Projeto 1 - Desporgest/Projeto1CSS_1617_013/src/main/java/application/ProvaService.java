package application;

import java.util.Date;

import business.AdicionaProvaHandler;
import business.Periodicidade;
import facade.exceptions.ApplicationException;

/**
 * Um servico que oferece operacoes para adicionar uma prova. Esta classe tem o
 * proposito de dar acesso ah camada de negocio e assim esconde a sua
 * implementacao dos clientes.
 * 
 */
public class ProvaService {

	private AdicionaProvaHandler provaHandler;

	/**
	 * Constroi um servico de adicionar uma prova dado o handler
	 * 
	 * @param provaHandler
	 *            O handler que cria uma nova prova
	 */
	public ProvaService(AdicionaProvaHandler provaHandler) {
		this.provaHandler = provaHandler;
	}

	/**
	 * Adiciona uma nova prova
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
		provaHandler.adicionaProva(tipo, designacao, dataInicio, numParticipantes, numArbitrosEncontro, periocidade);
	}
}
