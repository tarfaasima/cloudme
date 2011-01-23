package org.cloudme.cashbox.stripes.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import net.sourceforge.stripes.action.FileBean;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

import org.cloudme.cashbox.account.AccountService;
import org.cloudme.cashbox.csv.CsvParser;
import org.cloudme.cashbox.domain.Account;
import org.cloudme.cashbox.transaction.TransactionImport;
import org.cloudme.gaestripes.AbstractActionBean;

import com.google.inject.Inject;

@UrlBinding( "/action/transaction/{$event}/{accountId}" )
public class TransactionActionBean extends AbstractActionBean {
    private static final String DEFAULT_CHARSET = "ISO-8859-1";
    @Inject
    private AccountService accountService;
    private Long accountId;
    private Account account;
    private FileBean transactionFile;
    private TransactionImport transactionImport;

    public Resolution show() {
        account = accountService.get(accountId);
        return resolve("transaction.jsp");
    }

    public Resolution upload() {
        try {
            BufferedReader in = new BufferedReader(getTransactionFile().getReader(DEFAULT_CHARSET));
            CsvParser csv = new CsvParser();
            String line = null;
            transactionImport = new TransactionImport();
            while ((line = in.readLine()) != null) {
                String[] items = csv.parse(line);
                transactionImport.add(items);
            }
        }
        catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return show();
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setTransactionFile(FileBean transactionFile) {
        this.transactionFile = transactionFile;
    }

    public FileBean getTransactionFile() {
        return transactionFile;
    }

    public void setTransactionImport(TransactionImport transactionImport) {
        this.transactionImport = transactionImport;
    }

    public TransactionImport getTransactionImport() {
        return transactionImport;
    }
}
