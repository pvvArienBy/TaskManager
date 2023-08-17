package by.it_academy.jd2.service.api;

import by.it_academy.jd2.dao.entity.ParamEntity;
import org.example.mylib.tm.itacademy.dto.ParamDTO;

import java.util.UUID;

public interface IParamService {

    ParamEntity get(UUID uuid);

    ParamEntity save(ParamDTO item);

    ParamEntity save(ParamEntity item);
}
