package com.cdisejemploDMJS.springboot.app.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cdisejemploDMJS.springboot.app.editors.CuentaPropertyEditor;
import com.cdisejemploDMJS.springboot.app.models.dao.ICuentaDao;
import com.cdisejemploDMJS.springboot.app.models.dao.ITarjetaDao;
import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;
import com.cdisejemploDMJS.springboot.app.validator.TarjetaValidator;

@Controller
@SessionAttributes("tarjeta")
public class TarjetaController {
 
	@Autowired
	private ITarjetaDao tarjetaDao;
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Autowired
	private CuentaPropertyEditor cuentaEditor;
	
	@Autowired
	private TarjetaValidator tarjetaValidator;
	
	@InitBinder
	public void InitBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Cuenta.class, "cuenta",cuentaEditor);
	}
	
    
	@GetMapping("/listatarjetas")
	public String tarjetaLista(Model model) {
		model.addAttribute("titulo","Lista de tarjetas");
		model.addAttribute("tarjetas",tarjetaDao.findAll());
		return "listatarjetas";
	}
	
	@RequestMapping(value = "/formtarjeta")
	public String crear(Map<String, Object> model, Model modelList){
		Tarjeta tarjeta = new Tarjeta();
		List<Cuenta> listaCuentas = cuentaDao.findAll();
		model.put("tarjeta", tarjeta);
		modelList.addAttribute("listaCuentas",listaCuentas);
		model.put("titulo","Llenar los datos de una tarjeta");
		model.put("boton","Registrar ");
		return "formtarjeta";
	}
	
	@RequestMapping(value = "/formtarjeta/{id}")
	public String editar(@PathVariable(value="id") Long id, Map<String, Object> model,Model modelList) {
		Tarjeta tarjeta = null;
		List<Cuenta> listaCuentas = cuentaDao.findAll();
		modelList.addAttribute("listaCuentas",listaCuentas);
		if (id != null && id>0) {
			tarjeta = tarjetaDao.findOne(id);
		}else {
			return "redirect:/listatarjetas";
		}
		model.put("tarjeta", tarjeta);
		model.put("boton","Editar ");
		model.put("titulo","Editar tarjeta");
		return "formtarjeta";
	}
	
	@PostMapping(value="/formtarjeta")
	public String guardar(@Valid Tarjeta tarjeta, BindingResult result, Model model, SessionStatus status, RedirectAttributes flash) {
		List<Cuenta> listaCuentas = cuentaDao.findAll();
		model.addAttribute("listaCuentas",listaCuentas);
		tarjetaValidator.validate(tarjeta,result);
		if(result.hasErrors()) {
			model.addAttribute("titulo","Formulario de tarjeta");
			model.addAttribute("result",result.hasErrors());
			model.addAttribute("mensaje","Ocurrio un error");
			model.addAttribute("errList",result.getFieldError());
			model.addAttribute("boton","Registrar ");
			return "formtarjeta";
		}else {
			model.addAttribute("result",false);
			model.addAttribute("errList","");
		}
		model.addAttribute("boton","Registrar ");
		model.addAttribute("titulo","Formulario de tarjeta");
		model.addAttribute("mensaje","Se envio la información correctamente");
		try {
			tarjetaDao.save(tarjeta);
		}catch (Exception e) {
			e.printStackTrace();
			flash.addFlashAttribute("mensaje",e.getMessage());
		}
		
		status.setComplete();
		return "redirect:/formtarjeta";
	}
	
	@RequestMapping(value="/eliminartarjeta/{id}")
	public String eliminar(@PathVariable(value = "id") Long id) {
		if(id != null && id > 0 ) {
			tarjetaDao.delete(id);
		}
		return "redirect:/listatarjetas";
	}
}
