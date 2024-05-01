package com.cdisejemploDMJS.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdisejemploDMJS.springboot.app.models.entity.Cuenta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CuentaDaoImpl implements ICuentaDao {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override	
	public List<Cuenta> findAll() {
		return em.createQuery("from cuentas").getResultList();
	}

	@Override
	@Transactional
	public void save(Cuenta cuenta) {
		if(cuenta.getId() != null && cuenta.getId() > 0) {
			em.merge(cuenta);
		}else {
			em.persist(cuenta);
		}
		
	}
	
	

}
