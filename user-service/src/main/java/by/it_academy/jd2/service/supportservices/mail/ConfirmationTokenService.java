package by.it_academy.jd2.service.supportservices.mail;

import by.it_academy.jd2.dao.repositories.IConfirmationTokenDao;
import by.it_academy.jd2.dao.entity.TokenEntity;
import by.it_academy.jd2.service.api.IConfirmationTokenService;
import by.it_academy.jd2.core.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmationTokenService implements IConfirmationTokenService {
    private static final String TOKEN_NOT_FOUND = "token not found!";
    private final IConfirmationTokenDao tokenDao;

    public ConfirmationTokenService(IConfirmationTokenDao tokenDao) {
        this.tokenDao = tokenDao;
    }

    @Override
    public void save(TokenEntity token) {
        this.tokenDao.save(token);
    }

    @Override
    public TokenEntity findByToken(UUID token) {
        Optional<TokenEntity> userOptional = this.tokenDao.findByToken(token);
        return userOptional.orElseThrow(() -> new EntityNotFoundException(TOKEN_NOT_FOUND));
    }

    @Override
    public void setConfirmedAt(UUID token) {
        TokenEntity entity = findByToken(token);
        entity.setConfirmedAt(LocalDateTime.now());
        this.tokenDao.save(entity);
    }
}
