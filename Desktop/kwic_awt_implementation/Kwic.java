/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Creates an applet to provide the user interface.
 *  File              Kwic.java
 *  Date:             9/23/2013
********************************************************************/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Kwic extends Applet implements ActionListener {

/*
<APPLET
    CODE=Kwic.class
    WIDTH=800
    HEIGHT=300 >
</APPLET>
*/

    // create the text input and button
    TextField   txtInput;
    Button      btnSubmit;

    // create the output textareas
    TextArea    txtOriginal;
    TextArea    txtCirShift;
    TextArea    txtMerged;

    // setup the input UI
    @Override
    public void init() {

        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(gridbag);
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.NONE;
        constraints.weightx = 2;
        
        txtInput = new TextField(75);
        gridbag.setConstraints(txtInput, constraints);
        add(txtInput);

        constraints.weightx = 1;
        btnSubmit = new Button("Submit");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(btnSubmit, constraints);
        add(btnSubmit);
        btnSubmit.addActionListener(this);

        constraints.fill = GridBagConstraints.BOTH;
        txtOriginal = new TextArea("", 5, 20);
        gridbag.setConstraints(txtOriginal, constraints);
        add(txtOriginal);

        txtCirShift = new TextArea("", 5, 20);
        gridbag.setConstraints(txtCirShift, constraints);
        add(txtCirShift);

        txtMerged = new TextArea("", 5, 20);
        gridbag.setConstraints(txtMerged, constraints);
        add(txtMerged);
    }

    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == btnSubmit) {
            StorageLine line = new StorageLine(this, "thread 1", txtInput.getText());
            txtInput.setText(null);
            //displayOriginal(txtInput.getText() + " within Applet");
        }
    }

    synchronized void displayOriginal(String s)
    {
        txtOriginal.append(s + "\n");
    }

    synchronized void displayShift(String s)
    {
        txtCirShift.append(s + "\n");
    }

    synchronized void displayMerge(String s)
    {
        txtMerged.append(s + "\n");
    }
}
