package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.ProjectCreateUpdateDTO;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;


public interface IProjectService extends ICRUService<ProjectEntity, ProjectCreateUpdateDTO> {
    Page<ProjectEntity> getAll(PageRequest pageRequest, Boolean archived);

    List<UUID> getMyList();

    boolean existsByUuidAndStaffContaining(UUID projectUuid, UUID staffUuid);

    boolean existsByManager(UUID managerUuid);

    boolean existsById(UUID uuid);

}
