package de.moritzpetersen.homepage.mail;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.guice.GuiceServlet;
import de.moritzpetersen.homepage.util.ObjectUtil;

@SuppressWarnings( "serial" )
public class MailHandlerServlet extends GuiceServlet {
    private static final Logger LOG = Logger.getLogger(MailHandlerServlet.class.getName());
    @Inject
    private MailServiceProvider provider;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Session session = Session.getDefaultInstance(new Properties(), null);
            MimeMessage message = new MimeMessage(session, req.getInputStream());
            MailService service = provider.locateService(message.getAllRecipients());
            if (service != null) {
                service.execute(message);
            }
            else {
                LOG.warning("No service found for recipient(s) "
                        + ObjectUtil.toString(message.getAllRecipients())
                        + " sent by "
                        + ObjectUtil.toString(message.getFrom()));
            }
        }
        catch (MessagingException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
