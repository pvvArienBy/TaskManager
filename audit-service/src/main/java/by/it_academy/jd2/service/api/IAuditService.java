package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.AuditDTO;
import by.it_academy.jd2.dao.entity.AuditEntity;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface IAuditService {
    AuditEntity findById(UUID uuid);

    AuditEntity save(AuditCreateDTO item);

    Page<AuditEntity> findAll(PageRequest of);

    List<AuditEntity> getList(ParamDTO dto);
}
