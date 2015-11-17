package com.apps.juan.myanalytics;

public class SqlCommandHelper {

    private static final String TABLE_NAME = "Life";

    private static final String TABLE_ROW_ONE = "Action";
    private static final String TABLE_ROW_TWO = "Details";
    private static final String TABLE_ROW_THREE = "Price";

    String tableName, rowOne, rowTwo, rowThree;
    String defaultAction, defaultDetails;
    int defaultPrice;

    public SqlCommandHelper(String tableName, String rowOne, String rowTwo, String rowThree){
        this.tableName = tableName;
        this.rowOne = rowOne;
        this.rowTwo = rowTwo;
        this.rowThree = rowThree;

        this.defaultAction = "''";
        this.defaultDetails = "''";
        this.defaultPrice = 0;
    }

    public SqlCommandHelper(String defaultAction, String defaultDetails, int defaultPrice){
        this.tableName = TABLE_NAME;
        this.rowOne = TABLE_ROW_ONE;
        this.rowTwo = TABLE_ROW_TWO;
        this.rowThree = TABLE_ROW_THREE;

        this.defaultAction = defaultAction;
        this.defaultDetails = defaultDetails;
        this.defaultPrice = defaultPrice;
    }

    public String insertSet(String action, String details, int price) {

        String act, det;
        int pri;

        if (action != null){
            act = action;
        }
        else{
            act = defaultAction;
        }

        if (details != null){
            det = details;
        }
        else {
            det = defaultDetails;
        }
        if (price != 0){
            pri = price;
        }
        else{
            pri = defaultPrice;
        }

        return "INSERT INTO " + tableName
                + " ( " + rowOne + ", "
                + rowTwo + ", "
                + rowThree + " )"
                + " VALUES " + "( "
                + "'" + act + "'" + ", "
                + "'" + det + "'" + ", "
                +  pri + " );";

    }
}
