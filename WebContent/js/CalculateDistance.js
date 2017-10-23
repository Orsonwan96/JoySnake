var map = new BMap.Map("container");
  //var mapStyle = { style: "mapbox" }
  //map.setMapStyle(mapStyle);
  map.centerAndZoom(new BMap.Point(116.404, 39.915), 14);
  //map.centerAndZoom(point, 11);
  map.addControl(new BMap.NavigationControl()); // 添加平移缩放控件
  map.addControl(new BMap.OverviewMapControl()); //添加缩略地图控件
  map.enableScrollWheelZoom(); //启用滚轮放大缩小
  map.setMapStyle({ style: "mapbox" });
  
  function SetMap2() {
    var oGl = document.getElementById("div_gongli");
    var ofname = document.getElementById("farea");
    var otname = document.getElementById("tarea");
	var gongli = document.getElementById("gongli");
    var shijian = document.getElementById("shijian");
    var output = "全程：";
    if (ofname.value == "") {
      alert('请输入出发地！');
      return;
    }
    if (otname.value == "") {
      alert('请输入到达地!');
      return;
    }
    var title = document.getElementById("div_title");
    title.innerText = "'" + ofname.value + "到" + otname.value + "' 查询结果";
    var searchComplete = function(results) {
      if (transit.getStatus() != BMAP_STATUS_SUCCESS) { return; }
      var plan = results.getPlan(0);
     // alert(plan.getDistance(true));
	 gongli.value = plan.getDistance(true); 
	 shijian.value = plan.getDuration(true);
      output += plan.getDistance(true);       //获取距离
      output += "/";
      output += plan.getDuration(true);        //获取时间
    }
    var transit = new BMap.DrivingRoute(map, { renderOptions: { map: map, panel: "results", autoViewport: true },
      onSearchComplete: searchComplete,
      onPolylinesSet: function() { oGl.innerText = output; }
    });
    transit.search(ofname.value, otname.value);
  }