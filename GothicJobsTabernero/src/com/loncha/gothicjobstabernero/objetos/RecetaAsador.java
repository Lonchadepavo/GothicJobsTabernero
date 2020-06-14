package com.loncha.gothicjobstabernero.objetos;

import org.bukkit.Material;

public class RecetaAsador {
	Material tipoIngrediente;
	String nombreIngrediente;
	
	Material tipoResultado;
	String nombreResultado;
	
	int tiempo;
	int nivel;
	int piezas;
	public Material getTipoIngrediente() {
		return tipoIngrediente;
	}
	public void setTipoIngrediente(Material tipoIngrediente) {
		this.tipoIngrediente = tipoIngrediente;
	}
	public String getNombreIngrediente() {
		return nombreIngrediente;
	}
	public void setNombreIngrediente(String nombreIngrediente) {
		this.nombreIngrediente = nombreIngrediente;
	}
	public Material getTipoResultado() {
		return tipoResultado;
	}
	public void setTipoResultado(Material tipoResultado) {
		this.tipoResultado = tipoResultado;
	}
	public String getNombreResultado() {
		return nombreResultado;
	}
	public void setNombreResultado(String nombreResultado) {
		this.nombreResultado = nombreResultado;
	}
	public int getTiempo() {
		return tiempo;
	}
	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getPiezas() {
		return piezas;
	}
	public void setPiezas(int piezas) {
		this.piezas = piezas;
	}
	
	
}
