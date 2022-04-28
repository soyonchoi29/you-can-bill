
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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.FlowLayout;

public class ImagePopup {
    //public static void main(String[] args) throws IOException {
    //}

    public static void drawNew(File input) {
        try {
            JFrame frame = new JFrame("Selected receipt"); // change later???
            JPanel panel = new JPanel();
            BufferedImage myPicture = ImageIO.read(input);
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
            frame.add(panel); // Button to rotate
            frame.setSize(500, 600); // perhaps cropping is drawing new jpanels on top or something
            frame.setVisible(true); // will need to recenter receipt better most likely
            // Looking for while button not being pressed
            //Maybe some sort of an instructional popup
            JButton rotateB = new JButton("Rotate Image");
            rotateB.setBounds(0, 0, 50, 30);
            panel.add(rotateB);
            rotateB.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e){
                    System.out.println("reeeeeee");
                    panel.remove(picLabel);
                    panel.validate();
                    panel.repaint(); // only rotates the first time, then doesn't rotate again - maybe solve with a call to external method???
                    AffineTransform transform = AffineTransform.getRotateInstance(Math.toRadians (90), 250, 250); // ROTATE BY 90 (CAN CHANGE)
                    AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR); // some sort of rotation option
                    BufferedImage rotated = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB); // Creates new BufferedImage, then fills it in with rotated/scaled version
                    Graphics2D redraw = rotated.createGraphics(); // Turns rotated into Graphics2D object and draws it
                    redraw.drawImage(resized, 0, 0, null);
                    redraw.dispose();
        
                    JLabel picLabel = new JLabel(new ImageIcon(operation.filter(rotated, null)));
                    panel.add(picLabel);
                    frame.setVisible(true);
                }

            });
        } catch (IOException error) {}
    }
}

//Based on https://www.javaexercise.com/java/add-an-image-to-a-jpanel
//https://docs.oracle.com/javase/7/docs/api/java/awt/Image.html#getScaledInstance(int,%20int,%20int)
// https://mkyong.com/java/how-to-resize-an-image-in-java/
//https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
//https://stackoverflow.com/questions/8639567/java-rotating-images