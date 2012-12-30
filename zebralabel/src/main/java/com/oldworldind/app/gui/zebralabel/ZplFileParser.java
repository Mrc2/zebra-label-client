package com.oldworldind.app.gui.zebralabel;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

/**
 *
 * @since Dec 29, 2012 at 6:55:29 PM
 * @author mcolegrove 
 */
public final class ZplFileParser {
    private static final String BYTES_UTF_8 = "UTF-8";
    private static final String BYTES_WINDOWS_WTF = "Cp1252";
    public static final String BYTE_CHAR_READER = BYTES_WINDOWS_WTF;
    static Map<String, Object> parse(File file) {
        Map<String, Object> res = new HashMap<String, Object>();
        if (file == null || FileUtils.sizeOf(file) < 2) {
            return res;
        }

        long bytesRead = 0;
        int pageCount = 0;
        InputStream is = null;
        try {
            if (!file.isFile() || !file.canRead()) {
                res.put("page-count", pageCount);
                return res;
            }
            is = new DataInputStream(new FileInputStream(file));
            byte[] buf = (byte[]) Array.newInstance(byte.class, 4096);
            int bufferRead = is.read(buf);
            while (bufferRead > -1) {
                bytesRead = bytesRead + bufferRead;
                String temp = new String(buf, BYTE_CHAR_READER);
                int startLabelPos = temp.indexOf("^XA");
                while (startLabelPos > -1) {
                    pageCount++;
                    startLabelPos = temp.indexOf("^XA", startLabelPos);
                }
                bufferRead = is.read(buf);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ZplFileParser.class.getName()).log(Level.WARNING, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ZplFileParser.class.getName()).log(Level.WARNING, null, ex);
        } finally {
            cleanup(is);
        }
        Logger.getLogger(ZplFileParser.class.getName()).log(Level.WARNING, "buffer input bytes read:" + bytesRead);
        res.put("page-count", pageCount);
        return res;
    }

    private static boolean cleanup(InputStream is) {
        if (is == null) {
            return false;
        }
        try {
            is.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ZplFileParser.class.getName()).log(Level.INFO, null, ex);
        }
        return false;
    }
}
