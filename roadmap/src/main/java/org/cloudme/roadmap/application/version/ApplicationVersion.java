package org.cloudme.roadmap.application.version;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

@Getter
@Setter
public class ApplicationVersion extends Entity {
    private Long applicationId;
    private String version;
}
