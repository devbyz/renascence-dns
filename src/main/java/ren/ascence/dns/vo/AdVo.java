package ren.ascence.dns.vo;

public class AdVo {
  //劫持的网站
  private String webUrl;
  //广告地址
  private String adUrl;
  //广告链接
  private String adLanding;
  
  public AdVo() {}
  
  public AdVo(String webUrl, String adUrl, String adLanding) {
    super();
    this.webUrl = webUrl;
    this.adUrl = adUrl;
    this.adLanding = adLanding;
  }
  public String getWebUrl() {
    return webUrl;
  }
  public void setWebUrl(String webUrl) {
    this.webUrl = webUrl;
  }
  public String getAdUrl() {
    return adUrl;
  }
  public void setAdUrl(String adUrl) {
    this.adUrl = adUrl;
  }

  public String getAdLanding() {
    return adLanding;
  }

  public void setAdLanding(String adLanding) {
    this.adLanding = adLanding;
  }

}
