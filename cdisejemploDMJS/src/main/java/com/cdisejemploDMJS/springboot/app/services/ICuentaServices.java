package com.cdisejemploDMJS.springboot.app.services;

import java.util.List;

import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;

public interface ICuentaServices {

	public Cuenta getById(Long id, List<Cuenta> lista);
}
