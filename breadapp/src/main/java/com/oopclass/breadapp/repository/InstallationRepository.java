/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oopclass.breadapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oopclass.breadapp.models.Installation;
/**
 *
 * @author Herradura
 */
@Repository
public interface InstallationRepository extends JpaRepository<Installation, Long> {
    
}
