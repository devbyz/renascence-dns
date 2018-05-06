package ren.ascence.dns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DnsController {

  @RequestMapping("/")
  public String oots(ModelMap map) {
      
      map.put("title", "腾讯首页");
      map.put("url", "http://www.qq.com");
      map.put("lurl", "http://www.baidu.com");
      
      return "index";
  }
  
}
