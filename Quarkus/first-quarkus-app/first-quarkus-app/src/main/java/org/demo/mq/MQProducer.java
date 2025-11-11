package org.demo.mq;


import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

@Path("/mq")
public class MQProducer {

    @Inject
    MQConfig config;

    @POST
    @Path("/send")
    public String sendMessage(String messageText) throws Exception {
        ConnectionFactory factory = config.connectionFactory();
        try (JMSContext context = factory.createContext("app", "passw0rd")) {
            Queue queue = context.createQueue("queue:///TEST.QUEUE");
            context.createProducer().send(queue, messageText);
        }
        return "Message sent to MQ: " + messageText;
    }
}