package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.api.IConfirmationTokenDao;
import by.it_academy.jd2.dao.entity.ConfirmationTokenEntity;
import by.it_academy.jd2.service.api.IConfirmationTokenService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService {
    private final IConfirmationTokenDao tokenDao;

    public ConfirmationTokenService(IConfirmationTokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public void save(ConfirmationTokenEntity token) {
        this.tokenDao.save(token);
    }

    @Override
    public ConfirmationTokenEntity findByToken(UUID token) {
        Optional<ConfirmationTokenEntity> userOptional = this.tokenDao.findByToken(token);
        return userOptional.orElseThrow(() -> new EntityNotFoundException("token not found!"));
    }

    @Override
    public void setConfirmedAt(UUID token) {
        ConfirmationTokenEntity entity = findByToken(token);
        entity.setConfirmedAt(LocalDateTime.now());
        this.tokenDao.save(entity);
    }
}
