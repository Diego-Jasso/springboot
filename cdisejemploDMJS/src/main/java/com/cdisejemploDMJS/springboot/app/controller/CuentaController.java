package com.cdisejemploDMJS.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cdisejemploDMJS.springboot.app.models.dao.ICuentaDao;
import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;


@Controller
public class CuentaController {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@GetMapping("/listacuentas")
	public String cuentaLista(Model model) {
		model.addAttribute("titulo","Lista de cuentas");
		model.addAttribute("cuentas",cuentaDao.findAll());
		return "listacuentas";
	}

	@PostMapping("/guardarcuenta")
	public String guardarCuenta(@ModelAttribute("cuenta") Cuenta cuenta){
		cuentaDao.save(cuenta);
		return "redirect:/listacuentas";
	}
}
