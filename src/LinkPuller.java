import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LinkPuller implements ActionListener{
    private JFrame frame;
    private JPanel panel;
    private JButton searchButton;
    private JTextField urlInput;
    private JTextArea termInput;

    String URL = new String();
    String searchTerm = new String();

    public static void main(String[] args) {
        LinkPuller lnk = new LinkPuller();
    }

    public LinkPuller() {
        frame = new JFrame("Link Puller -- Kasen");
        panel = new JPanel(new BorderLayout());

        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        urlInput = new JTextField("Replace this text with a URL");
        termInput = new JTextArea("Replace this text with a search term");

        panel.add(searchButton, BorderLayout.SOUTH);
        panel.add(urlInput, BorderLayout.NORTH);
        panel.add(termInput, BorderLayout.CENTER);

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == "Search") {
            System.out.println("Search button clicked");
            String t = urlInput.getText();
            urlInput.setText(t);
            search();
        }
    }

    public void search(){
        try {
            System.out.println();
            System.out.print("hello \n");
            URL = urlInput.getText();
            URL url = new URL(URL);
            searchTerm = termInput.getText();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            int c = 1;
            while ((line = reader.readLine()) != null) {
                if (line.contains(searchTerm) && (line.contains("href")) ){
                    int realStart =  line.indexOf("href") + 6;
                    while (realStart >= 0) {
                        String miniLine = line.substring(realStart);
                        int end = miniLine.indexOf(">") -1;
                        String link = miniLine.substring(0,end);
                        System.out.println(c + ". " + link);
//                        termInput.append("\n" + "\n" + "Results below:" + "\n");
                        termInput.append("\n" + link );

                        c++;
                        realStart = miniLine.indexOf(searchTerm,end);
                    }
                }
            }
            reader.close();
        } catch (
                Exception ex) {
            System.out.println(ex);
        }
    }
}