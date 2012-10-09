/*
 *  Copyright 2012 Frederik Hahne
 *  
 *  This file is part of Mensa UPB.
 *
 *  Mensa UPB is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Mensa UPB is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Mensa UPB.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.atomfrede.android.mensa.upb.data.xml;

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
