package com.upreader.helper;

import java.util.HashMap;
import java.util.Map;

import com.upreader.context.Context;
import com.upreader.js.JavaScriptWriter;

public class WebHelper {
	public static boolean isJsonRequest(Context context) {
		return (StringHelper.containsNullSafe(context.headers().accept(), "application/json"))
				|| ("json".equals(context.query().get("format", "")));
	}

	public static boolean sendPlaintext(Context context, String text) {
		context.setContentType("text/plain");
		context.print(text);

		return true;
	}

	public static boolean sendJson(Context context, Object object) {
		return sendJson(context, null, object, null);
	}

	public static boolean sendJson(Context context, Object object, JavaScriptWriter jsw) {
		return sendJson(context, null, object, jsw);
	}

	public static boolean sendJson(Context context, String objectName, Object object) {
		return sendJson(context, objectName, object, null);
	}

	public static boolean sendJson(Context context, String objectName, Object object, JavaScriptWriter jsw) {
		JavaScriptWriter writer = jsw != null ? jsw : context.getApplication().getJavaScriptWriter();

		context.setContentType("application/json");

		if (StringHelper.isNonEmpty(objectName)) {
			context.print('{' + writer.write(objectName) + ':' + writer.write(object) + '}');
		} else {
			context.print(writer.write(object));
		}

		return true;
	}

	public static boolean sendJsonError(Context context, String error, String errorType, String errorMessage, String errorDescription,
			Map<String, String> ancillary) {
		Map<String, Object> map = new HashMap<>(6);

		map.put("error", error);
		map.put("errorType", errorType);
		map.put("errorMessage", errorMessage);
		map.put("errorDescription", StringHelper.isNonEmpty(errorDescription) ? errorDescription : "No detail available.");

		if (ancillary != null) {
			for (String key : ancillary.keySet()) {
				map.put(key, (String) ancillary.get(key));
			}

		}

		context.setStatus(500);

		return sendJson(context, map);
	}
}
