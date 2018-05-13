package ren.ascence.dns.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
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

import ren.ascence.dns.vo.CreativeVo;


@Controller
public class UploadImgController {

  @Value("${sys.uploadMaxSize}")
  private int uploadMaxSize;

  private String addr = "/src/main/resources/static/ad/";

  @RequestMapping("/add")
  public String toAdd(ModelMap map) {
    return "add";
  }
  
  @RequestMapping("/delete")
  public String delete(HttpServletRequest request) {
    String index = request.getParameter("index");
    String courseFile = getRoot();
    String filePath = courseFile + addr;
    List<String> list = readFileByLines(filePath+"ad.txt"); 
    list.remove(Integer.valueOf(index).intValue());
    String co = "";
    for(String str : list) {
      co += str;
    }

    if(StringUtils.isBlank(co)) {
      deleteFile();
    }else {
        this.updateContent(co, false);
    }
    return "redirect:list";
  }

  @RequestMapping("/list")
  public String list(ModelMap map) {
    String courseFile = getRoot();
    String filePath = courseFile + addr;
    List<String> list = readFileByLines(filePath+"ad.txt"); 
    List<Ad> ads = new ArrayList<>();
    for(String str :list) {
      if(StringUtils.isNotBlank(str)) {
      String[] s = str.split(" ");
      ads.add(new Ad(s[2],s[1],s[0]));
      }
    }
    map.put("list", ads);
    return "list";
  }

  
  public class Ad{
    String actualPath;
    String adUrl;
    String webUrl;
    
    public Ad(String actualPath, String adUrl, String webUrl) {
      super();
      this.actualPath = actualPath;
      this.adUrl = adUrl;
      this.webUrl = webUrl;
    }
    public String getActualPath() {
      return actualPath;
    }
    public void setActualPath(String actualPath) {
      this.actualPath = actualPath;
    }
    public String getAdUrl() {
      return adUrl;
    }
    public void setAdUrl(String adUrl) {
      this.adUrl = adUrl;
    }
    public String getWebUrl() {
      return webUrl;
    }
    public void setWebUrl(String webUrl) {
      this.webUrl = webUrl;
    }
    
    
  }
  
  @RequestMapping("/saveAd")
  public String saveAd(HttpServletRequest request) {
    // ad/name
    String actualPath = request.getParameter("actualPath");
    String adUrl = request.getParameter("adUrl");
    String webUrl = request.getParameter("webUrl");
    if (StringUtils.isNotBlank(actualPath) && StringUtils.isNotBlank(adUrl)
        && StringUtils.isNotBlank(webUrl)) {
      String content = webUrl + " " + adUrl + " " + actualPath;
      updateContent(content, true);
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
      String courseFile = getRoot();
      String filePath = courseFile + addr;
      uploadFile(file.getBytes(), filePath, fileName);
      ret.put("creative", this.createCreative(fileName));
    } catch (IOException e) {
      e.printStackTrace();
      ret.put("failReason", "服务异常！");
      ret.put("creative", null);
    }
    return ret;
  }

  public String getRoot() {
    File directory = new File("");
    String courseFile = "";
    try {
      courseFile = directory.getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return courseFile;
  }

  public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
    File targetFile = new File(filePath);
    if (!targetFile.exists()) {
      targetFile.mkdirs();
    }
    FileOutputStream out = new FileOutputStream(filePath + fileName);
    out.write(file);
    out.flush();
    out.close();
  }

  public CreativeVo createCreative(String fileName) throws MalformedURLException, Exception {
    CreativeVo vo = new CreativeVo();
    vo.setName(fileName);
    vo.setWidth(0);
    vo.setHeight(0);
    return vo;
  }

  public void deleteFile() {
    String courseFile = getRoot();
    String filePath = courseFile + addr + "ad.txt";
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }
  
  public boolean updateContent(String content, boolean append) {
    String courseFile = getRoot();
    String filePath = courseFile + addr + "ad.txt";
    boolean res = true;
    File file = new File(filePath);
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter writer = new FileWriter(file, append);
      writer.write(content+"\n");
      writer.flush();
      writer.close();
    } catch (IOException ex) {
      res = false;
      ex.printStackTrace();
    }
    return res;
  }
  
  public static List<String> readFileByLines(String fileName) {
    List<String> list = new ArrayList<>();
    File file = new File(fileName);
if(!file.exists()) {
  return list;
}
    BufferedReader reader = null;

    try {

      //System.out.println("以行为单位读取文件内容，一次读一整行：");

      reader = new BufferedReader(new FileReader(file));

      String tempString = null;

      int line = 1;

      // 一次读入一行，直到读入null为文件结束

      while ((tempString = reader.readLine()) != null) {

        // 显示行号

        //System.out.println("line " + line + ": " + tempString);
        list.add(tempString);
        line++;

      }

      reader.close();

    } catch (IOException e) {

      e.printStackTrace();

    } finally {

      if (reader != null) {

        try {

          reader.close();

        } catch (IOException e1) {

        }

      }

    }
    return list;
  }
}
