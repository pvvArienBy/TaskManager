package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.ConfirmationTokenEntity;

public interface IConfirmationTokenService {
    void save(ConfirmationTokenEntity token);

    ConfirmationTokenEntity findByToken (String token);

    void setConfirmedAt (String token);
}
