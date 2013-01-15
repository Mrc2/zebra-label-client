package com.oldworldind.app.gui.zebralabel;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JRadioButton;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.log4j.Logger;

/**
 *
 * @since Dec 29, 2012 at 6:30:01 PM
 * @author mcolegrove 
 */
class LabelDataModel extends DefaultTableModel {
    private static final Logger LOG = Logger.getLogger(LabelDataModel.class);
    private static final long serialVersionUID = 4183629661904518943L;
    public static final String COL_SEL = "Sel";
    public static final String COL_FILE = "File";
    public static final String COL_LASTMODIFIED = "Last Modified";
    public static final String COL_ZPL_PATTERN = "txt / zpl";
    public static final String COL_LINE_COUNT = "Line Count";
    public static final String COL_PAGE_COUNT = "Page Count";
    public static final String COL_PRINTED_ON = "Printed Job Date";
    private static final String DATE_DISPLAYPATTERN = "MM/dd/yy @HH:mm:ss";
    private final String[] columnNames;
    private int ccount = 0;

    public LabelDataModel() {
        Properties batColProps = new Properties();
        batColProps.put(COL_SEL, Integer.valueOf(ccount++));
        batColProps.put(COL_FILE, Integer.valueOf(ccount++));
        batColProps.put(COL_LASTMODIFIED, Integer.valueOf(ccount++));
        batColProps.put(COL_ZPL_PATTERN, Integer.valueOf(ccount++));
        batColProps.put(COL_LINE_COUNT, Integer.valueOf(ccount++));
        batColProps.put(COL_PAGE_COUNT, Integer.valueOf(ccount++));
        batColProps.put(COL_PRINTED_ON, Integer.valueOf(ccount++));

        LOG.info(" col count:" + ccount);
        columnNames = (String[]) Array.newInstance(String.class, ccount);

        for (Iterator<String> it = batColProps.stringPropertyNames().iterator(); it.hasNext();) {
            String propName = it.next();
            columnNames[(Integer) batColProps.get(propName)] = propName;
            addColumn(propName);
        }
        for (Iterator<String> it = batColProps.stringPropertyNames().iterator(); it.hasNext();) {
            String propName = it.next();
            addColumn(propName);
        }
    }

    public boolean addFileInfo(File file, String zplPattern, long entries, long labelCount, Date lastPrinted) {
        LOG.info("add info:" + file);
        FastDateFormat fmt = FastDateFormat.getInstance(DATE_DISPLAYPATTERN);
        JRadioButton colSelect = new JRadioButton("");
        String batchDate = getFormattedDate(fmt, lastPrinted);
        addRow(new Object[]{colSelect, file.getName(), fmt.format(file.lastModified()), zplPattern,
                Long.toString(entries), Long.toString(labelCount), batchDate});
        return true;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return ccount;
    }

    private String getFormattedDate(FastDateFormat fdfd, Date lastPrinted) {
        if (lastPrinted == null) {
            return "N/A";

        }
        return fdfd.format(lastPrinted);
    }
}