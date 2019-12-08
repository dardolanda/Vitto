
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Testing {
    public static void main(String[] args) {
        
        System.out.println("------------------------- Time testing----------------------------");
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"); 
        Calendar cal = Calendar.getInstance();
        
        System.out.println("cal --> ");
        System.out.println(cal.getTimeInMillis());
        
        Date dateNow = new Date(cal.getTimeInMillis());
        
        Timestamp timeStampNow = new Timestamp(cal.getTimeInMillis());
        
        System.out.print("datenow: ");
        System.out.println(dateNow);
        System.out.print("timeStampNow: ");
        System.out.println(timeStampNow);
        
        System.out.println("------------------------FIN: Time testing----------------------------");
    }
    
    
}
