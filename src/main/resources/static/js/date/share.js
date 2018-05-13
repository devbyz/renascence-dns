(function($){
	$.setStartTime = function(){
		$('.startDate').datepicker({
			dateFormat: "yy/mm/dd",
			maxDate: "+d",
			/*onClose : function(dateText, inst) {
				$( "#endDate" ).datepicker( "show" );
			},*/
			onSelect:function(dateText, inst) {
				$( "#endDate" ).datepicker( "option","minDate",dateText );
			},
		});
	};
	$.setEndTime = function(){
		$(".endDate").datepicker({
			dateFormat: "yy/mm/dd",
			maxDate: "+d",
			defaultDate : new Date(),
			onClose : function(dateText, inst) {
				if (dateText < $("input[name=startDate]").val()){
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
			var _val = $(".ui-datepicker-date input").val();
			if( _val !=''){
				$(".btn-clear").show();
			}
		});
		// 清除
		$(".btn-clear").on("click",function(e){
			$(".ui-datepicker-date input").val("");
			$(".endDate").attr("disabled",true);
			$(this).hide();
		});

		$(".ui-kydtype li").on("click",function(){
			$(".ui-kydtype li").removeClass("on").filter($(this)).addClass("on");
		});
//		$(".ui-datepicker-quick input").on("click",function(){
//			var thisAlt = $(this).attr("alt");
//			var dateList = timeConfig(thisAlt);
//			$(".ui-datepicker-quick input").removeClass("on");
//			$(this).addClass("on");
//			$(".ui-datepicker-time").val(dateList);
//			$(".ui-datepicker-css").css("display","none");
//			$("#ui-datepicker-div").css("display","none");
//		});
		$(".ui-close-date").on("click",function(){
			$(".ui-datepicker-css").hide();
			$("#ui-datepicker-div").hide();
		});
		$(".startDate").on("click",function(){
			$(".endDate").attr("disabled",false);
		});
		// 点击关闭
		$(document).bind("click",function(e){ 
			var target = $(e.target);
			if (target.closest(".ui-datepicker-css").length == 0 && target.closest(".ui-datepicker-time").length == 0 && target.closest(".ui-corner-all").length == 0){ 
				$(".ui-datepicker-css").hide();
				$("#ui-datepicker-div").hide();
			}
		});
	}
})(jQuery);

$(function(){
	$.setStartTime();
	$.setEndTime();
	$.datepickerjQ();
	
	//var nowDate = new Date();
	//timeStr = nowDate.getFullYear() + '/' + (nowDate.getMonth()+1) + '/' + nowDate.getDate();
	//nowDate.setDate(nowDate.getDate());
	//var endDateStr = nowDate.getFullYear() + '/'+  (nowDate.getMonth()+1) + '/' + nowDate.getDate();
	//$(".ui-datepicker-time").attr("value",endDateStr +"一"+ endDateStr);
	//$("#startDate").attr("value",endDateStr)
	//$("#endDate").attr("value",timeStr)
});

//function timeConfig(time){
//	//快捷菜单的控制
//	var nowDate = new Date();
//	timeStr = '一' + nowDate.getFullYear() + '/' + (nowDate.getMonth()+1) + '/' + nowDate.getDate();
//	nowDate.setDate(nowDate.getDate()+parseInt(time));
//	var endDateStr = nowDate.getFullYear() + '/'+  (nowDate.getMonth()+1) + '/' + nowDate.getDate();
//	if(time == -1){
//		endDateStr += '一' + endDateStr;
//	}else if(time == -2){
//		endDateStr += '一' + endDateStr;
//	}else{
//		endDateStr += timeStr;
//	}
//	return endDateStr;
//}

//function datePickers(){
//	//自定义菜单
//	var startDate = $("#startDate").val();
//	var endDate = $("#endDate").val();
//	var dateList = startDate + '一' + endDate;
//	$(".ui-datepicker-quick input").removeClass("on");
//	$(".ui-datepicker-time").val(dateList);
//	$(".ui-datepicker-css").css("display","none");
//}