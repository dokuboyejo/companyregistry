package companyregistry.util;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import companyregistry.restservice.exception.BadRequestResourceException;
import spark.ResponseTransformer;

public class LoadData implements ResponseTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadData.class);
    
    public static String jsonize(Object object) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	String data = "";
    	try {
    		data = objectMapper.writeValueAsString(object);
    	} catch (IOException e) {
    		LoadData.LOGGER.error(e.getMessage(), e);
    		throw new BadRequestResourceException(String.format("Couldn't jsonize %s object", object.getClass().getName()));
    	}
    	return data;
    }
    
    public static <T> T objectize(String json, Class<T> type) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	T t = null;
    	try {
    		t = objectMapper.readValue(json, type);
    	} catch (IOException e) {
    		LoadData.LOGGER.error(e.getMessage(), e);
    		throw new BadRequestResourceException(String.format("Couldn't build a %s object", type.getName()));
    	}
    	return t;
    }
    
    public static <T> List<T> objectizeList(String json, Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        List<T> t = null;
        try {
        	t = objectMapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, type));
        } catch (IOException e) {
            LoadData.LOGGER.error(e.getMessage(), e);
            throw new BadRequestResourceException(String.format("Couldn't build List<%s> object", type.getName()));
        }
        return t;
    }
    
    @Override
	public String render(Object model) {
		return LoadData.jsonize(model);
	}

}
