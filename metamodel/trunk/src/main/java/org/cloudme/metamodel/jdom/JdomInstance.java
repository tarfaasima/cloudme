package org.cloudme.metamodel.jdom;

import java.util.ArrayList;
import java.util.Collection;

import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.cloudme.metamodel.Instance;
import org.cloudme.metamodel.MetamodelException;
import org.cloudme.metamodel.util.ValidationError;
import org.cloudme.metamodel.util.ValidationMessageParser;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.transform.JDOMSource;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class JdomInstance extends AbstractJdomObject implements Instance {
    private static final Namespace NS_METAMODEL = Namespace.getNamespace("", "http://cloudme.org/metamodel");

    private final class ValidatorErrorHandler implements ErrorHandler {
        private final Collection<ValidationError> errors = new ArrayList<ValidationError>();

        public void warning(SAXParseException e) throws SAXException {
            throw new IllegalStateException("Unexpected warning.", e);
        }

        public void fatalError(SAXParseException e) throws SAXException {
            throw new IllegalStateException("Unexpected fatal error.", e);
        }

        public void error(SAXParseException e) throws SAXException {
            ValidationMessageParser parser = new ValidationMessageParser();
            ValidationError error = parser.parse(e.getMessage());
            if (error != null) {
                errors.add(error);
            }
        }

        public Collection<ValidationError> getErrors() {
            return errors;
        }
    }

    private final JdomEntity entity;

    public JdomInstance(JdomEntity entity) {
        super(new Element(entity.getName(), NS_METAMODEL));
        this.entity = entity;
    }

    public void setValue(String name, String value) {
        if (!entity.hasProperty(name)) {
            throw new MetamodelException("Unknown property: " + name);
        }
        getChild(name).setText(value);
    }

    public Collection<ValidationError> validate() {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Document xsd = entity.element.getDocument();
        Schema schema = null;
        try {
            schema = sf.newSchema(new JDOMSource(xsd));
        }
        catch (SAXException e) {
            throw new MetamodelException(e);
        }
        Validator validator = schema.newValidator();
        ValidatorErrorHandler errorHandler = new ValidatorErrorHandler();
        validator.setErrorHandler(errorHandler);
        Document xml = element.getDocument();
        if (xml == null) {
            xml = new Document(element);
        }
        try {
            validator.validate(new JDOMSource(xml));
        }
        catch (Exception e) {
            throw new MetamodelException(e);
        }
        return errorHandler.getErrors();
    }
}
