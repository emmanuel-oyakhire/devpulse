package com.devpulse.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${sendgrid.api-key}")
    private String apiKey;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendWelcomeEmail(String toEmail, String name) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "Welcome to DevPulse";
        String contentType = "text/plain";
        String body = "Hi " + name + ",\n\n"
                + "Welcome to DevPulse! Your account has been created successfully. \n\n"
                + "You can now start saving links, managing invoices, and more. \n\n"
                + "Best regards, \n"
                + "The DevPulse Team";
        Content content = new Content(contentType, body);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response =  sg.api(request);
            System.out.println("Email sent. Status: " + response.getStatusCode());
        } catch (Exception e) {
            System.out.println("Email failed: " + e.getMessage());
        }
    }

}
