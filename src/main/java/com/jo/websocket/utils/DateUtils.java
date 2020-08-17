package com.jo.websocket.utils;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils extends PropertyEditorSupport {

    public static ThreadLocal<SimpleDateFormat> date_sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };
    public static ThreadLocal<SimpleDateFormat> yyyyMMdd = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMdd");
        }
    };
    public static ThreadLocal<SimpleDateFormat> date_sdf_wz = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy年MM月dd日");
        }
    };
    public static ThreadLocal<SimpleDateFormat> time_sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm");
        }
    };
    public static ThreadLocal<SimpleDateFormat> yyyymmddhhmmss = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmss");
        }
    };
	public static ThreadLocal<SimpleDateFormat> yyyymmddhhmmssSSS = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyyMMddHHmmssSSS");
        }
    };
    public static ThreadLocal<SimpleDateFormat> short_time_sdf = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HH:mm");
        }
    };
	public static ThreadLocal<SimpleDateFormat> HHmm = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("HHmm");
        }
    };
    public static ThreadLocal<SimpleDateFormat> datetimeFormat = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };
    public static ThreadLocal<SimpleDateFormat> datetimeFormat_cron = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        }
    };

	// 以毫秒表示的时间
	private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
	private static final long HOUR_IN_MILLIS = 3600 * 1000;
	private static final long MINUTE_IN_MILLIS = 60 * 1000;
	private static final long SECOND_IN_MILLIS = 1000;

    // 指定模式的时间格式
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历，这里用中国时间表示
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        // --------------------cal.setTimeInMillis(millis);
        cal.setTime(new Date(millis));
        return cal;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getDate
    // 各种方式获取的Date
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 时间戳转换为字符串
     *
     * @param time
     * @return
     */
    public static String timestamptoStr(Timestamp time) {
        Date date = null;
        if (null != time) {
            date = new Date(time.getTime());
        }
        return date2Str(date_sdf.get());
    }

    /**
     * 字符串转换时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, date_sdf.get());
        return new Timestamp(date.getTime());
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (null == str || "".equals(str)) {
            return null;
        }
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 日期转换为字符串
     *
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(SimpleDateFormat date_sdf) {
        Date date = getDate();
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateformat(String date, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date _date = null;
        try {
            _date = sformat.parse(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sformat.format(_date);
    }

    /**
     * 日期转换为字符串
     *
     * @param date     日期
     * @param date_sdf 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, SimpleDateFormat date_sdf) {
        if (null == date) {
            return null;
        }
        return date_sdf.format(date);
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 指定毫秒数的时间戳
     *
     * @param millis 毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 以字符形式表示的时间戳
     *
     * @param time 毫秒数
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static String now() {
        return datetimeFormat.get().format(getCalendar().getTime());
    }

    /**
     * 指定日期的时间戳
     *
     * @param date 指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     *
     * @param cal 指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getCalendarTimestamp(Calendar cal) {
        // ---------------------return new Timestamp(cal.getTimeInMillis());
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp gettimestamp() {
        Date dt = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = df.format(dt);
        Timestamp buydate = Timestamp.valueOf(nowTime);
        return buydate;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMillis
    // 各种方式获取的Millis
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 指定日历的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        // --------------------return cal.getTimeInMillis();
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatDate
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日
     *
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        return date_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：yyyy-MM-dd HH:mm:ss
     *
     * @return 默认日期按“yyyy-MM-dd HH:mm:ss“格式显示
     */
    public static String formatDateTime() {
        return datetimeFormat.get().format(getCalendar().getTime());
    }

    /**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Calendar cal) {
        return date_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Date date) {
        return date_sdf.get().format(date);
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日“格式显示
     */
    public static String formatDate(long millis) {
        return date_sdf.get().format(new Date(millis));
    }

    /**
     * 默认日期按指定格式显示
     *
     * @param pattern 指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param cal     指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
     *
     * @return 默认日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime() {
        return time_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(long millis) {
        return time_sdf.get().format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Calendar cal) {
        return time_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Date date) {
        return time_sdf.get().format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatShortTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        return short_time_sdf.get().format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“时：分“格式显示
     */
    public static String formatShortTime(long millis) {
        return short_time_sdf.get().format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return short_time_sdf.get().format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Date date) {
        return short_time_sdf.get().format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // parseDate
    // parseCalendar
    // parseTimestamp
    // 将字符串按照一定的格式转化为日期或时间
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     */
    public static Date parseDate(String src, String pattern) throws ParseException {
        return getSDFormat(pattern).parse(src);

    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     * @throws ParseException
     */
    public static Calendar parseCalendar(String src, String pattern) throws ParseException {

        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatAddDate(String src, String pattern, int amount) throws ParseException {
        Calendar cal;
        cal = parseCalendar(src, pattern);
        cal.add(Calendar.DATE, amount);
        return formatDate(cal);
    }

	public static String formatAddMinute(String src, String pattern, int amount) throws ParseException {
		Calendar cal;
		cal = parseCalendar(src, pattern);
		cal.add(Calendar.MINUTE, amount);
		return formatDate(cal, DateUtils.HHmm.get().toPattern());
	}

	/**
	 * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
	 * 
	 * @param src     将要转换的原始字符窜
	 * @param pattern 转换的匹配格式
	 * @return 如果转换成功则返回转换后的时间戳
	 * @throws ParseException
	 */
	public static Timestamp parseTimestamp(String src, String pattern) throws ParseException {
		Date date = parseDate(src, pattern);
		return new Timestamp(date.getTime());
	}

	// ////////////////////////////////////////////////////////////////////////////
	// dateDiff
	// 计算两个日期之间的差值
	// ////////////////////////////////////////////////////////////////////////////

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * 
	 * @param flag   计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc 减数
	 * @param calDes 被减数
	 * @return 两个日期之间的差值
	 */
	public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {
		long millisDiff = getMillis(calSrc) - getMillis(calDes);
		if (flag == 'y') {
			return (calSrc.get(calSrc.YEAR) - calDes.get(calDes.YEAR));
		}
		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}
		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}
		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}
		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}
		return (int) millisDiff;
	}

	public static int dateDiff(char flag, Date calSrc, Date calDes) {
		long millisDiff = getMillis(calSrc) - getMillis(calDes);
		if (flag == 'y') {
			return (getYear(calSrc) - getYear(calDes));
		}
		if (flag == 'd') {
			return (int) (millisDiff / DAY_IN_MILLIS);
		}
		if (flag == 'h') {
			return (int) (millisDiff / HOUR_IN_MILLIS);
		}
		if (flag == 'm') {
			return (int) (millisDiff / MINUTE_IN_MILLIS);
		}
		if (flag == 's') {
			return (int) (millisDiff / SECOND_IN_MILLIS);
		}
		return (int) millisDiff;
	}

	/**
	 * 毫秒数转时间字符串
	 * @param millisDiff
	 * @return
	 */
	public static String millisDiffToDateStr(int millisDiff) {
		int d = (int) (millisDiff / DAY_IN_MILLIS);
		int hDiff = millisDiff - d * (int)DAY_IN_MILLIS;
		int h = (int) (hDiff / HOUR_IN_MILLIS);
		int mDiff = hDiff - h * (int)HOUR_IN_MILLIS;
		int m = (int) (mDiff / MINUTE_IN_MILLIS);
		int sDiff = mDiff - m * (int)MINUTE_IN_MILLIS;
		int s = (int) (sDiff / SECOND_IN_MILLIS);
		int ms = sDiff - s * (int)SECOND_IN_MILLIS;

		StringBuilder ret = new StringBuilder();
		if (d > 0) {
			ret.append(d + "天");
		}
		if (d > 0 || h > 0) {
			ret.append(h + "时");
		}
		if (d > 0 || h > 0 || m > 0) {
			ret.append(m + "分");
		}
		if (d > 0 || h > 0 || m > 0 || s > 0) {
			ret.append(s + "秒");
		}
		ret.append(ms + "毫秒");

		return ret.toString();
	}

	/**
	 * String类型 转换为Date, 如果参数长度为10 转换格式”yyyy-MM-dd“ 如果参数长度为19 转换格式”yyyy-MM-dd
	 * HH:mm:ss“ * @param text String类型的时间值
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(DateUtils.date_sdf.get().parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(DateUtils.datetimeFormat.get().parse(text));
                } else {
                    throw new IllegalArgumentException("Could not parse date, date format is error ");
                }
			} catch (ParseException ex) {
				IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + ex.getMessage());
				iae.initCause(ex);
				throw iae;
			}
		} else {
			setValue(null);
		}
	}

	public static int getYear() {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(getDate());
		return calendar.get(Calendar.YEAR);
	}

	public static int getYear(Date date) {
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	/**
	 * 判断该日期是否是该月的最后一天
	 *
	 * @param date 需要判断的日期
	 * @return
	 */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static boolean isLastDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		return calendar.get(Calendar.DAY_OF_MONTH) == calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isBelong(String begin, String end){

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;
        try {
            now = df.parse(df.format(new Date()));
            beginTime = df.parse(begin);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = belongCalendar(now, beginTime, endTime);
        return flag;
    }

    public static boolean isTimeBelong(String now, String begin, String end){
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date beginTime = null;
        Date endTime = null;
        Date nowTime = null;
        try {
            nowTime = df.parse(now);
            beginTime = df.parse(begin);
            endTime = df.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean flag = belongCalendar(nowTime, beginTime, endTime);
        return flag;

    }

    // 获取月份第一天（queryDate为null 的时候取当前月份）
    public static Date getMonthFirst(String queryDate) {
        // 按月
        Integer getMonth = 0;
        Integer getYear = 0;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryDate)) {
            // 用户输入月份
            try {
                Date param = DateUtils.parseDate(queryDate, "yyyy-MM");
                LocalDate localDate = param.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                getYear = localDate.getYear();
                getMonth = localDate.getMonthValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // 当前月份
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(new Date());
            getMonth = gc.get(2) + 1;
            getYear = gc.get(1);
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getYear);
        c.set(Calendar.MONTH, getMonth - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    // 获取月份最后一天（queryDate为null 的时候取当前月份）
    public static Date getMonthLast(String queryDate) {
        // 按月
        Integer getMonth = 0;
        Integer getYear = 0;
        if (org.apache.commons.lang3.StringUtils.isNotBlank(queryDate)) {
            // 用户输入月份
            try {
                Date param = DateUtils.parseDate(queryDate, "yyyy-MM");
                LocalDate localDate = param.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                getYear = localDate.getYear();
                getMonth = localDate.getMonthValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            // 当前月份
            GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
            gc.setTime(new Date());
            getMonth = gc.get(2) + 1;
            getYear = gc.get(1);
        }

        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, getYear);
        c.set(Calendar.MONTH, getMonth - 1);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        return c.getTime();
    }


    /**
     * 获取两时间字符串相减的结果
     * @param start 大
     * @param end 小
     * @return
     */
    public static Integer getDiffMin(String start, String end) {

        Integer t1H = Integer.valueOf(start.split(":")[0]);
        Integer t1M = Integer.valueOf(start.split(":")[1]);
        Integer t2H = Integer.valueOf(end.split(":")[0]);
        Integer t2M = Integer.valueOf(end.split(":")[1]);

        if (t2M > t1M) {
            t1H--;
            t1M += 60;
        }
        return (t1H-t2H) * 60 + (t1M-t2M);
    }

    public static Integer getWeekOfDateNum(Date dt) {
        Integer[] weekDays = {7, 1, 2, 3, 4, 5, 6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static String getWeekOfDateStr(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static Calendar date2Calendar (Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date getZeroTime (Date date) {
        try {
            return DateUtils.parseDate(DateUtils.formatDate(date, "yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String date2Cron(Date date) {
        return date2Str(date, datetimeFormat_cron.get());
    }

    public static void main(String[] args) {
        Date date = str2Date("20200604120000",yyyymmddhhmmss.get());

        System.out.println(date2Cron(date));
        System.out.println(getWeekOfDateStr(new Date()));
        System.out.println(getMonthFirst(null));
        System.out.println(getMonthFirst("2020-04"));
    }

}