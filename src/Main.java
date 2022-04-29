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

public class Main {
    static ArrayList<Person> Peeps = new ArrayList<Person>();
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Recieptify");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new mainMenu(frame));
        frame.pack();    
        frame.setVisible(true);
        
    
    }
}

class mainMenu extends JPanel{
    JFrame frame;
    public mainMenu(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
    
        JButton inputImage = new JButton("Input Image");
        inputImage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new inputImage(frame));
                f.setVisible(true);
            }
        });

        JButton enterNames = new JButton("Enter Names");
        enterNames.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new namePeople(frame));
                f.setVisible(true);
            }
        });

        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new pickOptions(frame));
                f.setVisible(true);
            }
        });

        add(inputImage);
        add(enterNames);
        add(options); 
    }
}


class inputImage extends JPanel{
    JFrame frame;
    public inputImage(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        JButton openFiles= new JButton("Open");
        openFiles.addActionListener(new ActionListener(){
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

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        add(openFiles);
        add(mainMenu);
    }
}

class namePeople extends JPanel{
    JFrame frame;
    public namePeople(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        JButton addNames = new JButton("People");
        addNames.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JLabel task = new JLabel("Enter Name");
                task.setBounds(50, 50, 200, 30);
                JTextField textfield = new JTextField();
                textfield.setBounds(50, 100, 200, 30);
                frame.add(task);
                frame.add(textfield);
                

                //add a button
                JButton submit = new JButton("Submit");
                submit.setBounds(50, 150, 50, 30);
                
                submit.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent d) {
                        Main.Peeps.add(new Person(textfield.getText()));
                        for(int i = 0; i < Main.Peeps.size(); i++) {
                            System.out.println(Main.Peeps.get(i).getName());
                        }
                    }
                });
                add(BorderLayout.SOUTH, submit);
                frame.setVisible(true);
            }
        });

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        add(BorderLayout.WEST, addNames);
        add(BorderLayout.EAST, mainMenu);
    }
}

class pickOptions extends JPanel{
    JFrame frame;
    public pickOptions(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        JButton optionOne = new JButton("Pay Yourself");
        JButton optionTwo = new JButton("Split Evenly");
        JButton optionThree = new JButton("Random Payer");

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        add(optionOne);
        add(optionTwo);
        add(optionThree);
        add(mainMenu);
    }
}