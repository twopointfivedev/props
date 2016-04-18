<%@ include file="include.jsp" %>
	<div class="row results_info hidden-xs" id="result-bar">
		<div class="col-sm-8 col-md-8" id="result-info">
			<div class="container-fluid">
			<div class="row">
			<div class="col-sm-4 col-md-4">Results ${model.start}-${model.end} of <fmt:formatNumber maxFractionDigits="0" value="${model.total}"/> </div>
			<div class="col-sm-8 col-md-8 orange bold"><div>${model.page_title}</div></div>
			</div>
			</div>
		</div>
		<div class="col-sm-4 col-md-4" id="sort">
			<div class="form-inline">Sort by 
				<select id="sort" name="s" class="input-sm form-control">
					<option value="r"<c:if test="${model.sort == 'r'}"> selected="selected"</c:if>>Most relevant</option>
					<%-- <option value="d"<c:if test="${model.sort == 'd'}"> selected="selected"</c:if>>Most recent</option> --%>
					<option value="h"<c:if test="${model.sort == 'h'}"> selected="selected"</c:if>>Price (highest to lowest)</option>
					<option value="l"<c:if test="${model.sort == 'l'}"> selected="selected"</c:if>>Price (lowest to highest)</option>
				</select></div>
		</div>
	</div>