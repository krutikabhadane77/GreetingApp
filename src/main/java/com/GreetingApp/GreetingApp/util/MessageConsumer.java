package com.GreetingApp.GreetingApp.util;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.GreetingApp.GreetingApp.config.RabbitMQConfig.QUEUE_NAME;

@Component
public class MessageConsumer {

    @Autowired
    private EmailSenderService emailSenderService;

    @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message from RabbitMQ: " + message);

        // Split msg to identify (REGISTER or LOGIN)
        String[] data = message.split("\\|");
        String messageType = data[0];
        String email = data[1];
        String firstName = data[2];

        if ("REGISTER".equals(messageType)) {
            String token = data[3]; // Retrieve token from message

            String body = "Hi " + firstName + ",\n\n"
                    + "You have been successfully registered in the Greeting App.\n\n"
                    + "Your token: " + token + "\n\n"
                    + "Best Regards,\nGreeting App Team";

            emailSenderService.sendEmail(email, "Registration Successful", body);

        } else if ("LOGIN".equals(messageType)) {
            String body = "Hi " + firstName + ",\n\n"
                    + "You have successfully logged into the Greeting App.\n\n"
                    + "Best Regards,\nGreeting App Team";

            emailSenderService.sendEmail(email, "Login Successful", body);
        }
    }
}
/*@Component
public class MessageConsumer {

    @Autowired
    private EmailSenderService emailSenderService;

     @RabbitListener(queues = QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        // trigger the EmailSenderService here
    }

}*/
