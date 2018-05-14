package ren.ascence.dns.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ren.ascence.dns.handler.AdHandler;
import ren.ascence.dns.vo.AdVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class DnsController {
  private final static Logger logger = LoggerFactory.getLogger(DnsController.class);
  @Autowired
  private AdHandler adHandler;

  @RequestMapping("/")
  public String index(ModelMap map, HttpServletRequest request) {
    // String requestUrl =
    // request.getScheme()+"://"+request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
    logger.debug("scheme:{},serverName:{},requestURI:{},queryString:{}", request.getScheme(),
        request.getServerName(), request.getRequestURI(), request.getQueryString());

    AdVo adVo = adHandler.getAd(request.getServerName());
    if (Objects.isNull(adVo)) {
      adVo = new AdVo("http://www.qq.com", "ad/jdp.png", "http://www.baidu.com");
    }
    map.put("adVo", adVo);
    map.put("adtit", "");

    return "index";
  }

}
