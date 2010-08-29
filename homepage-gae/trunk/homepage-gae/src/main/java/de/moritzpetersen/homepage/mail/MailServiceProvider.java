package de.moritzpetersen.homepage.mail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.domain.MailServiceConfig;
import de.moritzpetersen.homepage.service.MailServiceConfigService;

public class MailServiceProvider {
    @Inject
    private DataLoadMailService dataLoadMailService;
    @Inject
    private MailServiceConfigService configurationService;
    private static final Pattern TOKEN_PATTERN = Pattern.compile("(.+)@.+\\..+");

    public MailService locateService(Address[] addresses) {
        for (Address address : addresses) {
            String token = extractToken(address.toString());
            MailServiceConfig config = configurationService.findByToken(token);
            if (config != null) {
                switch (config.getServiceId()) {
                    case DataLoadMailService.SERVICE_ID:
                        return dataLoadMailService;
                }
            }
        }
        return null;
    }

    String extractToken(String address) {
        Matcher matcher = TOKEN_PATTERN.matcher(address);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        return null;
    }
}
