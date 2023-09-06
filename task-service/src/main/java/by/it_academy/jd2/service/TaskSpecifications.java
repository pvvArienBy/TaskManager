package by.it_academy.jd2.service;

import by.it_academy.jd2.core.enums.ETaskStatus;
import by.it_academy.jd2.dao.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskSpecifications {

    public  Specification<TaskEntity> findByProjectIn(List<UUID> projects) {
        return (root, query, criteriaBuilder) ->
                root.get("project").in(projects);
    }

    public  Specification<TaskEntity> findByImplementerIn(List<UUID> implementers) {
        return (root, query, criteriaBuilder) ->
                root.get("implementer").in(implementers);
    }

    public  Specification<TaskEntity> findByStatusIn(List<ETaskStatus> statuses) {
        return (root, query, criteriaBuilder) ->
                root.get("status").in(statuses);
    }
}