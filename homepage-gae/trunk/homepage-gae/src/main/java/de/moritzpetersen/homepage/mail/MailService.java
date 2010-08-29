package de.moritzpetersen.homepage.mail;

import javax.mail.internet.MimeMessage;

public interface MailService {
    void execute(MimeMessage message);
}
