package de.moritzpetersen.homepage.wicket.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;

import com.google.inject.Inject;

import de.moritzpetersen.homepage.domain.Entry;
import de.moritzpetersen.homepage.service.EntryService;

public class IndexPage extends WebPage {
    @Inject
    private EntryService entryService;

    @SuppressWarnings( "serial" )
    public IndexPage() {
        add(new ListView<Entry>("entries", entryService.getEntries()) {
            @Override
            protected void populateItem(ListItem<Entry> item) {
                Entry entry = item.getModelObject();
                item.add(new Label("title", entry.getTitle()));
            }
        });
    }
}
