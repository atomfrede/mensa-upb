package de.atomfrede.android.mensa.data;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class Loader {

	static final String TAG = "Loader";

	private static String readTextFromServer(HttpURLConnection connection) throws IOException {
		// The XML File is Cp1252 encoded!
		InputStreamReader inputStreamreader = new InputStreamReader(connection.getInputStream(), "Cp1252");
		BufferedReader bufferedReader = new BufferedReader(inputStreamreader);

		StringBuilder sb = new StringBuilder();

		String line = bufferedReader.readLine();
		while (line != null) {
			sb.append(line);
			line = bufferedReader.readLine();
		}

		String s = sb.toString();
		return s;
	}

	public static String downloadXml(String downloadUrl) {
		HttpURLConnection connection = null;

		try {
			URL url = new URL(downloadUrl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Accept", "application/xml");
			connection.setReadTimeout(500);
			connection.setConnectTimeout(1000);
			connection.connect();
			int statusCode = connection.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				Log.e(TAG, "Failed in getting data with status code " + statusCode);
				return "Error: Failed getting data!";
			}
			return readTextFromServer(connection);

		} catch (Exception e) {
			return "Error: " + e.getMessage();
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}
}
