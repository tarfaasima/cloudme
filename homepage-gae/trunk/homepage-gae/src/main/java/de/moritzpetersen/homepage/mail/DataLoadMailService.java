package de.moritzpetersen.homepage.mail;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dataimport.DataLoader;

public class DataLoadMailService implements MailService {
    private static final Logger LOG = Logger.getLogger(DataLoadMailService.class.getName());
    public static final int SERVICE_ID = 1;
    @Inject
    private DataLoader dataLoader;

    @Override
    public void execute(MimeMessage message) {
        try {
            Object content = message.getContent();
            if (content instanceof Multipart) {
                readContent((Multipart) content);
            }
            else {
                LOG.warning("Cannot read content.");
            }
        }
        catch (MessagingException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void readContent(Multipart content) throws MessagingException, IOException {
        for (int i = 0, count = content.getCount(); i < count; i++) {
            BodyPart bodyPart = content.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                dataLoader.load(bodyPart.getInputStream());
            }
        }
    }
}
