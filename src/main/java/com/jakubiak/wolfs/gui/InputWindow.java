package com.jakubiak.wolfs.gui;

import com.jakubiak.wolfs.AnimalsSimulation;
import com.jakubiak.wolfs.model.Parameters;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputWindow extends JFrame implements ActionListener {

    private static final String SUBMIT = "Submit";
    private Parameters parameters;

    private final static int DEFAULT_NUMBER_OF_SHEEP = 5;
    private final static int DEFAULT_HEIGHT = 10;
    private final static int DEFAULT_WIDTH = 10;
    private final static int DEFAULT_DELAY = 1000;

    private JTextField numberOfSheepTextField;
    private JTextField boardHeightTextField;
    private JTextField boardWidthTextField;
    private JTextField delayTextField;
    private JFrame frame;
    private JLabel textLabel;

    public void init() {
        // create a new frame to store text field and button
        this.frame = new JFrame("textfield");

        // create a label to display text
        this.textLabel = new JLabel("nothing entered");

        // create a new button
        JButton button = new JButton(SUBMIT);

        // create a object of the text class
        InputWindow te = new InputWindow();

        // addActionListener to button
        button.addActionListener(te);

        // create a object of JTextField with 16 columns
        this.numberOfSheepTextField = new JTextField(25);
        this.numberOfSheepTextField.setToolTipText("Type numbers of sheep  (Default = " + DEFAULT_NUMBER_OF_SHEEP + ")");
        this.boardHeightTextField = new JTextField(25);
        this.boardHeightTextField.setToolTipText("Type height  (Default = " + DEFAULT_HEIGHT + ")");
        this.boardWidthTextField = new JTextField(25);
        this.boardWidthTextField.setToolTipText("Type width  (Default = " + DEFAULT_WIDTH + ")");
        this.delayTextField = new JTextField(25);
        this.delayTextField.setToolTipText("Type delay (Default = " + DEFAULT_DELAY + ")");
        JPanel panel = new JPanel();

        // add buttons and textfield to panel
        panel.add(this.numberOfSheepTextField);
        panel.add(this.boardHeightTextField);
        panel.add(this.boardWidthTextField);
        panel.add(this.delayTextField);
        panel.add(button);
        panel.add(this.textLabel);

        // add panel to frame
        this.frame.add(panel);

        // set the size of frame
        this.frame.setSize(300, 300);

        this.frame.show();

    }




    // if the button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();
        if (s.equals(SUBMIT)) {
            Parameters parameters = new Parameters(
                    this.numberOfSheepTextField.getText().equals("") ? DEFAULT_NUMBER_OF_SHEEP : Integer.parseInt(this.numberOfSheepTextField.getText()),
                    this.boardHeightTextField.getText().equals("") ? DEFAULT_HEIGHT : Integer.parseInt(this.boardHeightTextField.getText()),
                    this.boardWidthTextField.getText().equals("") ? DEFAULT_WIDTH : Integer.parseInt(this.boardWidthTextField.getText()),
                    this.delayTextField.getText().equals("") ? DEFAULT_DELAY : Integer.parseInt(this.delayTextField.getText())
            );

            // set the text of the label to the text of the field
            this.textLabel.setText(this.numberOfSheepTextField.getText());

            this.frame.dispose();
            this.parameters = parameters;
        }
    }

    public Parameters getParameters() {
        return parameters;
    }
}