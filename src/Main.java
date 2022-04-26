import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.*;

public class Main extends JFrame{
    public Main() {
        JFrame frame = new JFrame("Recieptify");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setBackground(Color.BLACK);

        //creating the main menu
        JMenuBar MenuBar = new JMenuBar();

        JMenu Menu1 = new JMenu("Input Image");
        MenuBar.add(Menu1);
        JButton openfiles = new JButton("Open");
        Menu1.add(openfiles);
        openfiles.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                 JFileChooser chooser = new JFileChooser();
                 FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & JPEG Images", "jpg", "jpeg");
                 chooser.setFileFilter(filter);
                 int returnVal = chooser.showOpenDialog(frame);
                 if(returnVal == JFileChooser.APPROVE_OPTION) {
                     System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                     System.out.println(chooser.getSelectedFile().getPath());
                     //File input = new File(chooser.getSelectedFile().getPath());
                     ImageIcon image = new ImageIcon("C:\Users\kevin\Documents\COSC-112\COSC112Final\Images\TestInput.jpg"");
                     JLabel label = new JLabel("", image, JLabel.CENTER);
                     JPanel panel = new JPanel(new BorderLayout());
                     panel.add(label, BorderLayout.CENTER);
                 }
            }
        });
    
        JMenu Menu2 = new JMenu("Enter Names");
        MenuBar.add(Menu2);
        Menu2.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                 
            }
        });

        JMenu Menu3 = new JMenu("Pick Options");
        MenuBar.add(Menu3);
        JButton option1 = new JButton("Pay Yourself");
        JButton option2 = new JButton("Split Evenly");
        JButton option3 = new JButton("Random Payer");
        Menu3.add(option1);
        Menu3.add(option2);
        Menu3.add(option3);


        //adding components to the frame
        frame.getContentPane().add(BorderLayout.NORTH, MenuBar);
        frame.setVisible(true);
    }
    public static void main(String args[]) {
        new Main();
        
    }
}