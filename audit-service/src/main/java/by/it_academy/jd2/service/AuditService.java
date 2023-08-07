package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.dao.repositories.IAuditDao;
import by.it_academy.jd2.dao.entity.AuditEntity;
import by.it_academy.jd2.service.api.IAuditService;
import by.it_academy.jd2.service.exceptions.EntityNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private final IAuditDao auditDao;
    private final ConversionService conversionService;

    public AuditService(IAuditDao auditDao, ConversionService conversionService) {
        this.auditDao = auditDao;
        this.conversionService = conversionService;
    }

    @Override
    public Page<AuditEntity> findAll(PageRequest pageRequest) {
        return this.auditDao.findAll(pageRequest);
    }

    @Override
    public AuditEntity findById(UUID uuid) {
        Optional<AuditEntity> userOptional = this.auditDao.findById(uuid);

        return userOptional.orElseThrow(() -> new EntityNotFoundException("Объект не найден!"));
    }

    @Override
    public AuditEntity save(AuditCreateDTO item) {
        return this.auditDao.save(
                Objects.requireNonNull(
                        this.conversionService.convert(
                                item, AuditEntity.class)));
    }
}
