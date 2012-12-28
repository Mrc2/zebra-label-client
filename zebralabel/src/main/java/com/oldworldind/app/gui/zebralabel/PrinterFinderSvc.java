package com.oldworldind.app.gui.zebralabel;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.PrintServiceAttribute;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.print.attribute.standard.PrinterName;

/**
 *
 * @since Dec 28, 2012 at 5:34:19 PM
 * @author mcolegrove 
 */
public class PrinterFinderSvc {

    public String getFirstHomePrinterName() {

        PrintService psZebra = null;
        String sPrinterName = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            PrinterMakeAndModel mam = services[i].getAttribute(PrinterMakeAndModel.class);
            sPrinterName = ((PrinterName) attr).getValue();
            System.out.println("Found printer: " + sPrinterName + "\n");

            System.out.println("printer type: " + mam + "\n");
        if (sPrinterName.indexOf("Officejet") > -1) {
                return sPrinterName;
            }
        }
        System.out.println("Finshed\n");

        return null;
    }

    public String getFirstLabelPrinterName() {

        return getFirstLabelPrinterName("32");
    }

    String getFirstLabelPrinterName(String nameToMatch) {

        PrintService psZebra = null;
        String sPrinterName = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        for (int i = 0; i < services.length; i++) {
            PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
            PrinterMakeAndModel mam = services[i].getAttribute(PrinterMakeAndModel.class);
            sPrinterName = ((PrinterName) attr).getValue();
            System.out.println("Found printer: " + sPrinterName + "\n");

            System.out.println("printer type: " + mam + "\n");
        if (sPrinterName.indexOf(nameToMatch) > -1) {
                return sPrinterName;
            }
        }
        System.out.println("Finshed\n");

        return null;
    }

    PrintService getFirstLabelPrinterServiceNamed(String fullName) {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
			for (int i = 0; i < services.length; i++) {
				PrintServiceAttribute attr = services[i].getAttribute(PrinterName.class);
				String sPrinterName = ((PrinterName)attr).getValue();
				if (sPrinterName.indexOf(fullName) >= 0) {
					return services[i];
				}
			}
            return null;
    }
}
