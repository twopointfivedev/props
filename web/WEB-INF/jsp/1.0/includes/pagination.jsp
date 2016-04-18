<%@ include file="include.jsp" %>
	<ul class="pagination">
		<c:forEach var="p" begin="${model.startPage}" end="${model.endPage}">
			<c:if test="${p != model.page}">
				<li><a href="${model.searchUrl}&amp;p=${p}<c:forEach var="filter" items="${model.filter_page}">&amp;${filter.key}=${filter.value}</c:forEach>">${p}</a></li>
			</c:if>
			<c:if test="${p == model.page}">
				<li class="active"><a href="" onclick="return false;">${p}</a></li>
			</c:if>	
		</c:forEach>
		<c:if test="${model.page < model.totalPages}">
		<li><a href="${model.searchUrl}&amp;p=${model.page + 1}<c:forEach var="filter" items="${model.filter_page}">&amp;${filter.key}=${filter.value}</c:forEach>">Next &raquo;</a></li>
		</c:if>		
	</ul>