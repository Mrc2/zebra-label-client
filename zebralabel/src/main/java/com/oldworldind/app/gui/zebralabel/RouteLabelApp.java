package com.oldworldind.app.gui.zebralabel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * @since Dec 15, 2012 at 1:26:44 AM
 * @author mcolegrove
 */
public class RouteLabelApp
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        displayUsage();
        System.out.println( "Hello World!" );
    }


    /**
     * @TODO - Provide a brief description of the required and optional arguments for this application
     *
     */
    private static void displayUsage() {
        System.out.println("RouteLabel requires parameters see below");
        System.out.println("java RouteLabel <input File to Route>  <device>");
    }
}
