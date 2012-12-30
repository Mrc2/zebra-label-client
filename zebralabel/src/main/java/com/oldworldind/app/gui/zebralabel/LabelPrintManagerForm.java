package com.oldworldind.app.gui.zebralabel;

import java.io.File;

import javax.print.DocFlavor;
import javax.print.PrintService;
import org.apache.log4j.Logger;

/**
 *
 * @since Dec 29, 2012 at 6:09:43 PM
 * @author mcolegrove
 */
class LabelPrintManagerForm extends javax.swing.JPanel {
    private static final long serialVersionUID = -4367425235673580500L;
    private static final Logger LOG = Logger.getLogger(LabelPrintManagerForm.class);
    public static final String FILETYPE_XREF = "Xref";
    public static final String FILETYPE_ZPL = "txt/wri";
    private static final String DATE_DISPLAYPATTERN = "MM/dd/yy @HH:mm:ss:SSSSS";
    private static final String OTHER_LINE_END = "\n";

    private static void checkZipFilePath(String pathToZipFiles) {
        File dir = new File(pathToZipFiles);

        if (dir.isDirectory() && dir.canRead() && dir.exists()) {
            return;
        }

        throw new IllegalArgumentException("Path to Zpl Files:" + pathToZipFiles + " is not a valid directory");
    }

    private static boolean checkPrinterSettings(String zplPrinterName) {

        if (zplPrinterName == null || zplPrinterName.isEmpty()) {
            throw new IllegalArgumentException("A Valid ZPL Enabled Printer was not found with name:" + zplPrinterName + ": ");
        }
        PrinterFinderSvc svc = new PrinterFinderSvc();

        String fullName = svc.getFirstLabelPrinterName(zplPrinterName);
        if (fullName == null || fullName.isEmpty()) {
            throw new IllegalArgumentException("A Valid ZPL Enabled Printer was not found with name:" + zplPrinterName + ": ");
        }
        PrintService psZebra = svc.getFirstLabelPrinterServiceNamed(fullName);
        DocFlavor autoflavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        if (psZebra == null || !psZebra.isDocFlavorSupported(autoflavor)) {
            throw new IllegalArgumentException("A Valid ZPL Enabled Printer was not found with name:" + fullName + ": ");
        }
        return true;
    }
    private final String pathToZipFilesDefault;
    private final String zebraLabelPrinterDefault;
    private final LabelDataModel dm;

        public static LabelPrintManagerForm getInstance() {
        return new LabelPrintManagerForm("MEBANKE", "");
    }

    public static LabelPrintManagerForm getInstance(String pathToZipFiles, String zplPrinterName) {
        checkZipFilePath(pathToZipFiles);
        checkPrinterSettings(zplPrinterName);

        return new LabelPrintManagerForm(pathToZipFiles, zplPrinterName);
    } 

    private LabelPrintManagerForm(String pathToZipFiles, String zplPrinterName) {
        this.pathToZipFilesDefault = pathToZipFiles;
        this.zebraLabelPrinterDefault = zplPrinterName;

        dm = new LabelDataModel();
        initComponents();
    }

    private void initComponents() {
        LOG.info("doing init");


        LOG.info("end init");
    }
}
