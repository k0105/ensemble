package de.rwthaachen.ensemble.status;

import java.util.ArrayList;
import java.util.List;

/**
 * NOTE: WORK IN PROGRESS - PROVIDES MOCKUP DATA
 * This class is a prototype of how the JBT backend could provide status information
 */
public class StatusMonitor {
    private final static String REPORT_TTILE = "JoBim Text REST Backend Status Report";

    /**
     * Statūs of the different components (that advanced Latin proficiency exam finally pays off...)
     */
    public enum Status    {ONLINE, OFFLINE, UNKNOWN};

    private Status statusCore      = Status.ONLINE;
    private Status statusDbMySql   = Status.UNKNOWN;
    private Status statusFramework = Status.UNKNOWN;

    private List<String> corpora = new ArrayList<String>();

    /**
     * Constructor
     */
    public StatusMonitor() {
        //TODO Mockup entry
        corpora.add("Wikipedia (Stanford)");
    }

    //<editor-fold desc=".:: Update Methods ::.">
    /**
     * TODO Mockup!!
     * This method will later acquire information about whether the core is fully functional
     */
    public void updateCoreStatus() {
        statusCore = Status.ONLINE;
    }

    /**
     * TODO Mockup!!
     * This method will later determine whether the DB is up and running
     */
    public void updateDbMySqlStatus() {
        statusDbMySql = Status.UNKNOWN;
    }

    /**
     * TODO Mockup!!
     * This method will later determine whether the JBT framework is available
     */
    public void updateFrameworkStatus() {
        statusFramework = Status.UNKNOWN;
    }
    //</editor-fold>

    //<editor-fold desc=".:: Status Conversion ::.">
    /**
     * Converts status into String
     * @param status Status to be converted
     * @return String that contains the status name
     */
    public String statusEnumToString(Status status) {
        if (status.equals(Status.ONLINE)) {
            return "Online";
        } else if (status.equals(Status.UNKNOWN)) {
            return "Unknown";
        } else { // status.equals(Status.OFFLINE)
            return "Offline";
        }
    }

    /**
     * Converts status into bold color-coded HTML tag (online green, unknown orange, offline red)
     * @param status Status to be converted
     * @return HTML tag
     */
    public String statusEnumToStringHtml(Status status) { //<b style="color:green;">online</b>
        String result = "<b style=\"color:";
        if (status.equals(Status.ONLINE)) {
            return result + "green;\">Online</b>";
        } else if (status.equals(Status.UNKNOWN)) {
            return result + "orange;\">Unknown</b>";
        } else { // status.equals(Status.OFFLINE)
            return result + "red;\">Offline</b>";
        }
    }
    //</editor-fold>

    //<editor-fold desc=".:: Getters ::.">
    /**
     * Returns status of the core
     * @return Core status
     */
    public Status getStatusCore() {
        return statusCore;
    }

    /**
     * Returns status of the core as String
     * @return Core status
     */
    public String getStatusCoreString() {
        return "Core: " + statusEnumToString(statusCore);
    }

    /**
     * Returns status of the MySQL/MariaDB database
     * @return DB status
     */
    public Status getStatusDbMySql() {
        return statusDbMySql;
    }

    /**
     * Returns status of the MySQL/MariaDB database as String
     * @return DB status
     */
    public String getStatusDbMySqlString() {
        return "DB (MySQL): " + statusEnumToString(statusDbMySql);
    }

    /**
     * Returns status of the JoBim Text framework
     * @return JBT status
     */
    public Status getStatusFramework() {
        return statusFramework;
    }

    /**
     * Returns status of the JoBim Text framework as String
     * @return JBT status
     */
    public String getStatusFrameworkString() {
        return "Framework: " + statusEnumToString(statusFramework);
    }
    //</editor-fold>

    //<editor-fold desc=".:: Reports ::.">
    /**
     * Prints a report about the statūs of the different components
     * @return Plaintext status report
     */
    public String reportPlaintext() {
        return REPORT_TTILE + "\n" + getStatusCoreString() + "\n" + getStatusDbMySqlString() + "\n" + getStatusFrameworkString();
    }

    /**
     * Prints an HTML report about the statūs of the different components
     * @return HTML status report
     */
    public String reportHtml() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<!DOCTYPE html>\n");
        stringBuilder.append("<html>\n\t<head>\n\t\t<title>" + REPORT_TTILE + "</title>\n\t</head>\n\n\t<body>\n");
        stringBuilder.append("\t\t<h1>" + REPORT_TTILE + "</h1>\n");

        stringBuilder.append("<b>Note: This status report is only a demo and does not contain valid information, yet.</b><br /><br />\n");

        stringBuilder.append("\t\t<h2>Health Report</h2>\n");
        stringBuilder.append("\t\t<table>\n");
        stringBuilder.append("\t\t\t<tr><td><b>Core:</b></td><td>" + statusEnumToStringHtml(statusCore) + "</td></tr>\n");
        stringBuilder.append("\t\t\t<tr><td><b>DB:</b></td><td>" + statusEnumToStringHtml(statusDbMySql) + "</td></tr>\n");
        stringBuilder.append("\t\t\t<tr><td><b>JoBim:</b></td><td>" + statusEnumToStringHtml(statusFramework) + "</td></tr>\n");
        stringBuilder.append("\t\t</table>\n");

        stringBuilder.append("\t\t<h2>Available Corpora</h2>\n");
        stringBuilder.append("\t\t<ol>\n");
        for (String corpus : corpora) {
            stringBuilder.append("\t\t\t<li>");
            stringBuilder.append(corpus);
            stringBuilder.append("</li>\n");
        }
        stringBuilder.append("\t\t</ol>\n");

        stringBuilder.append("\t</body>\n</html>");

        return stringBuilder.toString();
    }

    /**
     * Creates a report about the statūs of the different components as an object (useful for marshalling)
     * @return Object status report
     */
    public HealthReport reportObject() {
        return new HealthReport(statusEnumToString(statusCore), statusEnumToString(statusDbMySql),
                statusEnumToString(statusFramework));
    }

    /**
     * Helper class to marshall status report
     */
    public static class HealthReport{
        String statusCore;
        String statusDb;
        String statusFramework;

        /**
         * Constructor
         */
        public HealthReport(){}

        /**
         * Constructor for HealthReport that sets statūs as Strings
         * @param statusCore Status of core
         * @param statusDb Status of DB
         * @param statusFramework Status of JBT
         */
        public HealthReport(String statusCore, String statusDb, String statusFramework) {
            this.statusCore = statusCore;
            this.statusDb = statusDb;
            this.statusFramework = statusFramework;
        }

        /**
         * Getter for status of core
         * @return Core status
         */
        public String getStatusCore() {
            return statusCore;
        }

        /**
         * Setter for status of core
         * @param statusCore Status of core
         */
        public void setStatusCore(String statusCore) {
            this.statusCore = statusCore;
        }

        /**
         * Getter for status of DB
         * @return DB status
         */
        public String getStatusDb() {
            return statusDb;
        }

        /**
         * Setter for status of DB
         * @param statusDb Status of DB
         */
        public void setStatusDb(String statusDb) {
            this.statusDb = statusDb;
        }

        /**
         * Getter for status of JoBim Text framework
         * @return JBT status
         */
        public String getStatusFramework() {
            return statusFramework;
        }

        /**
         * Setter for status of JoBim Text framework
         * @param statusFramework Status of JBT
         */
        public void setStatusFramework(String statusFramework) {
            this.statusFramework = statusFramework;
        }
    }
    //</editor-fold>
}
