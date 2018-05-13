package ren.ascence.dns.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import ren.ascence.dns.utils.FileUtil;
import ren.ascence.dns.vo.AdVo;

@Service
public class AdHandler {

  private static final String AD_DIR = "/config/static/ad/";
  
  /**
   * 广告文件完整名称
   */
  public String getAdFileFullName() {
    return getAdFileDir() + "ad.txt";
  }
  
  /**
   * 广告文件路径
   */
  public String getAdFileDir() {
    String courseFile = FileUtil.getProjectRoot();
    String fileDir = courseFile + AD_DIR;
    return fileDir;
  }
  
  /**
   * 获取所有广告
   * @return
   */
  public List<AdVo> getAllAds(){
    List<String> list = FileUtil.readFileByLinesToList(getAdFileFullName());
    List<AdVo> ads = new ArrayList<>();
    for (String str : list) {
      if (StringUtils.isNotBlank(str)) {
        String[] s = str.split(" ");
        ads.add(new AdVo(s[0], s[1], s[2]));
      }
    }
    return ads;
  }
  
  public AdVo getAd(String serverName) {
    List<AdVo> allAds = getAllAds();
    for(AdVo advo:allAds) {
      if(Objects.equals(serverName, advo.getWebUrl())) {
        return advo;
      }
    }
    return null;
  } 
  
}
