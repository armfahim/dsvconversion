package com.convert.dsvconversion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class DsvConversion {

	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy/MM/dd");
	private static final SimpleDateFormat DF2 = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat SIMPLEDATEFORMAT = new SimpleDateFormat("YYYY-MM-dd");

	public Boolean convert(String file) {
		Path path = Paths.get(file);
		List<String[]> list = new ArrayList<>();
		Pattern pattern = Pattern.compile("[\"|,;:]");
		List<String> jsonList = new ArrayList<>();

		if (Files.exists(path)) {
			try (BufferedReader in = new BufferedReader(new FileReader(file));) {
				list = in.lines().skip(0).map(pattern::split).collect(Collectors.toList());
			} catch (IOException e) {
				return false;
			}
			for (String[] strings : list) {
				for (int i = 0; i < strings.length; i++) {
					if (strings[i].contains("/") || strings[i].contains("-")) {
						strings[i] = parseDate(strings[i], DF, DF2);
					}
				}

				Collections.addAll(jsonList, strings);
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			try {
				mapper.writeValue(System.out, jsonList);
			} catch (IOException e) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	private String parseDate(String val, SimpleDateFormat... formats) {
		for (SimpleDateFormat fmt : formats) {
			try {
				return SIMPLEDATEFORMAT.format(fmt.parse(val));
			} catch (Exception e) {
				continue;
			}
		}
		return null;
	}
}