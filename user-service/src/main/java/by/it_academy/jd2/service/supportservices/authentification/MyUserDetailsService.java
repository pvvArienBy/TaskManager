package by.it_academy.jd2.service.supportservices.authentification;

import by.it_academy.jd2.service.api.IUserService;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public class MyUserDetailsService implements UserDetailsService {
    private static final String USER_NOT_FOUND = "User is not found";

    private final IUserService userService;

    public MyUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService
                .findByMail(username)
                .orElseThrow(()
                        -> new EntityNotFoundException(USER_NOT_FOUND));
    }
}
