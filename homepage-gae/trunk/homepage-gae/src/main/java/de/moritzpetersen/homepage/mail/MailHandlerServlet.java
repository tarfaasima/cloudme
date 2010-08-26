package de.moritzpetersen.homepage.mail;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import de.moritzpetersen.homepage.dataimport.DataLoader;
import de.moritzpetersen.homepage.guice.HomepageModule;

@SuppressWarnings( "serial" )
public class MailHandlerServlet extends HttpServlet {
    private static final Logger LOGGER = Logger
            .getLogger(MailHandlerServlet.class.getName());
    @Inject
    private DataLoader dataLoader;

    // public void setDataLoader(DataLoader dataLoader) {
    // this.dataLoader = dataLoader;
    // }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Injector injector = Guice.createInjector(new HomepageModule());
        injector.injectMembers(this);

        Session session = Session.getDefaultInstance(new Properties(), null);
        try {
            MimeMessage message = new MimeMessage(session, req.getInputStream());
            Object content = message.getContent();
            if (content instanceof Multipart) {
                readContent((Multipart) content);
            }
            else {
                LOGGER.warning("Cannot read content.");
            }
        }
        catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    protected void readContent(Multipart content) throws MessagingException,
            IOException {
        for (int i = 0, count = content.getCount(); i < count; i++) {
            BodyPart bodyPart = content.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                dataLoader.load(bodyPart.getInputStream());
            }
        }
    }
}
