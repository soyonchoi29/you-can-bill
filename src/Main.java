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

//Creating a linked list to store People
class Node{
    Person person;
    Node prev;
    public Node(Person person) {
        this.person = person;
    }
}

class personHolder{
	static Node end;
	public personHolder(){
		end = null;
    }

    //method called append to be able to add Persons
    public static void append(Person toAppend){
        Node toAdd = new Node(toAppend);
        toAdd.prev = end;
        end = toAdd;
    }

    //method called getPerson to get Person based off index
    public static Person getPerson(int index) {
        Node n = end;
        if(index == 0) {
            return n.person;
        } else {
            while(index > 0) {
                n = n.prev;
                index--;
            }
            return n.person;
        }
    }

    //method called getIndex to get index of Person based off name
    public static int getIndex(String name) {
        Node n = end;
        int index = -1;
        boolean done = false;
        while(n != null || !done) {
            if(n.person.getName() == name) {
                done = true;
            }
            n = n.prev;
            index++;
        }
        return index;
    }
    
    //method called length to get length of Person
    public static int length() {
        Node n = end;
        int toReturn = 0;
        while(n != null) {
            n = n.prev;
            toReturn++;
        }

        return toReturn;
    }
}

//Creating the general Main Menu and subsequent submenus
public class Main {
    public static void main(String[] args) {
        //Creating the frame that will hold everything
        JFrame frame = new JFrame("YouCanBill™");
        frame.pack();
        frame.setSize(300, 300);
        frame.setContentPane(new mainMenu(frame));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

class mainMenu extends JPanel{
    private JFrame frame;
    public mainMenu(JFrame f) {
        //creating the frame that will hold the buttons
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        //Button to go to submenu for inputting an image
        JButton inputImage = new JButton("Input Image");
        inputImage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new inputImage(frame));
                f.setVisible(true);
            }
        });

        //Button to go to submenu for adding people to your "party"
        JButton enterNames = new JButton("People");
        enterNames.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new namePeople(frame));
                f.setVisible(true);
            }
        });

        //Button to pick an option of how you want to pay your bill
        JButton options = new JButton("Options");
        options.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new pickOptions(frame));
                f.setVisible(true);
            }
        });

        //Adding buttons to the frame
        add(inputImage);
        add(enterNames);
        add(options); 
    }
}


class inputImage extends JPanel{
    private JFrame frame;
    public inputImage(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        //Button to go back to the Main Menu
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        //Button to open directory and pick an image
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

        //Adding buttons to frame
        add(openFiles);
        add(mainMenu);
    }
}

class namePeople extends JPanel{
    private JFrame frame;
    public namePeople(JFrame f) {
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        //Button to go back to the Main Menu
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        //Button to enter name of Persons
        JButton addNames = new JButton("Enter Name");
        addNames.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                remove(addNames);
                JLabel task = new JLabel("Enter Name");
                task.setSize(200, 30);
                JTextField textfield = new JTextField();
                textfield.setSize(200, 30);
                add(BorderLayout.SOUTH, task);
                add(BorderLayout.SOUTH, textfield);
                

                //Button to make an instance of Person
                JButton submit = new JButton("Submit");
                submit.setBounds(50, 150, 50, 30);
                submit.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent d) {
                        personHolder.append(new Person(textfield.getText()));
                    }
                });
                //Adding buttons to frame
                add(BorderLayout.SOUTH, submit);
                add(BorderLayout.EAST, mainMenu);
                frame.setVisible(true);
            }
        });

        //Adding buttons to frame
        add(BorderLayout.WEST, addNames);
        add(BorderLayout.EAST, mainMenu);
    }
}

//Submenu after picking Options to pick how you would like to pay the bill
class pickOptions extends JPanel{
    private JFrame frame;
    public pickOptions(JFrame f) {
        //Making frame that holds the options
        frame = f;
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());
        //Creating buttons for options
        JButton optionOne = new JButton("Pay Yourself");
        JButton optionTwo = new JButton("Split Evenly");
        JButton optionThree = new JButton("Random Payer");

        //Button to go back to the Main Menu
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setContentPane(new mainMenu(frame));
                f.setVisible(true);
            }
        });

        //Adding buttons to frame
        add(optionOne);
        add(optionTwo);
        add(optionThree);
        add(mainMenu);
    }
}