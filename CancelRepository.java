package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Cancel;

/**
 * OOP Class 20-21
 * @author Gerald Villaran
 */

@Repository
public interface CancelRepository extends JpaRepository<Cancel, Long> {

	//Cancel findByEmail(String email);
}
