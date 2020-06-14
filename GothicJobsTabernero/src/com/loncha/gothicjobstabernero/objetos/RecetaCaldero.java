package com.loncha.gothicjobstabernero.objetos;

import java.util.ArrayList;
import java.util.List;

public class RecetaCaldero {
	String nombreResultado;
	int dataResultado;
	int cantidadResultado;
	
	public List<String> nombreIngredientes = new ArrayList<String>();
	public List<Integer> dataIngredientes = new ArrayList<Integer>();
	public List<Integer> cantidadIngredientes = new ArrayList<Integer>();
	
	String herramienta;
	Boolean calor;
	int tiempo;
	int nivel;
	
	String profesion;
	
	public String getNombreResultado() {
		return nombreResultado;
	}
	
	public void setNombreResultado(String nombreResultado) {
		this.nombreResultado = nombreResultado;
	}
	
	public int getDataResultado() {
		return dataResultado;
	}
	
	public void setDataResultado(int dataResultado) {
		this.dataResultado = dataResultado;
	}
	
	public int getCantidadResultado() {
		return cantidadResultado;
	}
	
	public void setCantidadResultado(int cantidadResultado) {
		this.cantidadResultado = cantidadResultado;
	}
	
	public List<String> getNombreIngredientes() {
		return nombreIngredientes;
	}
	
	public void setNombreIngredientes(List<String> nombreIngredientes) {
		this.nombreIngredientes = nombreIngredientes;
	}
	
	public List<Integer> getDataIngredientes() {
		return dataIngredientes;
	}
	
	public void setDataIngredientes(List<Integer> dataIngredientes) {
		this.dataIngredientes = dataIngredientes;
	}
	
	public List<Integer> getCantidadIngredientes() {
		return cantidadIngredientes;
	}
	
	public void setCantidadIngredientes(List<Integer> cantidadIngredientes) {
		this.cantidadIngredientes = cantidadIngredientes;
	}
	
	public String getHerramienta() {
		return herramienta;
	}
	
	public void setHerramienta(String herramienta) {
		this.herramienta = herramienta;
	}
	
	public Boolean getCalor() {
		return calor;
	}
	
	public void setCalor(Boolean calor) {
		this.calor = calor;
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

	public String getProfesion() {
		return profesion;
	}

	public void setProfesion(String profesion) {
		this.profesion = profesion;
	}
	
	
	
	
}
