package de.moritzpetersen.homepage.mail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

public class MailHandlerServletTest {
    @Test
    public void testDoPost() throws ServletException, IOException {
        MailHandlerServlet servlet = new MailHandlerServlet();
        servlet.init();
        HttpServletRequest req = new AbstractHttpServletRequest() {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStream() {
                    {
                        try {
                            MimeMessage message = new MimeMessage((Session) null);
                            message.addFrom(new Address[] { new InternetAddress("test@test.com") });
                            message.addRecipient(RecipientType.TO, new InternetAddress("data_load_token@test.com"));
                            MimeMultipart content = new MimeMultipart();
                            MimeBodyPart bodyPart = new MimeBodyPart();
                            bodyPart.setText("hello");
                            content.addBodyPart(bodyPart);
                            message.setContent(content);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            message.writeTo(out);
                            in = new ByteArrayInputStream(out.toByteArray());
                        }
                        catch (AddressException e) {
                            throw new IllegalStateException(e);
                        }
                        catch (MessagingException e) {
                            throw new IllegalStateException(e);
                        }
                    }
                    InputStream in;

                    @Override
                    public int read() throws IOException {
                        return in.read();
                    }
                };
            }
        };
        servlet.doPost(req, null);
    }
}
