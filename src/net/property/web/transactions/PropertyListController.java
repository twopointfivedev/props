package net.property.web.transactions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.property.bean.location.GeoLocationBean;
import net.property.bean.location.LocationBean;
import net.property.data.Filter;
import net.property.data.Location;
import net.property.data.LocationDB;
import net.property.data.LocationDao;
import net.property.data.Property;
import net.property.data.searchList.PropertyInfoDao;
import net.property.search.Results;
import net.property.search.ResultsContainer;
import net.property.search.Searcher;
import net.property.utils.GlobalConstants;
import net.property.web.SearchController;
import net.property.web.transactionContext.PropertyListContext;
import net.property.web.viewContext.PropertyListViewContext;

import org.apache.log4j.Logger;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

public class PropertyListController extends MultiActionController {
	protected static final Logger logger = 	Logger.getLogger(PropertyListController.class);

	public ModelAndView propertyListHandler(HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		logger.info("inside list handler log");
		System.out.println("inside list handler");
		Map<String, Object> model = new HashMap<String, Object>();
		PropertyListContext transactionContext = new PropertyListContext(
				request);
		LocationBean loc = LocationDao.getInstance().getLocation(transactionContext.getLocId()); //TODO
		PropertyListViewContext viewContext = new PropertyListViewContext(
				transactionContext, model);

		//Map<String, String> filters = new HashMap<String, String>(); //This is no longer used
		Map<String, String> filtersPage = new HashMap<String, String>();
		List<String> filtersApplied = new ArrayList<String>();
		String fLocation = viewContext.handleLocationFilter(filtersApplied,
				filtersPage,loc);
		viewContext.handleFilters(filtersApplied, filtersPage);
		viewContext.setTitleAndPriceLimits(fLocation);
		viewContext.setGeneralParams();
		model.put("type", transactionContext.getType());
		viewContext.setSearchUrl();

	//	Searcher searcher = new Searcher(transactionContext);
		/** below code is to fetch results from solr. Not used any more*/
	//	Results results = searcher.search(transactionContext, filtersApplied, "*", loc);
		ResultsContainer results = PropertyInfoDao.getInstance().getResult(transactionContext, filtersApplied, loc);

		viewContext.handleFilterResults(results, filtersApplied);

		return new ModelAndView(GlobalConstants.VERSION + "/search", "model",
				model);
	}

}
