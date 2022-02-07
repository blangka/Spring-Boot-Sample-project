package com.hkmc.sample.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    public static JsonNode mapToJsonNode(Map<String, Object> map) {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(map, JsonNode.class);
    }

    public static JsonNode jsonToJsonNode(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.readTree(json);
    }

    public static <T> List<T> toListObject(String jsonString, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(jsonString, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) throws IOException {
        boolean check = false;
        if (jsonString.startsWith("\"")) {
            check = true;
            jsonString = jsonString.substring(1);
        }
        if (jsonString.endsWith("\"")) {
            jsonString = jsonString.substring(0, jsonString.lastIndexOf("\""));
            check = true;

        }
        if (check && jsonString.contains("\\\"")) {
            //jsonString = jsonString.replaceAll("\\\\\\\"", "\"");
            jsonString = MatcherUtil.replaceAll(jsonString, "\\\\\\\"", "\"");
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return mapper.readValue(jsonString, clazz);
    }

    public static <T> String toJsonString(T t) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(t);
    }
}
