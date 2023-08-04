package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.TokenEntity;

import java.util.UUID;

public interface IConfirmationTokenService {
    void save(TokenEntity token);

    TokenEntity findByToken (UUID token);

    void setConfirmedAt (UUID token);
}
