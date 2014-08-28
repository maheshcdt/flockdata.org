package com.auditbucket.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * User: mike
 * Date: 28/08/14
 * Time: 3:38 PM
 */
public class JsonUtils {
    public static byte[] getObjectAsJsonBytes(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static <T> T getBytesAsObject (byte[] bytes, Class<T> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(bytes, clazz);
    }

    public static <T> Collection<T> getAsCollection(String json, Class<T> clazz) throws IOException {
        if (json == null || json.equals(""))
            return new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        CollectionType javaType =
                mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        return mapper.readValue(json, javaType );

    }

    public static Map<String,Object> getAsMap(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> result = mapper.readValue(json, Map.class);
        return result;
    }

    public static String getJSON(Object obj) {
    	ObjectMapper mapper = new ObjectMapper();
    	String json = null;
    	try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return json;
    }
}
