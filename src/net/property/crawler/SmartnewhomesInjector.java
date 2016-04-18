package net.property.crawler;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import net.property.data.Location;
import net.property.data.LocationDB;
import net.property.data.Property;
import net.property.data.SearchDataManager;
import net.property.utils.CrawlerUtils;
import net.property.utils.HttpUtils;

import org.jsoup.nodes.Element;

public class SmartnewhomesInjector extends ZooplaInjector {/*
	// Checks to stop infinite looping
	
	private static String site = "smartnewhomes.com";
	private static String urls[] = {
			"http://www.smartnewhomes.com/new-homes/property/england/?&pn=1&page_size=100&results_sort=newest_listings",
			"http://www.smartnewhomes.com/new-homes/property/scotland/?&pn=1&page_size=100&results_sort=newest_listings",
			"http://www.smartnewhomes.com/new-homes/property/wales/?&pn=1&page_size=100&results_sort=newest_listings",
			"http://www.smartnewhomes.com/new-homes/property/northern-ireland/?&pn=1&page_size=100&results_sort=newest_listings"};

	public SmartnewhomesInjector() {
		super(site, urls);
	}
	
	public static void main(String[] args) {
		new SmartnewhomesInjector().start();
	}
*/}
