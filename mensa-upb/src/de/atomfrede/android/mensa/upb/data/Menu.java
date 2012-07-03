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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable{

	/*
	 * <menue><menu>Menü-Vorschlag
	 * 1</menu><speisentyp>Vegetarisch</speisentyp><text>Gebratene
	 * Frühlingsrolle</text><beilage>gefüllt mit
	 * Chinagemüse</beilage><beilage>dazu Sojasauce
	 * (3,4)</beilage><beilage>Curryreis
	 * </beilage><beilage>Pfannengemüse</beilage><beilage>Dessert
	 * (1)</beilage></menue>
	 */

	private String name;
	// Can be null!
	private String type;
	private String text;
	private String price;
	private String atCounter;
	private List<String> sideDishes;

	public void addSideDish(String sideDish) {
		if (sideDishes == null)
			sideDishes = new ArrayList<String>();

		sideDishes.add(sideDish);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<String> getSideDishes() {
		if (sideDishes == null)
			sideDishes = new ArrayList<String>();
		return sideDishes;
	}

	public void setSideDishes(List<String> sideDishes) {
		this.sideDishes = sideDishes;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getAtCounter() {
		return atCounter;
	}

	public void setAtCounter(String atCounter) {
		this.atCounter = atCounter;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (name != null)
			sb.append(name).append("\n");

		if (text != null)
			sb.append(text).append("\n");

		if (!getSideDishes().isEmpty()) {
			for (String sideDish : getSideDishes())
				sb.append(sideDish).append("\n");

		}

		if (atCounter != null && !atCounter.equals(""))
			sb.append(atCounter).append("\n");
		if (price != null)
			sb.append(price);

		if (type != null && !type.equals(""))
			sb.append(type).append("");
		return sb.append("\n").toString();
	}
}
