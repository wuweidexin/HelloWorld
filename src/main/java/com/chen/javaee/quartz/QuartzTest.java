package com.chen.javaee.quartz;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class QuartzTest {
	
	  /**
     * 计算从当前时间currentDate开始，满足条件dayOfWeek, hourOfDay, 
     * minuteOfHour, secondOfMinite的最近时间
     * @return
     */
	public static Calendar getEarliestDate(Calendar currentDate, int dayOfWeek,
            int hourOfDay, int minuteOfHour, int secondOfMinite) {
        //计算当前时间的WEEK_OF_YEAR,DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND等各个字段值
        int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
        int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
        int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentDate.get(Calendar.MINUTE);
        int currentSecond = currentDate.get(Calendar.SECOND);

        //如果输入条件中的dayOfWeek小于当前日期的dayOfWeek,则WEEK_OF_YEAR需要推迟一周
        boolean weekLater = false;
        if (dayOfWeek < currentDayOfWeek) {
            weekLater = true;
        } else if (dayOfWeek == currentDayOfWeek) {
            //当输入条件与当前日期的dayOfWeek相等时，如果输入条件中的
            //hourOfDay小于当前日期的
            //currentHour，则WEEK_OF_YEAR需要推迟一周    
            if (hourOfDay < currentHour) {
                weekLater = true;
            } else if (hourOfDay == currentHour) {
                 //当输入条件与当前日期的dayOfWeek, hourOfDay相等时，
                 //如果输入条件中的minuteOfHour小于当前日期的
                //currentMinute，则WEEK_OF_YEAR需要推迟一周
                if (minuteOfHour < currentMinute) {
                    weekLater = true;
                } else if (minuteOfHour == currentSecond) {
                     //当输入条件与当前日期的dayOfWeek, hourOfDay， 
                     //minuteOfHour相等时，如果输入条件中的
                    //secondOfMinite小于当前日期的currentSecond，
                    //则WEEK_OF_YEAR需要推迟一周
                    if (secondOfMinite < currentSecond) {
                        weekLater = true;
                    }
                }
            }
        }
        if (weekLater) {
            //设置当前日期中的WEEK_OF_YEAR为当前周推迟一周
            currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
        }
        // 设置当前日期中的DAY_OF_WEEK,HOUR_OF_DAY,MINUTE,SECOND为输入条件中的值。
        currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        currentDate.set(Calendar.MINUTE, minuteOfHour);
        currentDate.set(Calendar.SECOND, secondOfMinite);
        return currentDate;

    }
	/**
	 * 
	 * @param dayOfWeek 从星期天开始算起
	 * @param hourOfDay
	 * @param minute
	 * @param second
	 */
	public static void quartzSchedler(int dayOfWeek, int hourOfDay, int minute, int second, int intervalInHour, int intervalInMinutes, int intervalInSeconds){
		Scheduler scheduler = null;
		try {
			SchedulerFactory schedulerFactory = new StdSchedulerFactory();
			scheduler = schedulerFactory.getScheduler();
			scheduler.start();
			JobDetail jobDetail = newJob(MyJob.class)
					.withIdentity("myjob", "myjobgroup")
					.build();
			 //计算满足条件的最近一次执行时
            
			Date date = new Date();
			Calendar currentDate = Calendar.getInstance();
	        long currentDateLong = currentDate.getTime().getTime();
	        //每星期二 16:38:10 调度任务
			Calendar earliestDate =getEarliestDate(currentDate, dayOfWeek, hourOfDay, minute, second);
			
			long earliestDateLong = earliestDate.getTimeInMillis();
			Date d = new Date(earliestDateLong);
			
			SimpleTrigger trigger = null;
			if(intervalInSeconds > 0) {
				trigger = newTrigger()
					.withIdentity("trigger1", "group1")
					.startAt(d)
					.withSchedule(simpleSchedule().repeatSecondlyForever(intervalInSeconds))
					.build();
			}
			if(intervalInMinutes > 0) {
				trigger = newTrigger()
						.withIdentity("trigger1", "group1")
						.startAt(d)
						.withSchedule(simpleSchedule().repeatMinutelyForever(intervalInMinutes))
						.build();
			}
			if(intervalInHour > 0) {
				trigger = newTrigger()
						.withIdentity("trigger1", "group1")
						.startAt(d)
						.withSchedule(simpleSchedule().repeatHourlyForever(intervalInHour))
						.build();
			}
			
			scheduler.scheduleJob(jobDetail, trigger);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				scheduler.shutdown(true);
			} catch (SchedulerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}
	public static void main(String[] args) {
		//每周二14点06分0秒开始，间隔0时0分10秒掉一次
		QuartzTest.quartzSchedler(2,14,23,0,0,0,10);
	}
}
