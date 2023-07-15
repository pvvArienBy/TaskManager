package by.it_academy.jd2.service.api;

import java.util.List;
import java.util.UUID;

public interface ICRUDService<T, S> {

    List<T> findAll();

    T findById(UUID uuid);

    T save(S item);

    void delete(UUID uuid, Long version);

    T save(UUID uuid, Long version, S item);
}

