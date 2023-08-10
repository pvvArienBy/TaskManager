package by.it_academy.jd2.service.supportservices;

import jakarta.servlet.http.HttpServletRequest;
import org.example.mylib.tm.itacademy.exceptions.EntityNotFoundException;
import org.example.mylib.tm.itacademy.exceptions.GetTokenContextException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class UserHolder {
    private static final String TOKEN_NOT_FOUND = "Token is not found";

    public UserDetails getUser(){
        return (UserDetails) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
    }

    public String getToken() {
        HttpServletRequest request = (
                (ServletRequestAttributes) RequestContextHolder
                        .getRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }
}