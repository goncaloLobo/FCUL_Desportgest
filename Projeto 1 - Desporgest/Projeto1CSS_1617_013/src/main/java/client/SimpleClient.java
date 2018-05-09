package client;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import application.ArbitroService;
import application.CalendarioEncontrosService;
import application.ProvaService;
import business.Periodicidade;
import facade.exceptions.ApplicationException;
import facade.startup.DesporGes;

public class SimpleClient {

	/**
	 * An utility class should not have public constructors
	 */
	private SimpleClient() {
	}
	
	
	public static void main(String[] args) {
		
		DesporGes app = new DesporGes();
        try {
            app.run();
            
            // Access both available services
            ProvaService ps = app.getProvaService();
            CalendarioEncontrosService cs1 = app.getCalendarioEncontrosService();
            ArbitroService as = app.getArbitroService();

            Calendar cal = Calendar.getInstance();
            cal.set(2017, Calendar.MAY, 6, 18, 50, 26);
            Date dataInicio = cal.getTime();
            
            //Adicionar uma nova prova do tipo taca e uma do tipo campeonato
            ps.adicionaProva("taca", "taca1", dataInicio, 4, 2, Periodicidade.SEMANAL);
            ps.adicionaProva("campeonato", "LIGA NOS", dataInicio, 4, 5, Periodicidade.SEMANAL);
            
            //Obter encontros da prova com designacao "taca1"
            List<String> encontros = as.getEncontros("taca1");
            System.out.println("Encontros sem informação completa dos árbitros:");
            for (String str1 : encontros) {
				System.out.println(str1);
			}
            
            //Associar os arbitros 123 e o 234 ao encontro com id 8
            as.associarArbitro(8, 123);
            as.associarArbitro(8, 234);
            System.out.println("Arbitros associados com sucesso");
            
            //Visualizar encontros do arbitro 123
            List<String> calendario = cs1.visualizarCalendario(123);
            for (String str2 : calendario) {
            	System.out.println(str2);
			}
            
            //Visualizar arbitros do encontro com a data de inicio
            List<Integer> arbitros = cs1.escolheCalendario(dataInicio);
            System.out.println("Os arbitros desse encontro sao:");
            for (Integer i : arbitros) {
				System.out.print(i + " ");
			}
            System.out.println();
            
            app.stopRun();
        } catch (ApplicationException e) {
        	System.out.println("Error: " + e.getMessage());
        }
    }
		
		
	}
