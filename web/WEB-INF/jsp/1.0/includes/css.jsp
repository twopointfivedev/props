<%@page import="net.property.utils.GlobalConstants"%>
<script type="text/javascript">
	var stylesheet = document.createElement('link');
	stylesheet.href = 'assets/css/bootstrap.css';
	stylesheet.rel = 'stylesheet';
	stylesheet.type = 'text/css';
	document.getElementsByTagName('head')[0].appendChild(stylesheet);
	
	var stylesheet2 = document.createElement('link');
	stylesheet2.href = 'assets/css/${param.file}.css';
	stylesheet2.rel = 'stylesheet';
	stylesheet2.type = 'text/css';
	document.getElementsByTagName('head')[0].appendChild(stylesheet2);
</script>