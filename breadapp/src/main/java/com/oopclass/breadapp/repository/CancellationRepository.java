package com.oopclass.breadapp.repository;

import com.oopclass.breadapp.models.Cancellation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Herradura
 */
public interface CancellationRepository extends JpaRepository<Cancellation, Long> {
    
}
