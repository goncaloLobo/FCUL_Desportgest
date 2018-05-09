package application;

import java.util.Date;
import java.util.List;

import business.CalendarioArbitroHandler;
import facade.exceptions.ApplicationException;

/**
 * Um servico que oferece operacoes para visualizar o calendario de encontros de
 * um arbitro. Esta classe tem o proposito de dar acesso ah camada de negocio e
 * assim esconde a sua implementacao dos clientes.
 * 
 * @author css013
 * 
 */
public class CalendarioEncontrosService {

	private CalendarioArbitroHandler calendarioArbitroHandler;

	/**
	 * Constroi um servico de associar um arbitro a um encontro dado o handler
	 * 
	 * @param calendarioArbitroHandler
	 *            O handler para visualizar calendario de um arbitro
	 */
	public CalendarioEncontrosService(CalendarioArbitroHandler calendarioArbitroHandler) {
		this.calendarioArbitroHandler = calendarioArbitroHandler;
	}

	/**
	 * Visualiza o calendario do arbitro com o numero federativo
	 * @param numFederativo Numero federativo do arbitro
	 * @throws ApplicationException Caso o arbitro nao exista
	 */
	public List<String> visualizarCalendario(int numFederativo) throws ApplicationException {
		return calendarioArbitroHandler.visualizarCalendario(numFederativo);
	}

	/**
	 * Visualiza lista de arbitros do encontro do arbitro escolhido
	 * @param data Data do encontro escolhido
	 * @throws ApplicationException Caso nao hajam arbitros atribuidos nesse encontro
	 */
	public List<Integer> escolheCalendario(Date data) throws ApplicationException {
		return calendarioArbitroHandler.escolheCalendario(data);
	}
}
