package application;

import java.util.List;

import business.AssociarArbitroHandler;
import facade.exceptions.ApplicationException;

/**
 * Um servico que oferece operacoes para associar um arbitro a um encontro. Esta
 * classe tem o proposito de dar acesso ah camada de negocio e assim esconde a
 * sua implementacao dos clientes.
 * 
 * @author css013
 * 
 */
public class ArbitroService {

	private AssociarArbitroHandler arbitroHandler;

	/**
	 * Constroi um servico de associar um arbitro a um encontro dado o handler
	 * 
	 * @param arbitroHandler
	 *            O handler para associar o arbitro
	 */
	public ArbitroService(AssociarArbitroHandler arbitroHandler) {
		this.arbitroHandler = arbitroHandler;
	}

	/**
	 * Obtem os encontros de uma prova com a designacao
	 * 
	 * @param designacao
	 *            Designacao da prova
	 * @throws ApplicationException
	 *             No caso de nao existir uma prova com essa designacao
	 */
	public List<String> getEncontros(String designacao) throws ApplicationException {
		return arbitroHandler.getEncontros(designacao);
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
		arbitroHandler.associarArbitro(idEncontro, numFederativo);
	}

}
