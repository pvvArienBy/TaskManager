package by.it_academy.jd2.service.converters;

import by.it_academy.jd2.dao.entity.UserEntity;
import org.example.mylib.tm.itacademy.dto.UserDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class UserEntityToDtoConverter
        implements Converter<UserEntity, UserDTO> {

    /**
     * Converts a UserEntity object to a UserDTO object.
     *
     * @param item the UserEntity object to convert
     * @return the converted UserDTO object
     */
    @Override
    public UserDTO convert(UserEntity item) {
        UserDTO dto = new UserDTO();
        dto.setUuid(item.getUuid());
        dto.setDtCreate(item.getDtCreate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setDtUpdate(item.getDtUpdate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        dto.setMail(item.getMail());
        dto.setFio(item.getFio());
        dto.setRole(item.getRole());
        dto.setStatus(item.getStatus());

        return dto;
    }
}
