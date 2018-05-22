package ren.ascence.dns.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TimeUtil {
  
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
  
  /**
   * 默认时间格式: yyyy-MM-dd HH:mm:ss
   */
  private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TimeFormat.LONG_DATE_PATTERN_LINE.getValue());
  
  private static final ConcurrentMap<TimeFormat, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>();

  private static final int PATTERN_CACHE_SIZE = 500;
  
  
  /**
   * 以默认时间格式，获取当前时间
   *
   * @return
   */
  public static String getCurrentDatetime() {
      return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
  }
  
  /**
   * 以指定时间格式，获取当前时间
   *
   * @param format 时间格式
   * @return
   */
  public static String getCurrentDatetime(TimeFormat format) {
    DateTimeFormatter formatter = createCacheFormatter(format);
    return formatter.format(LocalDateTime.now());
  }
  
  /**
   * Date转换为格式化时间字符串
   * @param date
   * @param pattern 格式
   * @return
   */
  public static String format(Date date, TimeFormat format){
      return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), format);
  }

  /**
   * LocalDateTime转换为格式化时间
   * @param localDateTime
   * @param pattern 格式
   * @return
   */
  public static String format(LocalDateTime localDateTime, TimeFormat format){
      DateTimeFormatter formatter = createCacheFormatter(format);
      return localDateTime.format(formatter);
  }

  /**
   * 格式化字符串转为Date
   * @param timeStr 格式化时间
   * @param pattern 格式
   * @return
   */
  public static Date parseDate(String timeStr, TimeFormat format){
      return Date.from(parseLocalDateTime(timeStr, format).atZone(ZoneId.systemDefault()).toInstant());

  }
  
  /**
   * LocalDateTime 转 Date
   * 
   * @param localDateTime
   * @return
   */
  public static Date parseDate(LocalDateTime localDateTime) {
    ZoneId zoneId = ZoneId.systemDefault();
    ZonedDateTime zdt = localDateTime.atZone(zoneId);
    Date date = Date.from(zdt.toInstant());
    return date;
  }

  /**
   * 格式化字符串转为LocalDateTime
   * 
   * @param time 格式化时间
   * @param pattern 格式
   * @return
   */
  public static LocalDateTime parseLocalDateTime(String time, TimeFormat format){
      DateTimeFormatter formatter = createCacheFormatter(format);
      return LocalDateTime.parse(time, formatter);
  }
  
  /**
   * 格式化字符串转为LocalDateTime
   * 
   * @param time 格式化时间
   * @param pattern 格式
   * @return
   */
  public static LocalDateTime parseLocalDateTime(String time){
      return parseLocalDateTime(time, TimeFormat.LONG_DATE_PATTERN_LINE);
  }
  
  /**
   * Date 转 LocalDateTime 
   * 
   * @param date
   * @return
   */
  public static LocalDateTime parseLocalDateTime(Date date) {
    Instant instant = date.toInstant();
    ZoneId zoneId = ZoneId.systemDefault();
    LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
    return localDateTime;
  }

  /**
   * 在缓存中创建DateTimeFormatter
   * 
   * @param pattern 格式
   * @return
   */
  private static DateTimeFormatter createCacheFormatter(TimeFormat format){
      if(FORMATTER_CACHE.containsKey(format)) {
        return FORMATTER_CACHE.get(format);
      }
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.getValue());
      if(FORMATTER_CACHE.size() < PATTERN_CACHE_SIZE){
        FORMATTER_CACHE.putIfAbsent(format, formatter);
      }
      return formatter;
  }
}
