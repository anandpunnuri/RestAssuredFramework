package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.reflect.*;

public class DBUtility {
	
	public static Connection con;
	public static Statement stmt;
	public static ResultSet rs;
	
	public static void getDBConnection() throws ClassNotFoundException, SQLException
	{
		try
		{
	Class.forName("com.mysql.jdbc.Driver");  
	con=DriverManager.getConnection("jdbc:mysql://localhost:3306/site202asp","59001","!A8x559M:MedQa)-G4=y"); 
	System.out.println("SQL Driver is: "+con.getClass()+". Is connection valid?: "+con.isValid(60));
	
	stmt=con.createStatement();
		}
		catch(ClassNotFoundException cnf)
		{
			System.out.println("Class not found exception while loading the mysql driver class: "+cnf.getMessage());
		}
		catch(SQLException sqle)
		{
			System.out.println("SQL exception while getDBConnection: "+sqle.getMessage());
		}
	//here sonoo is database name, root is username and password  
//	Statement stmt=con.createStatement();  
//	ResultSet rs=stmt.executeQuery("select * from emp"); 
	}
	
	public static String[] getContentTypeDetails(String item) throws ClassNotFoundException, SQLException
	{
		getDBConnection();
		String[] CTdetails = new String[3];
		try {
			rs = stmt.executeQuery("select c.RECORDID, c.REFERENCEKEY, cr.NAME from CONTENTCHANNEL c, CONTENTCHANNELRESOURCE cr where cr.NAME ='"+item+"' and c.RECORDID=cr.CONTENTCHANNELID");
			while(rs.next())
			{
				for(int i=0,j=1;i<3;i++,j++)
				{
					CTdetails[i]=rs.getString(j);
				}
			} 
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return CTdetails;
	}
	
	public static String[] getViewDetails(String item) throws SQLException
	{
		String[] Viewdetails = new String[3];
		stmt=con.createStatement();
		rs = stmt.executeQuery("select s.RECORDID, s.REFERENCEKEY, sr.NAME from SITE s, SITERESOURCE sr where sr.NAME='"+item+"' and s.RECORDID=sr.SITEID");
		while(rs.next())
		{
			for(int i=0,j=1;i<3;i++,j++)
			{
				Viewdetails[i]=rs.getString(j);
			}
		}
		return Viewdetails;
	}
	
}
