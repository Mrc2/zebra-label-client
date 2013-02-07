package com.oldworldind.app.gui.zebralabel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

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

        int posBeforePort = nameToMatch.indexOf(':');
        int posBeforeLastColon = nameToMatch.lastIndexOf(':');

        int iPort = getPort(nameToMatch);
        if (posBeforePort != posBeforeLastColon) {
            LOG.info("there can only be 1 colon to indicate port found:" + posBeforePort + " and:" + posBeforeLastColon);
            return false;
        }
        if (iPort < 0) {
            LOG.info("invalid value for port  at:" + posBeforePort + " from:" + nameToMatch);
            return false;
        }

        return true;
    }

    public boolean isServicePingable(String serviceforsocketandPort, int timeInSeconds) {
        if (!isNameIpStyle(serviceforsocketandPort)) {
            return false;
        }

        int posBeforePort = serviceforsocketandPort.indexOf(':');
        int iPort = getPort(serviceforsocketandPort);
        String server = serviceforsocketandPort.substring(0, posBeforePort);
        return IoUtils.pingServer(server, iPort, timeInSeconds);
    }

    public URL getPrinterUrl(String nameProvided) {

        String fullUrlPath = "http://" + nameProvided;
        try {
            return new URL(fullUrlPath);
        } catch (MalformedURLException ex) {
            LOG.error("no url valid vs:" + fullUrlPath, ex);
        }
        return null;
    }

    public int getPort(String nameToMatch) {
        if (StringUtils.isBlank(nameToMatch)) {
            return -1;
        }

        int posBeforePort = nameToMatch.indexOf(':');
        int posBeforeLastColon = nameToMatch.lastIndexOf(':');

        if (posBeforePort < 0) {
            LOG.info("port not provided");
            return -1;
        }
        if (posBeforePort != posBeforeLastColon) {
            LOG.info("there can only be 1 colon to indicate port found:" + posBeforePort + " and:" + posBeforeLastColon);
            return -1;
        }
        String port = nameToMatch.substring(posBeforePort + 1);
        if (!Pattern.matches("[0-9]+", port)) {
            LOG.info("invalid value for port found:" + port + ": at:" + posBeforePort + " from:" + nameToMatch);
            return -1;
        }
        int iPort = NumberUtils.toInt(port, -1);
        if (port.isEmpty() || port.length() < 1 || port.length() > 8 || !NumberUtils.isDigits(port) || iPort < 0) {
            LOG.info("invalid value for port found:" + port + ": at:" + posBeforePort + " from:" + nameToMatch);
            return -1;
        }
        return iPort;
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
