package org.cloudme.webgallery.flickr;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.junit.Test;


public class PostBuilderTest {
    @Test
    public void testMultipart() throws IOException, MessagingException {
        PostBuilder builder = new PostBuilder(true);
        builder.append("filename", "photo.jpg");
        builder.append("photo", "image/jpeg", "abc.jpg", "hello world".getBytes());
        MimeMultipart multipart = builder.getMultipart();
        System.out.println(multipart.getContentType());
        System.out.println(multipart.getPreamble());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        multipart.writeTo(out);
        System.out.println(out.toString());
    }
}
