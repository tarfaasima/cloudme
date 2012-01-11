package org.cloudme.roadmap.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embedded;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Unindexed;

@Getter
@Setter
public class Application extends Entity {
    @Unindexed
    private String name;
    @Embedded
    @Unindexed
    private List<ApplicationVariant> applicationVariants = new ArrayList<ApplicationVariant>();
}
