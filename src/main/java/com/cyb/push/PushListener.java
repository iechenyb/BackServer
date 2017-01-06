package com.cyb.push;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;

public class PushListener implements DataListener<Object>{
  public static Logger log = LoggerFactory.getLogger(PushListener.class);
  SocketIOServer server;
  public PushListener(SocketIOServer server){
  	this.server = server;
  }
  public void onData(SocketIOClient client, Object action, AckRequest req)  {
	  try{
		log.debug("有客户主动请求数据，"+action+",client:"+client);
		JSONObject para = JSONObject.fromObject(action);
		String type = para.get("type").toString();
		String code = para.get("code").toString();
       if(PushConstant.MINQUTOES.equals(type)){
			PushConstant.qutoesCodes.put(code, client);
		}else  if(PushConstant.MYCONCERN.equals(type)){
			PushConstant.conerns.put(code, client);
		}		
	  }catch(Exception e){
		  e.printStackTrace();
	  }
  }
}