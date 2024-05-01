package com.cdisejemploDMJS.springboot.app.models.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cdisejemploDMJS.springboot.app.models.entity.Tarjeta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class TarjetaDaoImpl implements ITarjetaDao {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Tarjeta> findAll() {
		return em.createQuery("from tarjetas").getResultList();
	}

	@Transactional
	@Override
	public void save(Tarjeta tarjeta) {
		if(tarjeta.getId() != null && tarjeta.getId() > 0) {
			em.merge(tarjeta);
		}else {
			em.persist(tarjeta);
		}

	}

}
