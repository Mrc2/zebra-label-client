package com.oldworldind.app.gui.zebralabel;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.print.attribute.standard.PrinterName;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;

/**
 *
 * @since Dec 28, 2012 at 5:34:19 PM
 * @author mcolegrove
 */
public class PrinterFinderSvc {
    private static final Logger LOG = Logger.getLogger(PrinterFinderSvc.class);

    public String getFirstLabelPrinterName() {
        return getFirstLabelPrinterName("Zebra");
    }

    public boolean isNameIpStyle(String nameToMatch) {
        if (StringUtils.isBlank(nameToMatch)) {
            return false;
        }

        int posToLeftOfPort = nameToMatch.indexOf(':');
        int lastToLeftOfPort = nameToMatch.lastIndexOf(':');

        if (posToLeftOfPort < 0) {
            LOG.info("port not provided");
            return false;
        }
        if (posToLeftOfPort != lastToLeftOfPort) {
            LOG.info("there can only be 1 colon to indicate port found:" + posToLeftOfPort + " and:" + lastToLeftOfPort);
            return false;
        }
        String port = nameToMatch.substring(posToLeftOfPort+1);
        int iPort = NumberUtils.toInt(port, -1);
        if (port.isEmpty() || port.length() < 1 || port.length() > 8 || !NumberUtils.isDigits(port) || iPort < 0) {
            LOG.info("invalid value for port found:" + port + ": at:" + posToLeftOfPort + " from:" + nameToMatch);
            return false;
        }

        // #FIXME mcc 2013-02-01 - Update this as soon - enable open a ping to the server and port to verify something is
        //listening here

        return false;
    }

    String getFirstLabelPrinterName(String nameToMatch) {

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            PrinterMakeAndModel mam = services[i].getAttribute(PrinterMakeAndModel.class);
            String sPrinterName = ((PrinterName) attr).getValue();
            LOG.info("Found printer: " + sPrinterName + "\n");
            LOG.info("printer type: " + mam + "\n");
            if (sPrinterName.indexOf(nameToMatch) > -1) {
                return sPrinterName;
            }
        }
        LOG.info("Finshed\n");

        return null;
    }

    PrintService getFirstLabelPrinterServiceNamed(String fullName) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            String sPrinterName = ((PrinterName) attr).getValue();
            if (sPrinterName.indexOf(fullName) >= 0) {
                return services[i];
            }
        }
        return null;
    }
}
