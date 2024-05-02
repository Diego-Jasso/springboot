package com.cdisejemploDMJS.springboot.app.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cdisejemploDMJS.springboot.app.models.dao.ICuentaDao;
import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;


@Controller
@SessionAttributes("cuenta")
public class CuentaController {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@GetMapping("/listacuentas")
	public String cuentaLista(Model model) {
		model.addAttribute("titulo","Lista de cuentas");
		model.addAttribute("cuentas",cuentaDao.findAll());
		return "listacuentas";
	}

	@RequestMapping(value = "/formcuenta")
	public String crear(Map<String, Object> model){
		Cuenta cuenta = new Cuenta();
		model.put("cuenta", cuenta);
		model.put("titulo","Llenar los datos de la cuenta");
		return "formcuenta";
	}
	
	@RequestMapping(value ="/formcuenta/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Cuenta cuenta = null;
		if (id>0) {
			cuenta = cuentaDao.findOne(id);
		}else {
			return "redirect:/listacuentas";
		}
		model.put("cuenta", cuenta);
		model.put("titulo","Editar cuenta");
		return "formtarjeta";
	}
	
	@PostMapping(value = "/formcuenta")
	public String guardar(@Valid Cuenta cuenta, BindingResult result, Model model, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo","Ocurrio un error");
			for(FieldError error : result.getFieldErrors()) {
	            model.addAttribute(error.getField() + "Error", error.getDefaultMessage());
	        }
			return "formcuenta";
		}
		
		cuentaDao.save(cuenta);
		status.setComplete();
		model.addAttribute("titulo","La cuenta se ha creado con exito");
		return "formcuenta";
	}
	
	@DeleteMapping("/eliminarcuenta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if(id > 0 ) {
			cuentaDao.delete(id);
		}
		return "redirect:/listacuentas";
	}
}
