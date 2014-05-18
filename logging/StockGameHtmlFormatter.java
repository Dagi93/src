package logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class StockGameHtmlFormatter extends Formatter {

    public String getHead(Handler h) {
        return "<html>"
                + "<head> "
                +   "<title>"
                + "     Boersenspiel-Log vom " + (new Date()) + ""
                +   "</title>"
                + "</head>"
                + "<body>"
                +   "<h1>Boersenspiel-Log</h1>\n"
                +       "<table border= '1px solid black' width=50%>"
                +       "<tr><th>Seq.-Nr.</th><th>Zeit</th><th>Level</th><th>Nachricht</th></tr>";
      }
    
    @Override
    public String format(LogRecord rec) {

        StringBuilder sb = new StringBuilder();

        sb.append("<tr>")
            .append("<td>" + rec.getSequenceNumber() + "</td>")
            .append("<td>" + new Date(rec.getMillis()) + "</td>")
            .append("<td>" + rec.getLevel().getLocalizedName() + "</td>")
            .append("<td>" + formatMessage(rec) + "</td>")
            .append("</tr>");
       

        return sb.toString();
    }
    
    public String getTail(Handler h) {
        return                      
                        "</table>"
                    + "</body>"
                + "</html>";
      }
}
