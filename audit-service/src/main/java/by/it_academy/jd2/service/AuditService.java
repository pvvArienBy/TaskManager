package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.Audit;
import by.it_academy.jd2.dao.repositories.IAuditRepository;
import by.it_academy.jd2.service.api.IAuditService;
import org.example.mylib.tm.itacademy.dto.AuditCreateDTO;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class AuditService implements IAuditService {
    private static final String RECORD_NOT_FOUND_ERROR = "Record not found, please try again!";

    private final IAuditRepository auditRepository;
    private final ConversionService conversionService;

    public AuditService(IAuditRepository auditRepository, ConversionService conversionService) {
        this.auditRepository = auditRepository;
        this.conversionService = conversionService;
    }

    @Override
    public Page<Audit> findAll(PageRequest pageRequest) {
        return this.auditRepository.findAll(pageRequest);
    }

    @Override
    public Audit findById(UUID uuid) {
        return this.auditRepository
                .findById(uuid.toString())
                .orElseThrow(()
                        -> new EntityNotFoundException(RECORD_NOT_FOUND_ERROR));
    }

    @Override
    public Audit save(AuditCreateDTO item) {
        return this.auditRepository.save(
                Objects.requireNonNull(
                        this.conversionService.convert(
                                item, Audit.class)));
    }

    @Override
    public List<Audit> getList(ParamDTO dto) {
        LocalDateTime fromDateTime = dto.getFrom().atStartOfDay();
        LocalDateTime toDateTime = dto.getTo().atTime(LocalTime.MAX);

        return this.auditRepository
                .findByUserUuidAndDtCreateBetween(dto.getUser(), fromDateTime, toDateTime);
    }
}
