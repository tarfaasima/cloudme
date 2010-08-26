package de.moritzpetersen.homepage.dataimport;

public class DefaultSourceHandler implements SourceHandler {
    @Override
    public Long resolve(String sourceUrl) {
        System.out.println(sourceUrl);
        return null;
    }
}
