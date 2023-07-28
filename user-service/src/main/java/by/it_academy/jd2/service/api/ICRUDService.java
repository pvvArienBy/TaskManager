package by.it_academy.jd2.service.api;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ICRUDService<T, S> {

    T findById(UUID uuid);

    T save(@Valid S item);

    T save(UUID uuid, LocalDateTime version, S item);
}

