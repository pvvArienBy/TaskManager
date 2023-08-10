package by.it_academy.jd2.service.converters;

import org.example.mylib.tm.itacademy.dto.UserCheckDTO;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDtoToUserCheckDtoConverter
        implements Converter<UserDTO, UserCheckDTO> {

    /**
     * Converts a TaskEntity object to a TaskDTO object.
     *
     * @param item the TaskEntity object to convert
     * @return the converted TaskDTO object
     */
    @Override
    public UserCheckDTO convert(UserDTO item) {
        UserCheckDTO dto = new UserCheckDTO();
        dto.setUuid(item.getUuid());
        dto.setFio(item.getFio());
        dto.setMail(item.getMail());
        dto.setRole(item.getRole());

        return dto;
    }
}
