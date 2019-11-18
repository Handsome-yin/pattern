package com.pattern.factory.sqlhelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class DataBaseCmd {
	
	private PreparedStatement pstmt = null;
	private Connection con = null;
	private ResultSet rs = null;
	private String datasource = null;
	/**
	 *
	 */
	public DataBaseCmd()
	{
		
	}
	/**
	 */
	public DataBaseCmd(String datasource)
	{
		this.datasource = datasource;
	}
	   private synchronized void initCon()
	   {
		   try {
			   if(null == con)
			   {
				   if(null == datasource || "".equals(datasource))
				   {
					   con = ConManager.getConnection();
				   }else
				   {
					   con = ConManager.getConnection(datasource);
				   }
			   }
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
	   }
	
	 /**
	  * @throws Exception
	  */
	 public ResultSet excuteQuery(String sql, boolean cmdtype,List values)throws Exception
	 {
		 try
		 {
			 initCon();
			 if(cmdtype)
			 {
				 pstmt = con.prepareCall(sql);
			 }else
			 {
				 pstmt = con.prepareStatement(sql);
			 }
			 if(values != null && values.size() >0)
			 {
				 setValues(pstmt,values);
			 }
			rs = pstmt.executeQuery();
			return rs;
		 }catch(Exception ex)
		 {
				throw ex; 
		 }
	 }
	
	 /**
	  * @throws Exception
	  */
	 public int excuteUpdate(String sql,boolean cmdtype,List values)throws Exception
	 {
		 int noOfRows = 0;
		 try
		 {
			 initCon();
			 if(cmdtype)
			 {
				 pstmt = con.prepareCall(sql);
			 }else
			 {
				 pstmt = con.prepareStatement(sql);
			 }
			 if(values != null && values.size() >0)
			 {
				 setValues(pstmt,values);
			 }
			 noOfRows = pstmt.executeUpdate();
		 }catch(Exception ex)
		 {
			throw ex; 
		 }
		 return noOfRows; 
	 }
	 

	/**
	 */
	public void closeConnection() {
		try {
			if (null != con && !con.isClosed()) {

				ConManager.closeCon(con);
				con = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 */
	public void closePstmt() {
		if (null != pstmt) {
			try {
				pstmt.close();
				pstmt = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 */
	public void closeResultSet() {
		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}
	
	/**
	 *
	 */
	public void closeAll()
	{
		closePstmt();
		closeResultSet();
		closeConnection();
	}
	 
	 /**
	  * @throws SQLException
	  */
	 private void setValues(PreparedStatement pstmt,List values)throws SQLException
	 {
		 for(int i=0;i<values.size();i++)
		 {
			 Object v = values.get(i);
			 pstmt.setObject(i+1, v);
		 }
	 }
	 
	 /**
	  * @deprecated
	  */
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
}
