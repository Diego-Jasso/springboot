package com.cdisejemploDMJS.springboot.app.editors;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cdisejemploDMJS.springboot.app.models.dao.ICuentaDao;
import com.cdisejemploDMJS.springboot.app.services.ICuentaServices;

@Component
public class CuentaPropertyEditor extends PropertyEditorSupport {
	
	@Autowired
	private ICuentaServices cuentaService;
	
	@Autowired
	private ICuentaDao cuentaDao;
	
	@Override
	public void setAsText(String idStr) throws IllegalArgumentException {
		try {
			Long id = Long.parseLong(idStr);
			this.setValue(cuentaService.getById(id, cuentaDao.findAll()));
		}catch (Exception e) {
			System.out.println("Hubo un error: "+e);
		}
	}
	
	

}
