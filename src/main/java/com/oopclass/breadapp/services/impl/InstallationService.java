/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.oopclass.breadapp.services.impl;

import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.oopclass.breadapp.services.IInstallationService;
import com.oopclass.breadapp.repository.InstallationRepository;


/**
 *
 * @author Herradura
 */

@Service

public class InstallationService implements IInstallationService {

    @Autowired
    private InstallationRepository installationRepository;

    @Override
    public com.oopclass.breadapp.models.Installation save(com.oopclass.breadapp.models.Installation entity) {
        return installationRepository.save(entity);
    }

    @Override
    public com.oopclass.breadapp.models.Installation update(com.oopclass.breadapp.models.Installation entity) {
        return installationRepository.save(entity);
    }

    @Override
    public void delete(com.oopclass.breadapp.models.Installation entity) {
        installationRepository.delete(entity);
    }

    @Override
    public void delete(Long id) {
        installationRepository.deleteById(id);
    }

    @Override
    public com.oopclass.breadapp.models.Installation find(Long id) {
        return installationRepository.findById(id).orElse(null);
    }

    @Override
    public List<com.oopclass.breadapp.models.Installation> findAll() {
        return installationRepository.findAll();
    }

    @Override
    public void deleteInBatch(List<com.oopclass.breadapp.models.Installation> installation) {
        installationRepository.deleteInBatch(installation);
    }
    
}
