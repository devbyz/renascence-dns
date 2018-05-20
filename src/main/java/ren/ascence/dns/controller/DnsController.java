package ren.ascence.dns.controller;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ren.ascence.dns.handler.AdHandler;
import ren.ascence.dns.utils.GetUtil;
import ren.ascence.dns.vo.AdVo;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DnsController {
  private final static Logger logger = LoggerFactory.getLogger(DnsController.class);
  @Autowired
  private AdHandler adHandler;

  //String addr = "http://localhost/";
  String addr = "http://47.96.175.185/";
  
  @RequestMapping("/")
  public String jc(ModelMap map, HttpServletRequest request) {
    String url = request.getScheme()+"://"+request.getServerName();
    String requestUrl = request.getRequestURI();
    if(StringUtils.isNotBlank(requestUrl)) {
      if(requestUrl.length()>1) {
        url += request.getRequestURI();
      }
    }
    if(StringUtils.isNotBlank(request.getQueryString())) {
      url += ("?" + request.getQueryString());
    }
    logger.debug("url:{}",url);
    if("47.96.175.185".equals(request.getServerName())) {
      url = "http://www.adpadx.com";
    }
    try {
      String c = GetUtil.execute(url);
      c = parseC(c,request.getServerName());
      map.put("c", c);
    } catch (IOException e) {
      map.put("c", "");
      e.printStackTrace();
    }
    return "c";
  }

  public String parseC(String c,String serverName) {
    String css = boxCSS();
    String js = boxJS();
    String body = boxBODY(serverName);
    int bodystart = c.indexOf("<body>");
    c = c.substring(0,bodystart)+css+c.substring(bodystart);
    int bodyend = c.indexOf("</body>");
    c = c.substring(0,bodyend)+body+js+c.substring(bodyend);
    return c;
  }
  
  public String boxCSS() {
    String x = "";
    x += "<link rel=\"stylesheet\" type=\"text/css\" href=\""+addr+"/css/box.css\">";
    return x;
  }
  
  public String boxJS() {
    String x = "";
        x += "<script src=\""+addr+"/js/jquery-2.1.4.min.js\" type=\"text/javascript\"></script>";
        x += "<script src=\""+addr+"/js/box.js\" type=\"text/javascript\"></script>";
    return x;
  }
  
  public String boxBODY(String serverName) {
    AdVo adVo = adHandler.getAd(serverName);
    if (Objects.isNull(adVo)) {
      adVo = new AdVo("http://www.qq.com", addr+"ad/jdp.png", "http://www.baidu.com");
    }
    String x = "";
    x += "<dl id=\"boxad\" data-num=\"0\">";
    x += "<dt>";
        x += "<a id=\"AD_tit\"> </a>";
        x += "<a href=\"javascript:;\" id=\"close\" ><img title=\"点击关闭\" src=\""+addr+"/imgs/guanbi.jpg\" border=0></a>";
    x += "</dt>";
    x += "<dd>";
        x += "<a href=\""+adVo.getAdLanding()+"\" target=\"_blank\" title=\"ad\"><img src=\""+adVo.getAdUrl()+"\"></a>";
    x += "</dd>";
    x += "</dl>";
    return x;
  }
  
  
  
  
  
  
  @RequestMapping("/index")
  public String index(ModelMap map, HttpServletRequest request) {
    // String requestUrl =
    // request.getScheme()+"://"+request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
    logger.debug("scheme:{},serverName:{},requestURI:{},queryString:{}", request.getScheme(),
        request.getServerName(), request.getRequestURI(), request.getQueryString());

    AdVo adVo = adHandler.getAd(request.getServerName());
    if (Objects.isNull(adVo)) {
      adVo = new AdVo("http://www.qq.com", "ad/jdp.png", "http://www.baidu.com");
    }else {
      if(!adVo.getWebUrl().startsWith("http")) {
        adVo.setWebUrl("http://www."+adVo.getWebUrl());
      }
    }
    map.put("adVo", adVo);
    map.put("adtit", "");

    return "index";
  }
  
  @RequestMapping("/test")
  public String test(ModelMap map, HttpServletRequest request) {
    try {
      //String c = GetUtil.execute("http://localhost/test2");
      String c = GetUtil.execute("http://www.qq.com");
      c = parseC(c,request.getServerName());
//      int bodystart = c.indexOf("<body>");
//      c = c.substring(0,bodystart)+boxCSS()+c.substring(bodystart);
//      int bodyend = c.indexOf("</body>");
//      c = c.substring(0,bodyend)+boxBODY(request.getServerName())+boxJS()+c.substring(bodyend);
      System.out.println(c);
      map.put("c", c);
    } catch (IOException e) {
      map.put("c", "");
      e.printStackTrace();
    }
    return "test";
  }
  
  @RequestMapping("/test2")
  public String test2(ModelMap map, HttpServletRequest request) {
    
    return "test2";
  }
  
}
