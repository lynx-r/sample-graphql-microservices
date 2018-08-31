package pl.piomin.services.organization.clientgql;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Violates DRY. I suggest to use common module for all web services but not right now.
 */
public class JsonUtils {
  private static final ObjectMapper mapper;
  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  static {
    mapper = configureObjectMapper();
  }

  private static ObjectMapper configureObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
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