var map = new BMap.Map("container");
  //var mapStyle = { style: "mapbox" }
  //map.setMapStyle(mapStyle);
  map.centerAndZoom(new BMap.Point(116.404, 39.915), 14);
  //map.centerAndZoom(point, 11);
  map.addControl(new BMap.NavigationControl()); 
  map.addControl(new BMap.OverviewMapControl()); 
  map.enableScrollWheelZoom(); 

  
    var output = "";
  function SetMap2(a,b) {
	  
	  var re1 = "";
	  var re2 = "";
	  var re = "";
    var oGl = document.getElementById("div_gongli");

	var gongli = document.getElementById("gongli");
    var shijian = document.getElementById("shijian");

     var searchComplete = function(results) {
     
      var plan = results.getPlan(0);
     var re1 = plan.getDistance(true);
	 var re2 = plan.getDuration(true);
	 
	 
	 output += a;
	  output += "to";
	 output += b;
	 output += "a";
      output += plan.getDistance(true);       
      output += "/";
      output += plan.getDuration(true);       
	  output += ",";
    }
	   var transit = new BMap.DrivingRoute(map, { renderOptions: { map: map, panel: "results", autoViewport: true },
      onSearchComplete: searchComplete,
      onPolylinesSet: function() {}
    });
    transit.search(a, b);
  
   
  }
    
  
//    var btn1 = document.getElementById("start");
//    btn1.addEventListener("click", function() {
//		var place= new Array();
//		var i = 0;
//		
//		$('input:checkbox:checked').each(function() {
//			place[i] = $(this).attr('pname');
//			i++;
//		}); 
//		for(var n = 0; n < place.length; n++){
//			if(n == place.length - 1){
//				break;
//			}
//			else{
//				for(var m = n + 1; m < place.length; m++){
//					SetMap2(place[n],place[m]);
//				}
//			}
//		}
//  });
//  
//  var btn2 = document.getElementById("dfn");
//   btn2.addEventListener("click", function() {
//	   alert("ss");
//	   alert(output);
//
//	
//  });

