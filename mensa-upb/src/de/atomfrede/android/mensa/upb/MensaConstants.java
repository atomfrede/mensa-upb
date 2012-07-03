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
package de.atomfrede.android.mensa.upb;

public final class MensaConstants {

	public static final int LOC_MENSA = 0;
	public static final int LOC_HOT_SPOT = 1;
	public static final int LOC_PUB = 2;
	
	public static final int DAY_MONDAY = 0;
	public static final int DAY_TUESDAY = 1;
	public static final int DAY_WEDNESDAY = 2;
	public static final int DAY_THURSDAY = 3;
	public static final int DAY_FRIDAY = 4;
	
	public static final String MENSA_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/mensa.xml";
	public static final String PUB_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/gownsmenspub.xml";
	public static final String HOTSPOT_URL = "http://www.studentenwerk-pb.de/fileadmin/xml/palmengarten.xml";
	
	/**
	 * Key for preferences that saves the week of year when the last update of mensa data was done. e.g. 26
	 */
	public static final String LAST_UPDATE_KEY = "lastUpdate";
	public static final String MENSA_XML_KEY = "mensaXml";
	public static final String HOTSPOT_XML_KEY = "hotspotXml";
	public static final String PUB_XML_KEY = "pubXml";
	
	public static final String MENSA_PREFS = "mensaPrefs";
}
