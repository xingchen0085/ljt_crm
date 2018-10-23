package com.douples.pay.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * MQ配置文件
* <p>Title: MqConfig</p>  
* <p>Description: MQ配置文件</p>  
* @author hexuefeng  
* @date 2017-11-30
 */
public class MQConfig {
	
	/**
	 * 日志
	 */
	private static final Log LOG = LogFactory.getLog(MQConfig.class);
	
	/** 商户通知队列 **/
	public static String MERCHANT_NOTIFY_QUEUE = "";

	/** 订单通知队列 **/
	public static String ORDER_NOTIFY_QUEUE = "";
	
	/**
	 * 属性
	 */
	private static Properties properties = null;
	
	static{
		if(null == properties){
			properties  = new Properties();
		}
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("mq_config.properties");
		try {
			properties.load(is);
			init(properties);
		} catch (IOException e) {
			LOG.error("=== load and init mq exception:" + e);
		}
	}
	
	private static void init(Properties properties){
		MERCHANT_NOTIFY_QUEUE = properties.getProperty("tradeQueueName.notify");
		ORDER_NOTIFY_QUEUE = properties.getProperty("orderQueryQueueName.query");
	}
}
