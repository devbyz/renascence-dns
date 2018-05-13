<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8" />
    <title>首页</title>
</head>
<style type="text/css">
    html, body
    {
        margin: 0px 0px;
        width: 100%;
        height: 100%;
    }
    iframe
    {
        margin: 0px 0px;
        width: 100%;
        height: 800%;
    }
    dl,dt,dd{padding:0px;margin:0px;list-style-type: none;}
	#boxad {border: #b5b5b5 1px solid; padding: 1px 1px 0px 1px;z-index: 300; background: #ffffff; float: right; overflow: hidden; width: 300px; height: 250px;right: 0px; bottom: 0px;position:fixed;
		_position:absolute; /*兼容IE6*/
		_top:expression(offsetParent.scrollTop+document.documentElement.clientHeight-this.offsetHeight); /*兼容IE6*/
	}
	/*tit样式*/
	#boxad dt,#boxad dt #AD_tit{height:28px;line-height:28px;}
	#boxad dt{width:100%;background-image: url(/imgs/bg.jpg);background-repeat:repeat-x;background-position: right top;}
	#boxad dt #AD_tit{display:block;overflow:hidden;}
	#boxad dd{ width:96%; height:auto; display:block; margin:0 auto; overflow:hidden; padding:4px 0px 4px 0px;}
	#AD_tit{ width:70%;float:left;color:#203D5F;font-size:10pt;text-indent:0.8em;font-weight:bold;}
	#close,#zoomad{ width:12px;	height:28px;line-height:28px; display:block; float:right; margin-right:8px;}
	#close{ width:12px; float:right;}
	#zoomad{ float:right;}
	#close img{ margin-top:8px}
	#zoomad img{ margin-top:10px}
</style>
<body>
<script type="text/javascript">
	iframeHeight();
	
    function iframeHeight() {
        document.frames('ifrmname').location.reload();
    }

</script>
<iframe src="${url}" id="iframeId" name="ifrmname"  scrolling="no" frameborder="0" ></iframe>
	<dl id="boxad" data-num="0">
		<dt>
			<a id="AD_tit">ad</a>
			<a href="javascript:;" id="close" ><img title="点击关闭" src="/imgs/guanbi.jpg" border=0></a>
		</dt>
		<dd>
			<a href="${lurl}" target="_blank" title="ad"><img src="/imgs/jdp.png"></a>
		</dd>
	</dl>
	<script src="/js/jquery-2.1.4.min.js" type="text/javascript"></script>
	<script type="text/javascript">
		var tk_index=0;
		var xx_num;
		$(function(){
		     xx_num=$("#boxad").attr("data-num");
		    tankuan()
		
		    $("#close").click(function(){
		        $("#boxad").animate({height:"hide"},800);
		        if(tk_index!=xx_num){
		             setTimeout(tankuan,1000);
		       }
		
		    });
		})
		
		function tankuan(){
		    if(tk_index!=xx_num){     
		        $("#boxad").animate({height:"show"},800);
		     }
		}

	</script>
</body>
</html>