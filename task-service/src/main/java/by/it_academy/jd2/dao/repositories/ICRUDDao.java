package by.it_academy.jd2.dao.repositories;

import java.util.List;

public interface ICRUDDao<T> extends ICRDDao<T>  {

    List<T> get();

    void update(T item);

}

