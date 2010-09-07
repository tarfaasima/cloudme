package de.moritzpetersen.homepage.stripes.action.admin;

import java.io.ByteArrayInputStream;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.dao.EntryDao;
import de.moritzpetersen.homepage.dataload.DataLoader;
import de.moritzpetersen.homepage.dataload.InvalidDataException;
import de.moritzpetersen.homepage.domain.Entry;
import de.moritzpetersen.homepage.stripes.action.AbstractActionBean;

@UrlBinding( "/admin/dataload" )
public class DataLoadActionBean extends AbstractActionBean {
    @Inject
    private DataLoader dataLoader;
    @Inject
    private EntryDao entryDao;
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
            int count = 0;
            for (Entry entry : entryDao.findAll()) {
                System.out.println(entry.getTitle());
                count++;
            }
            System.out.println(count + " Entries");
        }
        catch (InvalidDataException e) {
            return addValidationError("data.invalid");
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
