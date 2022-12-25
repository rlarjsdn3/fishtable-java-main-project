package databaseProject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

public class MoreInformationFrame extends JFrame implements ActionListener {
	JPanel north, west, center, east, south;
	JPanel p01, p02, p03, p04, p05, p06;
	JPanel p07, p08, p09, p10, p11, p12;
	JPanel p13, p14, p15, p16, p17, p18;
	
	JLabel titleLabel, commonNameLabel, scientificNameLabel;
	JLabel divisionLabel, divisionData, sizeLabel, sizeData, colorLabel, colorData;
	JLabel swimmingAreaLabel, swimmingAreaData, aggresionLabel, aggresionData, tasteLabel, tasteData;
	JLabel minimumCelsiusLabel, minimumCelsiusData, maximumCelsiusLabel, maximumCelsiusData;
	JLabel properPHLabel, properPHData, properGHLabel, properGHData, breedDifficultyLabel;
	JLabel breedDifficultyData, breedingDifficultyLabel, breedingDifficultyData;
	JLabel lastModifiedDateLabel, lastModifiedDateData;
	
	JLabel imageLabel;
	
	TextArea textData;

	JButton okButton;
	
	Color mainColor = new Color(147, 59, 201);
	
	Font titleFont = new Font("나눔고딕", Font.BOLD, 20);
	Font labelPlainFont = new Font("나눔고딕", Font.PLAIN, 16);
	Font labelBoldFont = new Font("나눔고딕", Font.BOLD, 16);
	
	MoreInformationFrame() {
		super("관상어 DB 시스템");

		titleLabel = new JLabel("관상어 상세 정보");
		titleLabel.setFont(titleFont);
		titleLabel.setForeground(Color.white);
		
		commonNameLabel = new JLabel();
		commonNameLabel.setFont(titleFont = new Font("나눔고딕", Font.BOLD, 24));
		commonNameLabel.setText(FishData.getCommonName());
		
		scientificNameLabel = new JLabel();
		scientificNameLabel.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		if(!FishData.getScientificName().equals("")) {
			scientificNameLabel.setText("(" + FishData.getScientificName() + ")");
		} else {
			scientificNameLabel.setText("");
		}
		
		divisionLabel = new JLabel("분류: ");
		divisionLabel.setFont(labelBoldFont);
		
		divisionData = new JLabel();
		divisionData.setFont(labelPlainFont);
		divisionData.setText(FishData.getDivision());
		
		sizeLabel = new JLabel("최대 크기: ");
		sizeLabel.setFont(labelBoldFont);
		
		sizeData = new JLabel();
		sizeData.setFont(labelPlainFont);
		sizeData.setText(FishData.getSize());
		
		colorLabel = new JLabel("색상:");
		colorLabel.setFont(labelBoldFont);
		
		colorData = new JLabel();
		colorData.setFont(labelPlainFont);
		String color = "";
		int colorCnt = 0;
		try {
			colorCnt = FishData.getColor().length();
		} catch(NullPointerException npe) {
			colorCnt = 0;
		} finally {
			switch(colorCnt) {
			case 0:
				break;
			case 2:
				color = FishData.getColor().substring(0, 2);
				break;
			case 5:
				color = FishData.getColor().substring(0, 2)
							+ "/" + FishData.getColor().substring(3, 5);
			
			}
		}
		colorData.setText(color);
		
		swimmingAreaLabel = new JLabel("유영 영역: ");
		swimmingAreaLabel.setFont(labelBoldFont);
		
		swimmingAreaData = new JLabel();
		swimmingAreaData.setFont(labelPlainFont);
		swimmingAreaData.setText(FishData.getSwimmingArea());
		
		aggresionLabel = new JLabel("공  격  성: ");
		aggresionLabel.setFont(labelBoldFont);
		
		aggresionData = new JLabel();
		aggresionData.setFont(labelPlainFont);
		aggresionData.setText(FishData.getAggresion());
		
		tasteLabel = new JLabel("식       성: ");
		tasteLabel.setFont(labelBoldFont);
		
		tasteData = new JLabel();
		tasteData.setFont(labelPlainFont);
		tasteData.setText(FishData.getTaste());
		
		textData = new TextArea();
		textData.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		textData.setBackground(new Color(238, 238, 238));
		textData.setEditable(false);
		textData.setText(FishData.getText());
		
		minimumCelsiusLabel = new JLabel("최소 온도: ");
		minimumCelsiusLabel.setFont(labelBoldFont);
		
		minimumCelsiusData = new JLabel();
		minimumCelsiusData.setFont(labelPlainFont);
		minimumCelsiusData.setText(FishData.getMinimumCelsius());
		
		maximumCelsiusLabel = new JLabel("최소 온도: ");
		maximumCelsiusLabel.setFont(labelBoldFont);
		
		maximumCelsiusData = new JLabel();
		maximumCelsiusData.setFont(labelPlainFont);
		maximumCelsiusData.setText(FishData.getMaximumCelsius());
		
		properPHLabel = new JLabel("적정   PH: ");
		properPHLabel.setFont(labelBoldFont);
		
		properPHData = new JLabel();
		properPHData.setFont(labelPlainFont);
		properPHData.setText(FishData.getProperPH());
		
		properGHLabel = new JLabel("적정   GH: ");
		properGHLabel.setFont(labelBoldFont);
		
		properGHData = new JLabel();
		properGHData.setFont(labelPlainFont);
		properGHData.setText(FishData.getProperGH());
		
		breedDifficultyLabel = new JLabel("사육 난이도: ");
		breedDifficultyLabel.setFont(labelBoldFont);
		
		breedDifficultyData = new JLabel();
		breedDifficultyData.setFont(labelPlainFont);
		breedDifficultyData.setText(FishData.getBreedDifficulty());
		
		breedingDifficultyLabel = new JLabel("번식 난이도: ");
		breedingDifficultyLabel.setFont(labelBoldFont);
		
		breedingDifficultyData = new JLabel();
		breedingDifficultyData.setFont(labelPlainFont);
		breedingDifficultyData.setText(FishData.getBreedingDifficulty());
		
		lastModifiedDateLabel = new JLabel("최근 수정일: ");
		lastModifiedDateLabel.setFont(labelBoldFont);
		
		lastModifiedDateData = new JLabel();
		lastModifiedDateData.setFont(labelPlainFont);
		lastModifiedDateData.setText(FishData.getLastModifiedDate());
		
		ImageIcon image = ImageFrame.getImage(FishData.getImageFileDirectory()
				+FishData.getImageFileName(), 300, 400);
		
		imageLabel = new JLabel(image);
		
		okButton = new JButton("확인");
		okButton.setForeground(mainColor);
	
		//...
		
		p01 = new JPanel(); p02 = new JPanel(); p03 = new JPanel();
		p04 = new JPanel(); p05 = new JPanel(); p06 = new JPanel();
		p07 = new JPanel(); p08 = new JPanel(); p09 = new JPanel();
		p10 = new JPanel(); p11 = new JPanel(); p12 = new JPanel();
		p13 = new JPanel(); p14 = new JPanel(); p15 = new JPanel();
		p16 = new JPanel(); p17 = new JPanel(); p18 = new JPanel();
		
		north = new JPanel();
		north.setPreferredSize(new Dimension(700, 40));
		add(north, BorderLayout.NORTH);
		north.setBackground(mainColor);
		north.add(titleLabel);
		
		west = new JPanel(); 
		west.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		west.setPreferredSize(new Dimension(300, 400));
		add(west, BorderLayout.WEST);
		west.add(imageLabel);
		
		center = new JPanel(); 
		center.setPreferredSize(new Dimension(300, 300));
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(10, 1));
		center.add(p01); // 이름
		center.add(p02); // 학명
		center.add(p03); // 분류
		center.add(p04); // 색상
		center.add(p05); // 최대 크기
		center.add(p06); // 유영 영역
		center.add(p07); // 공격성
		center.add(p08); // 식성
		center.add(p09); // 설명
		
		east = new JPanel();
		east.setPreferredSize(new Dimension(300, 300));
		add(east, BorderLayout.EAST);
		east.setLayout(new GridLayout(10, 1));
		east.add(p10); 
		east.add(p11); 
		east.add(p12); // 최소 온도
		east.add(p13); // 최대 온도
		east.add(p14); // 적정 PH
		east.add(p15); // 적정 GH
		east.add(p16); // 사육 난이도 
		east.add(p17); // 번식 난이도
		east.add(p18); // 최근 수정일
		
		
		south = new JPanel();
		south.setPreferredSize(new Dimension(700, 40));
		add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 12, 5));
		south.setBackground(Color.white);
		south.add(okButton);
		
		p01.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		p01.add(commonNameLabel);
		
		p02.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		p02.add(scientificNameLabel);
		
		// p03은 공백 레이아웃
		
		p03.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p03.add(divisionLabel);
		p03.add(divisionData);
		
		p04.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p04.add(colorLabel);
		p04.add(colorData);
		
		p05.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p05.add(sizeLabel);
		p05.add(sizeData);
		
		p06.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p06.add(swimmingAreaLabel);
		p06.add(swimmingAreaData);
		
		p07.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p07.add(aggresionLabel);
		p07.add(aggresionData);
		
		p08.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p08.add(tasteLabel);
		p08.add(tasteData);
		
		p09.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p09.add(textData);
		
		//...
		
		
		p12.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		p12.add(minimumCelsiusLabel);
		p12.add(minimumCelsiusData);

		p13.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		p13.add(maximumCelsiusLabel);
		p13.add(maximumCelsiusData);
		
		p14.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p14.add(properPHLabel);
		p14.add(properPHData);
		
		p15.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p15.add(properGHLabel);
		p15.add(properGHData);
		
		p16.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p16.add(breedDifficultyLabel);
		p16.add(breedDifficultyData);
		
		p17.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p17.add(breedingDifficultyLabel);
		p17.add(breedingDifficultyData);
		
		p18.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p18.add(lastModifiedDateLabel);
		p18.add(lastModifiedDateData);
		
		//...
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width/2-200, screenSize.height/2-400);
		
		setSize(1000, 500);
		setVisible(true);
		
		//...
		
		okButton.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = (Object) e.getSource();
		
		if(obj.equals(okButton)) {
			this.setVisible(false);
			this.dispose();
		}
		
	}
}
