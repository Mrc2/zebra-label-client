package com.oldworldind.app.gui.zebralabel;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @since Sep 18, 2013 at 6:56:43 PM
 * @author mcolegrove
 */
public class LabelPrintManagerFormTest {
    private boolean doCleanup = true;
    private File sample4x6 = null;
    private File sample2x4 = null;
    private File calilabel = null;

    @Before
    public void doSetup() {
        sample4x6 = new File(FileUtils.getTempDirectory(),
            "sample4x6_" + RandomStringUtils.random(4, true, true) + "_zpl.txt");
        doHoldLabelSpace(sample4x6);

        sample2x4 = new File(FileUtils.getTempDirectory(),
            "sample2x4_" + RandomStringUtils.random(4, true, true) + "_zpl.txt");
        doHoldLabelSpace(sample2x4);

        calilabel = new File(FileUtils.getTempDirectory(),
            "sampleCalibrate_" + RandomStringUtils.random(4, true, true) + "_zpl.txt");
        doHoldLabelSpace(calilabel);
    }

    @Test
    public void testGetLabelSampleBytes() {

        LabelPrintManagerForm manager = LabelPrintManagerForm.getInstance();


        byte[] demo46 = manager.get4x6CustomLabelBytes();
        assertNotNull(demo46);

        try {
            FileUtils.writeByteArrayToFile(sample4x6, demo46);
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.WARNING,
                "Temp File written:" + sample4x6);
        } catch (IOException ex) {
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.SEVERE, null, ex);
            doCleanup = false;
        }

        byte[] demo24 = manager.get2x4CustomLabelBytes();
        assertNotNull(demo24);

        try {
            String labelContent = new String(demo24);
            FileUtils.writeStringToFile(sample2x4, labelContent);
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.WARNING,
                "Temp File written:" + sample2x4);
        } catch (Exception ex) {
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.SEVERE, null, ex);
            doCleanup = false;
            fail("did not set sample to file:" + sample2x4);
        }

        byte[] democali = manager.getPrinterCalibrateBytes();
        assertNotNull(democali);

        try {
            FileUtils.writeByteArrayToFile(calilabel, democali);
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.WARNING,
                "Temp File written:" + calilabel);
        } catch (IOException ex) {
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.SEVERE, null, ex);
            doCleanup = false;
        }


    }

    @After
    public void doTeardown() {
        if (doCleanup && sample4x6 != null) {
            FileUtils.deleteQuietly(sample4x6);
        }

        if (doCleanup && sample2x4 != null) {
            FileUtils.deleteQuietly(sample2x4);
        }

        if (doCleanup && calilabel != null) {
            FileUtils.deleteQuietly(calilabel);
        }
    }

    private void doHoldLabelSpace(File fileToHold) {
        try {
            FileUtils.touch(fileToHold);
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.WARNING,
                "Temp File created:" + fileToHold.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(LabelPrintManagerFormTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}