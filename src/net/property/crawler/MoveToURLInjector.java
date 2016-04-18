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

public class MoveToURLInjector {/*

	// Checks to stop infinite looping
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	private int fails = 0;
	private String site = "moveto.co.uk";
	private String urls[] = {"http://www.moveto.co.uk/aberdeen-city/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/aberdeenshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/angus/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/argyll-and-bute/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/bedfordshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/berkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/blaenau-gwent/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/bridgend/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/brighton-and-hove/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/bristol/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/bristol-avon/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/buckinghamshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/caerphilly/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/cambridgeshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/cardiff/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/carmarthenshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/ceredigion/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/channel-islands/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/cheshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/city-of-edinburgh/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/clackmannanshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/conwy/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/cornwall/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-antrim/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-armagh/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-down/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-durham/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-fermanagh/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-londonderry/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-tyrone/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/cumbria/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/darlington/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/denbighshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/derbyshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/devon/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/dorset/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/dumfries-and-galloway/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/dundee-city/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-ayrshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-dunbartonshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-lothian/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-renfrewshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-sussex/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-yorkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/essex/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/falkirk/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/fife/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/flintshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/glasgow-city/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/gloucestershire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/greater-london/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/greater-manchester/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/gwynedd/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/hampshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/hereford-and-worcester/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/herefordshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/hertfordshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/highland/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/inverclyde/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-anglesey/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-man/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-wight/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/isles-of-scilly/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/kent/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/lancashire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/leicestershire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/lincolnshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/merseyside/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/merthyr-tydfil/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/midlothian/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/milton-keynes/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/monmouthshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/moray/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/neath-port-talbot/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/newport/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/norfolk/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-ayrshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-eart-lincolnshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-lanarkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-lincolnshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-somerset/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-yorkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/northamptonshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/northumberland/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/nottinghamshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/orkney/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/oxfordshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/pembrokeshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/perth-and-kinross/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/powys/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/redcar-and-cleveland/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/renfrewshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/rhondda-cynon-taff/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/rutland/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/scottish-borders/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/shetland-islands/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/shropshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/somerset/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-ayrshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-gloucestershire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-lanarkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-yorkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/staffordshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/stirling/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/stockton-on-tees/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/suffolk/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/surrey/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/swansea/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/swindon/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/torfaen/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/tyne-and-wear/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/vale-of-glamorgan/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/wales/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/warwickshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-dunbart/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-lothian/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-midlands/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-sussex/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-yorkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/western-isles/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/wiltshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/worcestershire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/wrexham/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/yorkshire/sales/most-recent-first/page-1",
			"http://www.moveto.co.uk/aberdeen-city/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/aberdeenshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/angus/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/argyll-and-bute/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/bedfordshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/berkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/blaenau-gwent/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/bridgend/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/brighton-and-hove/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/bristol/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/bristol-avon/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/buckinghamshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/caerphilly/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/cambridgeshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/cardiff/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/carmarthenshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/ceredigion/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/channel-islands/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/cheshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/city-of-edinburgh/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/clackmannanshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/conwy/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/cornwall/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-antrim/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-armagh/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-down/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-durham/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-fermanagh/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-londonderry/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/county-tyrone/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/cumbria/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/darlington/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/denbighshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/derbyshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/devon/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/dorset/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/dumfries-and-galloway/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/dundee-city/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-ayrshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-dunbartonshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-lothian/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-renfrewshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-sussex/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/east-yorkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/essex/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/falkirk/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/fife/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/flintshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/glasgow-city/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/gloucestershire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/greater-london/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/greater-manchester/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/gwynedd/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/hampshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/hereford-and-worcester/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/herefordshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/hertfordshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/highland/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/inverclyde/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-anglesey/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-man/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/isle-of-wight/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/isles-of-scilly/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/kent/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/lancashire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/leicestershire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/lincolnshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/merseyside/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/merthyr-tydfil/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/midlothian/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/milton-keynes/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/monmouthshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/moray/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/neath-port-talbot/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/newport/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/norfolk/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-ayrshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-eart-lincolnshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-lanarkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-lincolnshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-somerset/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/north-yorkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/northamptonshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/northumberland/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/nottinghamshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/orkney/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/oxfordshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/pembrokeshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/perth-and-kinross/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/powys/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/redcar-and-cleveland/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/renfrewshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/rhondda-cynon-taff/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/rutland/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/scottish-borders/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/shetland-islands/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/shropshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/somerset/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-ayrshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-gloucestershire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-lanarkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/south-yorkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/staffordshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/stirling/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/stockton-on-tees/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/suffolk/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/surrey/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/swansea/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/swindon/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/torfaen/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/tyne-and-wear/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/vale-of-glamorgan/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/wales/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/warwickshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-dunbart/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-lothian/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-midlands/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-sussex/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/west-yorkshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/western-isles/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/wiltshire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/worcestershire/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/wrexham/lettings/most-recent-first/page-1",
			"http://www.moveto.co.uk/yorkshire/lettings/most-recent-first/page-1"
	};

	public MoveToURLInjector() {

	}
	public MoveToURLInjector(String site, String[] urls) {
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

			for (int i=2; i < 50; i++) {
				url = url.replaceAll("(?<=page-)(\\d+)", String.valueOf(i));
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
		for (Element e : CrawlerUtils.getElements(content,"#togglable_list .property_row")) {
			String itemLink = e.select(".property_actions a:contains(More Details)").attr("href");

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


			//Photo
			String photo = e.select("img:not(.agency_logo)").attr("src");

			try {
				new URL(photo);
			} catch (MalformedURLException mui) {
				// TODO Auto-generated catch block
				// mui.printStackTrace();
				photo = "http://" + url.getHost() + photo;
			}

			// Ignore if the image contains noimage -> Probably photos are missing.
			if(!photo.contains("noimage")) {
				String ext = CrawlerUtils.saveImage(item.getRef(),photo);
				item.setImageType(ext);
			}

			//Description
			String description = HttpUtils.getHTMLcontent(itemLink);
			Elements descMarkup = new Elements();
			Document doc = CrawlerUtils.getDocument(description);
			descMarkup.addAll(CrawlerUtils.getElements(doc, ".property_description"));
			descMarkup.select("#images").remove();		
			descMarkup.select("#interested-1").remove();	
			descMarkup.select(".social-media-share-buttons").remove();	
			String propDesc = CrawlerUtils.getSafeHtml(descMarkup.outerHtml());
			item.setDescription(propDesc);

			//Agent
			String agent = CrawlerUtils.getText(description, ".direct_contact");
			item.setAgent(agent);

			//House type
			String houseType = "";
			houseType = CrawlerUtils.getTextAdvanced(e,	"p");
			houseType = CrawlerUtils.getHouseType(houseType);
			if (houseType.isEmpty()) {
				houseType = CrawlerUtils.getHouseType(descMarkup.text(),true);
				item.setHouseType(houseType);
			} else {
				item.setHouseType(houseType);
			}

			//Bedrooms
			String bedrooms = "";
			bedrooms = CrawlerUtils.getTextAdvanced(e, ".brown");
			bedrooms = CrawlerUtils.getBedRooms(bedrooms);
			if (bedrooms.isEmpty()) {
				CrawlerUtils.getBedRooms(descMarkup.text());
			} else {
				item.setBedrooms(bedrooms);
			}

			//Price
			String price = CrawlerUtils.getText(description, "h2 span:not(.feetext)");
			price = price.replaceAll(",", "").trim();
			price = price.replaceAll("\u00A0", " ").trim();
			price = CrawlerUtils.getPrice(price);
			item.setPrice(price);

			if(url.toString().contains("sales")) {
				item.setAdType("s");

			} else if(url.toString().contains("rent") || url.toString().contains("lettings")) {
				item.setAdType("r");

			}		

			//Features
			Set<String> features = CrawlerUtils.getFeatureType(description);
			int featureCounter = 1;
			for (String s: features) {
				item.setFeature(s, featureCounter);
				featureCounter++;
			}
			
			//Image count
			int images = CrawlerUtils.getElements(description,"#property_slider img").size();
			item.setImages(images);

			item.setRegion("GB");
			item.setSource(site);

			String nameloc = description.replaceAll("(?ius).*?name : \"(.*?)\".*","$1");
			String county = description.replaceAll("(?ius).*?county_name: \"(.*?)\".*","$1");			
			String location	= nameloc.concat(", "+county);
			Location loc = LocationDB.getInstance().normalizeLocation(location);
			for (int i = 1; i <= 4; i++) {
				if (!loc.getLocation(i).isEmpty()) {
					item.setLocation(loc.getLocation(i),i);
				}
			}

			String streetaddress = CrawlerUtils.getTextAdvanced(e,	"h4:not(.brown)");
			Matcher m = Pattern.compile("([^,]{1,30}),.*").matcher(streetaddress);
			if (m.find()) {
				try {
					streetaddress = m.group(1);
					String[] stTokens = streetaddress.split(",");
					if(stTokens.length > 0){
						Location area = LocationDB.getInstance().normalizeLocation(stTokens[0]);
						if(area.getId()==-1)
							item.setLocation(stTokens[0], 100);
					}else{	
						item.setLocation(streetaddress, 100);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}


			String zipcode = CrawlerUtils.getText(description, ".results_header h2");
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
