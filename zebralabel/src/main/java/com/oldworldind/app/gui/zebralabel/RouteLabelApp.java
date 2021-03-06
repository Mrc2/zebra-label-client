package com.oldworldind.app.gui.zebralabel;

import java.util.Map;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @since Dec 15, 2012 at 1:26:44 AM
 * @author mcolegrove
 */
public class RouteLabelApp {
    private static final Logger LOG = LogManager.getLogger(RouteLabelApp.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        displayUsage();
        System.out.println("RouteLabelApp!");
        LOG.info("RouteLabelApp started");
        logProperties(System.getenv());
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createPickandRunLabelPanel();
            }

        });
    }

    private static void createPickandRunLabelPanel() {
        JFrame jf = new JFrame("Route Label App");
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   not preferred in an applet

        LabelPrintManagerForm pmf = LabelPrintManagerForm.getInstance();
        pmf.setOpaque(true); //content panes must be opaque
        jf.setContentPane(pmf);
        jf.pack();
        jf.setVisible(true);
    }

    /**
     *
     */
    private static void displayUsage() {
        System.out.println("RouteLabelApp users parameters as seen below");
        System.out.println("java RouteLabelApp <input File to Route>  <device>");
    }

    private static void logProperties(Map<String, String> env) {
        LOG.info("Start of Runtime Properties");
        env.entrySet().stream().forEach((entrySet) -> {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            LOG.info("key:" + key + "[" + value + "]");
        });
        LOG.info("End of Runtime Properties");
    }

}
