package com.oldworldind.app.gui.zebralabel;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @since Feb 19, 2013 at 6:13:47 PM
 * @author mcolegrove
 */
public class AboutInfo {
    private static final Logger LOG = LogManager.getLogger(AboutInfo.class);
    private static final String RESOURCES_BUNDLE = "com/oldworldind/app/gui/zebralabel/zebralabelprt";

    public static String getAppName() {
        return getProperty("appname");
    }

    private static String getProperty(String key, String object) {
        ResourceBundle props = ResourceBundle.getBundle(RESOURCES_BUNDLE);
        if (props.containsKey(key)) {
            return props.getString(key);
        }
        return object;
    }

    private static String getProperty(String key) {
        return getProperty(key, "****" + key + ":not found ****");
    }

    public static String getSupportCompany() {
        return getProperty("supportcompany");
    }

    public static String getVersion() {
        return getProperty("version");
    }

    public static String getReleease() {
        return getProperty("release");
    }

    public static String getTag() {
        return getProperty("tag");
    }

    public static String getHelpphone() {
        return getProperty("helpphone");
    }

    public static String getHelpEmail() {
        return getProperty("helpemail");
    }

    public static SortedMap<Integer, String> getRevisionHistory() {

        SortedMap<Integer, String> map = getOrderedMap(false);
        NumberFormat fmt = new DecimalFormat("#00");

        int el = 0;
        String key = "update." + fmt.format(el);
        LOG.info("lookup for k:" + key);
        String element = getProperty(key, null);
        while (element != null) {
            map.put(el, element);

            el++;
            key = "update." + fmt.format(el);
            element = getProperty(key, null);
        }

        return map;
    }

    public static SortedMap<Integer, String> getRoadMap() {
        SortedMap<Integer, String> map = getOrderedMap(true);
        NumberFormat fmt = new DecimalFormat("#00");


        int el = 0;
        String key = "roadmap." + fmt.format(el);
        LOG.info("lookup for k:" + key);
        String element = getProperty(key, null);
        while (element != null) {
            map.put(el, element);

            el++;
            key = "roadmap." + fmt.format(el);
            element = getProperty(key, null);
        }

        return map;
    }

    private static SortedMap<Integer, String> getOrderedMap(boolean ascending) {
        if (ascending) {
            SortedMap<Integer, String> map = new TreeMap<>(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 == null && o2 == null) {
                        return 0;
                    }
                    if (o1 == null) {
                        return -1;
                    }

                    if (o2 == null) {
                        return 1;
                    }
                    return 1 * o1.compareTo(o2);
                }
            });

            return map;
        }

        SortedMap<Integer, String> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }

                if (o2 == null) {
                    return -1;
                }
                return -1 * o1.compareTo(o2);
            }
        });

        return map;
    }
}
