package org.demo.mq;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

@Path("/mq")
public class MQConsumer {

    @Inject
    MQConfig config;

    @GET
    @Path("/receive")
    public String receiveMessage() {
        try {
            ConnectionFactory factory = config.connectionFactory();
            try (JMSContext context = factory.createContext("app", "passw0rd")) {
                Queue queue = context.createQueue("queue:///TEST.QUEUE");
                Message message = context.createConsumer(queue).receive(5000);
                if (message == null) {
                    return "No message received — queue might be empty.";
                }
                return "Received: " + message.getBody(String.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error receiving message: " + e.getMessage();
        }

    }
}
