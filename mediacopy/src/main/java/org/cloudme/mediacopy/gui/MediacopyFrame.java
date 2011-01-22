package org.cloudme.mediacopy.gui;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.cloudme.mediacopy.BaseCopy;

import com.google.inject.Inject;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

@SuppressWarnings( "serial" )
public class MediacopyFrame extends JFrame {
    @Inject
    private BaseCopy copy;

    public MediacopyFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel originalsLabel = new JLabel("Originals:");
        JTextField originalsField = new JTextField(30);
        originalsField.setEditable(false);
        JButton originalsButton = new JButton("Select...");

        Container panel = getContentPane();
        FormLayout layout = new FormLayout("24px, right:pref, 8px, pref:grow, 8px, pref, 24px",
                "24px, pref, 12px, pref, 12px, pref, 24px");
        panel.setLayout(layout);
        CellConstraints cc = new CellConstraints();
        panel.add(originalsLabel, cc.xy(2, 2));
        panel.add(originalsField, cc.xy(4, 2));
        panel.add(originalsButton, cc.xy(6, 2));

        pack();
    }
}
