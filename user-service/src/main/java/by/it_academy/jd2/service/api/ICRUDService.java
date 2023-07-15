package by.it_academy.jd2.service.api;

import java.util.List;

public interface ICRUDService<T, S, A> {

    List<T> findAll();

    T findById(Long id);

    T save(S item);

    void delete(A coordinates);

    T save(A coordinates, S item);
}

