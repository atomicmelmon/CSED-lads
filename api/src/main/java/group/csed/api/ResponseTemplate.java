package group.csed.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResponseTemplate {

    private JSONObject response;
    private ObjectWriter objectWriter;

    public ResponseTemplate(boolean success) {
        this.response = new JSONObject();
        this.objectWriter = new ObjectMapper().writer().without(SerializationFeature.INDENT_OUTPUT);
        this.response.put("success", success);
    }

    public ResponseTemplate put(String key, Object value) {
        if(value != null) {
            try {
                response.put(key, new JSONObject(objectWriter.writeValueAsString(value)));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public ResponseTemplate put(String key, List<?> value) {
        List<JSONObject> objects = new ArrayList<>();
        try {
            for(Object object : value) {
                objects.add(new JSONObject(objectWriter.writeValueAsString(object)));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.put(key, objects);
        return this;
    }

    public ResponseTemplate put(String key, String value) {
        response.put(key, value);
        return this;
    }

    public ResponseTemplate put(String key, boolean value) {
        response.put(key, value);
        return this;
    }

    public String build() {
        return response.toString();
    }
}