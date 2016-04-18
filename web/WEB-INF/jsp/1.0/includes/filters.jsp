<%@ include file="include.jsp"%>
<div id="wrap">
	<header style="height: 30px; background-color: #333;">
		<!-- My filters -->
		<div class="container">
			<div class="row">
			<nav id="navigation" style="height: 30px;"
					class="navigation col-md-1"><i class="fa fa-filter" style="color: white;font-weight: bold; vertical-align: bottom;">
					<span style="font-family: sans-serif;color:#F9C295;">
					Filters
					</span>
					</i>
					</nav>
				<nav id="navigation" style="height: 30px;"
					class="navigation col-md-2">
					<!-- Budget filter -->
					<div class="dropdown">
						<button id="min-max-price-range"
							class="dropdown-toggle filter-button" data-toggle="dropdown">
							Budget <i class="fa fa-chevron-down fa-1"></i>
						</button>
						<div class="dropdown-menu col-sm-2" style="padding: 10px;"
							id="price-drop-down" data-filter-type="price">
							<form class="row">
								<div class="col-xs-5">
									<input id="price-range-min"
										class="form-control price-label min-range-inp range-inp num_inp"
										placeholder="Min" data-dropdown-id="min-range" />
								</div>
								<!-- <div class="col-xs-2">  </div> -->
								<div class="col-xs-5">
									<input id="price-range-max"
										class="form-control price-label max-range-inp range-inp num_inp"
										placeholder="Max" data-dropdown-id="max-range" />
								</div>
								<div class="clearfix"></div>

								<ul id="price-min"
									class="min-range col-sm-12 price-range range-list list-unstyled">

									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
								<ul id="price-max"
									class="col-sm-12 max-range price-range range-list text-right list-unstyled hide">
									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
							</form>
						</div>
					</div>


				</nav>
				<nav style="height: 30px;" class="navigation col-md-2">
					<div class="dropdown">
						<button id="min-max-price-range"
							class="dropdown-toggle filter-button" data-toggle="dropdown">
							Gross Area <i class="fa fa-chevron-down fa-1"></i>
						</button>
						<div class="dropdown-menu col-sm-2" style="padding: 10px;"
							data-filter-type="grossArea">
							<form class="row">
								<div class="col-xs-5">
									<input id="gross-inp-min"
										class="form-control min-range-inp range-inp num_inp"
										placeholder="Min" data-dropdown-id="min-range" />
								</div>
								<!-- <div class="col-xs-2">  </div> -->
								<div class="col-xs-5">
									<input id="gross-inp-max"
										class="form-control max-range-inp range-inp num_inp"
										placeholder="Max" data-dropdown-id="max-range" />
								</div>
								<div class="clearfix"></div>

								<ul class="min-range col-sm-12 range-list list-unstyled">

									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
								<ul
									class="col-sm-12 max-range range-list text-right list-unstyled hide">
									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
							</form>
						</div>
					</div>
				</nav>

				<nav style="height: 30px;" class="navigation col-md-2">
					<div class="dropdown">
						<button id="min-max-price-range"
							class="dropdown-toggle filter-button" data-toggle="dropdown">
							Net Area <i class="fa fa-chevron-down fa-1"></i>
						</button>
						<div class="dropdown-menu col-sm-2" style="padding: 10px;"
							data-filter-type="saleableArea">
							<form class="row">
								<div class="col-xs-5">
									<input id="saleable-inp-min"
										class="form-control min-range-inp range-inp num_inp"
										placeholder="Min" data-dropdown-id="min-range" />
								</div>
								<!-- <div class="col-xs-2">  </div> -->
								<div class="col-xs-5">
									<input id="saleable-inp-max"
										class="form-control max-range-inp range-inp num_inp"
										placeholder="Max" data-dropdown-id="max-range" />
								</div>
								<div class="clearfix"></div>

								<ul class="min-range col-sm-12 range-list list-unstyled">

									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
								<ul
									class="col-sm-12 max-range range-list text-right list-unstyled hide">
									<c:forEach var="range_pair" items="${model.rent_range}">
										<li data-value="${range_pair.key}"
											class="cursor filter-values">${range_pair.value}</li>
									</c:forEach>
								</ul>
							</form>
						</div>
					</div>
				</nav>
				<nav id="navigation" style="height: 30px;"
					class="navigation col-md-8">
					<!-- Squared ONE -->
					<div class="squaredOne">
						<input type="checkbox" value="None" id="squaredOne" name="check" />
						<label for="squaredOne"></label>
					</div>
				</nav>
			</div>
		</div>
	</header>
</div>