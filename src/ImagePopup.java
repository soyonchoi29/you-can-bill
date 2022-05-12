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
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseAdapter;

public class ImagePopup extends JFrame implements MouseListener, MouseMotionListener {
    private boolean cropping = false;
    private static BufferedImage image;
    private static int imageWidth, imageHeight;
    private static int scaledImageWidth, scaledImageHeight;
    //static float scaleFactor = 1;
    static File inputFile;
    private Point imagePosition;
    private int xImage, yImage;
    private int x1, y1, x2, y2;
    static int counter = 0;
    private JFrame frame = new JFrame("Selected receipt");
    private JLabel picLabel = new JLabel();
    private JLayeredPane base = new JLayeredPane();
    private JPanel rectangle = new JPanel();

    public static void drawNew(File input) {
        new ImagePopup().crop(input);
        try{
            image = ImageIO.read(input);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            inputFile = input;
        }
        catch(IOException error){}
    }

    public void crop(File input) {

        //Creates panel that will contain receipt
        JPanel panel = new JPanel();
        panel.setSize(500,600);
        panel.setVisible(true);
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Sets image if it hasn't been yet
        try{
            image = ImageIO.read(input);
            imageWidth = image.getWidth();
            imageHeight = image.getHeight();
            inputFile = input;
        }
        catch(IOException error){}

        //Changes image size until it fits into JPanel
        scaledImageWidth = image.getWidth();
        scaledImageHeight = image.getHeight();

        if (scaledImageWidth > scaledImageHeight){
            while (scaledImageWidth >= 500){
                scaledImageWidth *= 0.7;
                scaledImageHeight *= 0.7;
                //scaleFactor *= 0.7; 
            }
        }
        if (scaledImageHeight > scaledImageWidth){
            while (scaledImageHeight >= 500){
                scaledImageWidth *= 0.7;
                scaledImageHeight *= 0.7;
                //scaleFactor *= 0.7;
            }
        }

        //Resizes image to fit entire upload into JPanel
        Image resized = image.getScaledInstance(scaledImageWidth, scaledImageHeight, Image.SCALE_SMOOTH);

        //Puts resized image in the frame
        picLabel.setIcon(new ImageIcon(resized));
        panel.add(picLabel);
        Rectangle imageBounds = picLabel.getBounds();
        imagePosition = imageBounds.getLocation();
        frame.setVisible(true); // May be redundant

        base.add(panel);
        frame.add(base);
        frame.setSize(500, 600);
        frame.setVisible(true);
        //Maybe some sort of an instructional popup

        //In case user inputs an image in the wrong orientation, button to fix it
        //JButton rotateB = new JButton("Rotate Image");
        //rotateB.setBounds(0, 0, 50, 30); // Might need to move
        //panel.add(rotateB);
        //rotateB.addActionListener(new ActionListener(){

        //    @Override
        //    public void actionPerformed(ActionEvent e){
        //        counter++;
                //System.out.println("reeeeeee");
                //Get the components in the panel
        //        Component[] componentList = panel.getComponents();

                //Loop through the components
        //        for(Component c : componentList){ // https://stackoverflow.com/questions/7117332/dynamically-remove-component-from-jpanel

                    //Find the components you want to remove
        //            if(c instanceof JLabel){

                        //Remove it
        //                panel.remove(c);
        //            }
        //        }
        //        panel.validate();
        //        panel.repaint();
        //        if (counter % 2 == 0){
        //            AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90 * counter), scaledImageWidth/2, scaledImageHeight/2); // ROTATE BY 90 (CAN CHANGE)
        //            AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        //            BufferedImage rotated = new BufferedImage(scaledImageWidth, scaledImageHeight, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
        //            Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
        //            redraw.drawImage(resized, 0, 0, null);
        //            redraw.dispose();

        //            JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
        //            panel.add(picLabel);
        //        } else if (counter % 2 == 1){
        //            AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90 * counter), scaledImageHeight/2, scaledImageWidth/2); // ROTATE BY 90 (CAN CHANGE)
        //            AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        //            BufferedImage rotated = new BufferedImage(scaledImageHeight, scaledImageWidth, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
        //            Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
        //            redraw.drawImage(resized, 0, 0, null);
        //            redraw.dispose();

        //            JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
        //            panel.add(picLabel);
        //        }
        //        frame.setVisible(true);
        //    }            
        //});

        JMenuBar recieptMenu = new JMenuBar();
        //Button to go "back" to main menu
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.setVisible(false);
            }
        });
        //Menu to choose who the cropped images are for
        JMenu pickPers = new JMenu("Pick a Person to Add images");
        for(int i = 0; i < personHolder.length(); i++) {
            String name = personHolder.getPerson(i).getName();
            JMenuItem names = new JMenuItem(name);
            pickPers.add(names);
            names.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ImageChange.ImageStitch(name);
                }
            });
        }

        

        recieptMenu.add(pickPers);
        recieptMenu.add(back);
        frame.setJMenuBar(recieptMenu);
        frame.setVisible(true);

    }

    // Draws a rectangle around the area of the image that you cropped
    public void drawRect() {
        base.remove(rectangle);
        int width = Math.abs(x1-x2);
        int height = Math.abs(y1-y2); // https://www.javatpoint.com/java-jlayeredpane
        rectangle.setBorder(BorderFactory.createLineBorder(Color.black)); // https://docs.oracle.com/javase/tutorial/uiswing/components/border.html
        if (x1-x2 >= 0 && y1-y2 >= 0) {
            rectangle.setBounds(x2, y2, width, height); // https://coderanch.com/t/632120/java/Add-Rectangle-JFrame
        } else if (y1-y2 >= 0) {
            rectangle.setBounds(x1, y2, width, height); // https://docs.oracle.com/javase/7/docs/api/javax/swing/JLayeredPane.html#moveToFront(java.awt.Component)
        } else if (x1-x2 >= 0) {
            rectangle.setBounds(x2, y1, width, height); // https://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
        } else {
            rectangle.setBounds(x1, y1, width, height); //https://microeducate.tech/placing-a-transparent-jpanel-on-top-of-another-jpanel-not-working/
        }
        rectangle.setOpaque(false);
        rectangle.setVisible(true);
        base.add(rectangle);
        base.moveToFront(rectangle);
        frame.validate();
        frame.repaint();
        frame.setVisible(true);
        }
    
    //Calls cropImage function in imageChange class
    public void cropImage() throws Exception {
        float width = Math.abs(x1-x2);
        float height = Math.abs(y1-y2);

        float widthRatio = imageWidth / (float)scaledImageWidth;
        float heightRatio = imageHeight / (float)scaledImageHeight;

        xImage = (int) picLabel.getLocation().getX();
        yImage = (int) picLabel.getLocation().getY();

        int cropWidth = (int) (width * widthRatio);
        int cropHeight = (int) (height * heightRatio);

        // System.out.println(x1);
        // System.out.println(y1);
        // System.out.println("scaledImageWidth = " + scaledImageWidth);
        // System.out.println("scaledImageHeight = " + scaledImageHeight);
        // System.out.println("mouse width = " + width);
        // System.out.println("mouse height = " + height);
        // System.out.println(widthRatio);
        // System.out.println(heightRatio);

    
        image = ImageIO.read(inputFile);
        ImageChange.imageCrop(image, (int)(Math.min(x1 - xImage, x2 - xImage)*widthRatio), (int)(Math.min(y1 - yImage, y2 - yImage)*heightRatio), cropWidth, cropHeight);
    }

    //Record x and y values of when mouse is first pressed
    @Override
    public void mousePressed(MouseEvent e) {
        cropping = true;
        x1 = e.getX()-imagePosition.x;
        y1 = e.getY()-imagePosition.y;
    }

    //Once mouse is released after being dragged, record x and y values
    @Override
    public void mouseReleased(MouseEvent e) {
        this.repaint();
        if(cropping == false) {
            x2 = e.getX()-imagePosition.x;
            y2 = e.getY()-imagePosition.y;
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
        this.repaint();
        cropping = false;
        x2 = e.getX()-imagePosition.x;
        y2 = e.getY()-imagePosition.y;
        drawRect();
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