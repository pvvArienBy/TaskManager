package by.it_academy.jd2.service.api;

import by.it_academy.jd2.core.dto.FileDTO;
import by.it_academy.jd2.dao.entity.FileEntity;

public interface IFileService {

    FileEntity get(Long id);

    FileEntity add (FileDTO item);

    void remove(FileDTO item);
}
