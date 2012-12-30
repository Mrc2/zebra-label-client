package com.oldworldind.app.gui.zebralabel;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.swing.JFileChooser;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
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

 /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jCloseButton = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        openButton = new javax.swing.JButton();
        parseButton = new javax.swing.JButton();
        //#FYI  Pre Creation Code of the JPanel
        String zplpath = getDefaultZplPath(pathToZipFilesDefault);
        systemPanel = new javax.swing.JPanel();
        JLabelMatchString = new javax.swing.JLabel();
        //#FYI  Enables starting lookup point for Zpl Files
        final String pathToArchive = getDefaultZplPath(pathToZipFilesDefault);
        archivePathTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        //#FYI  Enables starting lookup point from path in field list
        final String zplPrinterName = getDefaultZplPrinterName(zebraLabelPrinterDefault);
        jTextLabelMatchStr = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        log = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        //#FYI File Name to Import to HFA
        //#FYI  Enables starting lookup point from path in field list
        barCodeImageFileName = new javax.swing.JTextField();
        jPrinterTestButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        fileInputJTable = new javax.swing.JTable();
        jPrintLabelFileButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        jCloseButton.setText("Close");

        jProgressBar1.setBackground(new java.awt.Color(153, 255, 102));
        jProgressBar1.setForeground(new java.awt.Color(153, 102, 255));

        openButton.setText("Open");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonopenFile(evt);
            }
        });

        parseButton.setText("Parse");
        parseButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                parseButtonActionPerformed(evt);
            }
        });

        JLabelMatchString.setText("Label Printer  PartialName");

        archivePathTextField.setText(pathToArchive);

        jLabel2.setText("Archive Path");

        jTextLabelMatchStr.setText(zplPrinterName);

        log.setColumns(20);
        log.setRows(5);
        log.setName("log"); // NOI18N
        jScrollPane4.setViewportView(log);

        jLabel4.setText("Bar Code Source");

        barCodeImageFileName.setText(pathToZipFilesDefault);

        org.jdesktop.layout.GroupLayout systemPanelLayout = new org.jdesktop.layout.GroupLayout(systemPanel);
        systemPanel.setLayout(systemPanelLayout);
        systemPanelLayout.setHorizontalGroup(
            systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(systemPanelLayout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 304, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, systemPanelLayout.createSequentialGroup()
                .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(systemPanelLayout.createSequentialGroup()
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(JLabelMatchString))
                        .add(10, 10, 10))
                    .add(systemPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(barCodeImageFileName)
                    .add(archivePathTextField)
                    .add(systemPanelLayout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(jTextLabelMatchStr, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)))
                .add(306, 306, 306))
        );
        systemPanelLayout.setVerticalGroup(
            systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, systemPanelLayout.createSequentialGroup()
                .add(16, 16, 16)
                .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(barCodeImageFileName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(archivePathTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(systemPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextLabelMatchStr, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(JLabelMatchString))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jScrollPane4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 127, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPrinterTestButton.setText("Print Test Label");
        jPrinterTestButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPrinterTestButtonMouseClicked(evt);
            }
        });
        jPrinterTestButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrinterTestButtonActionPerformed(evt);
            }
        });

        final Integer[] columnWidth = (Integer[]) Array.newInstance(Integer.class, dm.getColumnCount());
        fileInputJTable.setModel(dm);
        fileInputJTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileInputJTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                int viewRow = fileInputJTable.getSelectedRow();
                if (viewRow < 0) {
                    //Selection got filtered away.
                    barCodeImageFileName.setText("");
                } else {
                    int modelRow =
                    fileInputJTable.convertRowIndexToModel(viewRow);
                    barCodeImageFileName.setText(
                        String.format("Selected Row in view: %d. "
                            + "Selected Row in model: %d.",
                            viewRow, modelRow));
                }
            }
        });
        jScrollPane2.setViewportView(fileInputJTable);

        jPrintLabelFileButton.setText("Print Label File");
        jPrintLabelFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPrintLabelFileButtonMouseClicked(evt);
            }
        });
        jPrintLabelFileButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPrintLabelFileButtonActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(405, 405, 405)
                .add(jScrollPane2)
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .add(43, 43, 43)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 570, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(69, 69, 69)
                .add(openButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(parseButton)
                .add(18, 18, 18)
                .add(jPrintLabelFileButton)
                .add(93, 93, 93)
                .add(jPrinterTestButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jCloseButton)
                .add(61, 61, 61))
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(55, 55, 55)
                    .add(systemPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 322, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(488, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 314, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(jProgressBar1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(33, 33, 33)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jCloseButton)
                    .add(openButton)
                    .add(parseButton)
                    .add(jPrinterTestButton)
                    .add(jPrintLabelFileButton))
                .add(35, 35, 35))
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(17, 17, 17)
                    .add(systemPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(163, Short.MAX_VALUE)))
        );
    }// </editor-fold>

    private void openButtonopenFile(java.awt.event.ActionEvent evt) {

        String pickupPath = this.barCodeImageFileName.getText();
        JFileChooser fchooser = new JFileChooser(pickupPath);
        fchooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Zpl Label Content Files", "txt", "wri", "zpl", "zip", "jar");
        fchooser.setFileFilter(filter);

        int returnVal = fchooser.showOpenDialog(LabelPrintManagerForm.this);
        // show file extension - detect if text or a zip. size.  allow accumulation of a count of file names

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File[] files = fchooser.getSelectedFiles();
            SimpleDateFormat fdfd = new SimpleDateFormat(DATE_DISPLAYPATTERN);

            int rc = this.fileInputJTable.getModel().getRowCount();
            boolean canAddModViewCell = fileInputJTable.isCellEditable(rc, 0);

            for (int fi = 0; fi < files.length; fi++) {
                File file = files[fi];
                //This is where a real application would open the file.
                log.append("Opening: " + file.getName() + " path" + file.getAbsolutePath() + " updated:" + fdfd.format(file.lastModified())
                    + " add row after:" + rc + " can Add^" + canAddModViewCell + "." + OTHER_LINE_END);

                if (canAddModViewCell) {
                    populateImageFileTable(file, fdfd);
                    this.archivePathTextField.setText(file.getParent());
                    this.barCodeImageFileName.setText(file.getName());
                }
            }

        } else {
            log.append("Open command cancelled by user." + OTHER_LINE_END);
        }
        log.setCaretPosition(log.getDocument().getLength());
    }

    private void populateImageFileTable(File file, DateFormat fdfd) {
        // add file info to table model

        for (int i = 0; i < fileInputJTable.getColumnCount(); i++) {
            String coName = fileInputJTable.getColumnName(i);

            log.append("add to cell:" + coName);
        }

        String isZip = "other";

        int pageCnt = 0;
        Date batchDate = null;
        long recCnt = -1;
        if (isZpl(file.getAbsolutePath())) {
            isZip = FILETYPE_ZPL;
            recCnt = FileUtils.sizeOf(file);

            Map<String, Object> bhSet = ZplFileParser.parse(file);
            if (bhSet.containsKey("page-count")) {
                pageCnt = (Integer) bhSet.get("page-count");
            }
            if (bhSet.containsKey("printed-date")) {
                batchDate = (Date) bhSet.get("printed-date");
            }
        }

        dm.addFileInfo(file, isZip, recCnt, pageCnt, batchDate);
    }

    private void parseButtonActionPerformed(java.awt.event.ActionEvent evt) {

        parseImageReferences();
    }

    private void jPrinterTestButtonActionPerformed(java.awt.event.ActionEvent evt) {

//        System.out.println("Print It Now");
        log.append(OTHER_LINE_END);
        log.append("PrinterTest It Now" + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());
    }

    private void jPrinterTestButtonMouseClicked(java.awt.event.MouseEvent evt) {


        log.append(OTHER_LINE_END);
        log.append("list PrinterTest sNow" + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());
        PrinterFinderSvc svc = new PrinterFinderSvc();

        String labelPartial = jTextLabelMatchStr.getText();

        log.append("matching partial:" + labelPartial + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());
        String fullName = svc.getFirstLabelPrinterName(labelPartial);
        log.append("matching printers:" + fullName + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());

        log.append("matching home printers:" + svc.getFirstHomePrinterName() + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());

        PrintService psZebra = svc.getFirstLabelPrinterServiceNamed(fullName);
        if (psZebra == null) {
            log.append("service nof found for name:" + fullName + OTHER_LINE_END);
            log.setCaretPosition(log.getDocument().getLength());
            return;
        }
        log.append("Found printer: " + fullName + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());

        jTextLabelMatchStr.setText(fullName);
        DocPrintJob job = psZebra.createPrintJob();
//        byte[] by = getTestStringBytes();
//        byte[] by = getSampleLabelBytes();
//        byte[] by = getScoopedLabelBytes();
        byte[] by = getScoopedCustomLabelBytes();

        /**
         * TEXT_PLAIN_UTF_8 => text/plain; charset=utf-8
         */
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_8;
        /**
         * TEXT_PLAIN_US_ASCII => text/plain; charset=us-ascii
         */
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY.TEXT_PLAIN_US_ASCII;
        /**
         * Label content flavor =>text/plain;charset=Cp1252
         *
         */
//        DocFlavor flavor = new DocFlavor("text/plain",Charset.forName("Cp1252").getClass().getName());
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;

        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            job.addPrintJobListener(new JobStatusListener(log));
            job.print(doc, null);

        } catch (PrintException e) {
            e.printStackTrace();
        }
        // TODO add your handling code here:
    }

    private void jPrintLabelFileButtonMouseClicked(java.awt.event.MouseEvent evt) {

        log.append("PrinterLabel File Button clicked!" + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());

        PrinterFinderSvc svc = new PrinterFinderSvc();

        String labelPartial = jTextLabelMatchStr.getText();
        String fullName = svc.getFirstLabelPrinterName(labelPartial);
        PrintService psZebra = svc.getFirstLabelPrinterServiceNamed(fullName);
        if (psZebra == null) {
            log.append("service nof found for name:" + fullName + OTHER_LINE_END);
            log.setCaretPosition(log.getDocument().getLength());
            return;
        }
        log.append("Found printer: " + fullName + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());

        jTextLabelMatchStr.setText(fullName);
//        fileInputJTable.getModel().
        // #FIXME mcc 12-20-12 - obtain selected row label file name, verify contents available, create print job under psZebra
        //  from bytes of this file.

        File labelFile = new File(archivePathTextField.getText() + File.separator + barCodeImageFileName.getText());

        DocPrintJob job = psZebra.createPrintJob();
//        byte[] by = getScoopedCustomLabelBytes();

        byte[] by = getLabelFileBytes(labelFile);
        /**
         * TEXT_PLAIN_UTF_8 => text/plain; charset=utf-8
         */
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY.TEXT_PLAIN_UTF_8;
        /**
         * TEXT_PLAIN_US_ASCII => text/plain; charset=us-ascii
         */
//        DocFlavor flavor = DocFlavor.BYTE_ARRAY.TEXT_PLAIN_US_ASCII;
        /**
         * Label content flavor =>text/plain;charset=Cp1252
         *
         */
//        DocFlavor flavor = new DocFlavor("text/plain",Charset.forName("Cp1252").getClass().getName());
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;

        Doc doc = new SimpleDoc(by, flavor, null);
        try {
            job.addPrintJobListener(new JobStatusListener(log));
            job.print(doc, null);

        } catch (PrintException e) {
            log.append("Found printer: " + e + OTHER_LINE_END);
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    private void jPrintLabelFileButtonActionPerformed(java.awt.event.ActionEvent evt) {

        log.append("Printer Label File Button Action Performed!!" + OTHER_LINE_END);
        log.setCaretPosition(log.getDocument().getLength());
        // TODO add your handling code here:
    }
    // Variables declaration - do not modify
    private javax.swing.JLabel JLabelMatchString;
    private javax.swing.JTextField archivePathTextField;
    private javax.swing.JTextField barCodeImageFileName;
    private javax.swing.JTable fileInputJTable;
    private javax.swing.JButton jCloseButton;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JButton jPrintLabelFileButton;

    private javax.swing.JButton jPrinterTestButton;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextField jTextLabelMatchStr;
    private javax.swing.JTextArea log;
    private javax.swing.JButton openButton;
    private javax.swing.JButton parseButton;
    private javax.swing.JPanel systemPanel;
    // End of variables declaration

    private void parseImageReferences() {
        LOG.info("parseImageReferences");
    }

    private String getDefaultZplPath(String prop) {
        if (prop == null || prop.isEmpty()) {
            return "/";
        }
        return prop;
    }

    private String getDefaultZplPrinterName(String prop) {
        if (prop == null || prop.isEmpty()) {
            return "Zebra";
        }
        return prop;
    }

    private boolean isZpl(String absolutePath) {
        File f = new File(absolutePath);
        if (!f.isFile()) {
            return false;
        }

        String ext = FilenameUtils.getExtension(f.getAbsolutePath());
        if (ext == null) {
            return false;
        }

        return "zpl".equals(ext) || "txt".equals(ext) || "wri".equals(ext);
    }
    private static final String WINDOWS_LINE_END = "\r\n";

    private byte[] getSampleLabelBytes() {

        String lineEnd = OTHER_LINE_END;
        String s = "";
        s = s + "N" + lineEnd;
        s = s + "Q380,24" + lineEnd;
        s = s + "R203,20" + lineEnd;
        s = s + "S2" + lineEnd;
        s = s + "A60,0,0,2,3,2,N,\"00-0000-00\"" + lineEnd;
        s = s + "B8,140,0,UA0,2,3,100,B,\"00000000000\"" + lineEnd;
        s = s + "B10,260,0,3,2,4,50,N,\"00-0000-00\"" + lineEnd;
        s = s + "P1" + OTHER_LINE_END;
        return s.getBytes(Charset.forName("UTF-8"));
    }

    private byte[] getTestStringBytes() {
        String s = "N\r\n";
        s = s + "Q380,24\r\n";
        s = s + "R203,20\r\n";
        s = s + "S2\r\n";
        s = s + "A60,0,0,2,3,2,N,\"00-0000-00\"\r\n";
        s = s + "B8,140,0,UA0,2,3,100,B,\"00000000000\"\r\n";
        s = s + "B10,260,0,3,2,4,50,N,\"00-0000-00\"\r\n";
        s = s + "P1\r\n";
        return s.getBytes();
    }

    private byte[] getScoopedCustomLabelBytes() {
        String lineEnd = OTHER_LINE_END;
        StringBuilder buf = new StringBuilder(123);
        buf.append("<------------------------------Click file, print, select Zebra printer, press print--------------------------------------->").append(lineEnd);

        buf.append("~CC^").append(lineEnd);
        buf.append("^XA^JMA^FS^XZ").append(lineEnd);
        buf.append("^XA^SS,,,1223^FS^XZ").append(lineEnd);
        buf.append("^XA^MNY^FS^XZ").append(lineEnd);
        buf.append("^XA^MMT^FS^XZ").append(lineEnd);
        buf.append("^XA^MD+00^FS^XZ").append(lineEnd);
        buf.append("^XA^PRC^FS^XZ").append(lineEnd);
        buf.append("^XA^IDR:*.GRF^XZ").append(lineEnd);
        buf.append("^XA^IDR:*.*^XZ").append(lineEnd);
        buf.append("^XA^MCY^XZ").append(lineEnd);

        buf.append("^XA^LH0000,0000^FS^PON^FS").append(lineEnd);
        buf.append("^FO0301,0012^GB0000,0190,0004^FS").append(lineEnd);
        buf.append("^FO0016,0016^A0N,0028,0033^FDSUPPLIER:^FS").append(lineEnd);
        buf.append("^FO0319,0020^A0N,0028,0033^FDSHIP TO:^FS").append(lineEnd);
        buf.append("^FO0016,0203^GB0777,0000,0004^FS").append(lineEnd);
        buf.append("^FO0393,0203^GB0000,0265,0004^FS").append(lineEnd);
        buf.append("^FO0020,0213^A0N,0022,0026^FDShip to Postal Code:^FS").append(lineEnd);
        buf.append("^FO0408,0215^A0N,0028,0033^FDCarrier:^FS").append(lineEnd);
        buf.append("^FO0406,0253^A0N,0028,0033^FDPRO #^FS").append(lineEnd);
        buf.append("^FO0406,0303^A0N,0028,0033^FDB/L #^FS").append(lineEnd);

        buf.append("^FO0010,0466^GB0775,0000,0004^FS").append(lineEnd);
        buf.append("^FO0010,0582^GB0773,0000,0004^FS").append(lineEnd);
        buf.append("^FO0026,0514^A0N,0039,0046^FDPO#:^FS").append(lineEnd);
        buf.append("^FO0010,0812^GB0775,0000,0004^FS").append(lineEnd);
        buf.append("^FO0068,0836^A0N,0028,0033^FDSSCC-18^FS").append(lineEnd);
        buf.append("^FO0062,0880^ADN,0036,0020^FD(^FS").append(lineEnd);
        buf.append("^FO0116,0880^ADN,0036,0020^FD)^FS").append(lineEnd);
        buf.append("^ISLB,N^FS^XZ").append(lineEnd);
        buf.append("^XA^MCY^XZ^XA^ILLB^FS").append(lineEnd);
        buf.append("^FO0000,0000^AAN,0000,0000^FD ^FS").append(lineEnd);

        buf.append("^FO0024,0046^ABN,0011,0007^FDOld World Industries, LLC^FS").append(lineEnd);
        buf.append("^FO0024,0074^ABN,0011,0007^FD5000 W 41ST ST (MFG PLANT)^FS").append(lineEnd);

        buf.append("^FO0317,0050^AFN,0026,0013^FDAUTOZONE INC DC #33^FS").append(lineEnd);
        buf.append("^FO0068,0263^A0N,0032,0040^FD(420) 61834^FS").append(lineEnd);

        DateFormat fdfd = new SimpleDateFormat(DATE_DISPLAYPATTERN);
//        FastDateFormat fdfd = FastDateFormat.getInstance(DATE_DISPLAYPATTERN);
        String dayTime = fdfd.format(new Date());
        buf.append("^FO0024,0092^ABN,0011,0007^FD").append(dayTime).append("^FS").append(lineEnd);
        String user = System.getProperty("user.name");
        buf.append("^FO0024,0110^ABN,0011,0007^FD").append("User:").append(user).append("^FS").append(lineEnd);
        buf.append("^FO0024,0150^ABN,0011,0007^FDCICERO, IL 60804-4524^FS").append(lineEnd);
        buf.append("^FO0317,0080^ADN,0018,0010^FD800 N LYNCH SPUR^FS").append(lineEnd);
        buf.append("^FO0317,0156^ADN,0018,0010^FDDANVILLE, IL 61834-9102^FS").append(lineEnd);
        buf.append("FO0520,0217^A0N,0028,0033^FDWLEL^FS").append(lineEnd);
        buf.append("^FO0506,0303^A0N,0028,0033^FD98846000^FS").append(lineEnd);
        buf.append("^FO0060,0303^BY03,3,100^BCN,0140,N,N^FD>;>842061834^FS").append(lineEnd);

        buf.append("^FO0128,0516^A0N,0032,0040^FD33906581^FS").append(lineEnd);
        buf.append("^FO0024,0633^ADN,0018,0010^FDIUM: 36 CS^FS").append(lineEnd);
        buf.append("^FO0024,0709^ADN,0018,0010^FD36 CS of Part: PRA0B3-02^FS").append(lineEnd);
        buf.append("^FO0024,0659^ADN,0018,0010^FDSUM: 216 GAL^FS").append(lineEnd);
        buf.append("^FO0076,0880^ADN,0036,0020^FD00 1 0074804 400000025 7^FS").append(lineEnd);
        buf.append("^FO0058,0920^BY04,3,100^BCN,0263,N,N^FD>;>800100748044000000257^FS").append(lineEnd);
        buf.append("^PQ0002,0000,0000,N^FS^MCN^XZ").append(lineEnd);
        buf.append("<----------------------------------break1--------------------------------------->").append(lineEnd);

        return buf.toString().getBytes(Charset.forName(ZplFileParser.BYTE_CHAR_READER));
    }

    private byte[] getScoopedLabelBytes() {
        String lineEnd = OTHER_LINE_END;
        StringBuilder buf = new StringBuilder(123);
        buf.append("<------------------------------Click file, print, select Zebra printer, press print--------------------------------------->").append(lineEnd);

        buf.append("~CC^").append(lineEnd);
        buf.append("^XA^JMA^FS^XZ").append(lineEnd);
        buf.append("^XA^SS,,,1223^FS^XZ").append(lineEnd);
        buf.append("^XA^MNY^FS^XZ").append(lineEnd);
        buf.append("^XA^MMT^FS^XZ").append(lineEnd);
        buf.append("^XA^MD+00^FS^XZ").append(lineEnd);
        buf.append("^XA^PRC^FS^XZ").append(lineEnd);
        buf.append("^XA^IDR:*.GRF^XZ").append(lineEnd);
        buf.append("^XA^IDR:*.*^XZ").append(lineEnd);
        buf.append("^XA^MCY^XZ").append(lineEnd);

        buf.append("^XA^LH0000,0000^FS^PON^FS").append(lineEnd);
        buf.append("^FO0301,0012^GB0000,0190,0004^FS").append(lineEnd);
        buf.append("^FO0016,0016^A0N,0028,0033^FDSUPPLIER:^FS").append(lineEnd);
        buf.append("^FO0319,0020^A0N,0028,0033^FDSHIP TO:^FS").append(lineEnd);
        buf.append("^FO0016,0203^GB0777,0000,0004^FS").append(lineEnd);
        buf.append("^FO0393,0203^GB0000,0265,0004^FS").append(lineEnd);
        buf.append("^FO0020,0213^A0N,0022,0026^FDShip to Postal Code:^FS").append(lineEnd);
        buf.append("^FO0408,0215^A0N,0028,0033^FDCarrier:^FS").append(lineEnd);
        buf.append("^FO0406,0253^A0N,0028,0033^FDPRO #^FS").append(lineEnd);
        buf.append("^FO0406,0303^A0N,0028,0033^FDB/L #^FS").append(lineEnd);

        buf.append("^FO0010,0466^GB0775,0000,0004^FS").append(lineEnd);
        buf.append("^FO0010,0582^GB0773,0000,0004^FS").append(lineEnd);
        buf.append("^FO0026,0514^A0N,0039,0046^FDPO#:^FS").append(lineEnd);
        buf.append("^FO0010,0812^GB0775,0000,0004^FS").append(lineEnd);
        buf.append("^FO0068,0836^A0N,0028,0033^FDSSCC-18^FS").append(lineEnd);
        buf.append("^FO0062,0880^ADN,0036,0020^FD(^FS").append(lineEnd);
        buf.append("^FO0116,0880^ADN,0036,0020^FD)^FS").append(lineEnd);
        buf.append("^ISLB,N^FS^XZ").append(lineEnd);
        buf.append("^XA^MCY^XZ^XA^ILLB^FS").append(lineEnd);
        buf.append("^FO0000,0000^AAN,0000,0000^FD ^FS").append(lineEnd);

        buf.append("^FO0024,0046^ABN,0011,0007^FDOld World Industries, LLC^FS").append(lineEnd);
        buf.append("^FO0024,0074^ABN,0011,0007^FD5000 W 41ST ST (MFG PLANT)^FS").append(lineEnd);
        buf.append("^FO0317,0050^AFN,0026,0013^FDAUTOZONE INC DC #33^FS").append(lineEnd);
        buf.append("^FO0068,0263^A0N,0032,0040^FD(420) 61834^FS").append(lineEnd);
        buf.append("^FO0024,0150^ABN,0011,0007^FDCICERO, IL 60804-4524^FS").append(lineEnd);
        buf.append("^FO0317,0080^ADN,0018,0010^FD800 N LYNCH SPUR^FS").append(lineEnd);
        buf.append("^FO0317,0156^ADN,0018,0010^FDDANVILLE, IL 61834-9102^FS").append(lineEnd);
        buf.append("FO0520,0217^A0N,0028,0033^FDWLEL^FS").append(lineEnd);
        buf.append("^FO0506,0303^A0N,0028,0033^FD98846000^FS").append(lineEnd);
        buf.append("^FO0060,0303^BY03,3,100^BCN,0140,N,N^FD>;>842061834^FS").append(lineEnd);

        buf.append("^FO0128,0516^A0N,0032,0040^FD33906581^FS").append(lineEnd);
        buf.append("^FO0024,0633^ADN,0018,0010^FDIUM: 36 CS^FS").append(lineEnd);
        buf.append("^FO0024,0709^ADN,0018,0010^FD36 CS of Part: PRA0B3-02^FS").append(lineEnd);
        buf.append("^FO0024,0659^ADN,0018,0010^FDSUM: 216 GAL^FS").append(lineEnd);
        buf.append("^FO0076,0880^ADN,0036,0020^FD00 1 0074804 400000025 7^FS").append(lineEnd);
        buf.append("^FO0058,0920^BY04,3,100^BCN,0263,N,N^FD>;>800100748044000000257^FS").append(lineEnd);
        buf.append("^PQ0002,0000,0000,N^FS^MCN^XZ").append(lineEnd);
        buf.append("<----------------------------------break1--------------------------------------->").append(lineEnd);

        return buf.toString().getBytes(Charset.forName(ZplFileParser.BYTE_CHAR_READER));
    }

    private byte[] getLabelFileBytes(File labelFile) {


        StringBuilder outputBuf = new StringBuilder();
        DataInputStream fis = null;
        OutputStream os =null;
        try {
            fis = new DataInputStream(FileUtils.openInputStream(labelFile));

            byte[] inputBuf = (byte[]) Array.newInstance(byte.class, 4096);
            int cnt = fis.read(inputBuf);
            while(cnt > -1) {
                String s = new String(inputBuf, 0, cnt);
                outputBuf.append(s);
                cnt = fis.read(inputBuf);
            }

            java.util.logging.Logger.getLogger(LabelPrintManagerForm.class.getName()).log(Level.INFO, "label bytes :" + outputBuf.length());

            return outputBuf.toString().getBytes(Charset.forName(ZplFileParser.BYTE_CHAR_READER));
//            return FileUtils.readFileToByteArray(labelFile);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(LabelPrintManagerForm.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
           cleanUpInputStream(fis);
        }
        return outputBuf.toString().getBytes(Charset.forName(ZplFileParser.BYTE_CHAR_READER));
    }

    private void cleanUpInputStream(DataInputStream fis) {
        if (fis != null) {
            try {
                fis.close();

            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(LabelPrintManagerForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


}
