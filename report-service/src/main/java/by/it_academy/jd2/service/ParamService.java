package by.it_academy.jd2.service;

import by.it_academy.jd2.dao.entity.ParamEntity;
import by.it_academy.jd2.dao.repositories.IParamDao;
import by.it_academy.jd2.service.api.IParamService;
import org.example.mylib.tm.itacademy.dto.ParamDTO;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class ParamService implements IParamService {
    private static final String PARAMS_NOT_FOUND = "Params is not found";
    private final IParamDao paramDao;
    private final ConversionService conversionService;

    public ParamService(IParamDao paramDao, ConversionService conversionService) {
        this.paramDao = paramDao;
        this.conversionService = conversionService;
    }

    @Transactional(readOnly = true)
    @Override
    public ParamEntity get(UUID uuid) {
        ParamEntity entity = this.paramDao
                .findById(uuid)
                .orElseThrow(()
                        -> new EntityNotFoundException(PARAMS_NOT_FOUND));

        return entity;
    }

    @Transactional
    @Override
    public ParamEntity save(ParamDTO item) {
        ParamEntity entity = Objects.requireNonNull(
                conversionService
                        .convert(item, ParamEntity.class));
        entity.setUuid(UUID.randomUUID());

        this.paramDao.saveAndFlush(entity);

        return entity;
    }

    @Transactional
    @Override
    public ParamEntity save(ParamEntity item) {
        item.setUuid(UUID.randomUUID());
        this.paramDao.saveAndFlush(item);

        return item;
    }
}
