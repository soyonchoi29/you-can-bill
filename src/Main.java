import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;

public class Main extends JPanel{
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
                     //try{
                         //BufferedImage image = ImageIO.read(new File("Images/TestInput.jpg"));
                        // JLabel picLabel = new JLabel(new ImageIcon(image));
                        // panel.add(picLabel);
                        // frame.add(panel);
                         //frame.repaint(); // This all doesn't work
                        // System.out.println(chooser.getSelectedFile().getPath());
                     //} catch (IOException error) {}

                     File input = new File(chooser.getSelectedFile().getPath());
                     ImagePopup.drawNew(input); // working

                     //Scanner input = new Scanner(chooser.getSelectedFile().getPath());
                     //ImageIcon image = new ImageIcon(input.getPath());
                     //JLabel label = new JLabel("", image, JLabel.CENTER);
                     //JPanel panel = new JPanel(new BorderLayout());
                     //frame.add(label, BorderLayout.CENTER);
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