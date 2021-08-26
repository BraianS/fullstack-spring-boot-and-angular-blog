package com.github.braians.springblog.service;

public interface CrudService <T, P> {

    T save (P p);
    T update (Long id,P p);
    T findById(Long id);
    void deleteById(Long id);
}
