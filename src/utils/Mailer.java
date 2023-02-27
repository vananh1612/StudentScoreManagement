package utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class Mailer {
    public static final String HOST = "smtp.gmail.com";
    public static final int PORT = 587;
    public static final String EMAIL = "buivannhan20032004@gmail.com";
    public static final String PASSWORD = "ylidraoxzasfzwgb";

    public Session session;

    public Mailer() {
        initSession();
    }

    public void initSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", HOST);
        props.put("mail.smtp.port", PORT);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(EMAIL, PASSWORD);
            }
        });
        session.setDebug(true);

    }

    public String getTemplateMail(String fileName) throws IOException {
        System.out.println(ClassLoader.getSystemResourceAsStream(fileName));
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream != null) {
            try (Scanner scanner = new Scanner(inputStream)) {
                String content = scanner.useDelimiter("\\A").next();
                return content;
            }
        } else {
            throw new FileNotFoundException("File not found: " + fileName);
        }
    }

    public void sendNewPasswordFromHtml(String to, String name, String code) throws Exception {
        String subject = "Khôi phục mật khẩu tài khoản " + to;
        String htmlBody = getTemplateMail("templates/sendCode.html");
        htmlBody = htmlBody.replace("{name}", name);
        htmlBody = htmlBody.replace("{code}", code);
        sendMail(to, subject, htmlBody);
    }

    public void sendMail(String to, String subject, String body) throws MessagingException {
       try {
           Message msg = new MimeMessage(session);
           msg.setFrom(new InternetAddress(EMAIL));
           msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
           msg.setSubject(subject);
           msg.setContent(body, "text/html; charset=utf-8");
           Transport.send(msg);
       }
       catch (MessagingException e) {
           throw e;
       }
    }

    public static void main(String[] args) throws IOException {
        Mailer mailer = new Mailer();
        try {
            System.out.println(mailer.getTemplateMail("templates/sendCode.html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

