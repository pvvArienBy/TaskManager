package by.it_academy.jd2.service.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ICRUDService<T, S> {
    List<T> findAll();
    T findById(UUID uuid);
    T save(S item);
    //todo ref
//    void delete(UUID uuid, Long version);
    T save(UUID uuid, LocalDateTime version, S item);
}

