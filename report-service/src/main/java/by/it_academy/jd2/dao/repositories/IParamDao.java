package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.ParamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IParamDao extends JpaRepository<ParamEntity, UUID> {
}


