package com.pattern.factory.sqlhelper;


import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 */
public class ConManager {
	
	private static Pool dbPool;
	
	private static ConManager instance = null;// ��������
	

	/**
	 */
	private ConManager() {
	}
	/**
	 */
	protected static void closeCon(Connection con) {
			dbPool.freeConnection(con);
	}

	/**
	 */
	private static ConManager getInstance() {
		if (null == instance) {
			instance = new ConManager();
		}
		return instance;
	}

	/**
	 * @return
	 */
	protected static Connection getConnection()
	{
		Connection conn = null;
		try
		{
			if(dbPool == null)
			{
				dbPool = DBConnectionPool.getInstance();
			}
			conn = dbPool.getConnection();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}
	
	/**
	 * @return
	 */
	protected static Connection getConnection(String lookupStr) {
		Connection conn = null;
		try {
			ConManager.getInstance();
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup(lookupStr);
			conn = ds.getConnection();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return conn;
	}

}
