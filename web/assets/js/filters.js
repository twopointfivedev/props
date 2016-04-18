$(document).ready(function() {

	$('.dropdown').hover(function() {
		$(this).find('.dropdown-menu').stop(true, true).delay(200).slideDown();
		var dropdown = $(this);
		setTimeout(function(){ dropdown.find('.range-inp').first().focus();	},500); 
		}, function() {
			$(this).find('.dropdown-menu').stop(true, true).delay(200).slideUp();
		});
	
//	   $('#min-max-price-range').click(function(event) {
//	        setTimeout(function(){ $('.price-label').first().focus();	},0);    
//	    });

	   var priceLabelObj=null;
	    $('.range-inp').focus(function (event) {
	        priceLabelObj=$(this);
	        var closestDropdownMenu = $(this).closest('.dropdown-menu');
	        closestDropdownMenu.find('.range-list').addClass('hide');
	       // $('.price-range').addClass('hide'); //.range-list
	        $(closestDropdownMenu.find('.'+$(this).data('dropdownId'))).removeClass('hide');
	    });

	    $(".range-list li").click(function(){    
	        priceLabelObj.attr('value', $(this).attr('data-value'));
//	        var curElmIndex=$( ".range-inp" ).index( priceLabelObj );
//	        var nextElm=$( ".range-inp" ).eq(curElmIndex+1);
	        var closestDropdownMenu = $(this).closest('.dropdown-menu');
	        var thisRangeInputs = closestDropdownMenu.find(".range-inp");
	        var curElmIndex=closestDropdownMenu.find(".range-inp").index( priceLabelObj );
	        var nextElm=thisRangeInputs.eq(curElmIndex+1);

	        if(nextElm.length){
	            //$( ".range-inp" ).eq(curElmIndex+1).focus();
	        	thisRangeInputs.eq(curElmIndex+1).focus();
	        }else{
	        	closestDropdownMenu.find('.filter-button').dropdown('toggle');
	        	if(closestDropdownMenu.attr('data-filter-type')=="price"){
	            applyPriceFilter();
	        	}
	        	else if(closestDropdownMenu.attr('data-filter-type')=="grossArea"){
	        		applyGrossAreaFilter();
	        	}
	        	else if(closestDropdownMenu.attr('data-filter-type')=="saleableArea"){
	        		applySaleableAreaFilter();
	        	}
	        }
	    });
	    

				    $(".dropdown-menu").mouseleave(
					function(event) {
						if ($(this).find(".min-range-inp").val()
								|| $(this).find(".max-range-inp").val()) {
							applyPriceFilter();
						}
					});
	    
	    function applyPriceFilter(){
	    	var newUrl = window.location.href;
			newUrl = setGetParameter("f_pl", $("#price-range-min").val(), newUrl);
			newUrl = setGetParameter("f_pu", $("#price-range-max").val(), newUrl);
			window.location.href = newUrl;
	    }
	    
	    function applyGrossAreaFilter(){
	    	var newUrl = window.location.href;
			newUrl = setGetParameter("f_gal", $("#gross-inp-min").val(), newUrl);
			newUrl = setGetParameter("f_gau", $("#gross-inp-max").val(), newUrl);
			window.location.href = newUrl;
	    }
	    
	    function applySaleableAreaFilter(){
	    	var newUrl = window.location.href;
			newUrl = setGetParameter("f_sal", $("#saleable-inp-min").val(), newUrl);
			newUrl = setGetParameter("f_sau", $("#saleable-inp-max").val(), newUrl);
			window.location.href = newUrl;
	    }
	    
	    $('.dropdown').on('keydown', '.num_inp', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault();});

});