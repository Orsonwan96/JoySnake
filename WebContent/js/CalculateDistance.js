var map = new BMap.Map("container");
  //var mapStyle = { style: "mapbox" }
  //map.setMapStyle(mapStyle);
  map.centerAndZoom(new BMap.Point(116.404, 39.915), 14);
  //map.centerAndZoom(point, 11);
  map.addControl(new BMap.NavigationControl()); // ���ƽ�����ſؼ�
  map.addControl(new BMap.OverviewMapControl()); //������Ե�ͼ�ؼ�
  map.enableScrollWheelZoom(); //���ù��ַŴ���С
  map.setMapStyle({ style: "mapbox" });
  
  function SetMap2() {
    var oGl = document.getElementById("div_gongli");
    var ofname = document.getElementById("farea");
    var otname = document.getElementById("tarea");
	var gongli = document.getElementById("gongli");
    var shijian = document.getElementById("shijian");
    var output = "ȫ�̣�";
    if (ofname.value == "") {
      alert('����������أ�');
      return;
    }
    if (otname.value == "") {
      alert('�����뵽���!');
      return;
    }
    var title = document.getElementById("div_title");
    title.innerText = "'" + ofname.value + "��" + otname.value + "' ��ѯ���";
    var searchComplete = function(results) {
      if (transit.getStatus() != BMAP_STATUS_SUCCESS) { return; }
      var plan = results.getPlan(0);
     // alert(plan.getDistance(true));
	 gongli.value = plan.getDistance(true); 
	 shijian.value = plan.getDuration(true);
      output += plan.getDistance(true);       //��ȡ����
      output += "/";
      output += plan.getDuration(true);        //��ȡʱ��
    }
    var transit = new BMap.DrivingRoute(map, { renderOptions: { map: map, panel: "results", autoViewport: true },
      onSearchComplete: searchComplete,
      onPolylinesSet: function() { oGl.innerText = output; }
    });
    transit.search(ofname.value, otname.value);
  }