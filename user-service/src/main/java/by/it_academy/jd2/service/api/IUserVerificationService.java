package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.ResultUsersVerificationDTO;
import by.it_academy.jd2.core.dto.UsersVerificationDTO;

public interface IUserVerificationService {
    ResultUsersVerificationDTO check(UsersVerificationDTO item);
}
