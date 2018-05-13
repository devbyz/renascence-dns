package ren.ascence.dns.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
  
  public static final String LINE_BREAK = "\n";
  
  //获得项目根路径
  public static String getProjectRoot() {
    File directory = new File("");
    String courseFile = "";
    try {
      courseFile = directory.getCanonicalPath();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return courseFile;
  }
  
  //以行为单位读取文件内容，一次读一整行
  public static List<String> readFileByLinesToList(String fileName) {
    List<String> list = new ArrayList<>();
    File file = new File(fileName);
    if (!file.exists()) {
      return list;
    }
    BufferedReader reader = null;
    try {
      reader = new BufferedReader(new FileReader(file));
      String tempString = null;
      while ((tempString = reader.readLine()) != null) {
        list.add(tempString);
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
  
  //删除文件
  public static void deleteFile(String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      file.delete();
    }
  }
  
  //更新文件
  public static void updateFile(String filePath,String content, boolean append) {
    //String courseFile = FileUtil.getProjectRoot();
   // String filePath = courseFile + addr + "ad.txt";
    File file = new File(filePath);
    try {
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter writer = new FileWriter(file, append);
      writer.write(content + LINE_BREAK);
      writer.flush();
      writer.close();
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
  
  //保存文件到本地
  public static void saveToLocal(byte[] file, String fileDir, String fileName) throws Exception {
    File targetFile = new File(fileDir);
    if (!targetFile.exists()) {
      targetFile.mkdirs();
    }
    FileOutputStream out = new FileOutputStream(fileDir + fileName);
    out.write(file);
    out.flush();
    out.close();
  }
}
