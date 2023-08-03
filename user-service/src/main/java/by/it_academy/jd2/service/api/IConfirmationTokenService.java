package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.ConfirmationTokenEntity;

import java.util.UUID;

public interface IConfirmationTokenService {
    void save(ConfirmationTokenEntity token);

    ConfirmationTokenEntity findByToken (UUID token);

    void setConfirmedAt (UUID token);
}
