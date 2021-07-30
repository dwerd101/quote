package ru.securities.service;

import ru.securities.model.Quote;

import java.util.List;
import java.util.Optional;

public interface SecuritiesService<T,ID> {

    List<T> findAll();
    T save(T t);
    T update(T t);
    void remove(T t);



}
