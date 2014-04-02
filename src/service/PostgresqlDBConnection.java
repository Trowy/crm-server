package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;


public class PostgresqlDBConnection {
	
	private static PostgresqlDBConnection instance = null;
	private static Connection connection = null;
	// TODO ACTIVATE LOGGER
	//private static final Logger log = Logger.getLogger(PostgresqlDBConnection.class);
	
	public static Connection getConnection()
	{
		if(instance == null) 
		{
	        try {
	        	instance = new PostgresqlDBConnection();
	        } catch (Exception e) {
	        	//log.fatal(e.getStackTrace());
	        }
	        assert(instance != null);
        }
        return connection;
    }

    private PostgresqlDBConnection() throws SQLException, ClassNotFoundException 
    {
        try {
        	Class.forName("org.postgresql.Driver");
        	 connection = DriverManager.getConnection(
             		"jdbc:postgresql://localhost:6543/crm",  // url
             		"postgres", 						// user
             		"qweasdzxc123");						// psw
            //log.debug("Connection without errors");
        } catch (SQLException ex) {
            //log.fatal("DBConnection is failed!", ex);
        }
    }
}
