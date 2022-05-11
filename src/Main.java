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
public class Main {
   public static void main(String[] args) {
 
       //Creating frame that will contain application
       JFrame frame = new JFrame("YouCanBill™");
       frame.pack();
       frame.setSize(400, 400);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
       //Creating "Login Window" for when users first open application, will add their name to the data structure immediately
       JPanel loginPanel = new JPanel();
       loginPanel.setLayout(null);
 
  
       JLabel enterName = new JLabel("Enter Name");
       enterName.setBounds(100, 58, 100, 20);
       loginPanel.add(enterName);
 
       //Text field for the users name
       JTextField nameTF = new JTextField();//name text field
       nameTF.setBounds(100, 77, 193, 28);
       loginPanel.add(nameTF);
 
       //Button for the puprose of entering the users name into the data structure and sending them to the application
       JButton loginButton = new JButton("Login");
       loginButton.setBounds(100, 110, 90, 25);
       loginButton.setForeground(Color.BLACK);
       loginButton.setBackground(Color.WHITE);
       loginButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               personHolder.append(new Person(nameTF.getText()));
               new YouCanBill(frame);
           }
       });
       loginPanel.add(loginButton);
 
       frame.add(loginPanel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
       frame.setDefaultCloseOperation(3);
       frame.setSize(400, 400);
       frame.setVisible(true);
   }
}
 
class YouCanBill {
   private JFrame frame;
   public YouCanBill(JFrame f) {
       // //Creating the Initial start screen
       frame = f;
       JMenuBar mbFrame = new JMenuBar();//MenuBar frame
       frame.pack();
       frame.setSize(400, 400);
       frame.setResizable(false);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
       //Creating a CardLayout layout for the purpose of going inbetween panels/options as the user desires
       CardLayout layout = new CardLayout();
       JPanel deck = new JPanel();
       deck.setLayout(layout);
 
       //Adding buttons to frame menu bar
       JButton help = new JButton("Help");
       JButton next = new JButton("Next");
       next.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               layout.show(deck, "Main Menu");
               next.setVisible(false);
           }
       });
 
       JLabel welcome = new JLabel("Welcome " + personHolder.getPerson(0).getName() + "!");
       mbFrame.add(help);
       mbFrame.add(next);
       mbFrame.add(welcome);
 
 
       //Adding Components to the frame.
       frame.getContentPane().add(BorderLayout.NORTH, mbFrame);
       frame.setVisible(true);
   
  
       //Creating first panel seen by user. Contain image and buttons. Help and Start. Help for instructions, and start to literally start using program
       JPanel startScreen = new JPanel();
       //Creating our brand image ;3. Don't worry image does not need license. I used the google search to help look for it.
       ImageIcon toucan = new ImageIcon("/Users/risantpaul/COSC-112/VSCode/Projects/Final Project/COSC112Final/src/Images/toco-3718588_1280.jpg");//load image to imageIcon
       Image toucan2 = toucan.getImage();//transform it
       Image toucan3 = toucan2.getScaledInstance(400, 375, java.awt.Image.SCALE_SMOOTH);//scale it and make it smooth ;3
       toucan = new ImageIcon(toucan3);
       JLabel toucanpic = new JLabel(toucan);
       startScreen.add(toucanpic);
  
 
      //////////MAIN MENU
      JPanel mainMenu = new JPanel();
       //Button to go to submenu for inputting an image
       JButton iImenu = new JButton("Input Image");//Input Image Menu
       iImenu.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Input Image");
           }
       });
 
       //Button to go to submenu for adding people to your "party"
       JButton enterNames = new JButton("People");
       enterNames.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Name People");
           }
       });
 
       //Button to pick an option of how you want to pay your bill
       JButton options = new JButton("Options");
       options.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Pick Options");
           }
       });
 
       //Adding buttons to the frame
       mainMenu.add(iImenu);
       mainMenu.add(enterNames);
       mainMenu.add(options);
       /////////////// MAIN MENU
 
 
       ////////INPUT IMAGE
       JPanel inputImage = new JPanel();
       //Button to go back to the Main Menu
       JButton iImainMenu = new JButton("Main Menu");//Input Image Main Menu
       iImainMenu.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Main Menu");
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
       inputImage.add(openFiles);
       inputImage.add(iImainMenu);
       ///////INPUT IMAGE
 
 
       ////////NAME PEOPLE
       JPanel namePeople = new JPanel();
       //Button to go back to the Main Menu
       JButton nPmainMenu = new JButton("Main Menu");//Name People Main Menu
       nPmainMenu.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Main Menu");
           }
       });
 
       JPanel enName = new JPanel();
       //Button to enter name of Persons
       JLabel task = new JLabel("Enter Name");
       task.setSize(200, 30);
       JTextField textfield = new JTextField();
       textfield.setSize(200, 30);
       namePeople.add(BorderLayout.SOUTH, task);
       namePeople.add(BorderLayout.SOUTH, textfield);
      
 
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
       namePeople.add(BorderLayout.SOUTH, submit);
       namePeople.add(BorderLayout.EAST, mainMenu);
       frame.setVisible(true);
 
 
       namePeople.add(nPmainMenu);
       namePeople.add(enName);
       namePeople.add(BorderLayout.EAST, mainMenu);
       ///////////ADD PEOPLE
 
 
       /////////PAYMENT OPTIONS
       JPanel paymentOptions = new JPanel();
       //Creating buttons for options
       JButton yourself = new JButton("Pay Yourself");
       JButton split = new JButton("Split Evenly");
       JButton random = new JButton("Random Payer");
 
       //Button to go back to the Main Menu
       JButton paymentMenu = new JButton("Main Menu");//Pick Option main menu
       paymentMenu.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               layout.show(deck, "Main Menu");
           }
       });
 
       //Adding buttons to panel
       paymentOptions.add(yourself);
       paymentOptions.add(split);
       paymentOptions.add(random);
       paymentOptions.add(paymentMenu);
       ////////PAYMENT OPTIONS
 
      
       //Adding panels to the "deck" in order/semantically
       deck.add(startScreen, "Start Screen");
       deck.add(mainMenu, "Main Menu");
       deck.add(inputImage, "Input Image");
       deck.add(namePeople, "Name People");
       deck.add(paymentOptions, "Pick Options");
 
       //More frame configuration
       frame.add(BorderLayout.NORTH, mbFrame);
       frame.getContentPane().add(deck);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setVisible(true);
   }
}
