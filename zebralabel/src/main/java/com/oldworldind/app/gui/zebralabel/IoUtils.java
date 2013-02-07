package com.oldworldind.app.gui.zebralabel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;

import org.apache.log4j.Logger;

/**
 *
 * @since Jan 23, 2013 at 9:08:21 AM
 * @author mcolegrove
 */
public final class IoUtils {
    private static final Logger LOG = Logger.getLogger(IoUtils.class);

    public static boolean cleanUpOutputStream(OutputStream os) {

        if (os == null) {
            LOG.info("no outputStream to close");
            return false;
        }
        try {
            os.flush();
        } catch (IOException ex) {
            LOG.warn("cannot close stream:" + os + " e:" + ex.getLocalizedMessage());
        }

        try {
            os.close();
            return true;
        } catch (IOException ex) {
            LOG.warn("cannot close stream:" + os + " e:" + ex.getLocalizedMessage());
        }
        return false;
    }

    public static boolean cleanUpInputStream(InputStream is) {
        if (is == null) {
            LOG.info("no inputStream to close");
            return false;
        }

        try {
            is.close();
            return true;
        } catch (IOException ex) {
            LOG.warn("cannot close stream:" + is + " e:" + ex.getLocalizedMessage());
        }
        return false;
    }

    public static StringBuilder pipeToBuffer(InputStream is) {

        if (is == null) {
            LOG.info("InputStream was null, no piping!");
            return null;
        }

        byte[] inputBuf = (byte[]) Array.newInstance(byte.class, 4096);
        StringBuilder outputBuf = new StringBuilder();

        try {
            if (is.available() < 1) {
                LOG.info("InputStream was not available, no piping!");
                return null;
            }
            int cnt = is.read(inputBuf);
            while (cnt > -1) {
                String s = new String(inputBuf, 0, cnt);
                outputBuf.append(s);
                cnt = is.read(inputBuf);
            }

            LOG.info("label bytes :" + outputBuf.length());

            return outputBuf;
        } catch (IOException ex) {
            LOG.error("cannot pipe to buffer from inputStream:", ex);
        }

        return null;
    }

    public static boolean pipeToHostPrinter(InputStream is, String hostOrIp) {
        return pipeToHost(is, hostOrIp, 9100);
    }

    public static boolean pipeToHostPrinter(String fileName, String hostOrIp) {
        return pipeToHost(fileName, hostOrIp, 9100);
    }

    public static boolean pipeToHost(InputStream is, String targetHostnameOrIP, int portNumber) {

        if (is == null) {
            LOG.info("InputStream was null, no piping!");
            return false;
        }

        OutputStream os = null;
        try {
            if (is.available() < 1) {
                LOG.info("InputStream was not available, no piping!");
                return false;
            }
            Socket s = new Socket(targetHostnameOrIP, portNumber);
            os = s.getOutputStream();
            pipe(is, os);
            LOG.info("file content piped to host:port <" + targetHostnameOrIP + ":" + portNumber + "> from inputStream:");
            return true;

        } catch (IOException e) {
            LOG.error("cannot pipe to host:port <" + targetHostnameOrIP + ":" + portNumber + "> from inputStream:", e);
        } finally {
            cleanUpOutputStream(os);
        }
        return false;
    }

    static boolean pingServer(final String serverAndProtocol, int port, int myTime) {
        Socket sock = null;
        int timeToTryReach = 10;
        if (myTime > 0 && myTime < 61) {
            timeToTryReach = myTime;
        }
        try {
            sock = new Socket(serverAndProtocol, port);

            InetAddress addr = sock.getInetAddress();

            boolean reachableInTime =  addr.isReachable(timeToTryReach);
            LOG.info("connected to:" + addr  + " resp:" + reachableInTime + " in seconds:" + timeToTryReach);
            return reachableInTime;
        } catch (final MalformedURLException e1) {
            LOG.error("Bad URL, Cannot connect to:" + serverAndProtocol + " port:" + port, e1);
        } catch (final IOException e) {
            LOG.error("Cannot connect to:" + serverAndProtocol + " port:" + port, e);
        } finally {
            cleanupSocket(sock);
        }
        return false;
    }

    private static boolean cleanupSocket(Socket is) {
        if (is == null) {
            LOG.info("no Socket to close");
            return false;
        }

        try {
            is.close();
            return true;
        } catch (IOException ex) {
            LOG.warn("cannot close Socket:" + is + " e:" + ex.getLocalizedMessage());
        }
        return false;
    }

    private IoUtils() {
        // no instance allowed
    }

    public static boolean pipeToHost(String inputFileName, String targetHostnameOrIP, int portNumber) {
        File file = new File(inputFileName);
        if (!file.exists()) {
            LOG.info("file not found:" + inputFileName);
            return false;
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            boolean result = pipeToHost(fis, targetHostnameOrIP, portNumber);
            LOG.info("file content result:" + result + " when piped to host:port <" + targetHostnameOrIP + ":" + portNumber + "> from file:" + inputFileName);
            return result;

        } catch (IOException e) {
            LOG.error("cannot pipe to host:port <" + targetHostnameOrIP + ":" + portNumber + "> from file:" + inputFileName, e);
        } finally {
            cleanUpInputStream(fis);
        }
        return false;
    }

    public static boolean pipe(InputStream in, OutputStream out) throws IOException {
        byte[] inputBuf = (byte[]) Array.newInstance(byte.class, 4096);
        boolean bytesPiped = false;
        int cnt = in.read(inputBuf);
        while (cnt > -1) {
            out.write(inputBuf, 0, cnt);
            bytesPiped = true;
            cnt = in.read(inputBuf);
        }
        return bytesPiped;
    }
}
