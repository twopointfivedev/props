<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
[<c:forEach items='${model.locations}' var='item' varStatus='status'>
	<c:if test="${status.index!=0}">,</c:if>
		{"value":"${item.neighbourhood}, ${item.locality}","id":"${item.id}"}
</c:forEach>]