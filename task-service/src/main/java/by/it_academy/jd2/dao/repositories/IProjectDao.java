package by.it_academy.jd2.dao.repositories;

import by.it_academy.jd2.core.enums.EProjectStatus;
import by.it_academy.jd2.dao.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IProjectDao extends JpaRepository<ProjectEntity, UUID> {

    Page<ProjectEntity> findByStatusNotLike(EProjectStatus status, Pageable pageable);

    Page<ProjectEntity> findByManagerOrStaffOrAndStatusIsNotLike(UUID managerUuid, UUID staffUuid, EProjectStatus status, Pageable pageable);

    boolean existsByUuidAndStaffContaining(UUID projectUuid, UUID staffUuid);

    boolean existsByManager(UUID managerUuid);

    Page<ProjectEntity> findByManagerOrStaff(UUID managerUuid, UUID staffUuid, Pageable pageable);

    List<ProjectEntity> findByManagerOrStaff(UUID managerUuid, UUID staffUuid);

}

