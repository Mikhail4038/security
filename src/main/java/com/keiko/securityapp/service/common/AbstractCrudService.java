package com.keiko.securityapp.service.common;

import java.util.List;

public interface AbstractCrudService<E> {
    E save (E entity);

    E fetchBy (Long id);

    List<E> fetchAll ();

    void delete (Long id);
}
