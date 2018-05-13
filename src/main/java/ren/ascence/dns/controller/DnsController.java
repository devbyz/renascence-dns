package ren.ascence.dns.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DnsController {

  @RequestMapping("/")
  public String oots(ModelMap map,HttpServletRequest request) {
      
      System.out.println("request.getRequestURI():"+request.getRequestURI());
      
      map.put("title", "首页");
      map.put("url", "http://www.qq.com");
      map.put("lurl", "http://www.baidu.com");
      
      return "index";
  }
  
}
