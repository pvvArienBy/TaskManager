package by.it_academy.jd2.service.api;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ICRUDService<T, S> {

    List<T> findAll();

    T findById(UUID uuid);

    T save(@Valid S item);

    T save(UUID uuid, LocalDateTime version, S item);
}

