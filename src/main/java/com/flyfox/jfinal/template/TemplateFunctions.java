package com.flyfox.jfinal.template;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TemplateFunctions extends TemplateUtils {

	// //////////////////////////////////产生随机数//////////////////////////////////
	private static AtomicInteger bgNum = new AtomicInteger(0);

	public static int bgNum(int num) {
		if (bgNum.get() > num) {
			bgNum.set(0);
		}
		bgNum.incrementAndGet();
		return bgNum.get();
	}

	public static int randomInt() {
		return new Random().nextInt();
	}

	public static int randomInt(int num) {
		return new Random().nextInt(num);
	}

	// //////////////////////////select 方法/////////////////////////////////
	public static String sel(String jsonData, int selected_value) {
		return sel(jsonData, selected_value + "");
	}

	public static String selValue(String jsonData, int key) {
		return selValue(jsonData, key + "");
	}

	// //////////////////////////radio 方法/////////////////////////////////
	public static String radio(String jsonData, String name, int selected_value) {
		return radio(jsonData, name, selected_value + "");
	}

	public static String radioValue(String jsonData, int key) {
		return radioValue(jsonData, key + "");
	}

	// //////////////////////////chechbox 方法/////////////////////////////////
	public static String checkbox(String jsonData, String name, int selected_value) {
		return checkbox(jsonData, name, selected_value + "");
	}

	public static String checkboxValue(String jsonData, int key) {
		return checkboxValue(jsonData, key + "");
	}
	
}
