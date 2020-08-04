package com.jo.websocket.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

//import com.spring.entity.comm.DateStartEnd;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMddHHmmss_pretty = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyyMMddHHmmss_chinese = "M月d日HH时mm分";
    private static String[] parsePatterns = { "yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "yyyyMMddHHmmss", "yyyyMMdd" };

    public static String getCurTime(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    public static String getCurDate() {
        return getCurTime("yyyyMMdd");
    }
    
    public static String getCurTime() {
        return getCurTime(yyyyMMddHHmmss_pretty);
    }

    public static Date parse(String dateStr) {
        try {
            return parseDateStrictly(dateStr, parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期时分秒置为0
     */
    public static Date truncate(Date date) {
        return truncate(date, Calendar.DATE);
    }

    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

	public static String formate(String dateStr, String newPattern) {
		if (StringUtils.isNoneBlank(dateStr)) {
			Date parse = parse(dateStr);
			if (parse != null) {
				return DateFormatUtils.format(parse, newPattern);
			}
		}
		return "";
	}
    
    /**
     * 解析起止日期
     * 
     * @param startDateStr
     *            起始日期yyMMdd
     * @param endDateStr
     *            结束日期yyMMdd
     * @return
     */
//    public static DateStartEnd getDateRange(String startDateStr, String endDateStr) {
//
//        Date startDate = null;
//        Date endDate = null;
//        try {
//            if (StringUtils.isNotBlank(startDateStr)) {
//                startDate = DateUtils.parseDateStrictly(startDateStr, "yyMMdd");
//            }
//            if (StringUtils.isNotBlank(endDateStr)) {
//                endDate = addDays(DateUtils.parseDateStrictly(endDateStr, "yyMMdd"), 1);// 加一天
//            }
//        } catch (ParseException e) {
//            throw new BusinessException("date.format.error");
//        }
//
//        // 开始时间应在结束时间之前，不可等
//        if (startDate != null && endDate != null && endDate.compareTo(startDate) <= 0) {
//            throw new BusinessException("date.start.le.end");
//        }
//
//        return new DateStartEnd(startDate, endDate);
//    }
	
    public static int diffDays(Date dateStart, Date dateEnd) {
        return (int) ((dateEnd.getTime() - dateStart.getTime()) / (1000 * 3600 * 24));
    }
    
    public static String tranDateTimePretty(String datetime14) {
        if (datetime14 != null && datetime14.trim().length() == 14) {
            StringBuilder sf = new StringBuilder();
            sf.append(datetime14.substring(0, 4)).append("-");
            sf.append(datetime14.substring(4, 6)).append("-");
            sf.append(datetime14.substring(6, 8)).append(" ");
            sf.append(datetime14.substring(8, 10)).append(":");
            sf.append(datetime14.substring(10, 12)).append(":");
            sf.append(datetime14.substring(12));
            return sf.toString();
        }
        return datetime14;
    }
    
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d)
            calendar.setTime(d);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());
    }
    
    /**
     * 获取上周某一天
     */
    public static Date getPreWeekCertainDay(int i) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);// 将每周第一天设为星期一，默认是星期天
        cal.add(Calendar.DATE, -1 * 7);
        cal.set(Calendar.DAY_OF_WEEK, i);
        return cal.getTime();
    }

    /**
     * 获取上一周周一
     */
    public static Date getPreWeekMonday() {
        return getPreWeekCertainDay(Calendar.MONDAY);
    }

    /**
     * 获取上一周周五
     */
    public static Date getPreWeekFriday() {
        return getPreWeekCertainDay(Calendar.FRIDAY);
    }
    
    /**
     * 获取月份第一天
     */
    public static Date getMonthFirst(int diffMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, diffMonth);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获月份最后一天
     */
    public static Date getMonthLast(int diffMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, diffMonth + 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1); 
        calendar.add(Calendar.DATE, -1);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取精确到秒的时间戳(10位)
     * @param date
     * @return
     */
    public static String getSecondTimestamp(Date date) {
        if (null == date) {
            return "0";
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return String.valueOf(timestamp);
    }

    
    public static void main(String[] args) {
        System.out.println(DateUtils.format(getPreWeekMonday(), yyyyMMddHHmmss_pretty));
        System.out.println(DateUtils.format(getPreWeekFriday(), yyyyMMddHHmmss_pretty));

        System.out.println(DateUtils.format(getMonthFirst(0), yyyyMMddHHmmss_pretty));
        System.out.println(DateUtils.format(getMonthFirst(-1), yyyyMMddHHmmss_pretty));
        System.out.println(DateUtils.format(getMonthFirst(-2), yyyyMMddHHmmss_pretty));

        System.out.println(DateUtils.format(getMonthLast(0), yyyyMMddHHmmss_pretty));
        System.out.println(DateUtils.format(getMonthLast(-1), yyyyMMddHHmmss_pretty));
        System.out.println(DateUtils.format(getMonthLast(-2), yyyyMMddHHmmss_pretty));
    }
}
