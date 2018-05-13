package ren.ascence.dns.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ren.ascence.dns.utils.FileUtil;
import ren.ascence.dns.vo.AdVo;


@Controller
public class UploadImgController {

  @Value("${sys.uploadMaxSize}")
  private int uploadMaxSize;

  private String addr = "/src/main/resources/static/ad/";

  @RequestMapping("/add")
  public String toAdd(ModelMap map,HttpServletRequest request) {
    String staticDomain = request.getScheme()+"://"+ request.getServerName();
    System.out.println("staticDomain:"+staticDomain);
    map.put("staticDomain", staticDomain);
    return "add";
  }

  @RequestMapping("/delete")
  public String delete(HttpServletRequest request) {
    String index = request.getParameter("index");
    String courseFile = FileUtil.getProjectRoot();
    String filePath = courseFile + addr;
    List<String> list = FileUtil.readFileByLinesToList(filePath + "ad.txt");
    list.remove(Integer.valueOf(index).intValue());
    if(list.isEmpty()) {
      FileUtil.deleteFile(filePath + "ad.txt");
    }
    for(int i=0;i<list.size();i++) {
      if(i==0) {
        FileUtil.updateFile(filePath + "ad.txt", list.get(0), false);
      }else {
        FileUtil.updateFile(filePath + "ad.txt", list.get(i), true);
      }
    }
    return "redirect:list";
  }

  @RequestMapping("/list")
  public String list(ModelMap map,HttpServletRequest request) {
    //String url = request.getScheme()+"://"+ request.getServerName()+request.getRequestURI()+"?"+request.getQueryString();
    String courseFile = FileUtil.getProjectRoot();
    String filePath = courseFile + addr;
    List<String> list = FileUtil.readFileByLinesToList(filePath + "ad.txt");
    List<AdVo> ads = new ArrayList<>();
    for (String str : list) {
      if (StringUtils.isNotBlank(str)) {
        String[] s = str.split(" ");
        ads.add(new AdVo(s[0], s[1], s[2]));
      }
    }
    map.put("list", ads);
    return "list";
  }

  @RequestMapping("/saveAd")
  public String saveAd(HttpServletRequest request) {
    String webUrl = request.getParameter("webUrl");
    String adUrl = request.getParameter("adUrl");
    String adLanding = request.getParameter("adLanding");
    if (StringUtils.isNotBlank(webUrl) && StringUtils.isNotBlank(adUrl)
        && StringUtils.isNotBlank(adLanding)) {
      String content = webUrl + " " + adUrl + " " + adLanding;
      String courseFile = FileUtil.getProjectRoot();
      String filePath = courseFile + addr;
      FileUtil.updateFile(filePath+ "ad.txt", content, true);
    }
    return "redirect:list";
  }

  @RequestMapping(value = "/upload", method = {RequestMethod.GET, RequestMethod.POST})
  public @ResponseBody Object upload(@RequestParam MultipartFile file, HttpServletRequest request)
      throws Exception {
    Map<String, Object> ret = new HashMap<String, Object>();
    if (file.getSize() >= uploadMaxSize) {
      ret.put("failReason", "超出" + (uploadMaxSize / 1024) + "K");
      ret.put("creative", null);
      return ret;
    }
    try {
      String fileName = file.getOriginalFilename();
      String courseFile = FileUtil.getProjectRoot();
      String filePath = courseFile + addr;
      FileUtil.saveToLocal(file.getBytes(), filePath, fileName);
      ret.put("creative", fileName);
    } catch (IOException e) {
      e.printStackTrace();
      ret.put("failReason", "服务异常！");
      ret.put("creative", null);
    }
    return ret;
  }


}
