package org.cloudme.webgallery.flickr;

import javax.mail.MessagingException;
import javax.mail.internet.InternetHeaders;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

class PostBuilder {
    private final MimeMultipart multipart;

    public PostBuilder(boolean isPost) {
        multipart = isPost ? new MimeMultipart("form-data") : null;
    }

    public void append(String name, String value) {
        if (multipart != null) {
            try {
                MimeBodyPart part = new MimeBodyPart();
                part.setDisposition("form-data; name=\"" + name + "\"");
                part.setText(value);
                multipart.addBodyPart(part);
            }
            catch (MessagingException e) {
                throw new IllegalStateException(e);
            }
        }
    }
    
    public void append(String name, String contentType, String filename, byte[] data) {
        try {
            InternetHeaders headers = new InternetHeaders();
            headers.addHeader("Content-Type", contentType);
            MimeBodyPart part = new MimeBodyPart(headers, data);
            part.setDisposition("form-data); name=\"" + name + "\"; filename=" + filename);
            multipart.addBodyPart(part);
        }
        catch (MessagingException e) {
            throw new IllegalStateException(e);
        }
    }
    
    public MimeMultipart getMultipart() {
        return multipart;
    }
}    
