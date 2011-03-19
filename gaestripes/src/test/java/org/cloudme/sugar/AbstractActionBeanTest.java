package org.cloudme.sugar;

import static org.junit.Assert.assertEquals;
import net.sourceforge.stripes.action.ForwardResolution;

import org.junit.Test;

public class AbstractActionBeanTest extends AbstractActionBean {
    @Test
    public void testResolve() {
        assertEquals("/WEB-INF/classes/org/cloudme/sugar/test.html", ((ForwardResolution) resolve("test.html"))
                .getPath());
    }
}
