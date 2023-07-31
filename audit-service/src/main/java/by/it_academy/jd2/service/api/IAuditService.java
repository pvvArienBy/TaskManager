package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.AuditCreateDTO;
import by.it_academy.jd2.dao.entity.AuditEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface IAuditService {
    AuditEntity findById(UUID uuid);

    AuditEntity save(AuditCreateDTO item);

    Page<AuditEntity> findAll(PageRequest of);
}
