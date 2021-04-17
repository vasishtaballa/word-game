package dev.vasishta.game.words.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Survey {
	static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	static String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun" };

	public static void main(String[] args) {
		try {
			File file = new File("C:\\Users\\Vasishta\\Downloads\\Survey.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			HashMap<String, Integer> msgCount = new HashMap<>();
			String line;
			int lineNo = 1;
			while ((line = br.readLine()) != null) {
				if (line.length() > 2 && line.charAt(2) == '/') {
					Date date = getDate(line);
					String dateStr = getDateStr(line);
					String month = getMonth(date);
					// System.out.println(lineNo + ". " + date.toString());
					updateHashMap(msgCount, dateStr);
					// System.out.println(month);
				}
				lineNo++;
			}
			int max = Integer.MIN_VALUE;
			for (String key : msgCount.keySet()) {
				System.out.println(key + " - " + msgCount.get(key));
				if (msgCount.get(key) > max)
					max = msgCount.get(key);
			}
			System.out.println(max);
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static String getDateStr(String line) {
		String[] tokens = line.split(",");
		String dateStr = tokens[0];
		return dateStr;
	}

	private static void updateHashMap(HashMap<String, Integer> msgCount, String month) {
		if (msgCount.containsKey(month))
			msgCount.put(month, msgCount.get(month) + 1);
		else
			msgCount.put(month, 1);
	}

	private static String getMonth(Date date) {
		return months[date.getMonth()];
	}

	private static Date getDate(String line) throws ParseException {
		String dateStr = getDateStr(line);
		return sdf.parse(dateStr);
	}
}
