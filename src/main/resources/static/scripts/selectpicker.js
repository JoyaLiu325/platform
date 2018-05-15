function selectpicker(){
	$.ajax({
		// get请求地址  
		    url: "/frame/getNames",  
		    dataType: "json",  
		    success: function (data) { 
		    	var list = data.jsonList;
		    	var optArr = [];  
		        for (var i = 0; i < list.length; i++) {  
		            $('.selectpicker').append("<option value=" + list[i] + ">" + list[i] + "</option>");  
		        }  
		  
		        // 缺一不可  
		        $('#pnodeNames').selectpicker('refresh');  
		        $('#pnodeNames').selectpicker('render');  
		    }  
		});	
}