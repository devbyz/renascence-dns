<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>创意列表</title>
	<link rel="stylesheet" type="text/css" href="css/style.css?v=2.0">
	<link rel="stylesheet" type="text/css" href="css/fonts.css?v=2.0">
	<link rel="stylesheet" type="text/css" href="css/daterangepicker.css" />
</head>
<body>
	<div class="wrapper clearfix">
		<header class="clearfix">
		</header>
		
		<div class="top" style="height:60px;width:100%;"><!--顶部占位--></div>

		<div class="main" id="main">
			<div class="side clearfix" style="width:85px;height:60px;float:left;"></div>
			<div class="main-container" style="height: auto;">
				<!-- 筛选 -->
				<div class="ty-effect">
					<div class="hd">
						<a href="/add" class="btn btn-small btn-primary">新增创意</a>
					</div>
				</div>
				
				<!-- 数据报表 -->
				<div class="data-report">
					<div class="bd">
						<table width="100%" cellpadding="0" cellspacing="0" border="0" class="tablesorter">
							<thead>
								<tr>
								<th>序号</th>
									<th>网站</th>
									<th>网站链接</th>
									<th width="90">创意缩略图</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
							<#list list as ct>
								<tr>
								<td>${ct_index+1}</td>
									<td>${ct.webUrl}</td>
									<td>${ct.adUrl}</td>
									<td>
										<div class="pic">
											<div class="subpic">
												<a href="${ct.actualPath}" class="thickbox">
													<img title="" alt="" src="${ct.actualPath}">
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

	<!-- 弹出框 -->
	<script type="text/javascript" src="js/layer/layer.js"></script>

	<!-- 下拉 -->
	<link href="js/select/jquery.searchableSelect.css" rel="stylesheet">
	<script src="js/select/jquery.searchableSelect.js"></script>

	<!-- 排序 -->
	<script src="js/table/jquery.dataTables.min.js"></script>

	<!-- 日期 -->
	<link rel="stylesheet" href="js/date/jquery-ui-1.9.2.custom.css" type="text/css">
	<script type="text/javascript" src="js/date/jquery-ui-1.9.2.custom.js"></script>
	<script type="text/javascript" src="js/date/share.js"></script>

	<!-- 图片缩放 -->
	<script type="text/javascript" src="js/jquery.resize.js"></script>
	<script type="text/javascript" src="js/jquery.thickbox.js"></script>

	<script type="text/javascript">
		$(function(){
			$(window).resize();
		});
		$(window).resize(function() {
			var h = $(window).outerHeight() + 15;
			$('.main-container').css('min-height', h);
		});
		
		$(function(){
			//点击展开账户
			$('.js-top-ico').on("click", function(){
				$('.poparea').hide();
				$(this).siblings('.poparea').toggle();
				return false;
			});

			// 点击关闭
			$(document).bind("click",function(e){ 
				var target = $(e.target);
				if (target.closest(".poparea").length == 0){ 
					$(".poparea").hide(); 
				}
				if (target.closest(".edit-price").length == 0 && target.closest(".edit-area").length == 0){ 
					$(".edit-price").hide(); 
				} 
				if (target.closest(".plan-unit-box").length == 0 && target.closest(".unit-num").length == 0){ 
					$(".plan-unit-box").hide(); 
				}
			});

			//弹出一个层
			$('.oper-but a').on('click', function(){
				layer.confirm('当前计划正在投放，暂停该计划将停止该计划下所有单元与创意的投放！<br>请确认是否<b class="s-red">暂停〈计划名称〉</b>计划推广', {
					icon :3,
					//scrollbar: false,
					btn: ['确定','取消'] //按钮
				}, function(){
					layer.msg('确定', {icon: 1});
				});
			});

			//消息切换
			$('.inside-letter .tpline li a').on("click", function(){
				var thiscity = $(this).attr("href");
				/*$("#ajaxtab .loading").ajaxStart(function(){
					$(this).show();
				});
				$("#ajaxtab .loading").ajaxStop(function(){
					$(this).hide();
				});*/
				$('.news-list').load(thiscity);
				$('.inside-letter .tpline li').removeClass("on");
				$(this).parent().addClass("on");
				return false;
			});
			$('.inside-letter .tpline li a').eq(0).trigger("click");

			// 排序
			$('.tablesorter').DataTable({
				paging:         false,	//分页
				searching: 		false,	//每页的条数
				lengthChange: 	false,	//搜索
				bInfo: 			false,	//页脚信息
				aaSorting: [ 7, "desc" ],
				columnDefs: 	[
					{"orderable": false, "targets": 0},
					{"orderable": false, "targets": 1},
					{"orderable": false, "targets": 2},
					{"orderable": false, "targets": 3},
					{"orderable": false, "targets": 12}
				]
			});

			// 下拉
			$('.plan-select').searchableSelect();


			// 修改日限额
			$("tbody tr").mouseover(function(){
				$(this).find(".editbt").css('visibility','visible');
				$(".change-price").on("click", function(){               
					$('._edit_price').css('left', $(this).offset().left+0);
					$('._edit_price').css('top', $(this).offset().top-25);
					$('._edit_price input._val').val($(this).prev().text().replace('¥', '').replace(',', ''));
					$('input[name="id"]').val($(this).prev().data('aid'));
					$('._edit_price').show();
				});
			});
			$("tbody tr").mouseout(function(){
				$(this).find(".editbt").css('visibility','hidden');
			});
			// 取消
			$('.quxiao').on('click', function(){
				$(this).closest('.edit-price').hide();
			});
			$('.queding').on('click', function(){
				$(this).closest('.edit-price').hide();
				//弹出一个层
				layer.msg('修改成功', {time: 1000});
			});
			$(window).scroll(function(){
				$('.edit-price').hide();
			});
			// 状态
			$(".plan-state").mouseenter(function(){
				$(this).find(".plan-state-default").addClass("plan-state-open");
				$(this).find(".plan-state-open").width($(this).find('.oper-but').width()+30);
				return false;
			});
			$(".plan-state").mouseleave(function(){
				$(this).find(".plan-state-default").removeClass("plan-state-open");
				$(this).find(".plan-state-default").width("35px");
			});


			$(".unit-num").on("click", function(){
				$(".plan-unit-box").show();
			});
			// 关闭
			$(".closed").on("click", function(){
				$(this).closest(".plan-unit-box").hide();
			});

			//图片等比例缩放
    		$(".pic img").resizeimg(80, 80);
		});
	</script>
</body>
</html>