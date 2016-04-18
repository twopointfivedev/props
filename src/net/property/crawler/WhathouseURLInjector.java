package net.property.crawler;

public class WhathouseURLInjector extends MoveToURLInjector {/*
	// Zoopla based site
	private static String urls[] ={
		"http://www.whathouse.co.uk/home/aberdeen-city/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/aberdeenshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/angus/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/argyll-and-bute/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bedfordshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/berkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/blaenau-gwent/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bridgend/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/brighton-and-hove/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bristol/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bristol-avon/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/buckinghamshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/caerphilly/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cambridgeshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cardiff/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/carmarthenshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/ceredigion/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/channel-islands/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cheshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/city-of-edinburgh/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/clackmannanshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/conwy/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cornwall/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-antrim/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-armagh/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-down/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-durham/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-fermanagh/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-londonderry/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-tyrone/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cumbria/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/darlington/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/denbighshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/derbyshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/devon/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dorset/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dumfries-and-galloway/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dundee-city/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-ayrshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-dunbartonshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-lothian/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-renfrewshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-sussex/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-yorkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/essex/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/falkirk/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/fife/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/flintshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/glasgow-city/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/gloucestershire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/greater-london/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/greater-manchester/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/gwynedd/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hampshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hereford-and-worcester/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/herefordshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hertfordshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/highland/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/inverclyde/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-anglesey/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-man/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-wight/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isles-of-scilly/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/kent/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/lancashire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/leicestershire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/lincolnshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/merseyside/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/merthyr-tydfil/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/midlothian/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/milton-keynes/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/monmouthshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/moray/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/neath-port-talbot/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/newport/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/norfolk/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-ayrshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-eart-lincolnshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-lanarkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-lincolnshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-somerset/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-yorkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/northamptonshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/northumberland/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/nottinghamshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/orkney/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/oxfordshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/pembrokeshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/perth-and-kinross/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/powys/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/redcar-and-cleveland/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/renfrewshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/rhondda-cynon-taff/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/rutland/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/scottish-borders/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/shetland-islands/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/shropshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/somerset/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-ayrshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-gloucestershire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-lanarkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-yorkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/staffordshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/stirling/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/stockton-on-tees/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/suffolk/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/surrey/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/swansea/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/swindon/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/torfaen/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/tyne-and-wear/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/vale-of-glamorgan/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wales/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/warwickshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-dunbart/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-lothian/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-midlands/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-sussex/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-yorkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/western-isles/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wiltshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/worcestershire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wrexham/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/yorkshire/sales/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/aberdeen-city/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/aberdeenshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/angus/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/argyll-and-bute/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bedfordshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/berkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/blaenau-gwent/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bridgend/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/brighton-and-hove/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bristol/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/bristol-avon/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/buckinghamshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/caerphilly/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cambridgeshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cardiff/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/carmarthenshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/ceredigion/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/channel-islands/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cheshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/city-of-edinburgh/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/clackmannanshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/conwy/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cornwall/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-antrim/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-armagh/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-down/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-durham/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-fermanagh/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-londonderry/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/county-tyrone/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/cumbria/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/darlington/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/denbighshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/derbyshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/devon/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dorset/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dumfries-and-galloway/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/dundee-city/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-ayrshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-dunbartonshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-lothian/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-renfrewshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-sussex/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/east-yorkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/essex/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/falkirk/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/fife/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/flintshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/glasgow-city/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/gloucestershire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/greater-london/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/greater-manchester/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/gwynedd/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hampshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hereford-and-worcester/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/herefordshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/hertfordshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/highland/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/inverclyde/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-anglesey/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-man/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isle-of-wight/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/isles-of-scilly/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/kent/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/lancashire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/leicestershire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/lincolnshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/merseyside/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/merthyr-tydfil/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/midlothian/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/milton-keynes/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/monmouthshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/moray/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/neath-port-talbot/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/newport/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/norfolk/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-ayrshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-eart-lincolnshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-lanarkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-lincolnshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-somerset/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/north-yorkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/northamptonshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/northumberland/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/nottinghamshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/orkney/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/oxfordshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/pembrokeshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/perth-and-kinross/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/powys/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/redcar-and-cleveland/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/renfrewshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/rhondda-cynon-taff/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/rutland/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/scottish-borders/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/shetland-islands/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/shropshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/somerset/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-ayrshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-gloucestershire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-lanarkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/south-yorkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/staffordshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/stirling/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/stockton-on-tees/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/suffolk/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/surrey/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/swansea/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/swindon/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/torfaen/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/tyne-and-wear/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/vale-of-glamorgan/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wales/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/warwickshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-dunbart/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-lothian/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-midlands/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-sussex/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/west-yorkshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/western-isles/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wiltshire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/worcestershire/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/wrexham/lettings/most-recent-first/page-1",
		"http://www.whathouse.co.uk/home/yorkshire/lettings/most-recent-first/page-1"
		};
	private static String site = "whathouse.co.uk";
	
	public WhathouseURLInjector() {
		super(site,urls);
	}
*/}