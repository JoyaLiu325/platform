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
          <li  ><a href="/frame" >安全知识体系框架</a></li>
          <li ><a href="/frame/asynchro?pid=0">分步加载框架</a></li>
          <li><a href="/frame/revise" class="Navigation--Selected">修改框架</a></li>
          <li ><a href="/knowledge">知识点详细内容</a></li>
          <li><a href="/frame/revise">修改知识点</a></li>
          <li><a href="">登录</a></li>
  </div>

  </nav>
</section> 
 <section class="Layout__Content">
 <h1 class="PageTitle">${frameNode.nodeName}</h1>
 	<p>所属知识领域:
 		<#list pnodeList as pnode>
 		<#if pnode??>
 		<a href="/knowledge?_nodeName=${pnode}">${pnode}</a></#if>
 		</#list>
		</p>
 	<p>包含知识领域:
 		<#list nodeList as node>
 		<#if node??>
 		<a href="/knowledge?_nodeName=${node}">${node}</a></#if>
 		</#list></p></br>

    <h2>摘要</h2>
    
	<p> <b>......................<b></p>
	
    </br><h2>详细内容</h2>

    <p>The fully-fledged PeARS system is supposed to be completely distributed. You can imagine it as an 'automated' version of Phase 1, where you don't have to go and hunt for pods yourself. Your PeARS install will automatically find them on other users' systems and connect to them. </p>

    <ul class="Splitter">
      <li class="Splitter__Square Splitter__Square--small"></li>
      <li class="Splitter__Square Splitter__Square--big"></li>
      <li class="Splitter__Square Splitter__Square--small"></li>
    </ul>
   </br><h2>相关链接</h2>
  </section>
<#include "footer.ftl"/>