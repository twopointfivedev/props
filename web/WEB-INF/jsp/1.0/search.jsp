<%@page import="net.property.search.Searcher"%>
<%@ include file="includes/include.jsp"%>
<%@ taglib prefix="str" uri="http://jakarta.apache.org/taglibs/string"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="assets/css/search.css" />" rel="stylesheet">
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-75224548-1', 'auto');
  ga('send', 'pageview');

</script>
<link href="<c:url value="assets/css/details_overlay.css" />"
	rel="stylesheet">
<link href="<c:url value="assets/css/filters.css" />"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<jsp:include page="includes/css.jsp"></jsp:include>
<title>${model.page_title}- PropertySeeker</title>
</head>
<body>
	<jsp:include page="includes/top_bar.jsp"></jsp:include>
	<div class="row navbar navbar-default navbar-fixed-top my-nav-bar">
	<div class="container">
		<div id="search-logo" class="col-sm-4 col-md-3">
			<a href="/" title="FindHome - A Hong Kong home searching specialist"><img
				title="propertySeeker" alt="FindHome - A Hong Kong home searching specialist"
				src="assets/img/logo_trans_mini_50px_height.png" height="50"></a>
		</div>
		<div id="search-form" class="col-sm-7 col-md-6">
			<form name="search" id="search" action="search.html"
				class="form-horizontal" role="form">
				<input type="hidden" id="loc_id" name="id" value="">
				<div class="col-sm-6 col-md-6" id="location-search">
					<div class="right-inner-addon">
						<span class="glyphicon glyphicon-map-marker"></span> <input
							type="text" class="form-control" name="f_l" id="location"
							placeholder="Start typing.. e.g Wan chai, Mong Kok" value="${model.location}">
					</div>
				</div>
				<div class="col-sm-4 col-md-4" id="type-search">
					<select name="t" class="form-control">
						<option <c:if test="${model.type == 1}">selected="selected"</c:if>
							value="2">For Sale</option>
						<option <c:if test="${model.type == 2}">selected="selected"</c:if>
							value="1">To Rent</option>
					</select>
				</div>
				<div class="col-sm-2 col-md-2">
					<button type="submit" class="btn btn-primary">
						<span class="glyphicon glyphicon-search"></span> Find
					</button>
				</div>
				<div class="sort" style="display: block;"></div>
			</form>
		</div>
		</div>
	</div>
	<jsp:include page="includes/filters.jsp"/>
	<div class="container searchPage">
		<!--  Comes from bootsrap up to 12 columns -->
		<jsp:include page="includes/left_sidebar.jsp"></jsp:include>
		<c:if test="${model.error == 'no_matching'}"><jsp:include
				page="includes/no_results.jsp"></jsp:include></c:if>
		<c:if test="${empty model.error}"><jsp:include
				page="includes/results.jsp"></jsp:include></c:if>
		<jsp:include page="includes/right_sidebar.jsp"></jsp:include>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
	<script type="text/javascript" src="assets/js/jquery-2.1.1.min.js"></script>
	<script type="text/javascript" src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="assets/js/jquery-ui-1.10.4.custom.js"></script>
	<script src="<c:url value="assets/js/search.js" />"></script>
	<script src="<c:url value="assets/js/filters.js" />"></script>
	<script
		src="<c:url value="assets/js/galleria-1.4.2/galleria/galleria-1.4.2.min.js" />"></script>
	<%-- <script src="<c:url value="/assets/js/galleria-1.4.2/galleria/themes/classic/galleria.classic.min.js" />"></script> --%>
	<script src="<c:url value="assets/js/Jssor.Slider.FullPack/js/jssor.slider.min.js" />"></script>
	<script src="<c:url value="assets/js/Jssor.Slider.FullPack/js/jssor.js" />"></script>
	<jsp:include page="includes/tracking.jsp"></jsp:include>
	
</body>
</html>