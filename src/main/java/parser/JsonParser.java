package parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    @SneakyThrows
    public <T> T convertResponseToObject(String response, Class<T> tClass) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, tClass);
    }

    @SneakyThrows
    public <T> List<T> convertResponseToListOfObjects(String response, Class<T> tClass) {
            ObjectMapper mapper = new ObjectMapper();
            List<T> objects = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(ArrayList.class, tClass));
            return objects;
    }
}
