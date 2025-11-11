package org.demo.mq;


import javax.jms.ConnectionFactory;

import com.ibm.mq.jms.MQConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MQConfig {

    public ConnectionFactory connectionFactory() throws Exception {
        MQConnectionFactory factory = new MQConnectionFactory();
        factory.setHostName("localhost");
        factory.setPort(1414);
        factory.setQueueManager("QM1");
        factory.setChannel("DEV.APP.SVRCONN");
        factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
        return factory;
    }
}
