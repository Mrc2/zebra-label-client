package com.oldworldind.app.gui.zebralabel;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.Logger;

/**
 *
 * @since Dec 29, 2012 at 6:55:29 PM
 * @author mcolegrove
 */
public final class ZplFileParser {
    private static final Logger LOG = Logger.getLogger(ZplFileParser.class);
//    private static final String BYTES_UTF_8 = "UTF-8";
    private static final String BYTES_WINDOWS_WTF = "Cp1252";
    private static final String START_OF_LABEL_CODE = "?XA";
    private static final String END_OF_LABEL_CODE = "?XZ";
    private static String END_OF_LABEL_LINE = "?PQ";
    static {
        if (SystemUtils.IS_OS_WINDOWS_XP) {
            // xp sp3 renders the page code with this with
            END_OF_LABEL_LINE = "?PQ";
        }
        else if (SystemUtils.IS_OS_WINDOWS) {
            END_OF_LABEL_LINE = "¬PQ";
        }
        LOG.info("zipfile parsing for:" + END_OF_LABEL_LINE + ": for end of label");
    }
    public static final String BYTE_CHAR_READER = BYTES_WINDOWS_WTF;
    public static final String LABEL_COUNT_PROPERTY = "label-count";
    public static final String FILE_SIZE_COUNT = "file-size";
    public static final String LABEL_FILE_LASTMODIFIED_DATE = "label-file-updated";

    static Map<String, Object> parse(File file) {

        Map<String, Object> res = new HashMap<String, Object>();
        res.put(FILE_SIZE_COUNT, Long.valueOf(0l));
        res.put(LABEL_COUNT_PROPERTY, Integer.valueOf(0));
        if (file == null) {
            return res;
        }

        LOG.info("starting parse on:" + file);
        long fileSize = FileUtils.sizeOf(file);

        res.put(FILE_SIZE_COUNT, fileSize);
        if (fileSize < 2) {
            return res;
        }
        long bytesRead = 0;
        int pageCount = 0;
        InputStream is = null;
        try {
            if (!file.isFile() || !file.canRead()) {
                return res;
            }

            Date lastModified = new Date(file.lastModified());
            if (lastModified != null) {
                res.put(LABEL_FILE_LASTMODIFIED_DATE, lastModified);
            }
            is = new DataInputStream(new FileInputStream(file));
            byte[] buf = (byte[]) Array.newInstance(byte.class, 4096);
            int bufferRead = is.read(buf);
            while (bufferRead > -1) {
                LOG.info("page read");
                bytesRead = bytesRead + bufferRead;
                String temp = new String(buf, BYTE_CHAR_READER);
                int startLabelPos = temp.indexOf(END_OF_LABEL_LINE);
                int bufferPt = startLabelPos + 1;
                while (bufferPt < bufferRead && startLabelPos > -1) {
                    LOG.info("label found:" + END_OF_LABEL_LINE);
                    pageCount++;
                    startLabelPos = temp.indexOf(END_OF_LABEL_LINE, bufferPt);
                    bufferPt = bufferPt + startLabelPos + 1;
                }
                bufferRead = is.read(buf);
            }
        } catch (FileNotFoundException ex) {
            LOG.error("File not found vs:" + file, ex);
        } catch (IOException ex) {
            LOG.error("Failed id vs:" + file, ex);
        } finally {
            IoUtils.cleanUpInputStream(is);
        }
        LOG.warn("buffer input bytes read:" + bytesRead);
        res.put(LABEL_COUNT_PROPERTY, pageCount);
        return res;
    }
}
