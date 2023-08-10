package by.it_academy.jd2.service.api;

import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ICRUService<T, S> {

    T get(UUID uuid);

    T save(@Valid S item);

    T update(UUID uuid, LocalDateTime version, S item);
}

