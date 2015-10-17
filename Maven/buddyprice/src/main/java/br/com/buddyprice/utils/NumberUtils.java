package br.com.buddyprice.utils;

import java.text.DecimalFormat;

public class NumberUtils {

	public static String formatToCurrecy(String value, String format){
		DecimalFormat dc = new DecimalFormat(format);  
		return dc.format(Double.parseDouble(value));
	}
	
}
