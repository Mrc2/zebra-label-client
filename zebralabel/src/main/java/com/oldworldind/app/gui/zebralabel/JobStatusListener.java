package com.oldworldind.app.gui.zebralabel;

import java.awt.Color;

import javax.print.attribute.Attribute;
import javax.print.attribute.PrintJobAttribute;
import javax.print.attribute.PrintJobAttributeSet;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.JTextArea;

/**
 * @since Dec 29, 2012 at 7:10:11 PM
 * @author mcolegrove 
 */
class JobStatusListener implements PrintJobListener {
    private static final String NEWLINE = "\n";
    private final JTextArea log;

    public JobStatusListener(JTextArea log) {
        this.log = log;
    }

    @Override
    public void printDataTransferCompleted(PrintJobEvent pje) {
        log.append("transfer done: " + pje + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void printJobCompleted(PrintJobEvent pje) {

         log.append("print Job Completed: " + pje.getPrintJob().getAttributes() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void printJobFailed(PrintJobEvent pje) {

        log.append("print Job failed: " + pje.getPrintJob() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void printJobCanceled(PrintJobEvent pje) {

        log.append("print Job Cancelled: " + pje.getPrintJob() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());
    }

    @Override
    public void printJobNoMoreEvents(PrintJobEvent pje) {

        Color caretprior = log.getCaretColor();
        Color fore = log.getForeground();
        log.setCaretColor(Color.red);
        log.setForeground(Color.green);
        log.append("print Job No More Events: Device cannot report events: " + pje.getPrintJob() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());


        log.append("print Job No More Events: Device cannot report events: " + pje.getPrintJob().getAttributes() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());

        PrintJobAttributeSet set = pje.getPrintJob().getAttributes();
        Attribute[] attrs = set.toArray();
        for (int i = 0; i < attrs.length; i++) {
            Attribute attribute = attrs[i];
            if (attribute instanceof PrintJobAttribute) {
                PrintJobAttribute pja = (PrintJobAttribute) attribute;

            log.append("attr name:" + pja.getName() + " Category:" + pja.getCategory() + " toString:" + attribute.toString() + NEWLINE);
            log.setCaretPosition(log.getDocument().getLength());
            }
        }
        log.setCaretColor(caretprior);
        log.setForeground(fore);
    }

    @Override
    public void printJobRequiresAttention(PrintJobEvent pje) {

        log.append("print Job Requires attention: " + pje.getPrintJob() + NEWLINE);
        log.setCaretPosition(log.getDocument().getLength());
    }

}