package com.cdisejemploDMJS.springboot.app.controller;

import java.util.List;
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
import com.cdisejemploDMJS.springboot.app.models.dao.ITarjetaDao;
import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;
import com.cdisejemploDMJS.springboot.app.services.ICuentaServices;
import com.cdisejemploDMJS.springboot.app.validator.CuentaValidator;


@Controller
@SessionAttributes("cuenta")
public class CuentaController {
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private ITarjetaDao tarjetaDao;
	@Autowired
	private ICuentaServices cuentaServices;
	@Autowired
	private CuentaValidator cuentaValidator;
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
		model.put("boton","Registrar ");
		return "formcuenta";
	}
	
	@RequestMapping(value ="/formcuenta/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model) {
		Cuenta cuenta = null;
		if (id != null && id>0) {
			cuenta = cuentaDao.findOne(id);
		}else {
			return "redirect:/listacuentas";
		}
		model.put("cuenta", cuenta);
		model.put("titulo","Editar cuenta");
		model.put("boton","Editar ");
		return "formcuenta";
	}
	
	@PostMapping(value = "/formcuenta")
	public String guardar(@Valid Cuenta cuenta, BindingResult result, Model model, SessionStatus status) {
		
		cuentaValidator.validate(cuenta,result);
		if(result.hasErrors()) {
			model.addAttribute("titulo","Llenar los datos de la cuenta");
			model.addAttribute("result", result.hasErrors());
			model.addAttribute("mensaje","Ocurrio un error");
			model.addAttribute("errList",result.getFieldError());
			model.addAttribute("boton","Registrar ");
			return "formcuenta";
		}else {
			model.addAttribute("result",false);
			model.addAttribute("errList","");
		}
		
		cuentaDao.save(cuenta);
		status.setComplete();
		model.addAttribute("titulo","La cuenta se ha creado con exito");
		model.addAttribute("boton","Registrar ");
		return "redirect:/formcuenta";
	}
	
	@RequestMapping("/eliminarcuenta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, Model model) {
		List<Tarjeta> list = tarjetaDao.findAll();
		if(id != null && id > 0 && !cuentaServices.searchInTarjetas(id, list)) {
			cuentaDao.delete(id);
			return "redirect:/listacuentas";
		}else {
			model.addAttribute("titulo","Lista de cuentas");
			model.addAttribute("cuentas",cuentaDao.findAll());
			model.addAttribute("mensaje","La cuenta no se puede eliminar porque contiene tarjetas, elimine las tarjetas de la cuenta y vuelva a intentarlo");
			return "listacuentas";
		}
		
	}
}
