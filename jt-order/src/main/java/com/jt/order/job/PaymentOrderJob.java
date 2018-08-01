package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class PaymentOrderJob extends QuartzJobBean{
	
	/** 定时任务的具体执行  */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//1.获取spring容器对象
		ApplicationContext applicationContext = (ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
		
		//2.获取OrderMapper接口对象
		OrderMapper orderMapper = applicationContext.getBean(OrderMapper.class);
		
		//3.如果订单已经超时,则应该将状态从1改为6(假如规定2天超时)
		Date timeOut = new DateTime().minusDays(2).toDate();//2天之前的时间
		//Date timeOut = new DateTime().minusMinutes(15).toDate();//15分钟前的时间
		orderMapper.updateStatus(timeOut);
		System.out.println("定时任务执行完成！");
		
		
		
		
	}

	
}
