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
	        $('.selectpicker').selectpicker('refresh');  
	        $('.selectpicker').selectpicker('render');  
	    }  
	});	
}
let num = 1;
$(document).ready(function(){
		selectpicker();
		$("#addNode").click(function(){
			num++;
			$("span.form-append").append('<div id="form-group'+num+'"><div class="form-group"><label for="firstname" class="col-sm-2 control-label" >父节点</label><select class="selectpicker col-sm-10"  id="_pnodeName'+num+'"></select></div><input type="text" class="form-control" name="_pnodeName[]" id="_inputNodeName'+num+'"+ style="display:none" placeholder="请输入父节点名"> <div class="form-group"><label for="lastname" class="col-sm-2 control-label" >子节点</label><div class="col-sm-10"><input type="text" class="form-control" name="_nodeName[]" placeholder="请输入子节点名"></div></div></div>');
			selectpicker();
		});
		$("#deleteNode").click(function () {
			console.log("#form-group"+num);
			if(num>1){
				$("#form-group"+num).remove();
				num --;
			}
		});
		
		$("#confirmNode").click(function () {
			for(var i=0;i<num;i++){
				var j = i + 1;
				$("#_inputNodeName"+j).val($("#_pnodeName"+j).selectpicker('val'));
			}
		    $.ajax({
		            type: "POST",   //提交的方法
		            dataType: "json",//预期服务器返回的数据类型
		            url:"/frame/addNode", //提交的地址  
		            data:$('#form1').serializeArray(),// 序列化表单值  
		            error: function() {  //失败的话
		                 console.log("error");  
		            },  
		            success: function(data) {  //成功
		                 $("#form-result").html(data.message);
		            }  
		         });
       });  
});