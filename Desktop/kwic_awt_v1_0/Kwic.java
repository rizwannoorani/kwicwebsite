/********************************************************************
 *  Program:          KWIC
 *  Programmer:       Dan Modesto
 *  Purpose:          Creates an applet to provide the ui.
 *  File              Kwic.java
 *  Date:             9/23/2013
********************************************************************/

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

public class Kwic extends Applet 
        implements ActionListener {

/*
<APPLET
    CODE=Kwic.class
    WIDTH=800
    HEIGHT=300 >
</APPLET>
*/

    // create the kwic index components
    OutputComponent output = new OutputComponent(this);
    MergerComponent merger = new MergerComponent(output);
    AlphabetizerComponent alpha = new AlphabetizerComponent(merger);
    ShifterComponent shifter = new ShifterComponent(alpha, output);
    InputComponent input = new InputComponent(shifter, output);

    // create the text input, submit button, and print button
    TextField   txtInput;
    Button      btnSubmit;
    Button      btnPrint;

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
/*
        constraints.weightx = 1;
        btnPrint = new Button("Print");
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        gridbag.setConstraints(btnSubmit, constraints);
        add(btnPrint);
        btnPrint.addActionListener(this);
*/
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
            input.setData(txtInput.getText());
            txtInput.setText(null);
        }

        if(event.getSource() == btnPrint) {
            output.printIndex();
        }
    }

    public synchronized void display(int i, String s)
    {
        switch(i) {
            case 1:
                txtOriginal.append(s + "\n");
            break;
            case 2:
                txtCirShift.append(s + "\n");
            break;
            case 3:
                txtMerged.append(s + "\n");
            break;
        }
    }
}
