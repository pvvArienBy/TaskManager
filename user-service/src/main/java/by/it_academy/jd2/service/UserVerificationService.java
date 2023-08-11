package by.it_academy.jd2.service;

import by.it_academy.jd2.core.dto.ResultUsersVerificationDTO;
import by.it_academy.jd2.core.dto.UsersVerificationDTO;
import by.it_academy.jd2.dao.repositories.IUserDao;
import by.it_academy.jd2.service.api.IUserVerificationService;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserVerificationService implements IUserVerificationService {
    private final IUserDao userDao;

    public UserVerificationService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResultUsersVerificationDTO check(UsersVerificationDTO item) {
        ResultUsersVerificationDTO dto = new ResultUsersVerificationDTO();
        dto.setListUsersCheck(existsAllByUuidIn(item.getStaff()));
        dto.setManagerCheck(this.userDao.existsById(item.getManager()));
        dto.setManagerCheckRole(this.userDao.existsByUuidAndRole(item.getManager(), ERole.MANAGER));

        return dto;
    }


    private boolean existsAllByUuidIn(List<UUID> uuidList) {
        long count = userDao.countByUuidIn(uuidList);

        return count == uuidList.size();
    }
}
