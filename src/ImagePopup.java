import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.lang.Math;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;

public class ImagePopup extends JFrame implements MouseListener, MouseMotionListener {
    private boolean cropping = false;
    static BufferedImage image;
    static int scaledImageWidth;
    static int scaledImageHeight;
    static float scaleFactor = 1;
    static File inputFile;
    private int x, y;
    private int x1, y1, x2, y2;
    static int counter = 0;

    public static void drawNew(File input) {
        new ImagePopup().crop(input);
        try{
            image = ImageIO.read(input);
            inputFile = input;
        }
        catch(IOException error){}
    }

    public void crop(File input) {

        //Creates frame and panel that will contain reciept
        JFrame frame = new JFrame("Selected receipt"); // change later???
        JPanel panel = new JPanel();
        panel.setSize(500,600);
        panel.setVisible(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener (this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Sets image if it hasn't been yet
        try{
            image = ImageIO.read(input);
            inputFile = input;
        }
        catch(IOException error){}

        //Changes image size until it fits into JPanel
        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        if (imageWidth > imageHeight){
            while (imageWidth >= 500){
                imageWidth *= 0.7;
                imageHeight *= 0.7;
                scaleFactor *= 0.7;
            }
        }
        if (imageHeight > imageWidth){
            while (imageHeight >= 500){
                imageWidth *= 0.7;
                imageHeight *= 0.7;
                scaleFactor *= 0.7;
            }
        }
        scaledImageWidth = imageWidth;
        scaledImageHeight = imageHeight;

        //Resizes image to fit entire upload into JPanel
        Image resized = image.getScaledInstance(imageWidth, imageHeight, 2); 

        //AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90), 200, 250); // ROTATE BY 90 (CAN CHANGE) - DON'T NEED IF BUTTONS
        //AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR); // some sort of rotation option
        //BufferedImage rotated = new BufferedImage(400, 500, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
        //Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
        //redraw.drawImage(resized, 0, 0, null);
        //redraw.dispose();

        //Puts resized image in the frame
        JLabel picLabel = new JLabel(new ImageIcon(resized));
        panel.add(picLabel);
        frame.setVisible(true);

        //JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
        // JLabel picLabel = new JLabel(new ImageIcon(resized));
        // panel.add(picLabel);
        frame.add(panel);
        frame.setSize(500, 600);
        frame.setVisible(true);
        // Looking for while button not being pressed
        //Maybe some sort of an instructional popup

        //In case user inputs an image in the wrong orientation, button to fix it
        JButton rotateB = new JButton("Rotate Image");
        rotateB.setBounds(0, 0, 50, 30); // Might need to move
        panel.add(rotateB);
        rotateB.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                counter++;
                //System.out.println("reeeeeee");
                //Get the components in the panel
                Component[] componentList = panel.getComponents();

                //Loop through the components
                for(Component c : componentList){ // https://stackoverflow.com/questions/7117332/dynamically-remove-component-from-jpanel

                    //Find the components you want to remove
                    if(c instanceof JLabel){

                        //Remove it
                        panel.remove(c);
                    }
                }
                panel.validate();
                panel.repaint(); // only rotates the first time, then doesn't rotate again - maybe solve with a call to external method???
                AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90 * counter), 250, 250); // ROTATE BY 90 (CAN CHANGE)
                AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
                BufferedImage rotated = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
                Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
                redraw.drawImage(resized, 0, 0, null);
                redraw.dispose();

                JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
                panel.add(picLabel);
                frame.setVisible(true);
            }
            
        });

        //Button to go "back" to main menu
        JButton mainMenu = new JButton("Main Menu");
        mainMenu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
            }
        });
        panel.add(mainMenu);
        frame.setVisible(true);

    }

    private Color cropToolColor = new Color(201, 221, 240);

    //Supposed to draw a rectangle around area that you cropped image
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);

        g.setColor(cropToolColor);
        g.drawRect(Math.min(x1,x2), Math.min(y1,y2), width, height);
    }
    
    //Calls cropImage function in imageChange class
    public void cropImage() throws Exception {
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2);

        // System.out.println(x1);
        // System.out.println(y1);

        image = ImageIO.read(inputFile);
        ImageChange.imageCrop(image, (int)(Math.min(x1, x2)/scaleFactor), (int)(Math.max(y1, y2)/scaleFactor), (int)(width/scaleFactor), (int)(height/scaleFactor));
    }

    //Record x and y values of when mouse is first pressed
    @Override
    public void mousePressed(MouseEvent e) {
        cropping = true;
        x1 = e.getX()-x;
        y1 = e.getY()-y;
    }

    //Once mouse is released after being dragged, record x and y values
    @Override
    public void mouseReleased(MouseEvent e) {
        repaint();
        if(cropping == false) {
            x2 = e.getX()-x;
            y2 = e.getY()-y;
            try {
                cropImage();
            }
            catch(Exception a){
                a.printStackTrace();
            }
        }
    }

    //As mouse is being dragged, record x and y values
    @Override
    public void mouseDragged (MouseEvent e) {
        repaint();
        cropping = false;
        x2 = e.getX()-x;
        y2 = e.getY()-y;
    }

    @Override
    public void mouseMoved(MouseEvent e) {}
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
}

//Based on https://www.javaexercise.com/java/add-an-image-to-a-jpanel
//https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html#getScaledInstance(int,%20int,%20int)
// https://mkyong.com/java/how-to-resize-an-image-in-java/
//https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
//https://stackoverflow.com/questions/8639567/java-rotating-images