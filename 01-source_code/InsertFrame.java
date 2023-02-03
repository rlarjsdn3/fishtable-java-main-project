package databaseProject;

import java.awt.Choice;
import java.awt.TextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class InsertFrame extends JFrame implements ConnectDB, ActionListener, TextListener {
	JPanel north, center, south;
	JPanel p01, p02, p03, p04, p05, p06;
	JPanel p07, p08, p09, p10, p11, p12;
	JPanel p13, p14, p15, p16, p17, p18;
	JPanel p19;
	
	JLabel titleLabel, imageLabel, commonNameLabel, scientificNameLabel;
	JLabel dividionLabel, sizeLabel, colorLabel, swimmingLabel, aggresionLabel, tasteLabel;
	JLabel minimumCelsiusLabel, maximumCelsiusLabel, properPHLabel, properGHLabel;
	JLabel breedDiffcultyLabel, breedingDifficultyLabel, textLabel;
	JLabel notificationLabel, statusLabel;
	
	TextField imageTextField, commonNameTextField, scientificNameTextfield;
	TextField sizeTextField, minimumCelsiusTextField, maximumCelsiusTextField;
	TextField properPHTextField, properGHTextField, textTextField;
	Choice color1Choice, color2Choice, divisionChoice; 
	Choice swimmingAreaChoice, aggresionChoice, tastChoice, breedDifficultyChoice, breedingDifficultyChoice;
	JButton insertImageButton, deleteImageButton, showImageButton, okButton, cancelButton;
	
	Font labelBoldFont = new Font("나눔고딕", Font.BOLD, 16);
	
	Color mainColor = new Color(34, 167, 244);
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	MainFrame mainFrame = null;
	ImageFrame ifm = null;
	
	String loadFileName = FishData.getImageFileName();
	String loadFileDirectory = loadFileDirectory = FishData.getImageFileDirectory();
	//										  0		1	  2		3	  4		5	 
	boolean[] buttonEnabler = new boolean[] { true, true, true, true, true, true };
	
	InsertFrame(MainFrame mf) {
		super("관상어 DB 시스템");
		this.mainFrame = mf;
		openDB();
		
		titleLabel = new JLabel("관상어 데이터 추가하기");
		titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		titleLabel.setForeground(Color.white);
		
		imageLabel = new JLabel("이미지: ");
		imageLabel.setFont(labelBoldFont);
		
		imageTextField = new TextField(23);
		imageTextField.setEditable(false);
		imageTextField.setText("(이미지 없음)");
		
		commonNameLabel = new JLabel("이름: ");
		commonNameLabel.setFont(labelBoldFont);
		
		commonNameTextField = new TextField(26);
		
		scientificNameLabel = new JLabel("학명: ");
		scientificNameLabel.setFont(labelBoldFont);
	
		scientificNameTextfield = new TextField(26);
		
		dividionLabel = new JLabel("분류: ");
		dividionLabel.setFont(labelBoldFont);
		
		divisionChoice= new Choice();
		divisionChoice.add("(선택)                                 ");
		divisionChoice.add("열대어");
		divisionChoice.add("해수어");
		divisionChoice.add("갑각류");
		divisionChoice.add("연체류");
		
		sizeLabel = new JLabel("최대 크기: ");
		sizeLabel.setFont(labelBoldFont);
		
		sizeTextField = new TextField(21);
		
		colorLabel = new JLabel("색상:");
		colorLabel.setFont(labelBoldFont);
		
		color1Choice = new Choice();
		color1Choice.add("(선택1)  ");
		color1Choice.add("녹색");
		color1Choice.add("노랑");
		color1Choice.add("주황");
		color1Choice.add("빨강");
		color1Choice.add("분홍");
		color1Choice.add("보라");
		color1Choice.add("파랑");
		color1Choice.add("검정");
		color1Choice.add("갈색");
		color1Choice.add("회색");
		color1Choice.add("은색");
				
		color2Choice = new Choice();
		color2Choice.add("(선택2)  ");
		color2Choice.add("녹색");
		color2Choice.add("노랑");
		color2Choice.add("주황");
		color2Choice.add("빨강");
		color2Choice.add("분홍");
		color2Choice.add("보라");
		color2Choice.add("파랑");
		color2Choice.add("검정");
		color2Choice.add("갈색");
		color2Choice.add("회색");
		color2Choice.add("은색");
		
		swimmingLabel = new JLabel("유영 영역: ");
		swimmingLabel.setFont(labelBoldFont);
		
		swimmingAreaChoice = new Choice();
		swimmingAreaChoice.add("(선택)                        ");
		swimmingAreaChoice.add("상층");
		swimmingAreaChoice.add("중상층");
		swimmingAreaChoice.add("중층");
		swimmingAreaChoice.add("중하층");
		swimmingAreaChoice.add("하층");
	
		aggresionLabel = new JLabel("공  격  성: ");
		aggresionLabel.setFont(labelBoldFont);
		
		aggresionChoice = new Choice();
		aggresionChoice.add("(선택)                        ");
		aggresionChoice.add("공격적");
		aggresionChoice.add("약간 공격적");
		aggresionChoice.add("온순함");
		
		tasteLabel = new JLabel("식       성: ");
		tasteLabel.setFont(labelBoldFont);
		
		tastChoice = new Choice();
		tastChoice.add("(선택)                        ");
		tastChoice.add("잡식성");
		tastChoice.add("초식성");
		tastChoice.add("육식성");
		
		minimumCelsiusLabel = new JLabel("최소 온도: ");
		minimumCelsiusLabel.setFont(labelBoldFont);
		
		minimumCelsiusTextField = new TextField(21);
		
		maximumCelsiusLabel = new JLabel("최대 온도: ");
		maximumCelsiusLabel.setFont(labelBoldFont);
		
		maximumCelsiusTextField = new TextField(21);
		
		properPHLabel = new JLabel("적정 PH: ");
		properPHLabel.setFont(labelBoldFont);
		
		properPHTextField = new TextField(21);
		
		properGHLabel = new JLabel("적정 GH: ");
		properGHLabel.setFont(labelBoldFont);
		
		properGHTextField = new TextField(21);
		
		breedDiffcultyLabel = new JLabel("사육 난이도: ");
		breedDiffcultyLabel.setFont(labelBoldFont);
		
		breedDifficultyChoice = new Choice();
		breedDifficultyChoice.add("(선택)                     ");
		breedDifficultyChoice.add("어려운 난이도");
		breedDifficultyChoice.add("중간 난이도");
		breedDifficultyChoice.add("쉬운 난이도");
		
		
		breedingDifficultyLabel = new JLabel("번식 난이도: ");
		breedingDifficultyLabel.setFont(labelBoldFont);
		
		breedingDifficultyChoice = new Choice();
		breedingDifficultyChoice.add("(선택)                     ");
		breedingDifficultyChoice.add("어려운 난이도");
		breedingDifficultyChoice.add("중간 난이도");
		breedingDifficultyChoice.add("쉬운 난이도");
		
		textLabel = new JLabel("설명: ");
		textLabel.setFont(labelBoldFont);
		
		textTextField = new TextField(26);
		
		statusLabel = new JLabel("▷ 이름은 고유한 값이어야 합니다.");
		statusLabel.setFont(new Font("나눔고딕", Font.PLAIN, 12));
		
		notificationLabel = new JLabel("▶ 「이름」은 필수 입력 항목입니다.");
		
		insertImageButton = new JButton("삽입");
		deleteImageButton = new JButton("삭제");
		showImageButton = new JButton("이미지 보기");
		
		okButton = new JButton("추가");
		okButton.setForeground(mainColor);
		okButton.setEnabled(false);
		
		cancelButton = new JButton("취소");
		
		//...
	
		p01 = new JPanel(); p02 = new JPanel(); p03 = new JPanel();
		p04 = new JPanel(); p05 = new JPanel(); p06 = new JPanel();
		p07 = new JPanel(); p08 = new JPanel(); p09 = new JPanel();
		p10 = new JPanel(); p11 = new JPanel(); p12 = new JPanel();
		p13 = new JPanel(); p14 = new JPanel(); p15 = new JPanel();
		p16 = new JPanel(); p17 = new JPanel(); p18 = new JPanel();
		p19 = new JPanel();
		
		north = new JPanel();
		north.setPreferredSize(new Dimension(300, 40));
		north.setBackground(mainColor);
		add(north, BorderLayout.NORTH);
		north.add(titleLabel);
		
		center = new JPanel();
		center.setPreferredSize(new Dimension(300, 400));
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(20, 1));
		center.add(p01);
		center.add(p02);
		center.add(p03);
		center.add(p04);
		center.add(p05);
		center.add(p06);
		center.add(p07);
		center.add(p08);
		center.add(p09);
		center.add(p10);
		center.add(p11);
		center.add(p12);
		center.add(p13);
		center.add(p14);
		center.add(p15);
		center.add(p16);
		center.add(p17);
		center.add(p18);
		center.add(p19);
		
		south = new JPanel();
		south.setPreferredSize(new Dimension(300, 50));
		south.setBackground(Color.white);
		add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 12));
		south.add(okButton);
		south.add(cancelButton);
		
		//...
	
		p01.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15));
		p01.add(imageLabel);
		p01.add(imageTextField);
		
		p02.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		p02.add(showImageButton);
		p02.add(insertImageButton);
		p02.add(deleteImageButton);
		
		p03.add(commonNameLabel);
		p03.add(commonNameTextField);
		
		p04.setLayout(new FlowLayout(FlowLayout.LEADING, 58, 5));
		p04.add(statusLabel);
		
		p05.add(scientificNameLabel);
		p05.add(scientificNameTextfield);
		
		p06.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p06.add(dividionLabel);
		p06.add(divisionChoice);
		
		p07.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p07.add(sizeLabel);
		p07.add(sizeTextField);
		
		p08.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p08.add(colorLabel);
		p08.add(color1Choice);
		p08.add(color2Choice);
		
		p09.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p09.add(swimmingLabel);
		p09.add(swimmingAreaChoice);
		
		p10.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p10.add(aggresionLabel);
		p10.add(aggresionChoice);
		
		p11.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p11.add(tasteLabel);
		p11.add(tastChoice);
		
		p12.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p12.add(minimumCelsiusLabel);
		p12.add(minimumCelsiusTextField);
		
		p13.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p13.add(maximumCelsiusLabel);
		p13.add(maximumCelsiusTextField);
		
		p14.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		p14.add(properPHLabel);
		p14.add(properPHTextField);

		p15.setLayout(new FlowLayout(FlowLayout.LEFT, 19, 5));
		p15.add(properGHLabel);
		p15.add(properGHTextField);
		
		p16.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p16.add(breedDiffcultyLabel);
		p16.add(breedDifficultyChoice);
		
		p17.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 5));
		p17.add(breedingDifficultyLabel);
		p17.add(breedingDifficultyChoice);
		
		p18.add(textLabel);
		p18.add(textTextField);
		
		p19.setLayout(new FlowLayout(FlowLayout.LEFT, 16, 0));
		p19.add(notificationLabel);
		
		//...
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width/2-200, screenSize.height/2-400);
		
		setSize(300, 800);
		setVisible(true);
		
		//...
		
		okButton.addActionListener(this);
		cancelButton.addActionListener(this);
		insertImageButton.addActionListener(this);
		deleteImageButton.addActionListener(this);
		showImageButton.addActionListener(this);
		
		commonNameTextField.addTextListener(this);
		sizeTextField.addTextListener(this);
		minimumCelsiusTextField.addTextListener(this);
		maximumCelsiusTextField.addTextListener(this);
		properPHTextField.addTextListener(this);
		properGHTextField.addTextListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String sql;
		Object obj = e.getSource();
		
		if(obj.equals(okButton)) {
			if(JOptionPane.showConfirmDialog(null, "데이터를 추가하시겠습니까?", "메세지", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
				
				// fishTBL(이름, 학명, 분류, 최근 수정일) 데이터를 추가하는 구문
				try {
					sql = "INSERT INTO fishTBL VALUES(?, ?, ?, CURRENT_DATE());";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, commonNameTextField.getText());
					
					if(scientificNameTextfield.getText().length()==0) {
						pstmt.setNull(2, Types.VARCHAR);
					} else {
						pstmt.setString(2, scientificNameTextfield.getText());
					}
					if(divisionChoice.getSelectedItem().trim().equals("(선택)")) {
						pstmt.setString(3, "");
					} else {
						pstmt.setString(3, divisionChoice.getSelectedItem());
					}
					
					pstmt.executeUpdate();
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (fishTBL)");
				}
				
				// featureTBL(최대 크기, 유영 영역, 공격성, 식성) 데이터를 추하는 구문
				try {
					if(!sizeTextField.getText().isEmpty() || 
							!swimmingAreaChoice.getSelectedItem().trim().equals("(선택)") || 
							!aggresionChoice.getSelectedItem().trim().equals("(선택)") || 
							!tastChoice.getSelectedItem().trim().equals("(선택)") ) {
						sql = "INSERT INTO featureTBL VALUES(?, ?, ?, ?, ?);";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
							
						if(sizeTextField.getText().isEmpty()) {
							pstmt.setNull(2, Types.DOUBLE);
						} else {
							pstmt.setDouble(2, Double.valueOf(sizeTextField.getText()));
						}
						if(swimmingAreaChoice.getSelectedItem().trim().equals("(선택)")) {
							pstmt.setNull(3, Types.VARCHAR);
						} else {
							pstmt.setString(3, swimmingAreaChoice.getSelectedItem());
						}
						if(aggresionChoice.getSelectedItem().trim().equals("(선택)")) {
							pstmt.setNull(4, Types.VARCHAR);
						} else {
							pstmt.setString(4, aggresionChoice.getSelectedItem());
						}		
						if(tastChoice.getSelectedItem().trim().equals("(선택)")) {
							pstmt.setNull(5, Types.VARCHAR);
						} else {
							pstmt.setString(5, tastChoice.getSelectedItem());
						}
					
						pstmt.executeUpdate();
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (featureTBL)");
				}
			
				
				// feedingEnvironmentTBL(이름, 최소 온도, 최대 온도, 적정PH, 적정GH) 데이터를 추가하는 구문			
				try {
					if(!minimumCelsiusTextField.getText().isEmpty() || 
							!maximumCelsiusTextField.getText().isEmpty() || 
							!properPHTextField.getText().isEmpty() || 
							!properGHTextField.getText().isEmpty()) {		
						sql = "INSERT INTO feedingEnvironmentTBL VALUES(?, ?, ?, ?, ?);";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
					
						if(minimumCelsiusTextField.getText().isEmpty()) {
							pstmt.setNull(2, Types.DOUBLE);
						} else {
							pstmt.setDouble(2, Double.valueOf(minimumCelsiusTextField.getText()));
						}
						if(maximumCelsiusTextField.getText().isEmpty()) {
							pstmt.setNull(3, Types.DOUBLE);
						} else {
							pstmt.setDouble(3, Double.valueOf(maximumCelsiusTextField.getText()));
						}
						if(properPHTextField.getText().isEmpty()) {
							pstmt.setNull(4, Types.DOUBLE);
						} else {
							pstmt.setDouble(4, Double.valueOf(properPHTextField.getText()));
						}
						if(properGHTextField.getText().isEmpty()) {
							pstmt.setNull(5, Types.DOUBLE);
						} else {
							pstmt.setDouble(5, Double.valueOf(properGHTextField.getText()));
						}	
		
						pstmt.executeUpdate();
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (feedingEnvironmentTBL)");
				}
				
				
				// difficultyTBL(이름, 사육 난이도, 번식 난이도) 데이터를 추가하는 구문
				try {
					if(!breedDifficultyChoice.getSelectedItem().trim().equals("(선택)") || 
							!breedingDifficultyChoice.getSelectedItem().trim().equals("(선택)")) {
						sql = "INSERT INTO difficultyTBL VALUES(?, ?, ?);";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
						
						if(breedDifficultyChoice.getSelectedItem().trim().equals("(선택)")) {
							pstmt.setNull(2, Types.VARCHAR);
						} else {
							pstmt.setString(2, String.valueOf(breedDifficultyChoice.getSelectedItem()));
						}
						if(breedingDifficultyChoice.getSelectedItem().trim().equals("(선택)")) {
							pstmt.setNull(3, Types.VARCHAR);
						} else {
							pstmt.setString(3, String.valueOf(breedingDifficultyChoice.getSelectedItem()));
						}
						
						pstmt.executeUpdate();
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (difficultyTBL)");
				}
				
				
				// descriptionTBL(이름, 설명) 데이터를 추가하는 구문
				try {
					if(!textTextField.getText().isEmpty()) {
						sql = "INSERT INTO descriptionTBL VALUES(?, ?);";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
						pstmt.setString(2, String.valueOf(textTextField.getText()));
					
						pstmt.executeUpdate();
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (descriptionTBL)");
				}
				
				
				// colorTBL(이름, 색상) 데이터를 추가하는 구문
				try {
					if(!color1Choice.getSelectedItem().trim().equals("(선택1)")) {
						sql = "INSERT INTO colorTBL VALUES(?, ?)"
								+ "ON DUPLICATE KEY UPDATE commonName=?, Color=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
						pstmt.setString(2, color1Choice.getSelectedItem());
						pstmt.setString(3, commonNameTextField.getText());
						pstmt.setString(4, color1Choice.getSelectedItem());
					
						pstmt.executeUpdate();
					}
					
					if(!color2Choice.getSelectedItem().trim().equals("(선택2)")) {
						sql = "INSERT INTO colorTBL VALUES(?, ?)"
								+ "ON DUPLICATE KEY UPDATE commonName=?, Color=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, commonNameTextField.getText());
						pstmt.setString(2, color2Choice.getSelectedItem());
						pstmt.setString(3, commonNameTextField.getText());
						pstmt.setString(4, color2Choice.getSelectedItem());
						
						pstmt.executeUpdate();
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (colorTBL)");
				}
				
				// imageTBL(이름, 이미지 경로) 데이터를 추가하는 구문
				try {
					if(!imageTextField.getText().equals("(이미지 없음)")) {
					sql = "INSERT INTO imageTBL VALUES(?, ?);";
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, commonNameTextField.getText());
					pstmt.setString(2, String.valueOf(imageTextField.getText()));
					
					pstmt.executeUpdate();
					FileUtil.copy(loadFileDirectory+loadFileName, FishData.getImageFileDirectory()+loadFileName);
					}
				} catch(SQLException se) {
					System.out.println("예외: InsertFrame > actionPerformed() > SQLExcpetion (imageTBL)");
				}
			}
			
			try {
				ifm.setVisible(false); ifm.dispose();
			} catch(NullPointerException ne) { /* ... */ }
			
			closeDB();
			mainFrame.updateTable();
			
			this.setVisible(false); this.dispose();
			FishData.setCommonName("");
		}
	
		if(obj.equals(cancelButton)) {
			try {
				ifm.setVisible(false); ifm.dispose();
			} catch(NullPointerException ne) { /* ... */ }
			
			closeDB();
			this.setVisible(false); this.dispose();
		}
		
		if(obj.equals(insertImageButton)) {
			FileDialog fileOpen = new FileDialog(this, "이미지 파일 열기", FileDialog.LOAD);
			fileOpen.setDirectory("/Users/kimkunwoo/Dropbox/Mac (2)/Desktop");
			fileOpen.setVisible(true);
			
			String ext = fileOpen.getFile().substring(fileOpen.getFile().indexOf('.')+1);
			if(ext.equalsIgnoreCase("JPG") || ext.equalsIgnoreCase("JPEG")  || ext.equalsIgnoreCase("PNG")) {
				loadFileDirectory = fileOpen.getDirectory();
				loadFileName = fileOpen.getFile();
				imageTextField.setText(fileOpen.getFile());
			} else {
				JOptionPane.showMessageDialog(null, "확장자가 다릅니다. 이미지 파일을 삽입해주세요.", "메세지", JOptionPane.WARNING_MESSAGE);
				imageTextField.setText("(이미지 없음)");
			}
		}
		
		if(obj.equals(deleteImageButton)) {
			imageTextField.setText("(이미지 없음)");
		}
		
		if(obj.equals(showImageButton)) {
			if(!imageTextField.getText().equals("(이미지 없음)")) {
				ifm = new ImageFrame(loadFileDirectory+loadFileName, 500, 500);
			} else {
				JOptionPane.showMessageDialog(null, "이미지가 없습니다. 이미지를 먼저 삽입해주세요.", "메세지", JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void textValueChanged(TextEvent te) {
		Object obj = te.getSource();
		
		if(obj.equals(commonNameTextField)) {
			if(commonNameTextField.getText().equals("")) {
				statusLabel.setText("▷ 이름은 고유한 값이어야 합니다.");
				statusLabel.setForeground(Color.black);
				commonNameTextField.setBackground(Color.WHITE);
				okButtonEnabled(false, 0);
			} else if(FishData.getListOfCommonName().contains(commonNameTextField.getText().trim())) {
				statusLabel.setText("▷ 사용할 수 없는 키 값입니다.");
				statusLabel.setForeground(new Color(254, 171, 43));
				commonNameTextField.setBackground(new Color(254, 171, 43));
				commonNameTextField.setForeground(Color.WHITE);
				okButtonEnabled(false, 0);
			} else {
				statusLabel.setText("▷ 사용 가능한 키 값입니다.");
				statusLabel.setForeground(new Color(34, 167, 244));
				commonNameTextField.setBackground(new Color(34, 167, 244));
				commonNameTextField.setForeground(Color.WHITE);
				okButtonEnabled(true, 0);
			}
		}
		
		if(obj.equals(sizeTextField)) {
			checkInputData(obj, 1);
		}
		
		if(obj.equals(minimumCelsiusTextField)) {
			checkInputData(obj, 2);
		}
		
		if(obj.equals(maximumCelsiusTextField)) {
			checkInputData(obj, 3);
		}
		
		if(obj.equals(properPHTextField)) {
			checkInputData(obj, 4);
		}
		
		if(obj.equals(properGHTextField)) {
			checkInputData(obj, 5);
		}
	}
	
	public void okButtonEnabled(boolean b, int n) {
		buttonEnabler[n] = b;
		
		for(int i=0; i<buttonEnabler.length; i++) {
			if(buttonEnabler[i]==false) {
				okButton.setEnabled(false);
				break;
			}
			
			okButton.setEnabled(true);
		}
	}	
	
	public void checkInputData(Object obj, int i) {
		TextField tf = (TextField) obj;
		if(tf.getText().equals("")) {
			tf.setBackground(Color.WHITE);
			okButtonEnabled(true, i);
		} else {
			try {
				Double.parseDouble(tf.getText());
				tf.setBackground(new Color(34, 167, 244));
				tf.setForeground(Color.WHITE);
				okButtonEnabled(true, i);
			} catch(NumberFormatException nfe) { 
				tf.setBackground(new Color(254, 171, 43));
				tf.setForeground(Color.WHITE);
				okButtonEnabled(false, i);
			}
		}
	}
	
	public void openDB() {
		try {
			Class.forName(FishData.getDriver());
			con = DriverManager.getConnection(FishData.getUrl(), FishData.getId(), FishData.getPassword());
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println("예외: MainFrame > openDB() > SQLExcpetion | ClassNotFoundExcpetion");
		}
	}
	
	public void closeDB() {
		if (rs != null) {
	        try {
	            rs.close();
	        } catch (SQLException se) { /* ... */ }
	    }
	    if (pstmt != null) {
	        try {
	            pstmt.close();
	        } catch (SQLException se) { /* ... */ }
	    }
	    if (con != null) {
	        try {
	            con.close();
	        } catch (SQLException se) { /* ... */ }
	    }
	}
}
