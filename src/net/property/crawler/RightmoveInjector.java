package net.property.crawler;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.property.data.Location;
import net.property.data.LocationDB;
import net.property.data.Property;
import net.property.data.SearchDataManager;
import net.property.utils.CrawlerUtils;
import net.property.utils.HttpUtils;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RightmoveInjector {/*


	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	private int fails = 0;

	private String site = "rightmove.co.uk";

	private String urls[] = {
			"http://www.rightmove.co.uk/property-for-sale/Derbyshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Leicestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Lincolnshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Northamptonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Nottinghamshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Rutland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Bedfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Cambridgeshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Essex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Hertfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Norfolk.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Suffolk.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Central-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/County-Durham.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Northumberland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Tyne-And-Wear.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Cheshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Cumbria.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Greater-Manchester.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Lancashire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Merseyside.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Berkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Buckinghamshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Sussex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Hampshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Isle-Of-Wight.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Kent.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Oxfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Surrey.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-Sussex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Bristol-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Cornwall.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Devon.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Dorset.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Gloucestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Somerset.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Wiltshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Herefordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Shropshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Staffordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Warwickshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-Midlands-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Worcestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Riding-Of-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Derbyshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Leicestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Lincolnshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Northamptonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Nottinghamshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Rutland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Bedfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Cambridgeshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Essex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Hertfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Norfolk.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Suffolk.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Central-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-East-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-London.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/County-Durham.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Northumberland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Tyne-And-Wear.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Cheshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Cumbria.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Greater-Manchester.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Lancashire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Merseyside.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Berkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Buckinghamshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Sussex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Hampshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Isle-Of-Wight.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Kent.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Oxfordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Surrey.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-Sussex.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Bristol-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Cornwall.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Devon.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Dorset.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Gloucestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Somerset.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Wiltshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Herefordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Shropshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Staffordshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Warwickshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-Midlands-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Worcestershire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Riding-Of-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-Yorkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Angus.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Argyll-and-Bute.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Clackmannanshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Dundee-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Falkirk-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Fife.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Perth-and-Kinross.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Stirling-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Lothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Edinburgh-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Midlothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-Lothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Dunbartonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Renfrewshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Glasgow-95748.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Inverclyde.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-Lanarkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Renfrewshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/West-Dunbartonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Highland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Moray.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Orkney.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Shetland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Western-Isles.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Dumfries-and-Galloway.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Scottish-Borders.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Aberdeen-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Aberdeenshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/East-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-Lanarkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Angus.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Argyll-and-Bute.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Clackmannanshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Dundee-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Falkirk-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Fife.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Perth-and-Kinross.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Stirling-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Lothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Edinburgh-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Midlothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-Lothian.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Dunbartonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Renfrewshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Glasgow-95748.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Inverclyde.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-Lanarkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Renfrewshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/West-Dunbartonshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Highland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Moray.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Orkney.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Shetland.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Western-Isles.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Dumfries-and-Galloway.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Scottish-Borders.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Aberdeen-County.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Aberdeenshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/East-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-Ayrshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-Lanarkshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Ceredigion.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Powys.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Conwy-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Denbighshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Flintshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Gwynedd.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Isle-Of-Anglesey.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Wrexham-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Blaenau-Gwent.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Bridgend-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Caerphilly-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Cardiff-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Merthyr-Tydfil-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Monmouthshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Neath-Port-Talbot.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Newport-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Rhondda-Cynon-Taff.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Swansea-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Torfaen.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Vale-Of-Glamorgan.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Carmarthenshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Pembrokeshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Ceredigion.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Powys.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Conwy-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Denbighshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Flintshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Gwynedd.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Isle-Of-Anglesey.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Wrexham-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Blaenau-Gwent.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Bridgend-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Caerphilly-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Cardiff-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Merthyr-Tydfil-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Monmouthshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Neath-Port-Talbot.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Newport-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Rhondda-Cynon-Taff.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Swansea-County-of.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Torfaen.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Vale-Of-Glamorgan.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Carmarthenshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Pembrokeshire.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Belfast-Area.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Belfast-Area.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/North-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/South-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Southern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Southern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Western-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-for-sale/Western-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Belfast-Area.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Belfast-Area.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/North-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/South-Eastern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Southern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Southern-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Western-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0",
			"http://www.rightmove.co.uk/property-to-rent/Western-NI.html?sortType=6&numberOfPropertiesPerPage=50&index=0"

	};

	public RightmoveInjector() {

	}

	public RightmoveInjector(String site, String[] urls) {
		this.site = site;
		this.urls = urls;
	}

	public void start() {
		for (String url: urls) {
			processSite(url);
		}
	}

	private void processSite(String url) {
		try {
			String content = "";

			System.out.println("Processing site: " + site);

			content = HttpUtils.getHTMLcontent(url);

			processPage(content, new URI(url));			
			int pages = 0;
			int jobsperpage = 50;
			String paginginfo = CrawlerUtils.getText(content,"#resultcount");
			Matcher m = Pattern.compile("(\\d+)").matcher(paginginfo);
			if (m.find()) {
				try {
					String pageCount = m.group(1).replaceAll("(?ius)[,.\u00A0\\s]", "").trim();
					if(pageCount.matches("\\d+")){
						pages = Integer.parseInt(pageCount);
						pages = (int) Math.ceil(pages / (float) jobsperpage);
					}else {
						pages = 0;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pages = pages * jobsperpage;
			for (int i=jobsperpage; i <= pages; i = i + jobsperpage) {
				url = url.replaceAll("(?<=index=)(\\d+)", String.valueOf(i));
				content = HttpUtils.getHTMLcontent(url);

				processPage(content, new URI(url));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * 
	 * @param content
	 * @param url
	 * @return false when no job links are found on the page
	 *//*
	public boolean processPage(String content, URI url) {
		int itemsFound = 0;
		int newItems = 0;
		currentPageItems.clear();
		for (Element e : CrawlerUtils.getElements(content,"[name=summary-list-item]")) {
			String itemLink = e.select(".price-new a").attr("href");
			//itemLink = itemLink.replaceAll("(.*?)search_identifier.*","$1");
			try {
				itemLink = itemLink.replaceAll(" ", "%20");
				itemLink = itemLink.replaceAll("\t", "%09");
				itemLink = url.resolve(itemLink).toString();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (itemLink.isEmpty())
				continue;

			Property item = new Property();

			// URL
			item.setUrl(itemLink);
			currentPageItems.add(item.getRef());

			itemsFound++;

			if (!previousPageItems.contains(item.getRef())) {
				newItems++;
			}

			// check for existing items
			if (SearchDataManager.getInstance().hasItem(item.getRef())) {	
				continue;
			}

			String location = e.select(".displayaddress").text();
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}

			String streetaddress = e.select(".displayaddress").text();
			String[] stTokens = streetaddress.split(",");
			if (stTokens.length > 0) {
				Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
				if (area.getId()==-1) {
					item.setLocation(stTokens[0], 100);
				}
			} else {
				item.setLocation(streetaddress, 100);
			}

			//Photo
			String photo = e.select(".photo img").attr("src");

			try {
				new URL(photo);
				String ext = CrawlerUtils.saveImage(item.getRef(),photo);
				item.setImageType(ext);
			} catch (MalformedURLException mui) {

			}

			//Description
			String description = HttpUtils.getHTMLcontent(itemLink);
			Elements descMarkup = new Elements();
			Document doc = CrawlerUtils.getDocument(description);
			descMarkup.addAll(CrawlerUtils.getElements(doc, ".agent-content"));
			descMarkup.select(".more-info").remove();		
			descMarkup.select("a").remove();	
			//descMarkup.select(".social-media-share-buttons").remove();	
			String propDesc = CrawlerUtils.getSafeHtml(descMarkup.outerHtml());
			item.setDescription(propDesc);

			//Agent
			String agent = CrawlerUtils.getText(description, "#secondaryContent .agent-details .agent-details-display strong");
			item.setAgent(agent);

			//House type
			String houseType = "";
			houseType = CrawlerUtils.getTextAdvanced(e,	"h2 [id^=standardPropertySummary]");
			houseType = CrawlerUtils.getHouseType(houseType);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(descMarkup.text());
				item.setHouseType(houseType);
			} else {
				item.setHouseType(houseType);
			}

			//Bedrooms
			String bedrooms = "";
			bedrooms = CrawlerUtils.getTextAdvanced(e, "h2 [id^=standardPropertySummary]");
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				CrawlerUtils.getBedRooms(descMarkup.text());
			} else {
				item.setBedrooms(bedrooms);
			}
			//Price
			String price = CrawlerUtils.getTextAdvanced(e, ".price");
			price = price.replaceAll(",", "").trim();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);

			//Ad-Type
			if (url.toString().contains("sale")) {
				item.setAdType("s");
			} else if (url.toString().contains("rent")) {
				item.setAdType("r");
			}
			if (site.equals("smartnewhomes.com")) {
				item.setAdType("s");
			}

			//Features
			Set<String> features = CrawlerUtils.getFeatureType(description);
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}

			//Image count
			int images = CrawlerUtils.getElements(description,"[property=og:image]").size();
			item.setImages(images);

			item.setRegion("GB");
			item.setSource(site);

			String location = CrawlerUtils.getElements(description,"[property=og:locality]").attr("content");
			item.setLocation(location, 4);

			String zipcode = CrawlerUtils.getText(description, ".left address");
			Location locationforzipcode = LocationDB.getInstance().normalizeLocation(zipcode);
			item.setZipcode(locationforzipcode.getZipcode());

			// COMMIT JOB
			SearchDataManager.getInstance().addItem(item);
		}

		// Check for sites with issues. We will break after few attempts.
		previousPageItems = new HashSet<String>(currentPageItems);
		if (itemsFound == 0 || newItems == 0) {
			return false;
		}else{
			return true;
		}
	}

*/}
