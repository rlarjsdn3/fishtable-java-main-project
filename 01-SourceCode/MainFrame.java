package databaseProject;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;
import java.lang.ClassNotFoundException;

import java.util.Vector;
import java.io.File;

public class MainFrame extends JFrame implements ConnectDB, ActionListener {
	JPanel north, center, south;
	JPanel titlePanel, searchPanel;
	
	JLabel titleLabel;
	
	String searchCategoryChoice = "전체";
	Choice searchCategory;
	JTextField searchTextField;
	
	JButton searchButton;
	JButton insertButton, updateButton, deleteButton, logoutButton, exitButton;
	
	FishTable fishTable;
	JTable jTable;
	JScrollPane scrollPane;
	
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	static String[] strList;
	
	MainFrame(String id, boolean isAdministrator) {
		super("관상어 DB 시스템 - (로그인: " + id + ")");
		openDB();
		
		titleLabel = new JLabel("관상어 대백과 사전");
		titleLabel.setFont(new Font("나눔고딕", Font.BOLD, 20));
		titleLabel.setForeground(Color.WHITE);
		
		searchCategory = new Choice();
		searchCategory.add("전체");
		searchCategory.add("분류");
		searchCategory.add("이름");
		searchCategory.add("학명");
		searchCategory.add("크기");
		searchCategory.add("색상");
		searchCategory.add("성격");
		
		searchTextField = new JTextField(25);
		
		searchButton = new JButton("검색");
		
		fishTable = new FishTable();
		jTable = new JTable(fishTable);
		jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
		jTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
		jTable.getColumnModel().getColumn(3).setPreferredWidth(30);
		jTable.getColumnModel().getColumn(4).setPreferredWidth(30);
		jTable.getColumnModel().getColumn(5).setPreferredWidth(30);
		jTable.getColumnModel().getColumn(6).setPreferredWidth(65);
		scrollPane = new JScrollPane(jTable);
		
		insertButton = new JButton("추가");
		updateButton = new JButton("수정");
		deleteButton = new JButton("삭제");
		
		logoutButton = new JButton("로그아웃");
		logoutButton.setForeground(Color.blue);
		
		exitButton = new JButton("종료");
		exitButton.setForeground(Color.red);
		
		//...
	
		titlePanel = new JPanel();
		titlePanel.setBackground(Color.BLACK);
		titlePanel.add(titleLabel);
		
		searchPanel = new JPanel();
		searchPanel.setBackground(Color.WHITE);
		searchPanel.setLayout(new FlowLayout());
		searchPanel.add(searchCategory);
		searchPanel.add(searchTextField);
		searchPanel.add(searchButton);
	
		north = new JPanel();
		north.setPreferredSize(new Dimension(450, 80));
		add(north, BorderLayout.NORTH);
		north.setLayout(new GridLayout(2, 1));
		north.add(titlePanel);
		north.add(searchPanel);
		
		center = new JPanel();
		center.setPreferredSize(new Dimension(450, 400));
		add(center, BorderLayout.CENTER);
		center.setLayout(new GridLayout(1, 1));
		center.setBackground(Color.WHITE);
		center.add(scrollPane);
		
		
		south = new JPanel();
		south.setPreferredSize(new Dimension(450, 50));
		add(south, BorderLayout.SOUTH);
		south.setLayout(new GridLayout(1, 5));
		south.setBackground(Color.WHITE);
		south.add(insertButton);
		south.add(updateButton);
		south.add(deleteButton);
		south.add(logoutButton);
		south.add(exitButton);

		//...
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width/2-200, screenSize.height/2-400);
		
		setSize(500, 600);
		setVisible(true);
		
		//...
		
		updateTable();
		insertButton.setEnabled(isAdministrator);
		updateButton.setEnabled(isAdministrator);
		deleteButton.setEnabled(isAdministrator);
		
		//...
		
		searchButton.addActionListener(this);
		insertButton.addActionListener(this);
		updateButton.addActionListener(this);
		deleteButton.addActionListener(this);
		logoutButton.addActionListener(this);
		exitButton.addActionListener(this); 
		
		searchCategory.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent ie) {
				Object obj = ie.getSource();
				searchCategoryChoice = (String)ie.getItem();
			}
		});
		
		jTable.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				FishData.setSelectedRow(jTable.getSelectedRow());
				updateData(FishData.getSelectedRow());
				
				if(me.getClickCount() == 2) {
					new MoreInformationFrame();
				}
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().setVisible(false);
				e.getWindow().dispose();
				System.exit(0);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String sql;
		Object obj = e.getSource();
		
		if(obj.equals(searchButton)) {
			if((searchTextField.getText()).equals("")) {
				updateTable(); 
				jTable.updateUI();
				
			} else {
				sql = "SELECT DISTINCT F.division, F.commonName, F.scientificName, T.size, C.color, T.aggresion, F.lastModifiedDate  \n"
						+ "	FROM fishTBL AS F\n"
						+ "	LEFT OUTER JOIN featureTBL AS T\n"
						+ "	ON F.commonName = T.commonName\n"
						+ "	LEFT OUTER JOIN colorTBL AS C\n"
						+ "	ON F.commonName = C.commonName\n";
				
				if(searchCategoryChoice.equals("전체")) {
					sql = sql + "WHERE F.division LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR F.commonName LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR F.scientificName LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR T.size LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR C.color LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR T.aggresion LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "OR F.commonName = ANY (SELECT F.commonName FROM fishTBL AS S\n"
							  + "								LEFT OUTER JOIN colorTBL AS C\n"
							  + "									ON F.commonName = C.commonName\n"
							  + "								WHERE C.color LIKE " + "\'%" + searchTextField.getText() + "%\')\n"
							  + "ORDER BY F.commonName ASC;\n";

				} else if(searchCategoryChoice.equals("분류")) {
					sql = sql + "WHERE F.division LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "ORDER BY F.commonName ASC;\n";

				} else if(searchCategoryChoice.equals("이름")) {
					sql = sql + "WHERE F.commonName LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "ORDER BY F.commonName ASC;\n";
		
				} else if(searchCategoryChoice.equals("학명")) {
					sql = sql + "WHERE F.scientificName LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "ORDER BY F.commonName ASC;\n";
					
				} else if(searchCategoryChoice.equals("크기")) {
					sql = sql + "WHERE T.size LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "ORDER BY F.commonName ASC;\n";

				} else if(searchCategoryChoice.equals("색상")) {
					sql = sql + "WHERE F.commonName = ANY (SELECT F.commonName FROM fishTBL AS S\n"
							  + "								LEFT OUTER JOIN colorTBL AS C\n"
						      + "									ON F.commonName = C.commonName\n"
							  + "								WHERE C.color LIKE " + "\'%" + searchTextField.getText() + "%\')\n"
						   	  + "ORDER BY F.commonName ASC;\n";

				} else if(searchCategoryChoice.equals("성격")) {
					sql = sql + "WHERE T.aggresion LIKE" + "\'%" + searchTextField.getText() + "%\'\n"
							  + "ORDER BY F.commonName ASC;\n";
				}
				
				updateTable(sql);
				jTable.updateUI();
			}
		}
		
		if(obj.equals(insertButton)) {
			new InsertFrame(this);
		}
		
		
		if(obj.equals(updateButton)) {
			if(FishData.getCommonName().equals("")) {
				JOptionPane.showMessageDialog(null, "수정할 데이터를 선택해주세요.", "메세지", 
						 JOptionPane.INFORMATION_MESSAGE);
			} else {
				new UpdateFrame(this);
			}
		}
		
		// '삭제' 버튼 클릭 시
		if(obj.equals(deleteButton)) {
			if(FishData.getCommonName().equals("")) {
				JOptionPane.showConfirmDialog(null, "삭제할 데이터를 지정해주세요!", "메세지", 
						JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			try {
				sql = "DELETE FROM fishTBL WHERE commonName=?;";
				
				if(JOptionPane.showConfirmDialog(null, FishData.getCommonName() + " " + "데이터를 삭제하시겠습니까?", "메세지", 
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
					pstmt = con.prepareStatement(sql);
					pstmt.setString(1, FishData.getCommonName());
					pstmt.executeUpdate();
					
					File file = new File(FishData.getImageFileDirectory()+FishData.getImageFileName());
					if(file.exists()) {
						FileUtil.delete(file.getAbsolutePath());
					}
					
					try {
						sql = "DELETE FROM imageTBL WHERE commonName=?";
						pstmt = con.prepareStatement(sql);
						pstmt.setString(1, FishData.getCommonName());
						pstmt.executeUpdate();
						
						FileUtil.delete(FishData.getImageFileDirectory()+FishData.getImageFileName());
					} catch(SQLException se) {
						se.printStackTrace();
					}
				
					updateTable();
					updateData(0);
				}
			} catch(SQLException se) {
				System.out.println("예외: MainFrame > actionPerformed() > SQLExcpetion");
			}		
		}
		
		// '로그아웃' 버튼 클릭 시
		if(obj.equals(logoutButton)) {
			if(JOptionPane.showConfirmDialog(null, "접속 중인 기기에서 로그아웃 하시겠습니까?", "메세지", 
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE) == 0) {
				closeDB();
				this.setVisible(false);
				this.dispose();
				new LoginFrame();
			}
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
	
	public void updateTable() {
		String sql = "SELECT F.division, F.commonName, F.scientificName, T.size, C.color, T.aggresion, F.lastModifiedDate  \n"
						+ "	FROM fishTBL AS F\n"
						+ "	LEFT OUTER JOIN featureTBL AS T\n"
						+ "	ON F.commonName = T.commonName\n"
						+ "	LEFT OUTER JOIN colorTBL AS C\n"
						+ "	ON F.commonName = C.commonName\n"
						+ "	ORDER BY F.commonName ASC;";
		
		updateTable(sql);
	}
	
	public void updateTable(String sql) {
		Vector v = new Vector();
		Vector listOfCommonName = new Vector();
		
		int row = -1;
		String previousCommonName = "";
		String previousColor = "";
		
		try {
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Vector r = new Vector();
				
				if(previousCommonName.equals(rs.getString("commonName"))) {
					Vector e = (Vector) v.get(row);
					String concatColor = (String) e.get(4) + "/" + rs.getString("color");
					e.set(4, concatColor);
					continue;
				}
				
				r.add(rs.getString("division"));
				r.add(rs.getString("commonName"));
				r.add(rs.getString("ScientificName"));
				
				if(String.valueOf(rs.getInt("Size")).equals("0")) {
					r.add("");
				} else {
					r.add(String.valueOf(rs.getInt("Size")) + "cm");
				}
				r.add(rs.getString("color"));
				r.add(rs.getString("Aggresion"));
				r.add(rs.getString("LastModifiedDate"));
				v.add(r);
				
				previousCommonName = rs.getString("commonName");
				previousColor = rs.getString("color");
				row++;
				
				listOfCommonName.add(rs.getString("commonName"));
			}
			
			FishData.setListOfCommonName(listOfCommonName);
			fishTable.setTable(v);
			jTable.updateUI(); this.repaint();
		} catch(SQLException e) {
			System.out.println("예외: MainFrame > updateTable() > SQLExcpetion");
			e.printStackTrace();
		}
	}
	
	public void updateData(int selRow) {
		String sql, name;
		try {
			name = String.valueOf(jTable.getValueAt(selRow, 1));
		} catch(ArrayIndexOutOfBoundsException abe) {
			System.out.println("예외: mainFrame-renewalData()-ArrayIndexOutOfBoundsException");
			FishData.setCommonName("");
			return;
		}
	
		sql = "SELECT DISTINCT S.commonName, S.scientificName, S.division, F.size, C.color, F.swimmingArea, F.aggresion, F.taste,\n"
				+ "				E.minimumCelsius, E.maximumCelsius, E.properPH, E.properGH, D.breedDifficulty, D.breedingDifficulty, P.text, S.lastModifiedDate, I.imageFileName\n"
				+ "	FROM fishTBL AS S\n"
				+ "		LEFT OUTER JOIN featureTBL AS F\n"
				+ "			ON S.commonName = F.commonName\n"
				+ "		LEFT OUTER JOIN colorTBL AS C\n"
				+ "			ON S.commonName = C.commonName\n"
				+ "		LEFT OUTER JOIN feedingEnvironmentTBL AS E\n"
				+ "			ON S.commonName = E.commonName\n"
				+ "		LEFT OUTER JOIN difficultyTBL AS D\n"
				+ "			ON S.commonName = D.commonName\n"
				+ "		LEFT OUTER JOIN descriptionTBL AS P\n"
				+ "			ON S.commonName = P.commonName\n"
				+ "		LEFT OUTER JOIN imageTBL AS I"
				+ "			ON S.commonName = I.commonName"
				+ "	WHERE S.commonName = " + "\'" + name + "\'";
		
		try {
			String color = "";
			boolean isRepeated = false;
			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
		
			while(rs.next()) {
				if(isRepeated) {
					color += "/" + rs.getString("color");
					FishData.setColor(color);
					break;
				}
				
				FishData.setCommonName(rs.getString("commonName"));
				FishData.setScientificName(rs.getString("scientificName"));
				FishData.setDivision(rs.getString("division"));
				
				color = rs.getString("color");
				FishData.setColor(color);
				FishData.setSize(rs.getString("size")!=null ? rs.getString("size") : "");
				FishData.setSwimmingArea(rs.getString("swimmingArea"));
				FishData.setAggresion(rs.getString("aggresion"));
				FishData.setTaste(rs.getString("taste"));
				
				FishData.setMinimumCelsius(rs.getString("minimumCelsius"));
				FishData.setMaximumCelsius(rs.getString("maximumCelsius"));
				FishData.setProperPH(rs.getString("properPH"));
				FishData.setProperGH(rs.getString("properGH"));
				
				FishData.setBreedDifficulty(rs.getString("breedDifficulty"));
				FishData.setBreedingDifficulty(rs.getString("breedingDifficulty"));
				
				FishData.setLastModifiedDate(rs.getString("lastModifiedDate"));
				FishData.setText(rs.getString("text"));
				FishData.setImageFileName(rs.getString("imageFileName"));
				
				isRepeated = true;
			}			
		} catch(SQLException e) {
			System.out.println("예외: mainFrame-renewwalData()-SQLException");
			e.printStackTrace();
		}
	}
	
	public void openDB() {
		try {
			Class.forName(FishData.getDriver());
			con = DriverManager.getConnection(FishData.getUrl(), FishData.getId(), FishData.getPassword());
		} catch(SQLException | ClassNotFoundException e) {
			System.out.println("예외: MainFrame > openDB() > SQLExcpetion | ClassNotFoundException");
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