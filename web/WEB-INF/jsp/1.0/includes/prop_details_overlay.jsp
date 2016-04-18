<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<div id="overlay" class="overlay">
	<div class="overlayContent img-rounded">
	<div>
		<div class="container-fluid">
			<div class="row my-row">
				<div class="pop-title col-md-12">
					<h2>
						<a target="_blank" href="#">${eachresult.title}</a>
					</h2>
				</div>
				<div class="row loc-rent-row my-row">
					<div class="col-md-6 border-right">
						<i class="fa fa-map-marker fa-1 inline-text"></i>
						<div class="neighbourhood light-gray inline-text">${eachresult.neighbourhood}</div>
					</div>
					<div class="col-md-6">
						<div class="light-gray inline-text">
							<c:if test="${eachresult.propertyType == 1}">
							Monthly Rent: 
							</c:if>
							<c:if test="${eachresult.propertyType == 2}">
						Price:
					</c:if>
							<span class="price"><fmt:formatNumber
									maxFractionDigits="0" value="${eachresult.monthlyRental}" /> </span>
							HK<i class="fa fa-usd fa-1 inline-text"></i>
						</div>
					</div>
				</div>
			</div>
		</div>
		<nav class="navbar navbar-default navbar-fixed-top">
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active nav-title details-title"><a class="white cursor"
						 onclick="switchNav('.details-tab','.details-title');">Details</a></li>
					<li class="nav-title agent-title"><a class="white cursor"
						onclick="switchNav('.agent-tab','.agent-title');">Contact</a></li>
					<li class="nav-title gallery-title"><a class="white cursor">Gallery</a></li>
				</ul>
			</div>

		</nav>
		</div>
		<div class="nav-tab details-tab">
			<div class="row features-details col-md-12">
				<c:if test="${eachresult.bedRooms >= 0}">
					<div class="col-sm-3 col-md-3">
						<div class="features-cell">
							<div class="value">${eachresult.bedRooms}</div>
							<div class="key">Bedrooms</div>
						</div>
					</div>
				</c:if>
				<c:if test="${result.bathRooms >= 0}">
					<div class="col-sm-3 col-md-3">
						<div class="value">${eachresult.bathRooms}</div>
						<div class="key">BathRooms</div>
					</div>
				</c:if>
				<c:if test="${eachresult.saleableArea > 0}">
					<div class="col-sm-3 col-md-3">
						<div class="value">${eachresult.saleableArea}
							<span class="unit">${eachresult.unit}</span>
						</div>
						<div class="key">Net Area</div>
					</div>
				</c:if>

				<c:if test="${eachresult.grossArea > 0}">
					<div class="col-sm-3 col-md-3">
						<div class="value">${eachresult.grossArea}
							<span class="unit">${eachresult.unit}</span>
						</div>
						<div class="key">Gross Area</div>
					</div>
				</c:if>
				<c:if
					test="${eachresult.furnished!=null && eachresult.furnished!=''}">
					<div class="col-sm-3 col-md-3">
						<div class="features-cell">
							<div class="value">${eachresult.furnished}</div>
							<div class="key">Furnishing</div>
						</div>
					</div>
				</c:if>
			</div>
			<div class="row my-row col-md-12 row-clearance">
				<span class="key">Address:</span><span>${eachresult.address}</span>
			</div>
			<div class="row my-row col-md-12 row-clearance">
				<span class="key">Details:</span>
				<div class="details">${eachresult.propertyDescription}</div>
			</div>
		</div>
		<div class="nav-tab gallery-tab"></div>
		<div class="nav-tab agent-tab">
			<div class="container-fluid"></div>
			<div class="row">
				<div class="col-sm-6 col-md-6">
					<div class="value">${eachresult.agentName}</div>
					<div class="key"><i class="fa fa-user fa-1"></i> Agent Name</div>
				</div>
				<div class="col-sm-6 col-md-6">
										<div class="value">${eachresult.agentPhoneNo}</div>
					<div class="key"><i class="fa fa-phone fa-1"></i> Phone</div>
					</div>
			</div>
		</div>
	</div>
</div>
<div id="gallery_overlay" class="overlay gallery_overlay">
	<div class="gallery_container img-rounded"></div>
</div>
