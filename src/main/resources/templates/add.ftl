<!DOCTYPE html>
<html>
<#assign basePath = request.contextPath/> 
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>广告上传</title>
<link rel="stylesheet" type="text/css" href="css/style.css?v=2.0">
<link rel="stylesheet" type="text/css" href="css/fonts.css?v=2.0">
<link rel="stylesheet" type="text/css" href="css/daterangepicker.css" />
</head>
<body>
	<div class="wrapper clearfix">
		<header class="clearfix"> </header>
		<div class="top" style="height: 60px; width: 100%;">
			<!--顶部占位-->
		</div>
		<div class="main" id="main">
			<div class="side clearfix"
				style="width: 85px; height: 60px; float: left;"></div>
			<div class="main-container" style="height: auto;">

				<div class="add-new bor-box">
					<div class="add-unit" style="width: 700px; margin: 0 auto;">
						<form action="/saveAd" method="post">
							<div class="basic-info">
								<div class="bd">
									<ul>
										<li><span class="p-l-t">网　　　站：</span>
											<div class="p-r-c">
												<input type="text" id="webUrl" name="webUrl" value=""
													class="form-control">
											</div></li>
											<li><span class="p-l-t">广告落地页：</span>
											<div class="p-r-c">
												<input type="text" id="adLanding" name="adLanding" value=""
													class="form-control">
											</div></li>
										<li><span class="p-l-t">上 传 广 告：</span>
											<div id="container" class="p-r-c">
												<a id="pickfiles" href="javascript:;"
													class="btn btn-success file">上　传 <input type="file"
													name="file" id="doc" multiple="multiple" />
												</a> <label><a href="javascript:;"
													title="请上传：jpg，jpeg，png，gif文件。文件小于100kb">?</a></label> <a
													href="javascript:;" id="modal">查看广告尺寸</a>
											</div></li>
										<li><span id="creative-p-l-t" class="p-l-t" style="">广　　　告：</span>
											<div class="p-r-c">
												<div class="up-pic-list">
													<div id="filelist"><#-- 广告 --></div>
												</div>
											</div></li>
										<li><span id="creative-p-l-t" class="p-l-t" style="">&nbsp;</span>
											<div class="p-r-c">
												<a id="uploadfiles" href="javascript:;" class="btn btn-primary"
									style="color: #fff; display: none;">上 传</a> 
									<input type="submit" class="btn btn-primary" id="submit" value="提　交" /> 
									<a class="btn btn-default" href="/list" style="color:#666;">返　回</a>
											</div></li>
									</ul>
								</div>
								
							</div>
						</form>
					</div>
				</div>
				<!--resolve E-->
				<div class="hold-bottom" style="height: 30px"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>
	<!-- 弹出框 -->
	<script type="text/javascript" src="js/layer/layer.js"></script>
	
	<!-- 图片缩放 -->
	<script type="text/javascript" src="js/jquery.resize.js"></script>
	<!-- TIPS -->
	<script type="text/javascript" src="js/jquery.tips.min.js"></script>
	
	<script type="text/javascript" src="js/plupload.full.min.js"></script>
	<script type="text/javascript">
		$(function() {

			//图片等比例缩放
			$(".pic img").resizeimg(110, 110);

			//弹出一个层
			$('#modal')
					.on(
							'click',
							function() {
								layer
										.open({
											type : 1,
											title : false,
											shadeClose : true,
											area : '300px',
											content : '<div class="data-report"><div class="bd"><table class="tablesorter" width="100%" cellpadding="0" cellspacing="0" border="0"><thead><tr><th>广告尺寸</th></tr></thead><tbody><tr><td>300*250</td></tr></tbody></table></div></div>'
										});
							});

		});
   		
	   	var uploader = new plupload.Uploader({
	   		<#-- 上传参数设置 -->
			runtimes : 'html5,flash,silverlight,html4',
			browse_button : 'pickfiles',
			container: document.getElementById('container'),
			url : '${basePath}/upload',
			flash_swf_url : '${staticDomain}/imgs/Moxie.swf',
			silverlight_xap_url : '${staticDomain}/imgs/Moxie.xap',
			filters : {
				max_file_size : '10mb',
				mime_types: [
					{title : "Image files", extensions : "jpg,gif,png"},
					{title : "Swf files", extensions : "swf" }
				]
			},
			<#--当Plupload初始化完成后触发,监听uploader,为它绑定事件--> 
			init: {
				<#--当Init事件发生后触发 -->
				PostInit: function() {
					$('#filelist').html('');
					$('#uploadfiles').on("click",function() {
			    		<#--开始上传-->
						uploader.start();
						return false;
					});
				},
				<#--当文件添加到上传队列后触发-->
				FilesAdded: function(up, files) {
					$("#creative-p-l-t").show();
					plupload.each(files, function(file) {
						//alert(JSON.stringify(file));
						var filehtml = '<div id="p'+file.id+'" class="pic-box" name="creative">';
							filehtml +='<div class="pic"><div class="subpic">正在上传</div></div>';
// 							filehtml +='<p title="'+file.name+'">文件名:'+file.name+'</p>';
// 							filehtml +='<p title="size">大小:'+plupload.formatSize(file.size)+'</p>';
// 							filehtml +='<p title="big">尺寸:</p>';
							filehtml +='</div>';
						//document.getElementById('filelist').innerHTML += filehtml;
							document.getElementById('filelist').innerHTML = filehtml;
					});
					<#--添加到队列之后马上就触发上传-->
					$("#uploadfiles").click();
				},
				<#--会在文件上传过程中不断触发，可以用此事件来显示上传进度 -->
				UploadProgress: function(up, file) {
					if($("#p"+file.id).find("p[title='"+file.name+"']").prev("p").length==0){
					   $("#p"+file.id).find("p[title='"+file.name+"']").before('<p>进度:'+file.percent+'%</p>');
					}
				},
				<#--当上传队列中所有文件都上传完成后触发 -->
				UploadComplete:function(up,file){

				},
				<#--
					当队列中的某一个文件上传完成后触发
					监听函数参数：(uploader,file,responseObject)
					uploader为当前的plupload实例对象，file为触发此事件的文件对象，responseObject为服务器返回的信息对象，它有以下3个属性：
					response：服务器返回的文本
					responseHeaders：服务器返回的头信息
					status：服务器返回的http状态码，比如200
				-->
				FileUploaded: function(up,file,result){
					//alert(JSON.stringify(file));
					var obj = eval("("+result.response+")");	
					
					//if(file.type == "application/x-shockwave-flash"){
						//alert("上传的是flash");
					//}else{
						if(typeof(obj)=='object'){
							//console.log(obj.creativeList)
							if(obj.creative!=null && typeof(obj.creative) != 'undefined'){
								if(file.type == "application/x-shockwave-flash"){
								$("#p"+file.id).find(".subpic").html('<img src="${staticDomain}/imgs/default_flash_background.png" alt="广告"/>');
								}else{
									$("#p"+file.id).find(".subpic").html('<img src="ad/'+obj.creative+'" alt="广告"/>');
								}
								$("#p"+file.id).find("p[title='big']").after('<span class="closed" name="pic_miss" id="p'+file.id+'">×</span>');
								$("#p"+file.id).append('<input type="hidden" name="name" value="'+file.name.replace(",","")+'" />');
								$("#p"+file.id).append('<input type="hidden" name="adUrl" id="adUrl" value="ad/'+obj.creative+'" />');
							}else if(obj.failReason!=null && typeof(obj.failReason)!='undefined'){
								$("#p"+file.id).find(".subpic").html('<img src="${staticDomain}/imgs/fail.jpg" />');
								$("#p"+file.id).find("p[title='big']").after('<p style="color:red">原因:'+obj.failReason+'</p>');
								$("#p"+file.id).find("p[title='big']").after('<span class="closed" name="pic_miss" id="p'+file.id+'">×</span>');
								$("#p"+file.id).find("p[title='big']").hide();
							}
						}
					//}
					<#--点击删除上传的广告-->
				     $(".closed").click(function(){
						if($(this).attr("name")=='pic_miss'){
							$("#"+$(this).attr("id")).remove();
							$("#noPic").text("");
							$("#noPic").hide();	              
						}
				     });    
				},
				<#--异常-->
				Error: function(up, err) {
					console.log(err);
					document.getElementById('console').appendChild(document.createTextNode("\nError #" + err.code + ": " + err.message));
				}
			}
		});

		uploader.init();		
	</script>
</body>
</html>