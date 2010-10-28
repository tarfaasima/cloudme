package org.cloudme.gaestripes;

import static org.junit.Assert.assertEquals;
import net.sourceforge.stripes.action.ForwardResolution;

import org.junit.Test;

public class AbstractActionBeanTest extends AbstractActionBean {
    @Test
    public void testResolve() {
        assertEquals("/WEB-INF/classes/org/cloudme/gaestripes/test.html", ((ForwardResolution) resolve("test.html"))
                .getPath());
    }
}
