package by.it_academy.jd2.service.supportservices.authentification;

import by.it_academy.jd2.dao.repositories.IUserDao;
import by.it_academy.jd2.dao.entity.UserEntity;
import by.it_academy.jd2.service.api.IMyUserDetailService;
import by.it_academy.jd2.core.exceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements IMyUserDetailService {
    private static final String USER_NOT_FOUND = "User is not found";
    private final IUserDao userDao;

    public MyUserDetailsService(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOptional = this.userDao.findByMail(username);
        return userOptional.orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
