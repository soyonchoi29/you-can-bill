
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
import javax.swing.filechooser.FileNameExtensionFilter;

import org.w3c.dom.events.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import java.awt.FlowLayout;

public class ImagePopup {
    //public static void main(String[] args) throws IOException {
    //}

    static int counter = 0;
    public static void drawNew(File input) {
        JFrame frame = new JFrame("Selected receipt"); // change later???
        JPanel panel = new JPanel();
        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(input);
        } catch (IOException error) {}

        JButton rotateB = new JButton("Rotate Image");
        rotateB.setBounds(0, 0, 50, 30); // Might need to move
        panel.add(rotateB);

        Image resized = myPicture.getScaledInstance(500, 500, 2); // Resizes image to fit entire upload into JPanel
        //AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90), 200, 250); // ROTATE BY 90 (CAN CHANGE) - DON'T NEED IF BUTTONS
        //AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR); // some sort of rotation option
        //BufferedImage rotated = new BufferedImage(400, 500, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
        //Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
        //redraw.drawImage(resized, 0, 0, null);
        //redraw.dispose();

        //JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
        JLabel picLabel = new JLabel(new ImageIcon(resized));
        panel.add(picLabel);
        frame.add(panel);
        frame.setSize(500, 600); // perhaps cropping is drawing new jpanels on top or something
        frame.setVisible(true); // will need to recenter receipt better most likely
        // Looking for while button not being pressed
        //Maybe some sort of an instructional popup
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
    }

    public static void redraw(JLabel newDraw) {
        //drawNew.add(picLabel);
        //frame.setVisible(true);
    }

}

class JImageCropComponent extends JComponent implements MouseListener, MouseMotionListener{
    private BufferedImage image;
    private BufferedImage croppedImage;
    private int x1, y1, x2, y2;
    private Color cropToolColor = new Color(201, 221, 240);
    private boolean cropping = false;

    public JImageCropComponent(BufferedImage image){
        this.image = image;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    @Override
    public void paintComponent(Graphics g){
        if (cropping){
            //Mark the area being cropped
            g.setColor(cropToolColor);
            g.drawRect(Math.min(x1,x2), Math.min(y1,y2), Math.max(x1,x2)-Math.min(x1,x2), Math.max(y1,y2)-Math.min(y1,y2));
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        this.x1 = e.getClientX();
        this.x2 = e.getClientY();
    }

    @Override
    public void mouseReleased(MouseEvent e){
        this.cropping = false;
        this.croppedImage = this.image.getSubimage(Math.min(x1,x2), Math.min(y1,y2), Math.max(x1,x2)-Math.min(x1,x2), Math.max(y1,y2)-Math.min(y1,y2));
    }

    @Override
    public void mouseDragged(MouseEvent e){
        this.cropping = true;
        this.x2 = e.getClientX();
        this.y2 = e.getClientY();
    }
}

//Based on https://www.javaexercise.com/java/add-an-image-to-a-jpanel
//https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html#getScaledInstance(int,%20int,%20int)
// https://mkyong.com/java/how-to-resize-an-image-in-java/
//https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
//https://stackoverflow.com/questions/8639567/java-rotating-images