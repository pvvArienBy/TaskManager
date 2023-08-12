package by.it_academy.jd2.service.supportservices;

import by.it_academy.jd2.service.api.feign.IUserClientService;
import jakarta.servlet.http.HttpServletRequest;
import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.enums.ERole;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Component
public class UserHolder {
    private final IUserClientService userClientService;
    private final ConversionService conversionService;

    public UserHolder(IUserClientService userClientService, ConversionService conversionService) {
        this.userClientService = userClientService;
        this.conversionService = conversionService;
    }

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

    public boolean checkAdminRole() {
        UserCheckDTO userCheckDTO = this.conversionService.convert(
                this.userClientService.meDetails(
                        getToken()).getBody(), UserCheckDTO.class);

        return userCheckDTO.getRole().equals(ERole.ADMIN);
    }
}