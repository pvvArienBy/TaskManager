package by.it_academy.jd2.service;

import by.it_academy.jd2.service.api.IFileService;
import io.minio.*;
import io.minio.errors.ErrorResponseException;
import io.minio.http.Method;
import org.example.mylib.tm.itacademy.exceptions.MinioClientException;
import org.example.mylib.tm.itacademy.utils.ClassNameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class FileService implements IFileService {
    private static final String REPORT_NOT_FOUND_MESSAGE = "This report was not found in the repository";
    private static final String UNKNOWN_ERROR_MESSAGE = "Unknown error during operation";

    private final MinioClient minioClient;

    private static final Logger logger = LoggerFactory.getLogger(ClassNameUtil.getCurrentClassName());

    public FileService(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public void saveFile(String fileName, String fileType) {
        boolean bucketExists;

        try {
            String bucketName = fileType.toLowerCase().replaceAll("_", "");
            bucketExists = minioClient.bucketExists(
                    BucketExistsArgs
                            .builder()
                            .bucket(bucketName)
                            .build()
            );

            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs
                                .builder()
                                .bucket(bucketName)
                                .build());
            }

            minioClient.uploadObject(
                    UploadObjectArgs
                            .builder()
                            .bucket(bucketName)
                            .object(fileName)
                            .filename(fileName)
                            .build()
            );

        } catch (Exception e) {
            logger.warn("Save file minio {}", e.getMessage());
            throw new MinioClientException(e);
        }
    }

    @Override
    public String getFileUrl(String fileName, String fileType) {
        try {
            String bucketName = fileType.toLowerCase().replaceAll("_", "");
            this.minioClient.statObject(StatObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build());

            String url =
                    this.minioClient.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(bucketName)
                                    .object(fileName)
                                    .expiry(30, TimeUnit.MINUTES)
                                    .build());

//            return url;
            int questionMarkIndex = url.indexOf('?');

            return url.replaceAll("minio", "localhost").substring(0, questionMarkIndex + 4);

        } catch (ErrorResponseException e) {
            if (e.errorResponse().code().equals("NoSuchKey")) {
                logger.warn("Get URL file minio {} and {}", e.getMessage(), REPORT_NOT_FOUND_MESSAGE);
                throw new MinioClientException(REPORT_NOT_FOUND_MESSAGE);
            } else {
                logger.warn("Get URL file minio {} and {}", e.getMessage(), UNKNOWN_ERROR_MESSAGE);
                throw new MinioClientException(UNKNOWN_ERROR_MESSAGE);
            }
        } catch (Exception e) {
            throw new MinioClientException(UNKNOWN_ERROR_MESSAGE);
        }
    }
}