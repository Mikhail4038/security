package com.keiko.securityapp.controller;

import com.keiko.securityapp.service.common.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

public class DefaultCrudController<E, D> {

    @Autowired
    private AbstractCrudService<E> crudService;

    @Autowired
    private Function<E, D> entityToDtoConverter;

    @Autowired
    private Function<D, E> dtoToEntityConverter;

    @PostMapping ("/save")
    public ResponseEntity<D> save (@RequestBody D dto) {
        E entity = dtoToEntityConverter.apply (dto);
        E saved = crudService.save (entity);
        D actual = entityToDtoConverter.apply (saved);
        return ResponseEntity.status (HttpStatus.OK).body (actual);
    }

    @GetMapping ("/fetchBy")
    public ResponseEntity<D> fetchBy (@RequestParam Long id) {
        E entity = crudService.fetchBy (id);
        D dto = entityToDtoConverter.apply (entity);
        return ResponseEntity.status (HttpStatus.OK).body (dto);
    }

    @GetMapping ("/fetchAll")
    public ResponseEntity<List<D>> fetchAll () {
        List<E> entities = crudService.fetchAll ();
        List<D> dto = entities.stream ().map ((e) -> entityToDtoConverter.apply (e)).toList ();
        return ResponseEntity.status (HttpStatus.OK).body (dto);
    }

    @DeleteMapping ("/delete")
    public ResponseEntity delete (@RequestParam Long id) {
        crudService.delete (id);
        return ResponseEntity.status (HttpStatus.OK).build ();
    }
}
