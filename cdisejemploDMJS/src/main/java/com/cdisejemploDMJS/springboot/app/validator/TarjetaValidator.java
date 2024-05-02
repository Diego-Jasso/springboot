package com.cdisejemploDMJS.springboot.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.cdisejemploDMJS.springboot.app.models.dao.CuentaDaoImpl;
import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;

@Controller
public class TarjetaValidator implements Validator {

	@Autowired
	public CuentaDaoImpl cuentaDao;
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Tarjeta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Tarjeta tarjeta = (Tarjeta)target;
		
		if(tarjeta.getNumeroTarjeta().length()!=16) {
			errors.rejectValue("numeroTarjeta","format.tarjeta.numeroTarjeta");
		}
		
		if(tarjeta.getIcv().length()!=3) {
			errors.rejectValue("icv","format.tarjeta.icv");
		}
		
		if(cuentaDao.findOne(tarjeta.getCuenta().getId()) == null) {
			errors.rejectValue("cuenta","format.tarjeta.cuenta");
		}
	}

}
