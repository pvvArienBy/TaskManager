package by.it_academy.jd2.service.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.example.mylib.tm.itacademy.errors.ErrorResponse;
import org.example.mylib.tm.itacademy.errors.StructuredErrorResponse;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;

@JsonComponent
public class ErrorResponseJsonComponent {
    public static class StructuredErrorResponseSerializer extends JsonObjectSerializer<StructuredErrorResponse> {
        @Override
        protected void serializeObject(StructuredErrorResponse dto,
                                       JsonGenerator jgen,
                                       SerializerProvider provider) throws IOException {

            jgen.writeStringField("logref", dto.getErrorType().name().toLowerCase());
            jgen.writeArrayFieldStart("errors");
            dto.getErrorMap().entrySet().stream().forEach(error -> {
                try {
                    jgen.writeStartObject();
                    String fieldNameWithUnderscores = error.getKey().replace(".", "_");
                    jgen.writeStringField("field", fieldNameWithUnderscores);
                    jgen.writeStringField("message", error.getValue());
                    jgen.writeEndObject();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            jgen.writeEndArray();
        }
    }

    public static class ErrorResponseSerializer extends JsonSerializer<ErrorResponse> {
        @Override
        public void serialize(ErrorResponse dto, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            jgen.writeStringField("logref", dto.getErrorType().name().toLowerCase());
            jgen.writeStringField("message", dto.getMessage());
            jgen.writeEndObject();
        }
    }
}
