package com.oldworldind.app.gui.zebralabel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 *
 * @since Feb 19, 2013 at 8:37:06 PM
 * @author mcolegrove
 */
public class AboutInfoTest {
    private static final Logger LOG = Logger.getLogger(AboutInfoTest.class);

    @Test
    public void testGetAppName() {

        LOG.info("testGetAppName");
        String result = AboutInfo.getAppName();
        assertNotNull("should get something for appName", result);
        LOG.info(result);
    }

    @Test
    public void testGetRevisionHistory() {

        LOG.info("testGetAppName");
        Map<Integer, String> res = AboutInfo.getRevisionHistory();

        assertNotNull("should get something for update.", res);
        assertFalse("should get map with content for update.", res.isEmpty());
        LOG.info(res);

        for (Map.Entry<Integer, String> entry : res.entrySet()) {
            Object key = entry.getKey();
            String value = entry.getValue();
            LOG.info("Key:" + key + " v:" + value);
        }
    }

}