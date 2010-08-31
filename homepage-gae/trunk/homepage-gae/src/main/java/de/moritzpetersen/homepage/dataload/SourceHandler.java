package de.moritzpetersen.homepage.dataload;

public interface SourceHandler {

    /**
     * Resolves the source by url. If the source doesn't exist, it will get
     * created based on the URL and the ID will be returned.
     * 
     * @param sourceUrl
     *            The URL of the source.
     * @return The id of the source.
     */
    Long resolve(String sourceUrl);

}
