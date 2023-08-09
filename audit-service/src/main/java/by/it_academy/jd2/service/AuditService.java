package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.AuditEntity;
import by.it_academy.jd2.dao.repositories.IAuditDao;
import by.it_academy.jd2.service.api.IAuditService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private static final String RECORD_NOT_FOUND_ERROR = "Record not found, please try again!";

    private final IAuditDao auditDao;
    private final ConversionService conversionService;

    public AuditService(IAuditDao auditDao, ConversionService conversionService) {
        this.auditDao = auditDao;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AuditEntity> findAll(PageRequest pageRequest) {
        return this.auditDao.findAll(pageRequest);
    }

    @Transactional(readOnly = true)
    @Override
    public AuditEntity findById(UUID uuid) {
        return this.auditDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(RECORD_NOT_FOUND_ERROR));
    }

    @Transactional
    @Override
    public AuditEntity save(AuditCreateDTO item) {
        return this.auditDao.save(
                Objects.requireNonNull(
                        this.conversionService.convert(
                                item, AuditEntity.class)));
    }
}
