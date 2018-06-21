package it.polito.tdp.flightdelays.model;

import java.time.LocalDateTime;

public class Passeggero {
	
	private Airport origine;
	private Airport destinazione;
	private LocalDateTime partenza;
	private LocalDateTime arrivo;
	private int ritardo;
	
	
	public Passeggero(Airport origine, Airport destinazione, LocalDateTime partenza, LocalDateTime arrivo) {
		super();
		this.origine = origine;
		this.destinazione = destinazione;
		this.partenza = partenza;
		this.arrivo = arrivo;
		this.ritardo = 0;
	}


	public Airport getOrigine() {
		return origine;
	}


	public void setOrigine(Airport origine) {
		this.origine = origine;
	}


	public Airport getDestinazione() {
		return destinazione;
	}


	public void setDestinazione(Airport destinazione) {
		this.destinazione = destinazione;
	}


	public LocalDateTime getPartenza() {
		return partenza;
	}


	public void setPartenza(LocalDateTime partenza) {
		this.partenza = partenza;
	}


	public LocalDateTime getArrivo() {
		return arrivo;
	}


	public void setArrivo(LocalDateTime arrivo) {
		this.arrivo = arrivo;
	}


	public int getRitardo() {
		return ritardo;
	}


	public void setRitardo(int ritardo) {
		this.ritardo += ritardo;
	}
	
	
	
}
