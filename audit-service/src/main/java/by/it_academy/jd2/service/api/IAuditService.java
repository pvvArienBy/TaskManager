package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.Audit;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface IAuditService {
    Audit findById(UUID uuid);

    Audit save(AuditCreateDTO item);

    Page<Audit> findAll(PageRequest of);

    List<Audit> getList(ParamDTO dto);
}
