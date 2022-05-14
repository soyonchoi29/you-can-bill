import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.Image;
import java.awt.CardLayout;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//Creating a linked list to store People

class Node {
    Node prev;
    Customer customer;
    public Node(Customer customer) {
        this.customer = customer;
    }
}

class CustomerHolder{
    Node end;
   public CustomerHolder(){
       end = null;
   }
 
   //method called append to be able to add Persons
   public  void append(Customer toAppend){
       Node toAdd = new Node(toAppend);
       toAdd.prev = end;
       end = toAdd;
   }
 
   //Get a customer based off the index given
   public  Customer getCustomer(int index) {
        Node n = end;
        int indices = length() - 1;
        if(index == indices) {
            return n.customer;
        } else {
            while(indices > index) {
                n = n.prev;
                indices--;
            }
        }
        return n.customer;
   }
 
   //get index of customer based off their name (i get 1 no matter what :(()))
   public  int getIndex(String name) {
       Node n = end;
       int index = -1;
       boolean done = false;
       while(n != null && !done) {
            if(n.customer.getName() == name) {
                done = true;
            }
            n = n.prev;
            index++;
           
       }
       return index;
   }
  
   //method called length to get length of customer
   public  int length() {
       Node n = end;
       int toReturn = 0;
       while(n != null) {
           n = n.prev;
           toReturn++;
       }
 
       return toReturn;
   }
 
   //returns all names inside
   public  String getName() {
       String toReturn = "";
       Node n = end;
       while(n != null) {
           toReturn = n.customer + " " + toReturn;
           n = n.prev;
       }
       return toReturn;
   }
   public Customer isCredit() {
       Node n = end;
       boolean done = false;
       while(n != null && !done) {
           if(n.customer.isCredit()) {
                done = true;
                return n.customer;
           }
           n = n.prev;
       }
       return n.customer;
   }
}
 
//Creating the general Main Menu and subsequent submenus
public class Main extends JPanel{
    public static void main(String[] args) {
 
        //Creating frame that will contain application
        JFrame frame = new JFrame("YouCanBill™");
        JMenuBar mbFrame = new JMenuBar();
        mbFrame.setOpaque(false);
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
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startScreen.setVisible(false);
                new YouCanBill();
            }
        });
        //Adding buttons to the mbFrame panel
        mbFrame.add(help);
        mbFrame.add(start);

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
    RSA rsa = new RSA();
    static CardLayout layout;
    static JPanel deck;
    static String ccnameinfo;

   public YouCanBill() {
       CustomerHolder customers = new CustomerHolder();
        //Creating the frame for application
        JFrame frame = new JFrame("YouCanBill™");
        JMenuBar mbFrame = new JMenuBar();//MenuBar frame
        frame.pack();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
       //Creating a CardLayout layout for the purpose of going inbetween panels/options as the user desires
       layout = new CardLayout();
       deck = new JPanel();
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
                    ImagePopup.drawNew(customers, input); // working
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
        JButton credituser = new JButton("Credit Card");
        credituser.setBounds(100, 110, 100, 25);
        credituser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d) {
                Customer creditp = new CreditUser(textfield.getText());
                customers.append(creditp);
                textfield.setText("");
            }
        });

        JButton cashpayer = new JButton("Cash");
        cashpayer.setBounds(200, 110, 90, 25);
        cashpayer.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d) {
                Customer cashp = new CashPayer(textfield.getText());
                customers.append(cashp);
                textfield.setText("");
            }
        });
        
        //Button to continue to submit and crop image after user is done adding people
        JButton done = new JButton("Done");
        done.setBounds(145, 140, 90, 25);
        done.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent d) {
                layout.show(deck, "Input Image");
            }
        });

        namePeople.add(done);
        //Adding buttons to frame
        namePeople.add(credituser);
        namePeople.add(cashpayer);
        frame.setVisible(true);
        ///////////ADD PEOPLE

        //Creating and Adding help and back button for the frame menubar
        JButton help = new JButton("Help");

        JButton mainMenu = new JButton("Main Menu");
        mainMenu.setVisible(false);
        mainMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                layout.show(deck, "Payment Options");
            }
        });

        mbFrame.add(help);
        mbFrame.add(mainMenu);


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
        JButton credit = new JButton("Credit Card");
        credit.setBounds(100, 110, 100, 25);
        credit.setForeground(Color.BLACK);
        credit.setBackground(Color.WHITE);
        credit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!nameTF.getText().isEmpty()) {
                    Customer dummy = new CreditUser("Dummy");
                    Customer initial = new CreditUser(nameTF.getText());
                    customers.append(dummy);
                    customers.append(initial);
                    JLabel welcome = new JLabel("Welcome " + customers.getCustomer(1).getName() + "!");
                    mbFrame.add(welcome);
                    mainMenu.setVisible(true);
                    layout.show(deck, "Payment Options");
                } else {
                    isEmpty.setVisible(true);
                }
            }
        });
        JButton cash = new JButton("Cash");
        cash.setBounds(200, 110, 90, 25);
        cash.setForeground(Color.BLACK);
        cash.setBackground(Color.WHITE);
        cash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!nameTF.getText().isEmpty()) {
                    Customer dummy = new CreditUser("Dummy");
                    Customer initial = new CashPayer(nameTF.getText());
                    customers.append(dummy);
                    customers.append(initial);
                    JLabel welcome = new JLabel("Welcome " + customers.getCustomer(1).getName() + "!");
                    mbFrame.add(welcome);
                    mainMenu.setVisible(true);
                    layout.show(deck, "Payment Options");
                } else {
                    isEmpty.setVisible(true);
                }
            }
        });

        loginPanel.add(enterName);
        loginPanel.add(nameTF);
        loginPanel.add(isEmpty);
        loginPanel.add(credit);
        loginPanel.add(cash);
        /////Login Window
        

        /////CCBilling
        JPanel ccbilling = new JPanel();
        ccbilling.setLayout(null);

        JLabel ccinfo = new JLabel("Enter Credit Card Information");
        ccinfo.setBounds(100, 0, 300, 30);
        
        JLabel infowarning = new JLabel("Please Input Missing Information");
        infowarning.setBounds(75, 240, 300, 25);
        infowarning.setVisible(false); 

        //Credit Card name information
        JLabel ccname = new JLabel("Enter Name on Credit Card");
        ccname.setBounds(20, 50, 300, 30);

        JTextField ccnameTF = new JTextField();//name text field
        ccnameTF.setBounds(20, 80, 175, 30);

        JButton ccnameButton = new JButton("Enter");
        ccnameButton.setBounds(200, 83, 90, 25);
        ccnameButton.setForeground(Color.BLACK);
        ccnameButton.setBackground(Color.WHITE);
        ccnameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ccnameinfo = ccnameTF.getText();
                ccnameTF.setText("**********");
            }
        });

        //Credit Card Number components
        JLabel ccnumber = new JLabel("Enter Credit Card Number");
        ccnumber.setBounds(20, 140, 300, 30);


        JTextField ccnumberTF = new JTextField();//name text field
        ccnumberTF.setBounds(20, 170, 175, 30);

        JButton ccnumberButton = new JButton("Enter");
        ccnumberButton.setBounds(200, 173, 90, 25);
        ccnumberButton.setForeground(Color.BLACK);
        ccnumberButton.setBackground(Color.WHITE);
        ccnumberButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BigInteger ccnumber = new BigInteger(ccnumberTF.getText());
                rsa.encryptAndSave(ccnumber, ccnameinfo);
                ccnumberTF.setText("****************");
            }
        });

        //Add and done buttons for cc information
        JButton addccinfo = new JButton("Add Billing Info");
        addccinfo.setBounds(70, 210, 130, 25);
        addccinfo.setForeground(Color.BLACK);
        addccinfo.setBackground(Color.WHITE);
        addccinfo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!ccnameTF.getText().isBlank() && !ccnumberTF.getText().isBlank()) {
                    ccnameTF.setText("");
                    ccnumberTF.setText("");
                    ccname.setForeground(Color.BLACK);
                    ccnumber.setForeground(Color.BLACK);
                    infowarning.setVisible(false);
                } else {
                    infowarning.setVisible(true);
                    if(ccnameTF.getText().isBlank()) {
                        ccname.setForeground(Color.RED);
                    }
                    if(ccnumberTF.getText().isBlank()) {
                        ccnumber.setForeground(Color.RED);
                    }
                }
            }
        });

        JButton ccdone = new JButton("Done");
        ccdone.setBounds(200, 210, 70, 25);
        ccdone.setForeground(Color.BLACK);
        ccdone.setBackground(Color.WHITE);
        ccdone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!ccnameTF.getText().isBlank() && !ccnumberTF.getText().isBlank()) {
                    mainMenu.setVisible(true);
                    layout.show(deck, "Reciepts");
                } else {
                    infowarning.setVisible(true);
                    if(ccnameTF.getText().isBlank()) {
                        ccname.setForeground(Color.RED);
                    }
                    if(ccnumberTF.getText().isBlank()) {
                        ccnumber.setForeground(Color.RED);
                    }
                }
            }
        });


        ccbilling.add(ccdone);
        ccbilling.add(infowarning);
        ccbilling.add(addccinfo);
        ccbilling.add(ccnumberButton);
        ccbilling.add(ccnameButton);
        ccbilling.add(ccnumberTF);
        ccbilling.add(ccnameTF);
        ccbilling.add(ccnumber);
        ccbilling.add(ccname);
        ccbilling.add(ccinfo);
        /////CCBilling


        /////Reciepts
        JPanel reciepts = new JPanel();

        /////Reciepts


        //Adding panels to the "deck" in order/semantically
        deck.add(loginPanel, "Login Panel");
        deck.add(paymentOptions, "Payment Options");
        deck.add(namePeople, "Name People");
        deck.add(inputImage, "Input Image");
        deck.add(ccbilling, "CC Billing");
        deck.add(reciepts, "Reciepts");
        
        

        //More frame configuration
        frame.add(BorderLayout.NORTH, mbFrame);
        frame.getContentPane().add(deck);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
   }
}
