import java.util.ArrayList;
import java.util.List;

public class Vertice<T> {

	private T elemento;
	private List<Arista<T>> aristas;
	private boolean esVisitado;
	
	public Vertice(T elemento) {
		this.elemento = elemento;
		aristas = new ArrayList<>();
	}

	public boolean esVisitado() {
		return esVisitado;
	}

	public void setVisitado(boolean esVisitado) {
		this.esVisitado = esVisitado;
	}

	public T getElemento() {
		return elemento;
	}

	public void setElemento(T elemento) {
		this.elemento = elemento;
	}

	public List<Arista<T>> getAristas() {
		return aristas;
	}

	public void setAristas(List<Arista<T>> aristas) {
		this.aristas = aristas;
	}
	
	public void addArista(Arista<T> a) {
		aristas.add(a);
	}
	
	

}
