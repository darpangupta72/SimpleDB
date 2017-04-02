package simpledb.query;
// importing SimpleDateFormat to parse timestamps
import java.text.SimpleDateFormat;
// In case input string is not a timestamp 
import java.text.ParseException;
import java.util.Date;

/**
 * The class that wraps Java Date as database constants.
 * @author Edward Sciore
 */
public class timestamp implements Constant {
   private Date val;
   // object to format and parse 'timestamps'
   private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
   
   /**
    * Create a constant by wrapping the specified string.
    * @param s the string value
    */
   public timestamp(String s) throws ParseException {
      val = simpleDateFormat.parse(s);
   }
   
   /**
    * Unwraps the string and returns it.
    * @see simpledb.query.Constant#asJavaVal()
    */
   public Date asJavaVal() {
      return val;
   }
   
   public boolean equals(Object obj) {
      timestamp t = (timestamp) obj;
      return t != null && val.equals(t.val);
   }
   
   public int compareTo(Constant c) {
      // StringConstant sc = (StringConstant) c;
      // return val.compareTo(sc.val);
      return 1;
   }
   
   public int hashCode() {
      return val.hashCode();
   }
   
   public String toString() {
      return simpleDateFormat.format(val);
   }
}
