package com.keiko.securityapp.service.common.impl;

import com.keiko.securityapp.entity.BaseEntity;
import com.keiko.securityapp.repository.common.AbstractCrudRepository;
import com.keiko.securityapp.service.common.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCrudService<E extends BaseEntity> implements AbstractCrudService<E> {

    @Autowired
    private AbstractCrudRepository<E> crudRepository;

    @Override
    public E save (E entity) {
        return crudRepository.save (entity);
    }

    @Override
    public E fetchBy (Long id) {
        return crudRepository.findById (id).orElseThrow ();
    }

    @Override
    public List<E> fetchAll () {
        return crudRepository.findAll ();
    }

    @Override
    public void delete (Long id) {
        E entity = this.fetchBy (id);
        crudRepository.delete (entity);
    }
}
