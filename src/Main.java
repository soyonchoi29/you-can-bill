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

//Creating a linked list to store Customers
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
 
    //Method called append to add customers to linked list
    public  void append(Customer toAppend){
        Node toAdd = new Node(toAppend);
        toAdd.prev = end;
        end = toAdd;
    }
 
    //Method called getCustomer to get customer based off an index
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
 
    //Method called getIndex to get the index of a customer based off their name (string)
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
  
    //Method called length to get the length of the linked list
    public  int length() {
        Node n = end;
        int toReturn = 0;
        while(n != null) {
            n = n.prev;
            toReturn++;
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

    //Method called get toString to get all names (of customers) inside the linked list
    public  String toString() {
        String toReturn = "";
        Node n = end;
        while(n != null) {
            toReturn = n.customer.getName() + " " + toReturn;
            n = n.prev;
        }
        return toReturn;
    }
}
 
//Main class that contains first JFrame seen by user and calls YouCanBill class to continue using the application
public class Main extends JPanel{
    static JFrame frame;
    static JButton help;
    public static void main(String[] args) {
        //Creating the frame that will contain Toucan picture and buttons to continue to next JFrame
        frame = new JFrame("YouCanBill™");
        JMenuBar mbFrame = new JMenuBar();//menubar for frame
        mbFrame.setBounds(0, 0, 400, 30);
        frame.pack();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adding a component listener to the frame so that when it is moved, it will return to its original location
        frame.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                frame.setLocation(0,0);
            }
        });

        //Creating start screen JPanel that will hold buttons and toucan image
        JPanel ssPanel = new JPanel();
        ssPanel.setLayout(null);

        //Creating Panel to hold picture of toucan
        JPanel toucanPanel = new JPanel();
        toucanPanel.setBounds(0, 0, 400, 400);
        //Inputting and resizing toucan image
        ImageIcon toucan = new ImageIcon("/Users/risantpaul/COSC-112/VSCode/Projects/Final Project/COSC112Final/src/Images/toco-3718588_1280.jpg");//load image to imageIcon
        Image toucan2 = toucan.getImage();//transform it
        Image toucan3 = toucan2.getScaledInstance(400, 400, java.awt.Image.SCALE_SMOOTH);//scale it and make it smooth ;3
        toucan = new ImageIcon(toucan3);
        JLabel toucanpic = new JLabel(toucan);
        toucanPanel.add(toucanpic);

        //Button called help to display help screen
        help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Setting current frame invisible and creating an instance of YouCanBill to show help panel
                frame.setVisible(false);
                new YouCanBill();
                YouCanBill.help.setVisible(false);
                YouCanBill.layout.show(YouCanBill.deck, "Help");
                YouCanBill.mMButton.setVisible(true);
            }
        });
        //Button called start for user to create instance of YouCanBill and start using application
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                YouCanBill.tracker++;
                frame.setVisible(false);
                new YouCanBill();
            }
        });

        //Adding buttons to the mbFrame panel
        mbFrame.add(help);
        mbFrame.add(start);

        //Adding menubar and panel containing buttons and toucan panel to start screen panel for a nice layout
        ssPanel.add(mbFrame);
        ssPanel.add(toucanPanel);

        //Adding Start Screen Panel to frame and frame configuration
        frame.add(ssPanel);
        frame.setVisible(true);
    }
}
 
class YouCanBill {
    //Creating an instance of RSA to use to encrypt credit card information
    RSA rsa = new RSA();
    //integer to track where the user (0 means at start screen, 1 means at login panel, 2 means at main menu)
    static int tracker = 0;

    //Customer information/storage
    static CustomerHolder customers;
    static String ccnameinfo;

    //Frame, Layout and buttons
    static JFrame frame;
    static CardLayout layout;
    static JPanel deck;
    static JButton mMButton;
    static JButton help;

    public YouCanBill() {
        customers = new CustomerHolder();
        //Creating the frame for application
        frame = new JFrame("YouCanBill™");
        JMenuBar mbFrame = new JMenuBar();//MenuBar frame
        frame.pack();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Adding a component listener to the frame so that when it is moved, it will return to its original location
        frame.addComponentListener(new ComponentAdapter() {
            public void componentMoved(ComponentEvent e) {
                frame.setLocation(0,0);
            }
        });

        //Creating a CardLayout layout for the purpose of going inbetween panels/options as the user desires
        layout = new CardLayout();
        deck = new JPanel();
        deck.setLayout(layout);

        
        /////Main Menu
        JPanel mainMenu = new JPanel();
        JPanel paymentOptions = new JPanel();//Payment Options Quesiton
        JLabel paymOpt = new JLabel("How would you like pay for your bill?");//Payment Option JLabel
        paymentOptions.add(paymOpt);

        //Button called yourself to take user directly to inputting their image and then billing
        JButton yourself = new JButton("Yourself");
        yourself.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Input Image");
                mMButton.setVisible(true);
            }
        });

        //Button called split to take user to namePeople panel where they are able to add whoever else they want
        JButton split = new JButton("Split");
        split.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Name People");
                mMButton.setVisible(true); 
            }
        });

        //Button called random to take user to namePeople panel where they are able to add whoever else they want
        JButton random = new JButton("Random Payer");
        random.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                layout.show(deck, "Name People");
                mMButton.setVisible(true);
            }
        });

        //Adding buttons to panel
        mainMenu.add(yourself);
        mainMenu.add(split);
        mainMenu.add(random);
        mainMenu.add(BorderLayout.NORTH, paymentOptions);
        /////PICK PAYMENT OPTIONS


        /////Input Image
        JPanel inputImage = new JPanel();
        //Button to open directory and pick an image
        JButton openFiles= new JButton("Open");
        openFiles.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & JPEG Images", "jpg", "jpeg");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(frame);
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
                    File input = new File(chooser.getSelectedFile().getPath());
                    ImagePopup.drawNew(input);
                }
            }
        });

        //Adding button to panel
        inputImage.add(openFiles);
        /////Input Image

 
        /////Add People
        JPanel namePeople = new JPanel();
        namePeople.setLayout(null);

        //Creating textfield and directions for users input
        JLabel task = new JLabel("Enter Name");
        task.setBounds(100, 58, 100, 20);
        JTextField textfield = new JTextField();
        textfield.setBounds(100, 77, 193, 20);
        namePeople.add(BorderLayout.SOUTH, task);
        namePeople.add(BorderLayout.SOUTH, textfield);

        //Button to make an instance of Customer that will use their credit card
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

        //Button to make an instance of Customer that will pay in cash
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
        
        //Button to continue to inputImage after user is done adding people
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
        /////Add People

       
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
                    //tracks when user is done "logging in"
                    tracker++;
                    //"Dummy" instance of Customer that will hold stitched image until it is distributed to a customer
                    Customer dummy = new CreditUser("Dummy");
                    //Instance of Customer of the user who "logged in"
                    Customer initial = new CreditUser(nameTF.getText());
                    customers.append(dummy);
                    customers.append(initial);
                    JLabel welcome = new JLabel("Welcome " + customers.getCustomer(1).getName() + "!");
                    mbFrame.add(welcome);
                    layout.show(deck, "Main Menu");
                } else {
                    isEmpty.setVisible(true);
                }
            }
        });

        //Button for the puprose of entering the users name into the data structure and sending them to the application
        JButton cash = new JButton("Cash");
        cash.setBounds(200, 110, 90, 25);
        cash.setForeground(Color.BLACK);
        cash.setBackground(Color.WHITE);
        cash.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(!nameTF.getText().isEmpty()) {
                    //tracks when user is done "logging in"
                    tracker++;
                    //"Dummy" instance of Customer that will hold stitched image until it is distributed to a customer
                    Customer dummy = new CreditUser("Dummy");
                    //Instance of Customer of the user who "logged in"
                    Customer initial = new CashPayer(nameTF.getText());
                    customers.append(dummy);
                    customers.append(initial);
                    JLabel welcome = new JLabel("Welcome " + customers.getCustomer(1).getName() + "!");
                    mbFrame.add(welcome);
                    layout.show(deck, "Main Menu");
                } else {
                    isEmpty.setVisible(true);
                }
            }
        });

        //Adding components to the panel
        loginPanel.add(enterName);
        loginPanel.add(nameTF);
        loginPanel.add(isEmpty);
        loginPanel.add(credit);
        loginPanel.add(cash);
        /////Login Window
        

        /////Credit Card Billing
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
                    mMButton.setVisible(true);
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
        /////Credit Card Billing


        /////Reciepts
        JPanel reciepts = new JPanel();
        JButton printout = new JButton("Print");
        printout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println(rsa.decrypt("Ris Paulino"));
            }
        });

        reciepts.add(printout);
        /////Reciepts


        /////Help Panel
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(null);
        JLabel label1 = new JLabel();
        label1.setText("<html><h2>Welcome to YouCanBill™!</h2></html>");
        label1.setBounds(5, 3, 400, 20);
        
        JLabel label2 = new JLabel();
        label2.setText("When you feel ready press return and continue");
        label2.setBounds(5, 25, 400, 15);

        JLabel label3 = new JLabel();
        label3.setText("<html><h3>Instructions to Use YouCanBill™</h2></html>");
        label3.setBounds(5, 40, 400, 15);
        label3.setForeground(Color.RED);

        JLabel label4 = new JLabel();
        label4.setText("1. After pressing start, you will be prompted to enter your");
        label4.setBounds(10, 60, 400, 15);

        JLabel label5 = new JLabel();
        label5.setText("name. Once done, choose how you would like to pay.");
        label5.setBounds(25, 80, 400, 15);

        JLabel label6 = new JLabel();
        label6.setText("2. Choose whether you are paying for the bill yourself,");
        label6.setBounds(10, 100, 400, 15);

        JLabel label7 = new JLabel();
        label7.setText("splitting the bill according to who ate what, or have it");
        label7.setBounds(25, 120, 400, 15);

        JLabel label8 = new JLabel();
        label8.setText("randomly choose who will pay for the bill.");
        label8.setBounds(25, 140, 400, 15);

        JLabel label9 = new JLabel();
        label9.setText("3. Given your previous choice, either input your receipt");
        label9.setBounds(10, 160, 400, 15);

        JLabel label10 = new JLabel();
        label10.setText("(only JPG or JPEG images are allowed), or add whoever");
        label10.setBounds(25, 180, 400, 15);

        JLabel label11 = new JLabel();
        label11.setText("you would like to join you in paying for the bill.");
        label11.setBounds(25, 200, 400, 15);

        JLabel label12 = new JLabel();
        label12.setText("4. After that, continue on to billing. If there is a credit card");
        label12.setBounds(10, 220, 400, 15);

        JLabel label13 = new JLabel();
        label13.setText("user amongst your party you will be prompted to enter");
        label13.setBounds(25, 240, 400, 15);

        JLabel label14 = new JLabel();
        label14.setText("their credit card information. Please do not include");
        label14.setBounds(25, 260, 400, 15);

        JLabel label15 = new JLabel();
        label15.setText("spaces in the credit card number.");
        label15.setBounds(25, 280, 400, 15);

        JLabel label16 = new JLabel();
        label16.setText("5. When done, press enter and then press done.");
        label16.setBounds(10, 300, 400, 15);

        JLabel label17 = new JLabel();
        label17.setText("6. You are finally done! Here are your receipts.");
        label17.setBounds(10, 320, 400, 15);

        helpPanel.add(label1);
        helpPanel.add(label2);
        helpPanel.add(label3);
        helpPanel.add(label4);
        helpPanel.add(label5);
        helpPanel.add(label6);
        helpPanel.add(label7);
        helpPanel.add(label8);
        helpPanel.add(label9);
        helpPanel.add(label10);
        helpPanel.add(label11);
        helpPanel.add(label12);
        helpPanel.add(label13);
        helpPanel.add(label14);
        helpPanel.add(label15);
        helpPanel.add(label16);
        helpPanel.add(label17);
        /////Help Panel



        //Creating and Adding help and reutn button for the frame menubar
        
        //Button to return user to the main menu in case they forgot something
        mMButton = new JButton("Return");
        mMButton.setVisible(false);
        mMButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(tracker == 1) {
                    layout.show(deck, "Login Panel");
                    mMButton.setVisible(false);
                    help.setVisible(true);
                } else if(tracker == 0) {
                    Main.frame.setVisible(true);
                    Main.help.setVisible(true);
                    frame.setVisible(false);
                } else {
                    layout.show(deck, "Main Menu");
                    mMButton.setVisible(false);
                    help.setVisible(true);
                }
                 
            }
        });

        //Button to take user to help panel in case they need instructions
        help = new JButton("Help");
        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                help.setVisible(false);
            layout.show(deck, "Help");
            mMButton.setVisible(true);
            }
        });
 
        mbFrame.add(help);
        mbFrame.add(mMButton);

        //Adding panels to the "deck" in order/semantically
        deck.add(loginPanel, "Login Panel");
        deck.add(mainMenu, "Main Menu");
        deck.add(namePeople, "Name People");
        deck.add(inputImage, "Input Image");
        deck.add(ccbilling, "CC Billing");
        deck.add(reciepts, "Reciepts");
        deck.add(helpPanel, "Help");
        
        

        //More frame configuration
        frame.add(BorderLayout.NORTH, mbFrame);
        frame.getContentPane().add(deck);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
   }
}
