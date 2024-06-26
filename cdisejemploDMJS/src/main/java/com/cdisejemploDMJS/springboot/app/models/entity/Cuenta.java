package com.cdisejemploDMJS.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "cuentas")
public class Cuenta implements Serializable{

	private static final long serialVersionUID = 5920296537665679330L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String Nombre;
	
	@Column
	private double saldo;
	
	@Column(name = "numero_telefono")
	private String numeroTelefono;
	
	@Column(name = "dia_creacion")
	@NotBlank(message = "Field is required")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date diaCreacion;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cuenta", cascade = CascadeType.MERGE)
	private List<Tarjeta> tarjetas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return Nombre;
	}

	public void setNombre(String nombre) {
		Nombre = nombre;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public Date getDiaCreacion() {
		return diaCreacion;
	}

	public void setDiaCreacion(Date diaCreacion) {
		this.diaCreacion = diaCreacion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
