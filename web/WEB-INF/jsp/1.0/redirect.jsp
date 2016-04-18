<%@ include file="includes/include.jsp" %>  
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%-- <jsp:include page="includes/css.jsp?file=redirect"></jsp:include> --%>	
<title>Redirecting to ${model.site} - propertySeeker</title>
</head>
<body>
	<jsp:include page="includes/top_bar.jsp"></jsp:include>	
	<div id="primary"> <!--  Holds the primary content -->
		<div id="home-logo">
			<a href="/" title="propertySeeker - Find the property of your dream" ><img title="propertySeeker" alt="propertySeeker - All homes under one roof" src="assets/img/propertyseeker.PNG"></a>
		</div>
		<h1>
			You are now being redirect to <b>${model.site}</b>
		</h1>
		<div class="progress progress-striped active" id="progress-bar">
		  	<div class="progress-bar"  role="progressbar" aria-valuenow="45" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
		    	<span class="sr-only"></span>
			</div>
		</div>
		<div>
			If you are not being redirected within 5 seconds, <a href="${model.propertyDetailsUrl}">view ad here</a>
		</div>
	</div>
	<script type="text/javascript">
		function redirect(){
			window.location.replace('${model.propertyDetailsUrl}');
		}
		setTimeout(redirect,1500);
	</script>
	<jsp:include page="includes/tracking.jsp"></jsp:include>
</body>
</html>