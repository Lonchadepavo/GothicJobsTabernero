package com.loncha.gothicjobstabernero.objetos;

import org.bukkit.Material;

public class RecetaMortero {
	Material tipoIngrediente;
	String nombreIngrediente;
	
	Material tipoResultado;
	String nombreResultado;
	
	int usosMortero;
	
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

	public int getUsosMortero() {
		return usosMortero;
	}

	public void setUsosMortero(int usosMortero) {
		this.usosMortero = usosMortero;
	}
	
}
