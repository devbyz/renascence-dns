package ren.ascence.dns.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DateUtil {
  
  public enum TimeFormat {

    /**
     * 短时间格式
     */
    SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
    SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
    SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
    SHORT_DATE_PATTERN_NONE("yyyyMMdd"),

    /**
     * 长时间格式
     */
    LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
    LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
    LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
    LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

    /**
     * 长时间格式 带毫秒
     */
    LONG_DATE_PATTERN_WITH_MILSEC_NUM("yyyyMMddHHmmssSSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
    LONG_DATE_PATTERN_WITH_MILSEC_NONE("yyyyMMdd HH:mm:ss.SSS");

    private String pattern;
    
    public String getValue() {
      return pattern;
    }
    
    TimeFormat(String pattern) {
      this.pattern = pattern;
    }
  }
  private static final ConcurrentMap<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

  private static final int PATTERN_CACHE_SIZE = 500;
  /**
   * Date转换为格式化时间
   * @param date date
   * @param pattern 格式
   * @return
   */
  public static String format(Date date, String pattern){
      return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
  }

  /**
   * localDateTime转换为格式化时间
   * @param localDateTime localDateTime
   * @param pattern 格式
   * @return
   */
  public static String format(LocalDateTime localDateTime, String pattern){
      DateTimeFormatter formatter = createCacheFormatter(pattern);
      return localDateTime.format(formatter);
  }

  /**
   * 格式化字符串转为Date
   * @param time 格式化时间
   * @param pattern 格式
   * @return
   */
  public static Date parseDate(String time, String pattern){
      return Date.from(parseLocalDateTime(time, pattern).atZone(ZoneId.systemDefault()).toInstant());

  }

  /**
   * 格式化字符串转为LocalDateTime
   * @param time 格式化时间
   * @param pattern 格式
   * @return
   */
  public static LocalDateTime parseLocalDateTime(String time, String pattern){
      DateTimeFormatter formatter = createCacheFormatter(pattern);
      return LocalDateTime.parse(time, formatter);
  }

  /**
   * 在缓存中创建DateTimeFormatter
   * @param pattern 格式
   * @return
   */
  private static DateTimeFormatter createCacheFormatter(String pattern){
      if (pattern == null || pattern.length() == 0) {
          throw new IllegalArgumentException("Invalid pattern specification");
      }
      DateTimeFormatter formatter = FORMATTER_CACHE.get(pattern);
      if(formatter == null){
          if(FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE){
              formatter = DateTimeFormatter.ofPattern(pattern);
              DateTimeFormatter oldFormatter = FORMATTER_CACHE.putIfAbsent(pattern, formatter);
              if(oldFormatter != null){
                  formatter = oldFormatter;
              }
          }
      }

      return formatter;
  }
}
