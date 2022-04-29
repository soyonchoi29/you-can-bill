import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;
import java.util.ArrayList;
public class Main extends JPanel {
    ArrayList<Person> Peeps = new ArrayList<>();
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

                     File input = new File(chooser.getSelectedFile().getPath());
                     ImagePopup.drawNew(input); // working
                 }
            }
        });
    
        JMenu Menu2 = new JMenu("Enter Names");
        MenuBar.add(Menu2);
        JButton addnames = new JButton("Add");
        Menu2.add(addnames);
        addnames.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(390, 300);
                frame.setLocation(100, 150);
                JLabel task = new JLabel("Enter Name");
                task.setBounds(50, 50, 200, 30);
                JTextField textfield = new JTextField();
                textfield.setBounds(50, 100, 200, 30);
                frame.add(task);
                frame.add(textfield);
                

                //add a button
                JButton submit = new JButton("Submit");
                submit.setBounds(50, 150, 50, 30);
                frame.add(submit);
                submit.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent d) {
                        Peeps.add(new Person(textfield.getText()));
                    }
                });
                frame.setVisible(true);
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