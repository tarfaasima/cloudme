package org.cloudme.metamodel.jdom;

import org.cloudme.metamodel.Instance;

public class JdomInstance implements Instance {
    private final JdomEntity entity;

    public JdomInstance(JdomEntity entity) {
        this.entity = entity;
    }

    public void setValue(String name, String value) {
    }

    public void validate() {
        // TODO Auto-generated method stub

    }

}
