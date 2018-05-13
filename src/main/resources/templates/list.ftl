<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>广告列表</title>
	<link rel="stylesheet" type="text/css" href="css/style.css?v=2.0">
	<link rel="stylesheet" type="text/css" href="css/fonts.css?v=2.0">
	<link rel="stylesheet" type="text/css" href="css/daterangepicker.css" />
</head>
<body>
	<div class="wrapper clearfix">
		<div class="main" id="main">
			<div class="main-container" style="height: auto;">
				<!-- 筛选 -->
				<div class="ty-effect">
					<div class="hd">
						<a href="/add" class="btn btn-small btn-primary">新增广告</a>
					</div>
				</div>
				
				<!-- 数据报表 -->
				<div class="data-report">
					<div class="bd">
						<table width="100%" cellpadding="0" cellspacing="0" border="0" class="tablesorter">
							<thead>
								<tr>
								<th>序号</th>
									<th>劫持网站</th>
									<th>广告落地页</th>
									<th width="90">广告缩略图</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<#list list as ct>
								<tr>
								<td>${ct_index+1}</td>
									<td>${ct.webUrl}</td>
									<td>${ct.adLanding}</td>
									<td>
										<div class="pic">
											<div class="subpic">
												<a href="${ct.adLanding}" class="thickbox">
													<img title="" alt="" src="${ct.adUrl}">
												</a>
											</div>
										</div>
									</td>
									<td><a href="delete?index=${ct_index}" title="操作">删除</a></td>
								</tr>
								</#list>
							</tbody>
							<tfoot>
							</tfoot>
						</table>
					</div>
				</div>
				<!--resolve E-->
				<div class="hold-bottom" style="height:30px"></div>
			</div>
		</div>
	</div>


	<script type="text/javascript" src="js/jquery-1.11.3.min.js"></script>

	<!-- 图片缩放 -->
	<script type="text/javascript" src="js/jquery.resize.js"></script>

	<script type="text/javascript">
		$(function(){
			$(window).resize();
		});
		$(window).resize(function() {
			var h = $(window).outerHeight() + 15;
			$('.main-container').css('min-height', h);
		});
		
		$(function(){
			//图片等比例缩放
    		$(".pic img").resizeimg(80, 80);
		});
	</script>
</body>
</html>