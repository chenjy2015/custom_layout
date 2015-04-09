package com.example.custom.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @author: 工具类
 * @Create: 2015.3.18
 * chenjy
 */

public class Utils {

	public static String getCurrentTimeFormat(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTimeFormat("yyyy-MM-dd  HH:mm:ss");
	}
}
