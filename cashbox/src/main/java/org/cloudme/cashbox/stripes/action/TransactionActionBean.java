package org.cloudme.cashbox.stripes.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.gaestripes.AbstractActionBean;

@UrlBinding( "/action/transaction/{accountId}/{$event}" )
public class TransactionActionBean extends AbstractActionBean {
    private FileBean importFile;

    public Resolution load() {
        try {
            importFile.getReader("");
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return resolve("transaction.jsp");
    }

    public void setImportFile(FileBean importFile) {
        this.importFile = importFile;
    }

    public FileBean getImportFile() {
        return importFile;
    }
}
