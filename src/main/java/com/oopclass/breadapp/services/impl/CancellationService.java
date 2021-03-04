package com.oopclass.breadapp.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oopclass.breadapp.models.Cancellation;
import com.oopclass.breadapp.repository.CancellationRepository;
import com.oopclass.breadapp.services.ICancellationService;

/**
 * OOP Class 20-21
 *
 * @author Gerald Villaran
 */
@Service
public class CancellationService implements ICancellationService {

    @Autowired
    private CancellationRepository cancellationRepository;

    @Override
    public Cancellation save(Cancellation entity) {
        return cancellationRepository.save(entity);
    }

    @Override
    public Cancellation update(Cancellation entity) {
        return cancellationRepository.save(entity);
    }

    @Override
    public void delete(Cancellation entity) {
        cancellationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        cancellationRepository.deleteById(id);
    }

    @Override
    public Cancellation find(Long id) {
        return cancellationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cancellation> findAll() {
        return cancellationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<Cancellation> reservations) {
        cancellationRepository.deleteInBatch(reservations);
    }


}
