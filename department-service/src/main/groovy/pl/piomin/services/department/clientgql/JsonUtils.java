package pl.piomin.services.department.clientgql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JsonUtils {
  public static final ObjectMapper mapper;
  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  static {
    mapper = configureObjectMapper();
  }

  private static ObjectMapper configureObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();

    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    SimpleModule module = new SimpleModule();
    mapper.registerModule(module);

    mapper.findAndRegisterModules();
    return mapper;
  }

  public static String dataToJson(Object data) {
    try {
      return mapper.writeValueAsString(data);
    } catch (IOException e) {
      throw new RuntimeException("IOEXception while mapping object (" + data + ") to JSON.\n" + e.getMessage());
    }
  }

  public static <T> T jsonToData(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException("IOException while mapping json " + json + ".\n" + e.getMessage());
    }
  }

  public static <T> T jsonToDataTypeRef(String json, TypeReference typeRef) {
    try {
      return mapper.readValue(json, typeRef);
    } catch (IOException e) {
      throw new RuntimeException("IOException while mapping json " + json + ".\n" + e.getMessage());
    }
  }
}