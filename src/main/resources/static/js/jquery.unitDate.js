// 日期选择
(function($){
	$.setStartTime = function(){
		$('.startDate').datepicker({
			dateFormat: "yy/mm/dd",
			minDate: "+d",
			onClose : function(dateText, inst) {
				$( "#endDate" ).datepicker( "show" );
			},
			onSelect:function(dateText, inst) {
				$( "#endDate" ).datepicker( "option","minDate",dateText );
			},
		});
	};
	$.setEndTime = function(){
		$(".endDate").datepicker({
			dateFormat: "yy/mm/dd",
			minDate: "+d",
			defaultDate : new Date(),
			onClose : function(dateText, inst) {
				var startDate = $("input[name=startDate]").val();
				startDate = startDate.replace('/','').replace('/','');
				dateText = dateText.replace('/','').replace('/','');
				
				if (Number(dateText) < Number(startDate)){
				  $( "#endDate" ).datepicker( "show" );
					alert("结束日期不能小于开始日期！");
				}
			}
		});
	};
	$.date = function(){
		$('.date').datepicker(
			$.extend({showMonthAfterYear:true}, $.datepicker.regional['zh-CN'],
				{'showAnim':'','dateFormat':'yy/mm/dd','changeMonth':'true','changeYear':'true','showButtonPanel':'true'}
			));
	};
	$.datepickerjQ = function(){
		$(".ui-datepicker-time").on("click",function(){
			$(".ui-datepicker-css").css("display","block");
		});
		$(".ui-kydtype li").on("click",function(){
			$(".ui-kydtype li").removeClass("on").filter($(this)).addClass("on");
		});
		$(".ui-datepicker-quick input").on("click",function(){
			var thisAlt = $(this).attr("alt");
			var dateList = timeConfig(thisAlt);
			$(".ui-datepicker-quick input").removeClass("on");
			$(this).addClass("on");
			$(".ui-datepicker-time").val(dateList);
			$(".ui-datepicker-css").css("display","none");
			$("#ui-datepicker-div").css("display","none");
		});
		$(".ui-close-date").on("click",function(){
			$(".ui-datepicker-css").css("display","none");
			$("#ui-datepicker-div").css("display","none");
		});
		$(".startDate").on("click",function(){
			$(".endDate").attr("disabled",false);
		});
	}
})(jQuery);

$(function(){
	$.setStartTime();
	$.setEndTime();
	$.datepickerjQ();
	
	var nowDate = new Date();
	timeStr = '不限';
	nowDate.setDate(nowDate.getDate());
	var endDateStr = nowDate.getFullYear() + '/'+  (nowDate.getMonth()+1) + '/' + nowDate.getDate();
	$("#startDate").attr("value",endDateStr)
	$("#endDate").attr("value",timeStr)
});
function datePickers(){
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var dateList = startDate +'一'+ endDate;
	$(".ui-datepicker-time").val(dateList);
	$(".ui-datepicker-css").css("display","none");
}

// 切换
$(".JsSub").change(function(){
	var area_val = $(this).val();
	if (area_val != 1) {
		$(this).closest(".p-r-c").siblings(".formZone").show();
	} else {
		$(this).closest(".p-r-c").siblings(".formZone").hide();
		//$(this).closest(".p-r-c").siblings(".formZone").find("input").removeAttr("checked");
	};
	if($("#ctImg").prop("checked")){
		$("#channelOrientationMain").hide();
		$("#payMode_1").closest("label").show();
	}else{
		$("#channelOrientationMain").show();
		$("#payMode_1").closest("label").hide();
		$("#payMode_1").prop("checked", false);
		$("#payMode_2").prop("checked", true);
	}
});
$(".crowd .nav-tabs li").click(function(){
	$(".crowd .nav-tabs li").removeClass("active");
	$(this).addClass("active");
	var o = $(this).prevAll().length;
	$("#selcont .crowd-con").hide().eq(o).show();
});

// 选择城市
$(function() {
	$("li.cItem").mouseover(function(){
		var l = $(this).find(".city").length;
		if(l > 0){
			$(this).addClass("on");
		}else{
			$(this).removeClass("on");
		}
	});
	$("li.cItem").mouseout(function(){
		$(this).removeClass("on");
	});


	$("input[name='item']").click(function () {
		if(this.checked){
			$(this).closest(".cItem").find('input[name="sub"]').attr("checked", true);
		}else{
			$(this).closest(".cItem").find('input[name="sub"]').attr("checked", false);
		}
	});

	$('input[name="sub"]').click(function () {
		var a_len = $(this).closest(".cItem").find('input[name="sub"]').length;
		var len = $(this).closest(".cItem").find('input[name="sub"]:checked').length;
		var s_num = $(this).closest(".cItem").find("span");
		if(len>0 || this.checked){
			$(this).closest(".cItem").find('input[name="item"]').attr("checked", true);
			if(a_len == len){
				s_num.hide();
			}else{
				s_num.show();
				s_num.html(len+"/"+a_len);
			}
		}else{
			$(this).closest(".cItem").find('input[name="item"]').attr("checked", false);
		}
	});
});