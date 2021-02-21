package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Rating;
import com.oopclass.breadapp.repository.RatingRepository;
import com.oopclass.breadapp.services.IRatingService;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Service
public class RatingService implements IRatingService {
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Override
	public Rating save(Rating entity) {
		return ratingRepository.save(entity);
	}

	@Override
	public Rating update(Rating entity) {
		return ratingRepository.save(entity);
	}

	@Override
	public void delete(Rating entity) {
		ratingRepository.delete(entity);
	}

	@Override
	public void delete(Long id) {
		ratingRepository.deleteById(id);
	}

	@Override
	public Rating find(Long id) {
		return ratingRepository.findById(id).orElse(null);
	}

	@Override
	public List<Rating> findAll() {
		return ratingRepository.findAll();
	}

	@Override
	public void deleteInBatch(List<Rating> ratings) {
		ratingRepository.deleteInBatch(ratings);
	}
	
}
