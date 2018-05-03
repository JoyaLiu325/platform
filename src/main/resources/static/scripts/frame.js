/************1.确定初始数据**********/
var width = "680",
    height = "680";
var radius = 20;

//设置缩放功能
var zoom = d3.behavior.zoom()
			 .scaleExtent([0.5,10]) //设置最大最小缩放比
			 .on("zoom",zoomed);

function zoomed(){
	svg.attr("transform","scale(" + d3.event.scale + ")")
	//取消zoom中双击放大图像的设置
		.on("dblclick.zoom",null);
}

var svg = d3.select("body")
			.append("svg")
			.attr("width",width)
			.attr("height",height);
	svg = svg.append("g")
			 .call(zoom);
    
var nodes = new Array();
var edges = new Array();
var linkedByIndex = new Array();
var nodeMap =d3.map(nodes,function(d){ return d.name; });
var edgeMap = d3.map(edges,function(d){ return d.target;});
//设置节点的索引
var index = 0;
//设置线段的序号，便于order
var sortNum = 0;
//设置开关控制高亮
var toggle = 0;
//size用来存放连线的层数，用来控制线的粗细
var size = 0;
//设置数组
//设置力导向图布局
var force = d3.layout.force()
			  .size([width,height]) 
			  .linkDistance(120)
			  .charge(-400);
	d3.json("/frame/loadmore?pid=0",function(error,data){
			if(error){
				return console.error(error);
			}
			console.log(data);
			var list = data.jsonList;
			draw(list);
			
//			必须要加前缀function
			function draw(list){
//				第一组的map为空
				if(nodeMap.empty()){
//					构造map,key为节点名，value为节点对应的索引
//					第一个节点特殊考虑
					nodes.push({ name : list[0].nodeName, count : 1});
					nodeMap.set(nodes[index].name,index);
					index++;
//					map创建和添加节点同步实现
					for(i = 1;i<list.length;i++){
						if(!nodeMap.has(list[i].nodeName)){
							nodes.push({ name : list[i].nodeName, count : 0});
							nodeMap.set(nodes[index].name,index);
							index++;
						}
					}
					edgeMap.set(0,0);
//					第一个连线组丢弃
					for(i = 1,j=0;j < list.length-1,i<list.length;i++,j++){
						edges.push({ source : nodeMap.get(list[i].pnodeName) , target : nodeMap.get(list[i].nodeName) , 
							size : 1 , num : sortNum++  });
						edgeMap.set(edges[j].target,edgeMap.get(edges[j].source) + 1);
						edges[j].size = edgeMap.get(edges[j].target);
					} 
				}
				else{
//					console.log(list);
//					判断元素在map中是否为空，直接在map中添加
					for(i=0;i<list.length;i++){
						if(!nodeMap.has(list[i].nodeName)){	
							nodes.push({ name : list[i].nodeName, count : 0});
							nodeMap.set(list[i].nodeName,index++);
							edges.push({source : nodeMap.get(list[i].pnodeName), target : nodeMap.get(list[i].nodeName)
								, num : sortNum,size : 1});
							edgeMap.set(edges[sortNum].target,edgeMap.get(edges[sortNum].source) + 1);
							edges[sortNum].size = edgeMap.get(edges[sortNum].target);
							sortNum++;
						}
					}
				}


	/***********2.转化数据***********/
			   force.nodes(nodes)
				    .links(edges);
				force.start();//开启布局计算

	/************3.绘制***********/
//				配置圆点渐变颜色，书119
				var a = d3.rgb("#3182bd");
				var b = d3.rgb("#c6dbef");
				var compute = d3.interpolate(a,b);
				
//				拖拽样式设置
				var drag=force.drag()
/*		   		 .on("dragstart",function(d){
		   			 d.fixed = true;
		   		 })*/
		   		 .on("dragend",function(d,i){
		   			 d3.select(this).style("fill",compute(edgeMap.get(i)/5.0));
		   		 })
		   		 .on("drag",function(d){
		   			 d3.select(this).style("fill",d3.rgb(49,91,125));
		   		 });
				
//              连线update处理部分
				var lineUpdate = svg.selectAll(".forceLine")
				               		.data(edges)
				               		.sort(function(a,b){ 
				               			return b.num-a.num ;});
				               		
//				连线enter处理部分
				var lineEnter = lineUpdate.enter();
				var enterLine = lineEnter
				               .append("line")
				               .attr("class","forceLine")
				               .style("stroke","#4679BD")
				               .style("stroke-width", 2)
				               .style("marker-end",  "url(#suit)");
				
//				获取圆点update部分
				var cirUpdate = svg.selectAll(".forceCircle")
                				   .data(nodes);

//                获取圆点enter部分
                 var cirEnter = cirUpdate.enter();
//                圆点enter部分处理部分
                 var enterCir = cirEnter.append("circle")
                						.attr("class","forceCircle")
                						.attr("id",function(d,i){ return "circle"+i;})
                						.attr("r",function(d,i){
                							return 15 + 15/(edgeMap.get(i)+1);
                						})
                						.style("fill", function(d,i){
                							return compute(edgeMap.get(i)/5.0);
                						})
                						.style("stroke","#fff")
                						.style("stroke-width",.5)
                						.call(drag)
                						.on("mouseover",function(d,i){
                							d3.select(this).style("cursor","pointer");
                							/*connectedNodes(d3.select(this));*/
                							if(d.count == 0){
                								force.start();
                								d.count = 1;
//                								请求参数名称于controller中一致
                								d3.json("/frame/loadmore?pnodeName="+d.name,function(error,data){
                									if(error)
                										return console.error(error);
                									draw(data.jsonList);
                								});
                							}
                							force.stop();
                						})
//                						点击打开知识点详细页面
                						.on("dblclick",function(d,i){
                							window.open("/knowledge?nodeName="+d.name);
                						});
//                文字update处理部分
                var textUpdate = svg.selectAll(".forceText")
			                 .data(nodes);
//               获取文字enter处理部分
                var textEnter = textUpdate.enter();
                var enterText = textEnter.append("text")
                						 .attr("class","forceText")
                						 .attr("dx",function(d,i){return d3.select("#"+"circle"+i).attr("r");})
                						 .attr("dy",6) //见书38，相对移动距离
                						 .text(function(d){ return d.name; })
                						 .style("font-size",function(d,i){
                							 return 10 + 5 / Math.sqrt(edgeMap.get(i));
                						 });
			/* * 这一段可以不要的，文字自动和被绑定的数据坐标一致      
			 * 				.attr("x",function(d){return d.x;})
			                 .attr("y",function(d){return d.y;})*/
                
				//tick表示运动的每一步，为tick设置监听器
				force.on("tick",function(){
//						设置活动范围
						nodes.forEach(function(d, i) {
						    d.x = Math.min(width-radius*2, Math.max(radius, d.x));
						    d.y = Math.min(height-radius*2, Math.max(radius, d.y));
						  });
//						跟新连线的端点坐标，d.source.x为当前坐标，d.source.px为前一个坐标
						lineUpdate.attr("x1",function(d){ return d.source.x; });
						lineUpdate.attr("y1",function(d){ return d.source.y; });
						lineUpdate.attr("x2",function(d){ return d.target.x; });
						lineUpdate.attr("y2",function(d){ return d.target.y; });
					// 更新节点坐标cx,cy圆心的x,y坐标，见书34
					    cirUpdate.attr("cx",function(d){ return d.x; });
					    cirUpdate.attr("cy",function(d){ return d.y; });
					// 更新文字的坐标
					    textUpdate.attr("x",function(d){ return d.x; });
					    textUpdate.attr("y",function(d){ return d.y; });
				});	
			}
//			svg中设置箭头marker
	svg.append("defs").selectAll("marker")
		    		  .data(["suit", "licensing", "resolved"])
		    		  .enter()
		    		  .append("marker")
		    		  .attr("id", function(d) { return d; })
		    		  .attr("viewBox", "0 -5 10 10")
		    		  .attr("refX", 27)
		    		  .attr("refY", 0)
		    		  .attr("markerWidth", 6)
		    		  .attr("markerHeight", 6)
		    		  .attr("orient", "auto")
		    		  .append("path")
		    		  .attr("d", "M0,-5L10,0L0,5 L10,0 L0, -5")
		    		  .style("stroke", "#4679BD")
		    		  .style("opacity", "1");
	

			});
/*****highlight******/	
	
/*	var preEdges = 0;
//	a,b是suorce和target的值
	function neighboring(a, b) {
	    return linkedByIndex[a + "," + b];
	}

	function connectedNodes(d){
		if(preEdges<edges.length){
			console.log(edges.length);
			for(i = preEdges;i<edges.length;i++) {
				linkedByIndex[i + "," + i] = 1;
				console.log(edges[i].source.index + "," + edges[i].target.index);
			    linkedByIndex[edges[i].source.index + "," + edges[i].target.index] = 1;
			}
			preEdges = edges.length;
		}
		
		if(toggle == 0){
			var thisIndex = d.node().__data__.index;
			console.log(thisIndex);
			d3.selectAll("circle").style("opacity",function(o){
				console.log(nodeMap.get(o.name));
				console.log(neighboring(nodeMap.get(o.name),thisIndex));
				console.log(neighboring(thisIndex,nodeMap.get(o.name)));
				return neighboring(nodeMap.get(o.name),thisIndex)|neighboring(thisIndex,nodeMap.get(o.name)) ? 1: 0.1;
			});
			
			d3.selectAll("line").style("opacity",function(o){
				if( thisIndex == o.source.index | thisIndex == o.target.index ){
					console.log(o.source.index);
					console.log( o.target.index);
			}
				return thisIndex == o.source.index | thisIndex == o.target.index ? 1 : 0.1;
			});
			toggle = 1;
		}else{
			d3.selectAll("circle").style("opacity",1);
			d3.selectAll("line").style("opacity",1);
			toggle = 0;
		}
	}	*/
/*//	设置鱼眼放大功能	
//	鱼眼放大功能必须要在画面静止的情况下才能实现，但是静止后无法加载子节点
	var fisheye = d3.fisheye.circular()
    						.radius(120);
	svg.on("mousemove", function() {
//		force.stop();
		fisheye.focus(d3.mouse(this));
		d3.selectAll("circle").each(function(d) { d.fisheye = fisheye(d); })
        					  .attr("cx", function(d) { return d.fisheye.x; })
        					  .attr("cy", function(d) { return d.fisheye.y; })
        					  .attr("r", function(d) { return d.fisheye.z * 20; });
		d3.selectAll("line").attr("x1", function(d) { return d.source.fisheye.x; })
        					.attr("y1", function(d) { return d.source.fisheye.y; })
        					.attr("x2", function(d) { return d.target.fisheye.x; })
        					.attr("y2", function(d) { return d.target.fisheye.y; });
		d3.selectAll("text").each(function(d){ d.fosheye = fisheye(d); })
    						.attr("x",function(d){ return d.fisheye.x; })
    						.attr("y",function(d){ return d.fisheye.y; })
  });*/	
	
