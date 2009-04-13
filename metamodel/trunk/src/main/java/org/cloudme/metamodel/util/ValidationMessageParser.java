package org.cloudme.metamodel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationMessageParser {
    public ValidationError parse(String msg) {
        Pattern p = Pattern.compile("cvc-type.3.1.3: The value '([^']*)' of element '([^']*)' is not valid.");
        Matcher m = p.matcher(msg);
        if (m.matches()) {
            return new ValidationError(m.group(1), m.group(2));
        }
        return null;
    }
}
