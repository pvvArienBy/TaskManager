package by.it_academy.jd2.service.api;

public interface IFileService {
    void saveFile(String fileName, String bucketName);

    String getFileUrl(String fileName, String bucketName);
}
