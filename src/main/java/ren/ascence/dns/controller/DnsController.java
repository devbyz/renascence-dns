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
  
  @RequestMapping("/")
  public String test(ModelMap map, HttpServletRequest request) {
    String url = request.getScheme()+"://"+request.getServerName();
    String requestUrl = request.getRequestURI();
    if(!StringUtils.isNotBlank(requestUrl)) {
      if(requestUrl.length()>1) {
        url += request.getRequestURI();
      }
    }
    if(!StringUtils.isNotBlank(request.getQueryString())) {
      url += request.getQueryString();
    }
    logger.debug("url:{}",url);
    if("47.96.175.185".equals(request.getServerName())) {
      url = "http://www.adpadx.com";
    }
    try {
      String c = GetUtil.execute(url);
      map.put("c", c);
    } catch (IOException e) {
      map.put("c", "");
      e.printStackTrace();
    }
    return "c";
  }

}
