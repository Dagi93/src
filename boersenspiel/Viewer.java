package boersenspiel;
import java.awt.Color;
import java.awt.Font;
import java.util.TimerTask;



import java.util.logging.Logger;

//import java.util.Timer;
//import java.util.Calendar;
//import java.util.Date;
//import java.text.DateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Viewer extends JFrame {
    private JLabel clockLabel;
    private StockPriceProvider provider;
    private Font font = new Font("font", 0, 24);
    private static Logger log = Logger.getLogger(Viewer.class.getName());

    public class TickerTask extends TimerTask {
        public void run() {
            String output = createText();
            clockLabel.setText(output);
            clockLabel.repaint();
        }

        public String createText() {     
            String output = "<html><body>Aktienkurse:<br>";
            output += provider.allSharesToString() + "</body></html>";
            return output;
        }
    }


    public Viewer(StockPriceProvider provider) {
        this.provider = provider;
        
        clockLabel = new JLabel("coming soon ...");
        clockLabel.setFont(font);
        clockLabel.setForeground(Color.BLUE);
        add("Center", clockLabel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 450);
        setVisible(true);

    }

    public void start() {
        final TickerTask task = new TickerTask();
        final Zeitgeist timer = Zeitgeist.getInstance();
        timer.scheduleAtFixedRate(task, 2000, 1000);
    }

}