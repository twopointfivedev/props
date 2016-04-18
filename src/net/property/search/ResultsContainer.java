package net.property.search;

import java.util.List;

import net.property.data.Property;

public class ResultsContainer {
	private List<Property> results;
	private long total;
	private long page;
	public ResultsContainer(List<Property> results, long total, long page) {
		super();
		this.results = results;
		this.total = total;
		this.page = page;
	}
	public List<Property> getResults() {
		return results;
	}
	public long getTotal() {
		return total;
	}
	public long getPage() {
		return page;
	}
	
	public long getStart() {
		return (page - 1) * Searcher.PAGE_SIZE + 1;
	}
	
	public long getEnd() {
		long end = ((page - 1) * Searcher.PAGE_SIZE) + Searcher.PAGE_SIZE;
		if(end > total)
			end = total;
		return end;
	}
	
	public long getTotalPages() {
		return (long)Math.ceil((float)total/(float)Searcher.PAGE_SIZE);
	}
	
	public long getStartPage() {
		long startPage = page - 2l;
		if(startPage < 1)
			startPage = 1l;
		return startPage;
	}
	
	public long getEndPage() {
		long totalPages = getTotalPages();
		long endPage = page + 2l;
		if(endPage < 5)
			endPage = 5l;
		if(endPage > totalPages)
			endPage = totalPages;
		return endPage;
	}
}
