package by.it_academy.jd2.dao.api;

import java.util.List;

public interface ICRUDDao<T> {

    List<T> get();

    T get(Long id);

    T add(T item);

    void remove(T item);

    void update(T item);

}
