<!DOCTYPE HTML>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link rel="stylesheet" type="text/css" href="../styles/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="../styles/bootstrap-select.css">
        <link rel="stylesheet" type="text/css" href="../styles/website.css">
        <title>force-directed</title>

    </head>
    
<body>
   	 <section class="Layout__Sidebar">
  <nav class="Navigation Navigation--Main">
  <div class="panel-default">
          <li  ><a href="/frame" >信息安全知识体系框架</a></li>
          <li ><a href="/frame/asynchro?pid=0">分步加载框架</a></li>
          <li><a href="/frame/add" class="Navigation--Selected">添加知识点</a></li>
          <li><a href="/frame/delete" >删除知识点</a></li>
          <li ><a href="/knowledge">知识点详细内容</a></li>
  </div>

  </nav>
</section> 
 <section class="Layout__Content">
 <form class="form-horizontal" role="form" id="form1">
 <span class="form-append">
 	<div id="form-group1">
		<div class="form-group">
			<label for="firstname" class="col-sm-2 control-label" >父节点</label>
			<select class="selectpicker col-sm-10"  id="_pnodeName1">                         
			</select>
		</div>
		<input type="text" class="form-control" name="_pnodeName[]" id="_inputNodeName1" style="display:none" placeholder="请输入父节点名"> 
		<div class="form-group">
			<label for="lastname" class="col-sm-2 control-label" >子节点</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="_nodeName[]" 
					   placeholder="请输入子节点名">
				</div>
		</div>
	</div>
 </span>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<a class="btn btn-default" id="addNode">继续添加</a>
				<a class="btn btn-default" id="deleteNode">删除添加</a>	
				<a class="btn btn-default" id="confirmNode">确认添加</a>
			</div>
		</div>

</form>
<hr>
<div id="form-result"></div>
  </section>
<#include "footer.ftl"/>