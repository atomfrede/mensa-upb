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

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MealHandler extends DefaultHandler {

	enum TagNames {
		kw, von, bis, tag, datum, wochentag, menue, menu, speisentyp, text, beilage, preis, ausgabe, txt, filiale
	}

	private WeeklyMeal parsedMeal = new WeeklyMeal();

	private DailyMeal currentDailyMeal;
	private Menu currentMenu;

	private TagNames currentTag;

	public WeeklyMeal getParsedMeal() {
		return parsedMeal;
	}

	public void setParsedMeal(WeeklyMeal parsedMeal) {
		this.parsedMeal = parsedMeal;
	}

	@Override
	public void startDocument() throws SAXException {
		this.parsedMeal = new WeeklyMeal();
	}

	@Override
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equals(TagNames.filiale.toString())) {
			currentTag = TagNames.filiale;
		}
		if (localName.equals(TagNames.kw.toString())) {
			currentTag = TagNames.kw;
		}
		if (localName.equals(TagNames.von.toString())) {
			currentTag = TagNames.von;
		}
		if (localName.equals(TagNames.bis.toString())) {
			currentTag = TagNames.bis;
		}
		if (localName.equals(TagNames.tag.toString())) {
			currentTag = TagNames.tag;
			currentDailyMeal = new DailyMeal();
			if (parsedMeal != null)
				parsedMeal.addMeal(currentDailyMeal);

		}
		if (localName.equals(TagNames.datum.toString())) {
			currentTag = TagNames.datum;
		}
		if (localName.equals(TagNames.wochentag.toString())) {
			currentTag = TagNames.wochentag;

		}
		if (localName.equals(TagNames.menue.toString())) {
			currentTag = TagNames.menue;
			currentMenu = new Menu();
			if (currentDailyMeal != null)
				currentDailyMeal.addMenu(currentMenu);
		}
		if (localName.equals(TagNames.menu.toString())) {
			currentTag = TagNames.menu;
		}
		if (localName.equals(TagNames.speisentyp.toString())) {
			currentTag = TagNames.speisentyp;
		}
		if (localName.equals(TagNames.text.toString())) {
			currentTag = TagNames.text;
		}
		if (localName.equals(TagNames.beilage.toString())) {
			currentTag = TagNames.beilage;
		}
		if (localName.equals(TagNames.preis.toString())) {
			currentTag = TagNames.preis;
		}
		if (localName.equals(TagNames.ausgabe.toString())) {
			currentTag = TagNames.ausgabe;
		}
		if (localName.equals(TagNames.txt.toString())) {
			currentTag = TagNames.txt;
		}
	}

	@Override
	public void characters(char ch[], int start, int length) {
		if (currentTag != null) {
			switch (currentTag) {
			case datum:
				currentDailyMeal.setDate(new String(ch, start, length));
				break;
			case wochentag:
				currentDailyMeal.setWeekday(new String(ch, start, length));
				break;
			case menu:
				currentMenu.setName(new String(ch, start, length));
				break;
			case speisentyp:
				currentMenu.setType(new String(ch, start, length));
				break;
			case text:
				currentMenu.setText(new String(ch, start, length));
				break;
			case txt:
				if (currentMenu == null) {
					currentMenu = new Menu();
					currentDailyMeal.addMenu(currentMenu);
				}
				currentMenu.setText(new String(ch, start, length));
				currentMenu = null;
				break;
			case beilage:
				currentMenu.addSideDish(new String(ch, start, length));
				break;
			case preis:
				currentMenu.setPrice(new String(ch, start, length));
				break;
			case ausgabe:
				currentMenu.setAtCounter(new String(ch, start, length));
			default:
				break;
			}
		}
	}

}
