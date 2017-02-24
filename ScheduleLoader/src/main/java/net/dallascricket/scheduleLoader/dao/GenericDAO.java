package net.dallascricket.scheduleLoader.dao;

import java.sql.Connection;

public class GenericDAO<T>{
	
    //Protected
    protected final String tableName;
    protected Connection con;

    protected GenericDAO(Connection con, String tableName) {
        this.tableName = tableName;
        this.con = con;
    }
	
}