<%@ include file="includes/include.jsp" %>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${model.page_title} - propertySeeker</title>
</head>
<body>
   	<jsp:include page="includes/top_bar.jsp"></jsp:include>	
	<div class="container" id="primary"> <!--  Holds the primary content -->
		<div id="search-form" class="row">
			<div id="search-logo" class="col-sm-3 col-md-3">
				<a href="index.html" title="propertySeeker - Find the property of your dream" ><img title="propSeek.co.uk" alt="propSeek.co.uk - Find the property of your dream" src="assets/img/search.png"></a>
			</div>
			<form name="search" id="search" action="search.html" class="form-horizontal" role="form">
				<div class="col-sm-4 col-md-4">
			    	<div class="left-inner-addon">
						<span class="glyphicon glyphicon-map-marker"></span>
						<input type="text" class="form-control" name="l" id="location" placeholder="e.g. Aberdeen, NW6, Covent Garden" value="${model.location}">
				  	</div>
			  	</div>
			  	<div class="col-sm-2 col-md-2">
			  		<select name="t" class="form-control">
			  			<option <c:if test="${model.type == '2'}">selected="selected"</c:if>value="2">For Sale</option>
			  			<option <c:if test="${model.type == '1'}">selected="selected"</c:if>value="1">To Rent</option>
			  		</select>
			  	</div>
			  	<div class="col-sm-2 col-md-2">
			  		<button type="submit" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span> Find</button>
			  	</div>
			</form>
		</div>
		
		<div class="container">
			<div id="" class="row">
				<div class="col-sm-12 col-md-12">
					<h1>${model.page_title}</h1>
				</div>
			</div>
		</div>
		
		<div class="container"> <!--  Comes from bootsrap up to 12 columns -->
			<jsp:include page="includes/left_sidebar.jsp"></jsp:include>
			<jsp:include page="includes/no_results.jsp"></jsp:include>
		  	<jsp:include page="includes/right_sidebar.jsp"></jsp:include>		  	
		</div>
	</div>
	<jsp:include page="includes/footer.jsp"></jsp:include>
	<script type="text/javascript" src="assets/js/jquery-2.1.1.min.js"></script>
	
	<script type="text/javascript" src="assets/js/bootstrap-slider.js"></script>
	<script type="text/javascript">
		$('.slider').slider().on("slide", function(e) {
			var price = $(this).val().split(",");
			var lowerPrice = price[0];
			var upperPrice = price[1];
			if(lowerPrice != undefined && upperPrice != undefined) {
				$("#price-start").text(getRepString(lowerPrice));
				$("#price-end").text(getRepString(upperPrice));
			}
		})
		.on("slideStop", function(e) {
			var price = $(this).val().split(",");
			var lowerPrice = price[0];
			var upperPrice = price[1];
			if(lowerPrice != undefined && upperPrice != undefined)
				window.location.href = "${model.searchUrl}<c:forEach var="filter" items="${model.filter_page}">&${filter.key}=${filter.value}</c:forEach>&f_pl=" + price[0] + "&f_pu=" + price[1];
		});
		
		/* $('.slider').slider().on("slide", function(e) {
			var price = $(this).val().split(",");
			$("#price-start").text(getRepString(price[0]));
			$("#price-end").text(getRepString(price[1]));
		})
		.on("slideStop", function(e) {
			var price = $(this).val().split(",");
			window.location.href = "${model.searchUrl}&f_pl=" + price[0] + "&f_pu=" + price[1];
		}); */
		
		function getRepString (rep) {
		    rep = rep+''; // coerce to string
		    if(rep > 1000000) {
		    	if(rep == 3000000)
		    		return (rep/1000000).toFixed(2)+'M+';
		        return (rep/1000000).toFixed(2)+'M';
		    } else if (rep > 1000) { // place a comma between
		        return (rep/1000).toFixed(0)+'k';
		    }
		    // divide and format
		    return rep;
		}
	</script>
</body>
</html>