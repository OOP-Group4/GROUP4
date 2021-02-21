package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Rating;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

	//Rating findByEmail(String email);
}
