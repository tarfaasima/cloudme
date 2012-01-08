package org.cloudme.roadmap.application;

import lombok.Getter;
import lombok.Setter;

import org.cloudme.sugar.Entity;

import com.googlecode.objectify.annotation.Unindexed;

@Getter
@Setter
public class Application extends Entity {
    @Unindexed
    private String name;
}
