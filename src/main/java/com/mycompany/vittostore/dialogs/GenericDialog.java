package com.mycompany.vittostore.dialogs;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

public class GenericDialog extends JDialog {

    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    public GenericDialog(JFrame frame, boolean modal, String myMessage) {
        super(frame, modal);
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel(myMessage));
        // yesButton = new JButton("Yes");
        // myPanel.add(yesButton);
        // noButton = new JButton("No");
        // myPanel.add(noButton);
        pack();
        
        
        setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        setVisible(true);
    }
}
