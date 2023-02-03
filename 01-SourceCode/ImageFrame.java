package databaseProject;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ImageFrame extends JFrame implements ActionListener {
	JPanel north, center, south;
	JLabel titleLabel, imageLabel;
	JButton exitButton;
	
	ImageFrame(String imageFilePath) {
		this(imageFilePath, 250, 250);
	}
	
	ImageFrame(String imageFilePath, int width, int height) {
		super("관상어 DB 시스템");
		
		north = new JPanel();
		center = new JPanel();
		south = new JPanel();
		titleLabel = new JLabel("이미지 보기");
		imageLabel = new JLabel(getImage(imageFilePath, width, height));
		exitButton = new JButton("닫기");
		
		titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		
		setLayout(new BorderLayout());
		add(north, BorderLayout.NORTH);
		north.setPreferredSize(new Dimension(width, 35));
		north.setBackground(Color.LIGHT_GRAY);
		north.add(titleLabel);
		
		add(center, BorderLayout.CENTER);
		center.setPreferredSize(new Dimension(width, height));
		center.setBackground(Color.LIGHT_GRAY);
		center.add(imageLabel);
		
		add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 5));
		south.setPreferredSize(new Dimension(width, 40));
		south.setBackground(Color.white);
		south.add(exitButton);
		
		setSize(width, height+100);
		setVisible(true);
		
		exitButton.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
			}
		});
	}
	
	public static ImageIcon getImage(String imageFilePath, int width, int height) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File(imageFilePath));
		} catch(Exception ie) {
			//System.out.println("예외: ImageFrame > getImage() > IOExcpetion");
			return null;
		}
		
		return new ImageIcon(bufferedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = (Object) e.getSource();
		
		if(obj.equals(exitButton)) {
			this.setVisible(false);
			this.dispose();
		}
	}
}
