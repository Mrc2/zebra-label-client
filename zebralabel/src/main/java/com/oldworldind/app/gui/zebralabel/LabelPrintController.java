package com.oldworldind.app.gui.zebralabel;

import java.io.File;
import java.util.Date;
import java.util.Map.Entry;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LabelPrintController {
    private static final Logger LOG = LoggerFactory.getLogger(LabelPrintController.class);
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label messageLabel;
    @FXML
    private Button closeBtn;
    @FXML
    private TextArea log;
    @FXML
    private MenuItem miOpen;
    @FXML
    private TextField barCodeImageFileName;

    public void doShowInfoDialogBox() {
        String lineRetn = "\r\n";
        StringBuilder buf = new StringBuilder(20);
        buf.append("Application:" + AboutInfo.getAppName() + lineRetn);
        buf.append("By:" + AboutInfo.getSupportCompany() + lineRetn);
        buf.append("For Help Call" + AboutInfo.getHelpphone() + lineRetn);
        buf.append("or mailto:" + AboutInfo.getHelpEmail() + lineRetn);
        buf.append("Tagged:" + AboutInfo.getTag() + lineRetn);
        buf.append("Version:" + AboutInfo.getVersion() + lineRetn);

        buf.append("Future RoadMap:" + lineRetn);
        for (Entry<Integer, String> en : AboutInfo.getRoadMap().entrySet()) {
            buf.append("" + en.getValue() + lineRetn);

        }
        log.appendText(buf.toString());

        buf.append("Recent Updates:" + lineRetn);
        for (Entry<Integer, String> en : AboutInfo.getRevisionHistory().entrySet()) {
            buf.append("" + en.getValue() + lineRetn);

        }
        log.appendText(buf.toString());
    }

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

    private boolean doPutInLog(String msg) {
        String lineRetn = "\r\n";
        StringBuilder buf = new StringBuilder(20);
        int l = 0;
        if (msg != null) {
            buf.append(msg);
            l = msg.length();
        } else {
            buf.append("");
        }
        buf.append(lineRetn);
        log.appendText(buf.toString());
        return l > 0;
    }

    public void doSelectFileToParse() {
        LOG.warn("doSelectFileToParse");
        FileChooser fChooser = new FileChooser();

        String[] arrayOfPatterns = {"*.txt", "*.wri", "*.zpl", "*.zip", "*.jar"};
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Zpl Label Content Files (*.txt, *.wri, *.zpl)", arrayOfPatterns);
        fChooser.getExtensionFilters().add(extFilter);
        File chosen = fChooser.showOpenDialog(miOpen.getParentPopup());
        if (chosen != null) {
            doPutInLog("chose" + chosen.getAbsolutePath());
            barCodeImageFileName.setText(chosen.getAbsolutePath());
            return;
        }
        doPutInLog("chose no file for label");

    }

    public void doPrint46Label() {
        LOG.warn("4x6 printed");
        Date d = new Date();
        messageLabel.setText("Print demo 4x6 Label at :" + d);
    }

    public void doPrint24Label() {

        LOG.warn("2x4 printed");
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
