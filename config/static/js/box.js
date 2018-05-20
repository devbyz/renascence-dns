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