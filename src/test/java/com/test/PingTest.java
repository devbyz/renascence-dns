package com.test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import ren.ascence.dns.utils.GetUtil;
import sun.net.www.http.HttpClient;

public class PingTest {

  
  public static void main(String[] args) throws IOException {
      //}System.out.println(getIP("www.qq.com"));
    String x = GetUtil.execute("http://www.qq.com");
    System.out.println(x);
    }

  public static String getIP(String name) {
    InetAddress address = null;
    try {
      address = InetAddress.getByName(name);
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.out.println("获取失败");
    }
    return address.getHostAddress().toString();
  }
}
