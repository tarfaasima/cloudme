package org.cloudme.roadmap.application;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

@Getter
@Setter
public class ApplicationVariant extends Entity {
    private String version;
}
