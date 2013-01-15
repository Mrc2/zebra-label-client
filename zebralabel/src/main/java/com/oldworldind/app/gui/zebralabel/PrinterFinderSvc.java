package com.oldworldind.app.gui.zebralabel;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.print.attribute.standard.PrinterName;

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

    String getFirstLabelPrinterName(String nameToMatch) {

        String sPrinterName = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            PrinterMakeAndModel mam = services[i].getAttribute(PrinterMakeAndModel.class);
            sPrinterName = ((PrinterName) attr).getValue();
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
