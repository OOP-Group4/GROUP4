package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Cancel;
import com.oopclass.breadapp.repository.CancelRepository;
import com.oopclass.breadapp.services.ICancelService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class CancelService implements ICancelService {
	
	@Autowired
	private CancelRepository cancelRepository;
	
	@Override
	public Cancel save(Cancel entity) {
		return cancelRepository.save(entity);
	}

	@Override
	public Cancel update(Cancel entity) {
		return cancelRepository.save(entity);
	}

	@Override
	public void delete(Cancel entity) {
		cancelRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		cancelRepository.deleteById(id);
	}

	@Override
	public Cancel find(Long id) {
		return cancelRepository.findById(id).orElse(null);
	}

	@Override
	public List<Cancel> findAll() {
		return cancelRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Cancel> cancels) {
		cancelRepository.deleteInBatch(cancels);
	}
	
}
