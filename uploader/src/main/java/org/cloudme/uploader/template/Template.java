package org.cloudme.uploader.template;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

public class Template {
    private final StringTemplate template;

    public Template(String str) {
        template = new StringTemplate(str, DefaultTemplateLexer.class);
    }

    public void replace(String name, String value) {
        template.setAttribute(name, value);
    }
    
    @Override
    public String toString() {
        return template.toString();
    }
}
