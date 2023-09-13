package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.dao.entity.Audit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface IAuditRepository extends MongoRepository<Audit, String> {
    List<Audit> findByUserUuidAndDtCreateBetween(UUID userUuid, LocalDateTime fromDate, LocalDateTime toDate);
}
