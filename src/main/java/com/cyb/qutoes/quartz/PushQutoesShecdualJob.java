package com.cyb.qutoes.quartz;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cyb.qutoes.contants.QutoesContants;
import com.cyb.utils.DateUtil;

@Component
public class PushQutoesShecdualJob {
	Log log = LogFactory.getLog(PushQutoesShecdualJob.class);
	@Resource
	JdbcTemplate jdbcTemplate;
	
	@Scheduled(cron="00 30 09 ? * MON-FRI")
	public void pushOpenNoon(){
		if(!QutoesContants.holidays.containsKey(DateUtil.date2long8(new Date()).toString())){
			log.info("执行！");
			/*Timer timer = new Timer();
			timer.schedule(new TimerTask(){
				@Override
				public void run() {
					while(QutoesContants.SWTICH){
						pushMinQutoes();
					}
				}
			},0, 1000);//单位毫秒
			 */	
			MessageThread pushThread = new MessageThread();
			QutoesContants.pushPool.execute(pushThread);
			PushNewPriceMessageThread pushNewPriceThread = new PushNewPriceMessageThread();
			QutoesContants.pushNewPricePool.execute(pushNewPriceThread);
			}
		}
	
	@Scheduled(cron="00 00 13 ? * MON-FRI")
	public void pushAfterNoon(){
		if(!QutoesContants.holidays.containsKey(DateUtil.date2long8(new Date()).toString())){
			MessageThread pushThread = new MessageThread();
			QutoesContants.pushPool.execute(pushThread);
			PushNewPriceMessageThread pushNewPriceThread = new PushNewPriceMessageThread();
			QutoesContants.pushNewPricePool.execute(pushNewPriceThread);
		}
	}
	
	@Scheduled(cron="0 */10 09-13,13-15 ? * MON-FRI")
	public void checkPushServerState(){//线程异常结束后，定期检查状态并恢复
		if(!QutoesContants.holidays.containsKey(DateUtil.date2long8(new Date()).toString())){
		   if(QutoesContants.SWTICH){
			   if(!PushManager.pushServerState){
					log.info("pushserver restart !");
					MessageThread pushThread = new MessageThread();
					QutoesContants.pushPool.execute(pushThread);
				}
			   if(!PushManager.pushNewPriceServerState){
				   PushNewPriceMessageThread pushNewPriceThread = new PushNewPriceMessageThread();
				   QutoesContants.pushNewPricePool.execute(pushNewPriceThread);
			   }
			}
		}
	}
}
