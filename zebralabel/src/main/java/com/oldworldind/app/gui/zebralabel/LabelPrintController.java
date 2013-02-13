package com.oldworldind.app.gui.zebralabel;

import java.util.Date;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelPrintController
{
    private static final Logger LOG = LoggerFactory.getLogger(LabelPrintController.class);

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private Label messageLabel;
    @FXML private Button closeBtn;


    public void doPrintRealLabel() {
        Date d = new Date();
        messageLabel.setText("I printed a Real Label at :" + d);
    }
    public void doClose() {
        Date d = new Date();
        messageLabel.setText("I Closing  at :" + d);
        
        Parent parent = closeBtn.getParent();
        LOG.info("do close for parent:" + parent);
        Platform.exit();
    }
    public void doPrint46Label() {
        Date d = new Date();
        messageLabel.setText("Print demo 4x6 Label at :" + d);
    }
    public void doPrint24Label() {
        Date d = new Date();
        messageLabel.setText("2x4 demo Label at :" + d);
    }

    public void doParseLabel() {
        Date d = new Date();
        messageLabel.setText("Parsed Label at :" + d);
    }

    public void sayHello() {

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        StringBuilder builder = new StringBuilder();

        if (!StringUtils.isEmpty(firstName)) {
            builder.append(firstName);
        }

        if (!StringUtils.isEmpty(lastName)) {
            if (builder.length() > 0) {
                builder.append(" ");
            }
            builder.append(lastName);
        }

        if (builder.length() > 0) {
            String name = builder.toString();
            LOG.debug("Saying hello to " + name);
            messageLabel.setText("Hello " + name);
        } else {
            LOG.debug("Neither first name nor last name was set, saying hello to anonymous person");
            messageLabel.setText("Hello mysterious person");
        }
    }

}