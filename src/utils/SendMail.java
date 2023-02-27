package utils;

import models.DataMail;

public class SendMail extends Thread {
    private DataMail dataMail;

    public SendMail(DataMail dataMail) {
        this.dataMail = dataMail;
    }

    @Override
    public void run() {
        Mailer mailer = new Mailer();
        try {
            System.out.println(dataMail);
            mailer.sendNewPasswordFromHtml(dataMail.getEmail(), dataMail.getTen(), dataMail.getCode());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
