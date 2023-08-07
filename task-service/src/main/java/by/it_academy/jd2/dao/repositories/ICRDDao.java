package by.it_academy.jd2.dao.repositories;

public interface ICRDDao <T> {

    T get(Long id);

    T add(T item);

    void remove(T item);

}