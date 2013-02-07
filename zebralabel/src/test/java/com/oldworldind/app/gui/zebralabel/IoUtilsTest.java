package com.oldworldind.app.gui.zebralabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @since Feb 4, 2013 at 11:01:07 PM
 * @author mcolegrove
 */
public class IoUtilsTest {
    private static final Logger LOG = Logger.getLogger(IoUtilsTest.class);
    private static final String TESTPRINTSERVER = "172.16.2.32";

    /**
     * Test of pingServer method, of class IoUtils.
     */
    @Test(timeout = 3000L)
    public void testPingServer() {
        LOG.info("pingServer");
        String serverAndProtocol = "192.168.1.73";
        int printServicePort = 9100;

//        boolean result = IoUtils.pingServer(serverAndProtocol, printServicePort);

        Boolean result = IoUtils.pingServer(TESTPRINTSERVER, printServicePort, 20);
        assertNotNull("must have a result from ping try on:" + serverAndProtocol, result);
        LOG.warn("port:" + printServicePort + " on ping yields:" + result + " vs:" + TESTPRINTSERVER);
    }

    /**
     * Test of pipe method, of class IoUtils.
     */
    @Test
    public void testPipe() {
        LOG.info("pipe");
        String lineEnd = "\n";
        StringBuilder buf = new StringBuilder(123);
        buf.append("<-------------4x6 TEST Label---------------Click file, print, select Zebra printer, press print--------------------------------------->").append(lineEnd);

        buf.append("~CC^").append(lineEnd);
        buf.append("^XA^JMA^FS^XZ").append(lineEnd);
        buf.append("^XA^SS,,,1223^FS^XZ").append(lineEnd);
        buf.append("^XA^MNY^FS^XZ").append(lineEnd);
        InputStream in = null;
        ByteArrayOutputStream baos = null;
        try {

            in = new ByteArrayInputStream(buf.toString().getBytes());
            baos = new ByteArrayOutputStream();
            boolean didPipeAll = IoUtils.pipe(in, baos);
            assertTrue("if good bytes piped to os:", didPipeAll);
            String result = baos.toString();

            int l1 = buf.toString().length();
            int l2 = result.length();


            assertEquals("output values sb same", l1, l2);

            assertTrue("output values sb equal", buf.toString().equals(result));

            LOG.info("input buffer length:" + l1);
            LOG.info("input buffer data:" + buf.toString() + ":");

            LOG.info("output buffer length:" + l2);
            LOG.info("output buffer data:" + result + ":");

        } catch (IOException ex) {
            LOG.error(ex, ex);

        } finally {
            IoUtils.cleanUpOutputStream(baos);
            IoUtils.cleanUpInputStream(in);
        }
    }
}