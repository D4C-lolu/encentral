package D4C.util;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class to format POJO as JSON using Jackson
 */
public class FormatJSON {

    public static  String toJSON(Object o) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        String JSON = objectMapper.writeValueAsString(o);
        return JSON;

    }
}
