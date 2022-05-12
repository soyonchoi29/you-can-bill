import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.Image;
import java.awt.CardLayout;
 
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
 
   public static String getName() {
       String toReturn = "";
       Node n = end;
       while(n != null) {
           toReturn = n.person + " " + toReturn;
           n = n.prev;
       }
       return toReturn;
   }
}
 
//Creating the general Main Menu and subsequent submenus
public class Main extends JPanel{
    public static void main(String[] args) {
 
        //Creating frame that will contain application
        JFrame frame = new JFrame("YouCanBill™");
        JMenuBar mbFrame = new JMenuBar();
        frame.pack();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Creating first panel seen by user. Contain image and buttons. Help and Start. Help for instructions, and start to literally start using program
        JPanel startScreen = new JPanel();
        //Creating our brand image ;3. Don't worry image does not need license. I used the google search to help look for it.
        ImageIcon toucan = new ImageIcon("/Users/risantpaul/COSC-112/VSCode/Projects/Final Project/COSC112Final/src/Images/toco-3718588_1280.jpg");//load image to imageIcon
        Image toucan2 = toucan.getImage();//transform it
        Image toucan3 = toucan2.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);//scale it and make it smooth ;3
        toucan = new ImageIcon(toucan3);
        JLabel toucanpic = new JLabel(toucan);

        JButton help = new JButton("Help");
        JButton contin = new JButton("Continue");
        contin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startScreen.setVisible(false);
                new YouCanBill();
            }
        });
        //Adding buttons to the mbFrame panel
        mbFrame.add(help);
        mbFrame.add(contin);

        //Adding label (with image) to start screen
        startScreen.add(toucanpic);

        
        //Adding Start Screen Panel to frame and other Frame configurations
        frame.add(BorderLayout.NORTH, mbFrame);
        frame.add(startScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
 
class YouCanBill {
   public YouCanBill() {
        //Creating the frame for application
        JFrame frame = new JFrame();
        JMenuBar mbFrame = new JMenuBar();//MenuBar frame
        frame.pack();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
       //Creating a CardLayout layout for the purpose of going inbetween panels/options as the user desires
       CardLayout layout = new CardLayout();
       JPanel deck = new JPanel();
       deck.setLayout(layout);

        
        /////PICK PAYMENT OPTION
        JPanel paymentOptions = new JPanel();
        JPanel pOQuestion = new JPanel();//Payment Options Quesiton
        JLabel paymOpt = new JLabel("How would you like pay for your bill?");//Payment Option JLabel
        pOQuestion.add(paymOpt);

        //Creating buttons for that represent users payment options
        JButton yourself = new JButton("Pay Yourself");
        yourself.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Input Image");
            }
        });

        JButton split = new JButton("Split Evenly");
        split.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Name People");
            }
        });

        JButton random = new JButton("Random Payer");
        random.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Name People");
            }
        });

        //Adding buttons to panel
        paymentOptions.add(yourself);
        paymentOptions.add(split);
        paymentOptions.add(random);
        paymentOptions.add(BorderLayout.NORTH, pOQuestion);
        /////PICK PAYMENT OPTIONS


        /////Input Image
        JPanel inputImage = new JPanel();

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
        inputImage.add(openFiles);
        /////Input Image

 
        ////////NAME PEOPLE
        JPanel namePeople = new JPanel();
        namePeople.setLayout(null);

        //Creating textfield and directions for users input
        JLabel task = new JLabel("Enter Name");
        task.setBounds(100, 58, 100, 20);
        JTextField textfield = new JTextField();
        textfield.setBounds(100, 77, 193, 20);
        namePeople.add(BorderLayout.SOUTH, task);
        namePeople.add(BorderLayout.SOUTH, textfield);
        


        //Button to make an instance of Person
        JButton submit = new JButton("Add");
        submit.setBounds(100, 110, 90, 25);
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d) {
                personHolder.append(new Person(textfield.getText()));
                textfield.setText("");
            }
        });
        
        //Button to continue to submit and crop image after user is done adding people
        JButton done = new JButton("Done");
        done.setBounds(200, 110, 90, 25);
        done.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d) {
                layout.show(deck, "Input Image");
            }
        });

        namePeople.add(done);
        //Adding buttons to frame
        namePeople.add(BorderLayout.SOUTH, submit);
        frame.setVisible(true);
        ///////////ADD PEOPLE

        //Creating and Adding help and back button for the frame menubar
        JButton help = new JButton("Help");

        JButton back = new JButton("Back");
        back.setVisible(false);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(inputImage.isVisible()) {
                    layout.show(deck, "Payment Options");
                }
                if(!paymentOptions.isVisible()) {
                    layout.previous(deck);
                }
            }
        });

        mbFrame.add(help);
        mbFrame.add(back);


        /////Login Window
        //Creating "Login Window" for when users first open application, will add their name to the data structure immediately
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel enterName = new JLabel("Enter Name");
        enterName.setBounds(100, 58, 100, 20);

        //Text field for the users name
        JTextField nameTF = new JTextField();//name text field
        nameTF.setBounds(100, 77, 193, 28);

        //JLabel for warning in case textfield is empty
        JLabel isEmpty = new JLabel("Please enter your name before continuing!");
        isEmpty.setBounds(100, 120, 300, 100);
        isEmpty.setVisible(false);
                
        //Button for the puprose of entering the users name into the data structure and sending them to the application
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 110, 90, 25);
        loginButton.setForeground(Color.BLACK);
        loginButton.setBackground(Color.WHITE);
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!nameTF.getText().isEmpty()) {
                    personHolder.append(new Person(nameTF.getText()));
                    JLabel welcome = new JLabel("Welcome " + personHolder.getPerson(0).getName() + "!");
                    mbFrame.add(welcome);
                    back.setVisible(true);
                    layout.show(deck, "Payment Options");
                } else {
                    isEmpty.setVisible(true);
                }
            }
        });

        loginPanel.add(enterName);
        loginPanel.add(nameTF);
        loginPanel.add(isEmpty);
        loginPanel.add(loginButton);
        /////Login Window
        

        //Adding panels to the "deck" in order/semantically
        deck.add(loginPanel, "Login Panel");
        deck.add(paymentOptions, "Payment Options");
        deck.add(namePeople, "Name People");
        deck.add(inputImage, "Input Image");

        //More frame configuration
        frame.add(BorderLayout.NORTH, mbFrame);
        frame.getContentPane().add(deck);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
   }
}
