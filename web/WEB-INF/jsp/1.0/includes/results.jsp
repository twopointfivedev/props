<%@ include file="include.jsp"%>


<div id="results" class="col-sm-9 col-md-9 col-lg-9">
	<jsp:include page="results_info.jsp"></jsp:include>
	<!-- <div class="fadeMe"></div> -->
	<c:set var="propcounter" value="0" scope="page"></c:set>
	<c:forEach var="result" items="${model.results}">
	<c:set var="eachresult" value="${result}" scope="request"></c:set>
	<c:set var="propcounter" value="${propcounter + 1}"/>
		<div class="property">
			<div class="container-fluid">
				<div class="row">

					<div class="col-md-3 photo">
						<div class="prop-img">
							<div class="photo-frame cursor">
								<img src="${result.thumbnailPath}">
							</div>
						</div>
					</div>
					<div class="col-md-9">
						<div class="row title-contact-row">
							<div class="row col-md-9">
								<div class="title">
									<h2>
										<a class="proptitle cursor">${result.title}</a>
									</h2>
								</div>
							</div>
							<div class="col-md-3">

								<button type="button" class="btn btn-sm btn-success orange pull-right contact-agnt" >
									<i class="fa fa-phone fa-1"></i> Contact
								</button>
							</div>
						</div>
						<div class="row loc-rent-row">
							<div class="col-md-6">
								<i class="fa fa-map-marker fa-1 inline-text"></i>
								<div class="neighbourhood light-gray inline-text">
									${result.neighbourhood}</div>
							</div>
							<div class="col-md-6">
								<div class="light-gray inline-text pull-right">
									<c:if test="${result.propertyType == 1}">
						Monthly Rent:
					</c:if>
									<c:if test="${result.propertyType == 2}">
						Price:
					</c:if>
									<div class="price inline-text">
										<h2>
											<fmt:formatNumber maxFractionDigits="0"
												value="${result.monthlyRental}" />
										</h2>
									</div>
									HK<i class="fa fa-usd fa-1 inline-text"></i>
								</div>
							</div>
						</div>
						<div class="row features-details">
							<c:if test="${result.bedRooms >= 0}">
								<div class="col-sm-3 col-md-3">
									<div class="features-cell">
										<div class="value">${result.bedRooms}</div>
										<div class="key">Bedrooms</div>
									</div>
								</div>
							</c:if>
							<c:if test="${result.bathRooms >= 0}">
								<div class="col-sm-3 col-md-3">
									<div class="value">${result.bathRooms}</div>
									<div class="key">BathRooms</div>
								</div>
							</c:if>
							<c:if test="${result.saleableArea > 0}">
								<div class="col-sm-3 col-md-3">
									<div class="value">${result.saleableArea}
										<span class="unit">${result.unit}</span>
									</div>
									<div class="key">Net Area</div>
								</div>
							</c:if>

							<c:if test="${result.grossArea > 0}">
								<div class="col-sm-3 col-md-3">
									<div class="value">${result.grossArea}
										<span class="unit">${result.unit}</span>
									</div>
									<div class="key">Gross Area</div>
								</div>
							</c:if>
							<c:if test="${result.furnished!=null && result.furnished!=''}">
								<div class="col-sm-5 col-md-5">
									<div class="features-cell">
										<div class="value">${result.furnished}</div>
										<div class="key">Furnishing</div>
									</div>
								</div>
							</c:if>
						</div>
					</div>
				</div>



				<div class="row">

					<%-- 						<div name="grossArea"> 
							<c:if test="${result.grossArea > 0}">
							Gross Area:
								${result.grossArea} ${result.unit}
							</c:if>
						</div> --%>
				</div>
			</div>
			<div class="more-info">
				<input id="image-count" type="number" value='${result.imageCount}'>
				<input id="big-image-path" type="text" value='${result.bigImagePath}'>
				<input id="property-no" type="number" value='${propcounter}'>
			</div>
			<%-- <c:if test="${result.title == 'Tak Lee Building'}"> --%>
				<jsp:include page="prop_details_overlay.jsp"></jsp:include>
			<%-- </c:if> --%>
		</div>
<%-- 		<div class="row">
			<div class="description">${result.summary}</div>
			<div class="row more-options">
				<div class="col-sm-8 col-md-8 photo">
					<a target="_blank" href="${result.propertyDetailsUrl}">full
						details and photos &raquo;</a>
				</div>
			</div>

		</div> --%>

	</c:forEach>
</div>


<jsp:include page="pagination.jsp"></jsp:include>
