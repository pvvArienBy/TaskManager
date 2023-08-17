package by.it_academy.jd2.service;

import by.it_academy.jd2.config.properties.MinioProperty;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.mylib.tm.itacademy.dto.AuditDTO;
import org.example.mylib.tm.itacademy.exceptions.ReportUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ApachePOIService {

    private final MinioClient minioClient;
    private final MinioProperty minioProperty;

    public ApachePOIService(MinioClient minioClient, MinioProperty minioProperty) {
        this.minioClient = minioClient;
        this.minioProperty = minioProperty;
    }


    public void convertToExcel(List<AuditDTO> auditList, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Audit Data");

        // Создание заголовка таблицы
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("UUID");
        headerRow.createCell(1).setCellValue("Date");
        headerRow.createCell(2).setCellValue("User UUID");
        headerRow.createCell(3).setCellValue("User Mail");
        headerRow.createCell(4).setCellValue("User FIO");
        headerRow.createCell(5).setCellValue("User Role");
        headerRow.createCell(6).setCellValue("Text");
        headerRow.createCell(7).setCellValue("Type");
        headerRow.createCell(8).setCellValue("ID");

        int rowNum = 1;
        for (AuditDTO audit : auditList) {
            Instant instant = Instant.ofEpochMilli(audit.getDtCreate());
            ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());
            LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            Row dataRow = sheet.createRow(rowNum++);
            dataRow.createCell(0).setCellValue(audit.getUuid().toString());
            dataRow.createCell(1).setCellValue("\'" + localDateTime.format(formatter));
            dataRow.createCell(2).setCellValue(audit.getUser().getUuid().toString());
            dataRow.createCell(3).setCellValue(audit.getUser().getMail());
            dataRow.createCell(4).setCellValue(audit.getUser().getFio());
            dataRow.createCell(5).setCellValue(audit.getUser().getRole().toString());
            dataRow.createCell(6).setCellValue(audit.getText());
            dataRow.createCell(7).setCellValue(audit.getType().toString());
            dataRow.createCell(8).setCellValue(audit.getId());
        }

        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }

//        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
//            workbook.write(outputStream);
//        }
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            workbook.write(outputStream);

            byte[] data = outputStream.toByteArray();

            // Загрузка файла в MinIO
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(minioProperty.getBucket())
                            .object(filePath)
                            .stream(inputStream, data.length, -1)
                            .contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                            .build()
            );
        } catch (ServerException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (InsufficientDataException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (ErrorResponseException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (InvalidResponseException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (XmlParserException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        } catch (InternalException e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        }
    }


    public String upload(ByteArrayOutputStream outputStream, String filePath) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new ReportUploadException("Report upload failed!: " + e.getMessage());
        }

        byte[] data = outputStream.toByteArray();

        String fileName = generateFileName(filePath);
        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(data)) {
            saveReport(inputStream, UUID.randomUUID().toString() +".xml");
        } catch (IOException e) {
            throw new ReportUploadException("Report upload failed: " + e.getMessage() + e.getCause().getMessage());
        }

        return fileName;
    }

    @SneakyThrows
    private void createBucket() {
        boolean found = minioClient.bucketExists(
                BucketExistsArgs.builder()
                        .bucket(minioProperty.getBucket())
                        .build());

        if (!found) {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder().bucket(minioProperty.getBucket())
                            .build());
        }
    }

    @SneakyThrows
    private void saveReport(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperty.getBucket())
                .object(fileName)
                .build());
    }

    private String generateFileName(String file) {
        return UUID.randomUUID() + "." + file;
    }
}
