package net.property.data;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class LocationsInserter extends AbstractDao {
	public static void main(String args[]) throws IOException{
	String url = "http://www.hongkonghomes.com/en/property/rent/list";
	//Document doc = Jsoup.connect(url).timeout(0).get();
	Document doc = Jsoup.parse(new URL(url), 30000);
	System.out.println(doc.select("[for=\"select-location_customSelect_3\"]").text());
	Elements localities = doc.select("li.group-sub-item:lt(32)");
//	for (int i = 1;i<32; i++){
//		System.out.println(doc.select("input#select-location_customSelect_"+i+"+label").text());
//	}
/*	for (Element locality: localities){
		i++;
		String loc = 
		System.out.println(locality.text() + i);
	}*/
	System.out.println("DOne");
	}
}
