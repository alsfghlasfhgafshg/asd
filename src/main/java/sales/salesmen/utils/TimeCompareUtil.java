package sales.salesmen.utils;

import java.util.Date;

public class TimeCompareUtil {

    private static long nd = 86400000;
    private static long nh = 3600000;
    private static long nm = 60000;

    public String timeDifference(Date endDate,Date nowDate){
        long diff = endDate.getTime() - nowDate.getTime();
        long day = diff/nd;
        long year = day/365;
        if (year!=0L) return year+"年前";
        if (day!=0L) return day+"天前";
        long hour = diff%nd/nh;
        if (hour!=0L) return hour+"小时前";
        long min = diff%nd%nh/nm;
        return min+"分钟前";
    }
}
