package by.it_academy.jd2.service.supportservices.mail;

import by.it_academy.jd2.dao.entity.TokenEntity;
import by.it_academy.jd2.dao.repositories.IConfirmationTokenDao;
import by.it_academy.jd2.service.api.IConfirmationTokenService;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService {
    private static final String TOKEN_NOT_FOUND = "token not found!";
    private final IConfirmationTokenDao tokenDao;

    public ConfirmationTokenService(IConfirmationTokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Transactional
    @Override
    public void save(TokenEntity token) {
        this.tokenDao.saveAndFlush(token);
    }

    @Transactional(readOnly = true)
    @Override
    public TokenEntity findByToken(UUID token) {
        return this.tokenDao.findByToken(token)
                .orElseThrow(()
                        -> new EntityNotFoundException(TOKEN_NOT_FOUND));
    }

    @Transactional
    @Override
    public void setConfirmedAt(UUID token) {
        TokenEntity entity = findByToken(token);
        entity.setConfirmedAt(LocalDateTime.now());
        this.tokenDao.saveAndFlush(entity);
    }
}
