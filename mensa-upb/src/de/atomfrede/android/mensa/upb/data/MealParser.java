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
package de.atomfrede.android.mensa.upb.data;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

public class MealParser {

	public static String MENSA_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/mensa.xml";
	public static String PUB_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/gownsmenspub.xml";
	public static String HOTSPOT_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/palmengarten.xml";
	
	public static WeeklyMeal parseXmlString(String xmlString) throws Exception {
		XMLReader xmlReader = null;
		try{
			SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            
            xmlReader = sp.getXMLReader();
            MealHandler handler = new MealHandler();
            xmlReader.setContentHandler(handler);
            
            InputSource inSource = new InputSource(new StringReader(xmlString));
            xmlReader.parse(inSource);
            
            WeeklyMeal parsedMeal = handler.getParsedMeal();
            return parsedMeal;
		}catch(Exception e){
			Log.e("MealParsed", "Could not parse XML.", e);
		}
		return null;
	}
	
	public static WeeklyMeal parseXml(String urlString) throws Exception{
		XMLReader xmlReader = null;
		InputStream in = null;
		InputSource inSource = null;
		
		try{
			URL url = new URL(urlString);
			SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            
            xmlReader = sp.getXMLReader();
            MealHandler handler = new MealHandler();
            xmlReader.setContentHandler(handler);
            
            in = url.openStream();
            inSource = new InputSource(in);
            xmlReader.parse(inSource);
            
            
            WeeklyMeal parsedMeal = handler.getParsedMeal();
            Log.d("MealParser", "ParsedMeal "+parsedMeal);
            return parsedMeal;
            
		}catch(Exception e){
			Log.e("MealParsed", "Could not parse XML.", e);
		}finally{
			in.close();
		}
		
		return null;
	}
}
