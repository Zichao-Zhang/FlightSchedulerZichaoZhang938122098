/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhang
 */
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

public class BookingQueries 
{
    private static final String URL = "jdbc:derby://localhost:1527/FlightSchedulerDBZichaoZhang938122098 ";
    private static final String USERNAME ="java";
    private static final String PASSWORD ="java";
    
    private Connection connection;
    private static PreparedStatement getBookedSeat;
    private static PreparedStatement insertBooking;
    private static PreparedStatement getTotalSeat;
    
    private  static PreparedStatement seletAllBookingDate;
    private  static PreparedStatement seletAllBookingFN;
    private static PreparedStatement selectAllBookingByDateFN;// for status display
    private static PreparedStatement selectBooingByCustomerName;
    private static PreparedStatement deleteBooking;
    private static PreparedStatement selectFNByNameDate;
    private static PreparedStatement selectAllBookingByFN;
    private static PreparedStatement deleteBookingByFN;
    //constructor 
    public BookingQueries ()
    {
        try
        {
          connection=DriverManager.getConnection(URL,USERNAME,PASSWORD);
          insertBooking=connection.prepareStatement("INSERT INTO BOOKING (FLIGHTNUMBER,NAME,DATE,TIMESTAMP) values (?,?,?,?)"); 
          getBookedSeat= connection.prepareStatement("select count(FLIGHTNUMBER) from BOOKING where FLIGHTNUMBER = ? and DATE = ?"); 
          getTotalSeat=connection.prepareStatement("SELECT*FROM FLIGHTS WHERE NAME = ?");
          selectBooingByCustomerName=connection.prepareStatement("SELECT*FROM BOOKING WHERE NAME = ?");
          selectFNByNameDate=connection.prepareStatement("SELECT*FROM BOOKING WHERE NAME = ?and DATE = ?");
          seletAllBookingDate=connection.prepareStatement("SELECT *FROM BOOKING");
          seletAllBookingFN=connection.prepareStatement("SELECT *FROM BOOKING");
          selectAllBookingByDateFN=connection.prepareStatement("SELECT*FROM BOOKING WHERE FLIGHTNUMBER = ? and DATE = ?");
          selectAllBookingByFN=connection.prepareStatement("SELECT*FROM BOOKING WHERE FLIGHTNUMBER = ? ORDER BY TIMESTAMP");
          
          deleteBooking=connection.prepareStatement("DELETE FROM BOOKING WHERE NAME = ? and DATE = ? ");
          deleteBookingByFN=connection.prepareStatement("DELETE FROM BOOKING WHERE FLIGHTNUMBER = ? ");
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
            System.exit(1);
            
        }
    }
    
    //selet BOOKING by Customer Name
    public static List <Booking> getBookingCustomerName(String name)
    {
        List<Booking> results= null;
        ResultSet resultSet = null;
        try
        {
            selectBooingByCustomerName.setString(1, name);
            
            resultSet=selectBooingByCustomerName.executeQuery();
            results = new ArrayList<Booking>();
            while (resultSet.next())
            {
                results.add(new Booking(null,resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATE"),resultSet.getTimestamp("TIMESTAMP")));//add Timestamp later
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }   
    
    //get cancelled flight number
    public static  List <String> getCanceledFN(String name,Date date)
    {
       
        ResultSet resultSet = null;
        List<String> results= null;
        try
        {
            selectFNByNameDate.setString(1,name);
            selectFNByNameDate.setDate(2,date);
            resultSet=selectFNByNameDate.executeQuery();
            results = new ArrayList<String>();
            while (resultSet.next())
            {
                results.add(resultSet.getString("FLIGHTNUMBER"));
            }    
           
            
        }
             catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
    }
    
    
    
    
    //read booking table get seat
    public static int getFLightSeats(String flight,Date date)
    {
        int count = 0;
        try{
        getBookedSeat.setString(1, flight); 
        getBookedSeat.setDate(2, date); 
        ResultSet resultSet;
        resultSet = getBookedSeat.executeQuery(); 
        resultSet.next(); 
        count = resultSet.getInt(1);
       
        }
        catch(SQLException sqlException)
        {
          sqlException.printStackTrace();
        } 
        return count;
    }    
    
    //get total seat of a flight 
    public static int getAvailableSeat(String flight)
    {
        int count = 0;
        try{
            getTotalSeat.setString(1,flight);
            ResultSet resultSet;
            resultSet = getTotalSeat.executeQuery(); 
            resultSet.next(); 
            count = resultSet.getInt(2);//ask hear 
        }
        catch(SQLException sqlException)
        {
          sqlException.printStackTrace();
        } 
        return count;
    }
    
    
    //add an entry 
    public static int addBooking(String flightNum,String customer,Date date,Timestamp timestamp)
        {
            int result = 0;
            try 
            {
                insertBooking.setString(1,flightNum);
                insertBooking.setString(2,customer);
                insertBooking.setDate(3,date);
                insertBooking.setTimestamp(4,timestamp);
                
                result = insertBooking.executeUpdate();  
            }
            catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
        }
    
   
    //customer cancle bookig
    public static int cancelBooking(String name,Date date)
    {
        int result =0;
        try
        {
           deleteBooking.setString(1,name);
            deleteBooking.setDate(2,date);
            result = deleteBooking.executeUpdate();
        }
        catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
    }
    
    //cancel booking of droped flight
     public static int cancelBookingByFN(String flightNum)
    {
        int result =0;
        try
        {
           deleteBookingByFN.setString(1,flightNum);
            
            result = deleteBookingByFN.executeUpdate();
        }
        catch(SQLException sqlException)
            {
                sqlException.printStackTrace();
               
            }
            return result;
    }
    
    //get date for booking status combo box
    public static List<Date> getAllBookingDay()
    {
        List<Date> result = null;
           ResultSet resultSet = null;
          
           try
           {
             resultSet =seletAllBookingDate.executeQuery();
             result = new ArrayList<Date>();
             while (resultSet.next())
             {
                 result.add((resultSet.getDate("DATE")));
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
    
    //get date for booking status combo box
    public static List<String> getAllBookingFN()
    {
        List<String> result = null;
           ResultSet resultSet = null;
          
           try
           {
             resultSet =seletAllBookingFN.executeQuery();
             result = new ArrayList<String>();
             while (resultSet.next())
             {
                 result.add((resultSet.getString("FLIGHTNUMBER")));
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
    //get all booked name by FN and date 
    public static List <String> getBookedName(String flightNum,Date date)
    {
        List<String> results= null;
        ResultSet resultSet = null;
        try
        {
            selectAllBookingByDateFN.setString(1, flightNum);
             selectAllBookingByDateFN.setDate(2, date);
            resultSet=selectAllBookingByDateFN.executeQuery();
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
    
     public static List <Booking> getBookingByFN(String flightNum)
     {
          List<Booking> results= null;
        ResultSet resultSet = null;
        try
        {
            selectAllBookingByFN.setString(1, flightNum);
            
            resultSet=selectAllBookingByFN.executeQuery();
            results = new ArrayList<Booking>();
            while (resultSet.next())
            {
                results.add(new Booking(resultSet.getString("NAME"),resultSet.getString("FLIGHTNUMBER"),resultSet.getDate("DATE"),resultSet.getTimestamp("TIMESTAMP")));
            }    
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return results;
     }
    
    
    
    /*
     public int getAvailableSeat (String flightNum,String date){
         try
        {
            getAvailableSeat.setString(1, flightNum);
            getAvailableSeat.setString(3, date);
            
            resultSet=selectBooingByFlightNum.executeQuery();
            results = new ArrayList<Booking>();
            while (resultSet.next())
            {
                results.add(new Booking(resultSet.getString("FLIGHTNUMBER"),resultSet.getString("NAME"),resultSet.getString("DATE")));//add Timestamp later
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
                //close();
            }
            return results;
         
     }*/
    
}

