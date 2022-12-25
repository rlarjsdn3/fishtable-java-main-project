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

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.lang.ClassNotFoundException;

public class LoginFrame extends JFrame implements ConnectDB, ActionListener {
	JPanel north, center, south;
	JPanel p01, p02, p03;
	
	JButton loginButton, joinButton, exitButton;
	JLabel titleLabel, idLabel, passwordLabel;
	TextField idTextField, passwordTextField;
	
	Font labelBoldFont = new Font("나눔고딕", Font.BOLD, 16);
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	LoginFrame() {
		super("관상어 DB 시스템");
		
		titleLabel = new JLabel("로그인");
		titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);
		
		idLabel = new JLabel("식별아디:", Label.RIGHT);
		idLabel.setFont(labelBoldFont);
		
		passwordLabel = new JLabel("비밀번호:", Label.RIGHT);
		passwordLabel.setFont(labelBoldFont);
		
		loginButton = new JButton("로그인 ");
		joinButton = new JButton("회원가입");
		exitButton = new JButton("종료");
		exitButton.setForeground(Color.red);
		
		idTextField = new TextField(22);
		passwordTextField = new TextField(22);
		passwordTextField.setEchoChar('*');
		
		//...
		
		p01 = new JPanel();
		p02 = new JPanel();
		p03 = new JPanel();
		
		north = new JPanel();
		north.setPreferredSize(new Dimension(300, 40));
		add(north, BorderLayout.NORTH);
		north.setBackground(Color.BLACK);
		north.add(titleLabel);
		
		center = new JPanel();
		center.setPreferredSize(new Dimension(300, 200));
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(2, 1));
		center.add(p01);
		center.add(p02);
		
		south = new JPanel();
		south.setPreferredSize(new Dimension(300, 50));
		add(south, BorderLayout.SOUTH);
		south.setLayout(new FlowLayout(FlowLayout.RIGHT, 16, 5));
		south.add(joinButton);
		south.add(loginButton);
		south.add(exitButton);
		
		//...
		
		p01.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		p01.add(idLabel);
		p01.add(idTextField);
		p01.add(passwordLabel);
		p01.add(passwordTextField);
		p02.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 5));
		p02.add(passwordLabel);
		p02.add(passwordTextField);
		
		//...
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width/2-200, screenSize.height/2-400);
	
		setSize(300, 180);
		setVisible(true);
		
		//...
		
		loginButton.addActionListener(this);
		joinButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}
		});
	}
	
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		
		if(obj.equals(loginButton)) {
			openDB();
		} 
		
		if(obj.equals(joinButton)) {
			new JoinFrame();
		}
		
		if(obj.equals(exitButton)) {
			if(JOptionPane.showConfirmDialog(null, "프로그램을 종료하시겠습니까?", "메세지", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
				closeDB();
				this.setVisible(false);
				this.dispose();
				System.exit(0);
			}
		}
	}
	
	public void openDB() {
		String sql;
		String selectPriv="", insertPriv="", updatePriv="", deletePriv="", createPriv="", dropPriv="";
		boolean isAdministrator = false;
		
		String id = idTextField.getText();
		String password = passwordTextField.getText();
		
		try {
			Class.forName(FishData.getDriver());
			con = DriverManager.getConnection(FishData.getUrl(), id, password);
			
			sql = "SELECT Select_priv, Insert_priv, Update_priv, Delete_priv, Create_priv, Drop_priv\n"
					+ "	FROM mysql.user \n"
					+ " WHERE User = ?;";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				selectPriv = rs.getString("Select_priv");
				insertPriv = rs.getString("Insert_priv");
				updatePriv = rs.getString("Update_priv");
				deletePriv = rs.getString("Delete_priv");
				createPriv = rs.getString("Create_priv");
				dropPriv = rs.getString("Drop_priv");
				
				if(selectPriv.equals("Y") && insertPriv.equals("Y") &&
						updatePriv.equals("Y") && deletePriv.equals("Y") &&
						createPriv.equals("Y") && dropPriv.equals("Y")) 
					isAdministrator = true;
			}

			FishData.setId(id);
			FishData.setPassword(password);
			
			JOptionPane.showConfirmDialog(null, id + "님, 환영합니다!", "메세지", 
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			setVisible(false);
			dispose();
			
			new MainFrame(id, isAdministrator);
		} catch(SQLException se) {
			JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 다시 확인하세요.", "메세지", JOptionPane.INFORMATION_MESSAGE);
		} catch(ClassNotFoundException ce) {
			System.out.println("예외: LogineFrame > openDB() > ClassNotFoundException");
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