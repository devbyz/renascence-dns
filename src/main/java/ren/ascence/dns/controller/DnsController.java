package ren.ascence.dns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DnsController {

  @RequestMapping("/")
  public String oots(ModelMap map,HttpServletRequest request) {
  //String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
    String staticDomain = request.getScheme()+"://"+ request.getServerName();
    System.out.println("staticDomain:"+staticDomain);
      
      map.put("title", "首页");
      map.put("url", "http://www.qq.com");
      map.put("lurl", "http://www.baidu.com");
      
      return "index";
  }
  
}
