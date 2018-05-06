package ren.ascence.dns.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DnsController {

    @RequestMapping("index")
    public String hello(ModelMap map) {
        map.put("host", "使用freemarker!");
        return "index";
    }

    @RequestMapping("test")
    public String test(ModelMap map) {
        map.put("host", "使用freemarker!");
        return "test";
    }
}
