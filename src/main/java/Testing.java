
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Testing {
    public static void main(String[] args) {
        /*
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
        
        String name = "Christian_Decarli";
        System.out.print(name.split("_")[0] + "    "); 
        System.out.print(name.split("_")[1]);
        */
        
        String porcentage = "65%";
        System.out.println(porcentage.split("%")[0]);
        
        
        //double pago = 100.00;
        //double porcentage = (pago * 5) / 100;
        //System.out.println(pago - porcentage);
        
        int a  = 2;
        String b = "hola " + a;
        
        
        
        System.out.println(b);
        
        
        
    }
    
    
}
