
import java.sql.Date;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zhang
 */
public class Waitlist {
    private String customer;
    private String flightNum;
    private Date date;
    private Timestamp timeStamp;
     public Waitlist(){}
    //constructor 
    public Waitlist(String customer,String flightNum, Date date,Timestamp timeStamp ){
    setCustomerName(customer);
    setFLightNum(flightNum);
    setDate(date);
    setTimeStamp(timeStamp);
    }
    //begning of setter and getter 
    public void setCustomerName(String customer){
        this.customer=customer;
    }
    
    public  String getCustomer(){
        return customer;
    }
    
    public void setFLightNum(String flightNum) {
        this.flightNum=flightNum;
    }
    
    public String getFlightNum(){
        return flightNum;
    }
    
     public void setDate(Date date) {
        this.date=date;
    }
    
    public Date getDate(){
        return date;
    }

    public void setTimeStamp(Timestamp timeStamp){
        this.timeStamp=timeStamp;
    }

    public Timestamp getTimeStamp(){
        return timeStamp;
    }

//end of setter and getter   
    
    
}
