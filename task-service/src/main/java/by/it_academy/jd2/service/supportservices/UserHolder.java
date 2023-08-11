package by.it_academy.jd2.service.supportservices;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class UserHolder {

    public UserDetails getUser() {
        return (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    public String getToken() {
        HttpServletRequest request = (
                (ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                        .getRequestAttributes())).getRequest();
        return request.getHeader("Authorization");
    }
}