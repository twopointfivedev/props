package net.property.crawler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import net.property.data.Property;
import net.property.data.SearchDataManager;
import net.property.utils.CrawlerUtils;

import org.jsoup.nodes.Element;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WaitingRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class UnifiedInjector {/*
	// Checks to stop infinite looping
	private Set<String> previousPageItems = new HashSet<String>();
	private Set<String> currentPageItems = new HashSet<String>();
	private int fails = 0;
	
	private void processSite(String url) {
		WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
		HtmlPage currentPage = null;
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		//webClient.getOptions().setJavaScriptEnabled(false);
		//webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		webClient.setRefreshHandler(new WaitingRefreshHandler(5));
		webClient.getCookieManager().setCookiesEnabled(true);
		webClient.getOptions().setUseInsecureSSL(true);

		try {
			String content = "";

			System.out.println("Processing site: ");

			
				content = InjectorUtils.getHTMLcontent(url, sitePatterns.getValue(CrawlingPatterns.JOBLIST_CRAWLING_ENCODING));
			
			
			
				HtmlElement nextAnchor;
				List<String> xpathList = sitePatterns.getValues(CrawlingPatterns.PAGING_JOBLIST_FINDER);
				webClient.waitForBackgroundJavaScript(20000);
				currentPage = (HtmlPage) webClient.getPage(url);
				for (String xpath : xpathList) {
					try {
						nextAnchor = (HtmlElement) currentPage.getByXPath(xpath).get(0);
						WebWindow window = currentPage.getEnclosingWindow();
						currentPage = nextAnchor.click();
						webClient.waitForBackgroundJavaScript(20000);
						currentPage = (HtmlPage) window.getEnclosedPage();
						content = currentPage.asXml();
					} catch (Exception e) { // This is an expected exception so break without printing
						e.printStackTrace();
					}
				}
				
			
			
			currentPage = (HtmlPage) webClient.getPage(url);
			webClient.waitForBackgroundJavaScript(20000);
			content = currentPage.asXml();

//			System.out.println(content);

			processPage(content, new URI(url));

		} catch (Exception e) {
			e.printStackTrace();
		}

		webClient.closeAllWindows();
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
		for (Element e : CrawlerUtils.getElements(content,"")) {
			String itemLink = e.select("").attr("href");
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
			
			*//**
			 * GET ADDITIONAL DETAILS HERE 
			 *//*
			
			
			// COMMIT JOB
			SearchDataManager.getInstance().addItem(item);
		}

		// Check for sites with issues. We will break after few attempts.
		previousPageItems = new HashSet<String>(currentPageItems);
		if (itemsFound == 0 || newItems == 0) {
			fails++;
		} else {
			fails = 0;
		}
 
		if (fails > 3) { 
			return false;
		} else {
			return true;
		}
	}

*/}
