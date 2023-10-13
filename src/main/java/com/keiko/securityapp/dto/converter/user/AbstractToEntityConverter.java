package com.keiko.securityapp.dto.converter.user;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

public abstract class AbstractToEntityConverter<D, E> implements Function<D, E> {

    private final Class<D> dtoClass;
    private final Class<E> entityClass;

    @Autowired
    private ModelMapper modelMapper;

    public AbstractToEntityConverter (Class<D> dtoClass, Class<E> entityClass) {
        this.dtoClass = dtoClass;
        this.entityClass = entityClass;
    }

    @Override
    public E apply (D dto) {
        return modelMapper.map (dto, entityClass);
    }

    private Converter<D, E> converter = context -> {
        D dto = context.getSource ();
        E entity = context.getDestination ();
        mapSpecificFields (dto, entity);
        return entity;
    };

    public abstract void mapSpecificFields (D dto, E entity);
}
