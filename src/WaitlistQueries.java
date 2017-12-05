
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhang
 */
public class WaitlistQueries {
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBZichaoZhang938122098";
    private static final String USERNAME ="java";
    private static final String PASSWORD ="java";
    
    private Connection connection;
    private  static PreparedStatement seletWaitlistByDate;
    private static PreparedStatement  insertWaistlist;
    
    private  static PreparedStatement seletAllWaitlistDate;
    private  static PreparedStatement selectWaitlistByCustomerName;
    private static PreparedStatement deleteWaitlist;
    private static PreparedStatement getWaitlistByFNandDate;
    private static PreparedStatement deleteWaitlistByFN;
    private static PreparedStatement getWaitlistByFN;
    //constructor 
    public  WaitlistQueries()
    {
        try
        {
          connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
         
          seletWaitlistByDate=connection.prepareStatement("SELECT*FROM WAITLIST WHERE DATER = ?");
          insertWaistlist=connection.prepareStatement("INSERT INTO WAITLIST  (FLIGHTNUMBER,NAME,DATER,TIMESTAMP) values (?,?,?,?)");  
          
          seletAllWaitlistDate=connection.prepareStatement("SELECT *FROM WAITLIST");
          selectWaitlistByCustomerName=connection.prepareStatement("SELECT*FROM WAITLIST WHERE NAME = ?");
          deleteWaitlist=connection.prepareStatement("DELETE FROM WAITLIST WHERE NAME = ? and DATER = ?");
          getWaitlistByFNandDate=connection.prepareStatement("SELECT*FROM WAITLIST WHERE FLIGHTNUMBER =? and DATER = ?  ORDER BY TIMESTAMP");
          deleteWaitlistByFN=connection.prepareStatement("DELETE FROM WAITLIST WHERE FLIGHTNUMBER = ?");
          getWaitlistByFN=connection.prepareStatement("SELECT*FROM WAITLIST WHERE FLIGHTNUMBER =?");
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
            
        }
    }
    
    //selet Waitlist by date
    public static List <Waitlist> getWaitlistByDate(Date date)
    {
        List<Waitlist> results= null;
        ResultSet resultSet = null;
        try
        {
            seletWaitlistByDate.setDate(1, date);
            
            resultSet=seletWaitlistByDate.executeQuery();
            results = new ArrayList<Waitlist>();
            while (resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString("NAME"),resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATER"),resultSet.getTimestamp("TIMESTAMP")));
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       return results;
    }      
    //selete Waitlist by flight number 
     public static List <Waitlist> getWaitlistByFN(String flightNum)
    {
        List<Waitlist> results= null;
        ResultSet resultSet = null;
        try
        {
            getWaitlistByFN.setString(1, flightNum);
            
            resultSet=getWaitlistByFN.executeQuery();
            results = new ArrayList<Waitlist>();
            while (resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString("NAME"),resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATER"),resultSet.getTimestamp("TIMESTAMP")));//add Timestamp later
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       return results;
    }      
    
    //add an entry 
    public static int addWaistlist(String flightNum,String customer,Date date,Timestamp timeStamp)
        {
            int result =0;
            try 
            {
                insertWaistlist.setString(1,flightNum);
                insertWaistlist.setString(2,customer);
                insertWaistlist.setDate(3,date);
                insertWaistlist.setTimestamp (4,timeStamp);
                result = insertWaistlist.executeUpdate();
                result++;
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
                        
            }
            return result;
        }
    
    public void close()
    {
        try 
        {
            connection.close();
        }
        catch (SQLException sqlException)
        {
             sqlException.printStackTrace();
        }
    }
    
    //for waitlisy status combo box
    public static List<Date> getAllWaitlistDay()
    {
        List<Date> result = null;
           ResultSet resultSet = null;
          
           try
           {
             resultSet =seletAllWaitlistDate.executeQuery();
             result = new ArrayList<Date>();
             while (resultSet.next())
             {
                 result.add((resultSet.getDate("DATER")));
             }
             
           }
          catch(SQLException sqlException)
          {
            sqlException.printStackTrace(); 
          }
          
           finally
           {
               try
               {
                   resultSet.close();
               }
               catch(SQLException sqlException)
               {
                    sqlException.printStackTrace(); 
               }
           }
           
           return result;
    }
    
    //display name on waitlist 
    public static List <String> getWaitlistName(Date date)
    {
        List<String> results= null;
        ResultSet resultSet = null;
        try
        {
            seletWaitlistByDate.setDate(1, date);
            
            resultSet=seletWaitlistByDate.executeQuery();
            results = new ArrayList<String>();
            while (resultSet.next())
            {
                results.add(resultSet.getString("NAME"));
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       return results;
    }      
    
    //get waitlist by customer name 
    public static List <Booking> getWaitlistCustomerName(String name)
    {
        List<Booking> results= null;
        ResultSet resultSet = null;
        try
        {
            selectWaitlistByCustomerName.setString(1, name);
            
            resultSet=selectWaitlistByCustomerName.executeQuery();
            results = new ArrayList<Booking>();
            while (resultSet.next())
            {
                results.add(new Booking(null,resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATER"),resultSet.getTimestamp("TIMESTAMP")));//add Timestamp later
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }  
    public static int cancelWaitlsit(String name,Date date)
    {
        int result =0;
        try
        {
           deleteWaitlist.setString(1,name);
            deleteWaitlist.setDate(2,date);
            result = deleteWaitlist.executeUpdate();
        }
        catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
    }
    
     public static int cancelWaitlsitByFN(String flightNum)
     {
         int result=0;
         try
         {
             deleteWaitlistByFN.setString(1,flightNum);
             result = deleteWaitlistByFN.executeUpdate();
         }
         catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
     }
      public static List <Waitlist> getWaitlistByFNandDate(String flightNum,Date date)
      {
        List<Waitlist> results= null;
        ResultSet resultSet = null;
        try
        {
            getWaitlistByFNandDate.setString(1, flightNum);
            getWaitlistByFNandDate.setDate(2, date);
            
            resultSet=getWaitlistByFNandDate.executeQuery();
            results = new ArrayList<Waitlist>();
            while (resultSet.next())
            {
                results.add(new Waitlist(resultSet.getString("NAME"),resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATER"),resultSet.getTimestamp("TIMESTAMP")));
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
      }
}
