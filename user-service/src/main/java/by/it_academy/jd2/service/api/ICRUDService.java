package by.it_academy.jd2.service.api;

import java.util.List;

public interface ICRUDService<T, S, A> {

    List<T> get();

    T get(Long id);

    T add (S item);

    void remove(Long id);

    void update(A coordinates, S item);
}

