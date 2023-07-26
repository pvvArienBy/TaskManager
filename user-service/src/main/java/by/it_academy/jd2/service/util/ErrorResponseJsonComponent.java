package by.it_academy.jd2.service.util;

import by.it_academy.jd2.core.dto.ErrorDTO;
import by.it_academy.jd2.core.dto.StructuredErrorDTO;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.io.IOException;
import java.util.List;

@JsonComponent
public class ErrorResponseJsonComponent {
    public static class  StructuredErrorResponseSerializer extends JsonObjectSerializer<StructuredErrorDTO> {
        @Override
        protected void serializeObject(StructuredErrorDTO dto,
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

    public static class ErrorResponseSerializer extends JsonSerializer<List<ErrorDTO>> {
        @Override
        public void serialize(List<ErrorDTO> errorList, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartArray();
            for (ErrorDTO dto : errorList) {
                jgen.writeStartObject();
                jgen.writeStringField("logref", dto.getErrorType().name().toLowerCase());
                jgen.writeStringField("message", dto.getMessage());
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
    }
}
