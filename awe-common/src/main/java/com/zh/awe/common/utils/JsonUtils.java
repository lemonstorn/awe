package com.zh.awe.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.List;

/**
 * jackson工具类
 */
public class JsonUtils {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	static {
	    //序列化的时候序列对象的所有属性
	    MAPPER.setSerializationInclusion(Include.ALWAYS);
	    //反序列化的时候如果多了其他属性,不抛出异常
	    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	    //如果是空对象的时候,不抛异常
	    MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		//增加jdk8时间支持
		MAPPER.findAndRegisterModules();
	}

	public static String objectToJson(Object data) {
		try {
			return MAPPER.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
		try {
			return MAPPER.readValue(jsonData, beanType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) {
		JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
		try {
			return MAPPER.readValue(jsonData, javaType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}