package com.cdisejemploDMJS.springboot.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;

@Service
public class CuentaServiceImpl implements ICuentaServices {

	private List<Cuenta> lista;
	private List<Tarjeta> listaTarjetas;
	
	public CuentaServiceImpl() {
		
	}
	
	@Override
	public Cuenta getById(Long id, List<Cuenta> lista) {
		this.lista = lista;
		Cuenta cuentaResult = null;
		
		for (Cuenta cuenta: this.lista) {
			if(id == cuenta.getId()) {
				cuentaResult = cuenta;
				break;
			}
		}
		return cuentaResult;
	}
	
	public boolean searchInTarjetas(Long id, List<Tarjeta> lista) {
		this.listaTarjetas = lista;
		for(Tarjeta tarjeta: this.listaTarjetas) {
			if(tarjeta.getCuenta().getId() == id) {
				return true;
			}
		}
		return false;
	}

}
