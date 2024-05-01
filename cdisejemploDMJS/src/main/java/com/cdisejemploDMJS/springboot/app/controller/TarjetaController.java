package com.cdisejemploDMJS.springboot.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.cdisejemploDMJS.springboot.app.models.dao.ITarjetaDao;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;

@Controller
public class TarjetaController {
 
	@Autowired
	private ITarjetaDao tarjetaDao;
	
	@GetMapping("/listatarjetas")
	public String cuentaLista(Model model) {
		model.addAttribute("titulo","Lista de tarjetas");
		model.addAttribute("tarjetas",tarjetaDao.findAll());
		return "listatarjetas";
	}
	
	@PostMapping("/guardartarjeta")
	public String guardarCuenta(@ModelAttribute("tarjeta") Tarjeta tarjeta){
		tarjetaDao.save(tarjeta);
		return "redirect:/listatarjetas";
	}
}
