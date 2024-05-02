package com.cdisejemploDMJS.springboot.app.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;

@Component
public class CuentaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Cuenta.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Cuenta cuenta = (Cuenta)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nombre", "NotEmpty.cuenta.nombre");
		
		if(!cuenta.getNombre().matches("[a-z,A-Z]{1,15}?[ ]?[a-z,A-Z]{1,15}")) {
			errors.rejectValue("nombre","format.cuenta.nombre");
		}
		
		if(cuenta.getSaldo()<100) {
			errors.rejectValue("saldo","format.cuenta.saldo");
		}
		
		if(cuenta.getNumeroTelefono().length()!=10 ) {
			errors.rejectValue("numeroTelefono","format.cuenta.numero");
		}

	}
}
