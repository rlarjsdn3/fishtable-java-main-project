package databaseProject;

import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.Label;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Choice;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextListener;
import java.awt.event.TextEvent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.lang.Exception;
import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class JoinFrame extends JFrame implements ConnectDB, ActionListener, TextListener {
	JPanel north, center, south;
	JPanel p01, p02, p03, p04;
	
	JLabel titleLabel, idLabel, passwordLabel, passwordCheckLabel;
	
	TextField idTextField, passwordTextField, passwordCheckTextField;
	Choice authenticationChoice;
	
	JButton idDuplicateCheckButton, joinButton, cancelButton;
	boolean isDuplicateCheckId = false;
	
	Font labelBoldFont = new Font("나눔고딕", Font.BOLD, 16);
	
	Color mainColor = new Color(34, 167, 244);
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	JoinFrame() {
		super("관상어 DB 시스템");
		openDB();
		
		titleLabel = new JLabel("회원가입");
		titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);
		
		idLabel = new JLabel("식별아디: ");
		idLabel.setFont(labelBoldFont);
		
		passwordLabel = new JLabel("비밀번호: ");
		passwordLabel.setFont(labelBoldFont);
		
		passwordCheckLabel = new JLabel("비번확인: ");
		passwordCheckLabel.setFont(labelBoldFont);
		
		idTextField = new TextField(10);
		passwordTextField = new TextField(22);
		passwordTextField.setEchoChar('*');
		passwordCheckTextField = new TextField(22);
		passwordCheckTextField.setEchoChar('*');
		
		idDuplicateCheckButton = new JButton("중복 확인");
		cancelButton = new JButton("취소");
		joinButton = new JButton("회원가입");
		joinButton.setForeground(mainColor);
		joinButton.setEnabled(false);
		
		authenticationChoice = new Choice();
		authenticationChoice.add("                       사용자                        ");
		authenticationChoice.add("                       관리자                        ");
		
		//...
		
		JPanel p01 = new JPanel(); 
		JPanel p02 = new JPanel();
		JPanel p03 = new JPanel();
		JPanel p04 = new JPanel();
		
		north = new JPanel();
		north.setPreferredSize(new Dimension(300, 40));
		add(north, BorderLayout.NORTH);
		north.setBackground(mainColor);
		north.add(titleLabel);
		
		center = new JPanel();
		center.setPreferredSize(new Dimension(300, 200));
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(5, 1));
		center.add(p01);
		center.add(p02);
		center.add(p03);
		center.add(p04);
		
		south = new JPanel();
		south.setPreferredSize(new Dimension(300, 50));
		south.setBackground(Color.white);
		add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 3, 12));
		south.add(joinButton);
		south.add(cancelButton);
		
		//...
		
		p01.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		p01.add(idLabel);
		p01.add(idTextField);
		p01.add(idDuplicateCheckButton);
		
		p02.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		p02.add(passwordLabel);
		p02.add(passwordTextField);
		
		p03.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		p03.add(passwordCheckLabel);
		p03.add(passwordCheckTextField);
		
		p04.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 5));
		p04.add(authenticationChoice);
		
		//...
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width/2-200, screenSize.height/2-400);
	
		setSize(300, 280);
		setVisible(true);
		
		//...
		
		idDuplicateCheckButton.addActionListener(this);
		joinButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		idTextField.addTextListener(this);
		passwordTextField.addTextListener(this);
		passwordCheckTextField.addTextListener(this);
		
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
		Object obj = (Object) e.getSource();
		
		if(obj.equals(idDuplicateCheckButton)) {
			if(idTextField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "아이디를 입력해주세요.", "메세지", 
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try {
				sql = "SELECT user FROM mysql.user;";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					if(rs.getString("user").equals(idTextField.getText())) {
						JOptionPane.showMessageDialog(null, "동일한 아이디가 이미 존재합니다. 다시 입력해주세요.", "메세지", 
								JOptionPane.WARNING_MESSAGE);
						return;
					}
				}
				
				if(JOptionPane.showConfirmDialog(null, "사용 가능한 아이디입니다. 사용하시겠습니까?", "메세지", 
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
					idTextField.setEditable(false);
					idTextField.setBackground(new Color(34, 167, 244));
					isDuplicateCheckId = true;
					idDuplicateCheckButton.setEnabled(false);
				}
			} catch(SQLException se) {
				System.out.println("예외: JoinFrame > actionPerformed() > SQLExcpetion");
			}
		}
		
		if(obj.equals(joinButton)) {
			if(JOptionPane.showConfirmDialog(null, "회원가입하시겠습니까?", "메세지", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
				try {
					if(isDuplicateCheckId &&
							passwordTextField.getText().equals(passwordCheckTextField.getText())) {
						sql = "create user ?@'localhost' identified by ?;";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, idTextField.getText());
						pstmt.setString(2, passwordTextField.getText());
						
						pstmt.execute();
						
						if(authenticationChoice.getSelectedItem().trim().equals("사용자")) {
							sql = "GRANT SELECT ON *.* TO ?@'localhost';";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, idTextField.getText());
						} else {
							sql = "GRANT ALL PRIVILEGES ON *.* to ?@'localhost';";
							pstmt = con.prepareStatement(sql);
							pstmt.setString(1, idTextField.getText());
						}
						
						pstmt.execute();
						
						JOptionPane.showConfirmDialog(null, "회원가입이 완료되었습니다!", "메세지", 
								JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "입력하신 내용을 다시 한번 확인해주세요.", "메세지", 
								JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					this.setVisible(false);
					this.dispose();
				} catch(SQLException se) {
					System.out.println("예외: JoinFrame > actionPerformed() > SQLExcpetion");
				}
			}
		}
		
		if(obj.equals(cancelButton)) {
			if(JOptionPane.showConfirmDialog(null, "창을 닫으시겠습니까?", "메세지", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
				closeDB();
				this.setVisible(false);
				this.dispose();
			}
		}
	}
		
	
	@Override
	public void textValueChanged(TextEvent e) {
		if(isDuplicateCheckId &&
				!passwordTextField.getText().equals("") &&
				!passwordCheckTextField.getText().equals("") &&
				!authenticationChoice.getSelectedItem().trim().equals("(선택)")) {
			joinButton.setEnabled(true);
		} else {
			joinButton.setEnabled(false);
		}
	}
	
	public void openDB() {
		try {
			Class.forName(FishData.getDriver());
			con = DriverManager.getConnection(FishData.getUrl(), "root", "@ch907678");
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println("예외: JoinFrame > openDB() > SQLExcpeion | ClassNotFoundException");
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
