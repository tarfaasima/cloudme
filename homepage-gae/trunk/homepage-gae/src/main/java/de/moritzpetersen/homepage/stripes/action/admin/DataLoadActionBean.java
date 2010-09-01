package de.moritzpetersen.homepage.stripes.action.admin;

import java.io.ByteArrayInputStream;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dataload.DataLoader;
import de.moritzpetersen.homepage.dataload.InvalidDataException;
import de.moritzpetersen.homepage.stripes.action.AbstractActionBean;

@UrlBinding( "/admin/dataload.php" )
public class DataLoadActionBean extends AbstractActionBean {
    @Inject
    private DataLoader dataLoader;
    @Validate( required = true )
    private String data;

    @DontValidate
    @DefaultHandler
    public Resolution show() {
        return new ForwardResolution("/WEB-INF/jsp/admin/dataload.jsp");
    }

    public Resolution save() {
        try {
            dataLoader.load(new ByteArrayInputStream(data.getBytes()));
        }
        catch (InvalidDataException e) {
            addError("Invalid data. Please enter data in XML format.");
        }
        return show();
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
}
