package com.hust.platform.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hust.platform.model.FrameNode;

public class PlatformUtil {

	public static String getJsonString(int code) {
		JSONObject json = new JSONObject();
		json.put("code", code);
		return json.toJSONString();
	}
	
	public static String getJsonString(int code,List<FrameNode> list) {
		JSONObject json = new JSONObject();
		json.put("code", code);
//		传入的列表不能转化为String，否则前端无法将JSONString转化为json对象
		json.put("jsonList", list);
		return json.toJSONString();
	}
	
	public static String getJsonString(int code,String message) {
		JSONObject json = new JSONObject();
		json.put("code", code);
//		传入的列表不能转化为String，否则前端无法将JSONString转化为json对象
		json.put("message", message);
		return json.toJSONString();
	}
}
