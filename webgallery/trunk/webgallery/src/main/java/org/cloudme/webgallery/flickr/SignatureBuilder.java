package org.cloudme.webgallery.flickr;

import org.apache.commons.codec.digest.DigestUtils;

class SignatureBuilder {
    private StringBuilder builder;

    public SignatureBuilder(String secret) {
        if (secret != null) {
            builder = new StringBuilder(secret);
        }
    }

    public void append(String name, String value) {
        if (builder != null) {
            builder.append(name).append(value);
        }
    }

    public String toSignature() {
        if (builder == null) {
            return null;
        }
        else {
            return DigestUtils.md5Hex(builder.toString());
        }
    }
}
