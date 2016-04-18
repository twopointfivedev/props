/**
 * 
 */ 
	$.fn.exists = function () {
	    return this.length !== 0;
	};
	
	$(document).ready(function() {
		$(".filtered").show();
		$(".filtered").parents().show();
		if($(".filtered").parent().find("ul").find("li").exists())
			$(".filtered").parent().find("ul").find("li").slice(0,10).show();
		else
			$(".filtered").parent().siblings().slice(0,10).show();		
	});
	

	$("#see_more").click(function(e) {
		$(".filtered").parent().find("ul").find("li").slice(0,20).show();
		$(this).hide();
		e.preventDefault();
	});

	$("#location").autocomplete({
        source: function( request, response ) {
        	var q = $('#location').val();
            $.getJSON("sugg-location.html?q=" + encodeURI(q), response);
        },
      	focus: function( event, ui ) {
    		return false;
    	},
    	delay: 0,
    	open: function(event, ui) {
            //using the 'open' event to capture the originally typed text
            var self = $(this), val = self.val();
            //saving original search term in 'data'.
            self.data('searchTerm', val);
        },
    	select: function( event, ui ) {
    		var self = $(this),
               keyPressed = event.keyCode,
               keyWasEnter = event.keyCode === 13,
               useSelection = true,
               val = self.data('searchTerm');
    		$("#location").val(ui.item.value);
    		$("#loc_id").val(ui.item.id);
    		console.debug("ID: " + ui.item.id);
    		if (keyPressed) {
                if (keyWasEnter) {
                    useSelection = true;
                    event.preventDefault();
                    window.setTimeout(function() {
                        $('form#search').submit();
                    }, 0);
                }
            }
            return useSelection;
    	}
    }).data( "ui-autocomplete" )._renderItem = function( ul, item ) {
    	ul.addClass("query-autocomplete");
    	var re = new RegExp("^" + $("#location").val(), "i");
    	return $( "<li>" )
    		.data( "ui-autocomplete-item", item )
    	    .append("<a>" + item.value.replace(re,"<b>$&</b>") + "</a>")
    	    .appendTo( ul );
    };
	
	// Changing the sorting order
	$("select[name='s']").change(function(e) {
		var sort = $(this).find("option:selected").val();
		window.location.href = setGetParameter('f_sort',sort, window.location.href);
	});
	
	$("#buttonFilterGross").click(function(e) {
		var newUrl = window.location.href;
		newUrl = setGetParameter("f_gal", $("[name='f_gal']").val(), newUrl);
		newUrl = setGetParameter("f_gau", $("[name='f_gau']").val(), newUrl);
		window.location.href = newUrl;
	});
	
	$("#buttonFilterSaleable").click(function(e) {
		var newUrl = window.location.href;
		newUrl = setGetParameter("f_sal", $("[name='f_sal']").val(), newUrl);
		newUrl = setGetParameter("f_sau", $("[name='f_sau']").val(), newUrl);
		window.location.href = newUrl;
	});
	
	function setGetParameter(paramName, paramValue, url)
	{
	   // var url = window.location.href;
	    if (url.indexOf(paramName + "=") > 0)
	    {
	        var prefix = url.substring(0, url.indexOf(paramName+"="));
	        var suffix = url.substring(url.indexOf(paramName+"="));
	        suffix = suffix.substring(suffix.indexOf("=") + 1);
	        suffix = (suffix.indexOf("&") >= 0) ? suffix.substring(suffix.indexOf("&")) : "";
	        url = prefix + paramName + "=" + paramValue + suffix;
	    }
	    else
	    {
	    if (url.indexOf("?") < 0)
	        url += "?" + paramName + "=" + paramValue;
	    else
	        url += "&" + paramName + "=" + paramValue;
	    }
	    return url;
	}
	
	function removeURLParameter(parameter , url) {
	    //prefer to use l.search if you have a location/link object
	    var urlparts= url.split('?');   
	    if (urlparts.length>=2) {

	        var prefix= encodeURIComponent(parameter)+'=';
	        var pars= urlparts[1].split(/[&;]/g);

	        //reverse iteration as may be destructive
	        for (var i= pars.length; i-- > 0;) {    
	            //idiom for string.startsWith
	            if (pars[i].lastIndexOf(prefix, 0) !== -1) {  
	                pars.splice(i, 1);
	            }
	        }

	        url= urlparts[0]+'?'+pars.join('&');
	        return url;
	    } else {
	        return url;
	    }
	}
	function removeAreaFilter(param1,param2){
		var newUrl = removeURLParameter(param1, window.location.href);
			newUrl = removeURLParameter(param2, newUrl);
		window.location.href = newUrl;
	}
	function bedroomFilter(bedroomCount){
		window.location.href = setGetParameter("f_r", bedroomCount, window.location.href);
	}
/*	function handleTitleClick(){
	    var value = $(this).closest('.property');
	    if(value!=null && value.length==1){
	    	var value2 = value.find( "#overlay" );
	    	if(value2!=null && value2.length >0){
	    		value2.show();
	    	}
	    }
	}*/
	
	function switchNav(selector, activeTabSel){
		$('.nav-tab').hide();
		$(selector).show();
		$('.nav-title').removeClass('active');
		$(activeTabSel).addClass('active');
	}
	
	$('.gallery-title').click(showGallery);
	$('.photo-frame').click(showGallery);
	function showGallery(){
		var prop = $(this).closest('.property');
		if(!(prop.find('#gallery_overlay div[u="slides"]').length)){
			createGallery(prop);
		};
		$('.overlay').hide();
		prop.find('#gallery_overlay').show();
	}

	$('.proptitle').click({selector: ".details-tab", activeTabSel: ".details-title"}, showOverlay);
		function showOverlay(event){
			var prop = $(this).closest('.property');
			var imageCount = prop.find('#image-count').val();
			var bigImgPath = prop.find('#big-image-path').val();
			var prop_count = prop.find('#property-no').val();
			if(!(prop.find('#gallery_overlay div[u="slides"]').length)){
				//createGallery(prop, 'slider1_container' + prop_count, imageCount, bigImgPath);
				createGallery(prop);
			};
	    if(prop!=null && prop.length==1){
	    	var value2 = prop.find( "#overlay" );
	    	if(value2!=null && value2.length >0){
	    		value2.show();
	    	}
	    }
	    switchNav(event.data.selector, event.data.activeTabSel);
	}
	$('.overlay').click(function(event) {
	    if(!$(event.target).closest('.overlayContent').length) {
	        if($('.overlayContent').is(":visible")) {
	            $('.overlay').hide();
	        }
	    }        
	});
	

	$('.gallery_overlay').click(hideGallery);
function hideGallery(event) {
	if (!$(event.target).closest('.my_slider1_container').length) {
		if ($('.my_slider1_container').is(":visible")) {
			$('.overlay').hide();
		}
	}
}
	function createGallery(prop){
		var imageCount = prop.find('#image-count').val();
		var bigImgPath = prop.find('#big-image-path').val();
		var prop_count = prop.find('#property-no').val();
		var slider_selector = 'slider1_container' + prop_count;
		var galleryDiv = '<div id="' +slider_selector+'" class="my_slider1_container"> '+
		'<div u="slides"> ';
		for (var i = 1; i <= imageCount; i++) {
			var imgFileName =  bigImgPath+i+'.jpg';
			galleryDiv += '<div> '+'<img u="image" src="'+imgFileName+'" /> '+'</div> ';
		}
		galleryDiv +='<span u="arrowleft" class="jssora05l"></span> ' +
		'<span u="arrowright" class="jssora05r"></span> ' +
		'</div>';
		$(prop.find('#gallery_overlay .gallery_container')).append(
				galleryDiv
		);
		
		var options = {
				$AutoPlay : true, //[Optional] Whether to auto play, to enable slideshow, this option must be set to true, default value is false
				$AutoPlayInterval : 1500, //[Optional] Interval (in milliseconds) to go for next slide since the previous stopped if the slider is auto playing, default value is 3000
				$PauseOnHover : 1, //[Optional] Whether to pause when mouse over if a slider is auto playing, 0 no pause, 1 pause for desktop, 2 pause for touch device, 3 pause for desktop and touch device, 4 freeze for desktop, 8 freeze for touch device, 12 freeze for desktop and touch device, default value is 1

				$DragOrientation : 3, //[Optional] Orientation to drag slide, 0 no drag, 1 horizental, 2 vertical, 3 either, default value is 1 (Note that the $DragOrientation should be the same as $PlayOrientation when $DisplayPieces is greater than 1, or parking position is not 0)
				$ArrowKeyNavigation : true, //[Optional] Allows keyboard (arrow key) navigation or not, default value is false
				$SlideDuration : 800, //Specifies default duration (swipe) for slide in milliseconds

				$ArrowNavigatorOptions : { //[Optional] Options to specify and enable arrow navigator or not
					$Class : $JssorArrowNavigator$, //[Requried] Class to create arrow navigator instance
					$ChanceToShow : 2
				//[Required] 0 Never, 1 Mouse Over, 2 Always
				},

				$ThumbnailNavigatorOptions : { //[Optional] Options to specify and enable thumbnail navigator or not
					$Class : $JssorThumbnailNavigator$, //[Required] Class to create thumbnail navigator instance
					$ChanceToShow : 2, //[Required] 0 Never, 1 Mouse Over, 2 Always

					$ActionMode : 1, //[Optional] 0 None, 1 act by click, 2 act by mouse hover, 3 both, default value is 1
					$SpacingX : 8, //[Optional] Horizontal space between each thumbnail in pixel, default value is 0
					$DisplayPieces : 10, //[Optional] Number of pieces to display, default value is 1
					$ParkingPosition : 360
				//[Optional] The offset position to park thumbnail
				}
			};
			var jssor_slider1 = new $JssorSlider$(slider_selector,
					options);
            ScaleSlider(jssor_slider1);
            $(window).bind("load", ScaleSlider(jssor_slider1));
            $(window).bind("resize", ScaleSlider(jssor_slider1));
            $(window).bind("orientationchange", ScaleSlider(jssor_slider1));
	}
	
    function ScaleSlider(jssor_slider1) {
    //    var refSize = jssor_slider1.$Elmt.parentNode.clientWidth;
      //  if (refSize) {
       //     refSize = Math.min(refSize, 800);
            jssor_slider1.$ScaleWidth(1000);
   //     }
/*        else {
            window.setTimeout(ScaleSlider, 30);
        }*/
    }
    
    $('.contact-agnt').click({selector: ".agent-tab", activeTabSel: ".agent-title"},showOverlay);
    
    $('.filters').on('keydown', '.num_inp', function(e){-1!==$.inArray(e.keyCode,[46,8,9,27,13,110,190])||/65|67|86|88/.test(e.keyCode)&&(!0===e.ctrlKey||!0===e.metaKey)||35<=e.keyCode&&40>=e.keyCode||(e.shiftKey||48>e.keyCode||57<e.keyCode)&&(96>e.keyCode||105<e.keyCode)&&e.preventDefault();});
  
