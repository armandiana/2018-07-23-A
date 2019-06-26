package it.polito.tdp.newufosightings.model;

public class StatiAvvistamenti {
	private String statoVicino;
	private Integer numeroAvvistamenti; //con stesso anno e stessa forma
	
	public StatiAvvistamenti(String statoVicino, Integer numeroAvvistamenti) {
		super();
		this.statoVicino = statoVicino;
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	public String getStatoVicino() {
		return statoVicino;
	}
	public void setStatoVicino(String statoVicino) {
		this.statoVicino = statoVicino;
	}
	public Integer getNumeroAvvistamenti() {
		return numeroAvvistamenti;
	}
	public void setNumeroAvvistamenti(Integer numeroAvvistamenti) {
		this.numeroAvvistamenti = numeroAvvistamenti;
	}
	@Override
	public String toString() {
		return  statoVicino + "- numeroAvvistamenti=" + numeroAvvistamenti;
	}
	
}
