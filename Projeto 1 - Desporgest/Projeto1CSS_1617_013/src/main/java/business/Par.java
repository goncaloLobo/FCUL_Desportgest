package business;

/**
 * Um par de elementos que armazena dois elementos preservando a sua ordem.
 * 
 * @author css013
 * 
 */
public class Par<A, B> {

	private A itemA; // o primeiro item
	private B itemB; // o segundo item

	/**
	 * Cria um par imutavel com os dois items dados
	 * 
	 * @param itemA
	 *            O primeiro item
	 * @param itemB
	 *            O segundo item
	 */
	public Par(A itemA, B itemB) {
		this.itemA = itemA;
		this.itemB = itemB;
	}

	/**
	 * @return O primeiro item do par
	 */
	public A primeiro() {
		return itemA;
	}

	/**
	 * @return O segundo item do par
	 */
	public B segundo() {
		return itemB;
	}
}
