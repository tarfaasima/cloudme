package org.cloudme.cashbox.stripes.extensions;

import net.sourceforge.stripes.controller.Intercepts;
import net.sourceforge.stripes.controller.LifecycleStage;

import org.cloudme.cashbox.account.AccountService;
import org.cloudme.cashbox.dao.AccountDao;
import org.cloudme.cashbox.domain.Account;
import org.cloudme.gaestripes.AbstractActionBeanResolutionInterceptor;

@Intercepts( { LifecycleStage.ActionBeanResolution } )
public class ActionBeanResolutionInterceptor extends AbstractActionBeanResolutionInterceptor {
    @Override
    protected Class<?>[] guiceModuleClasses() {
        return new Class<?>[] { AccountService.class, AccountDao.class };
    }

    @Override
    protected Class<?>[] objectifyEntityClasses() {
        return new Class<?>[] { Account.class };
    }
}
