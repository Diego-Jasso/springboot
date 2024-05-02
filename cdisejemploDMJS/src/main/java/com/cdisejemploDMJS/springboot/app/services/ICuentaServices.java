package com.cdisejemploDMJS.springboot.app.services;

import java.util.List;

import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;

public interface ICuentaServices {

	public Cuenta getById(Long id, List<Cuenta> lista);
	
	public boolean searchInTarjetas(Long id, List<Tarjeta> lista);
}
