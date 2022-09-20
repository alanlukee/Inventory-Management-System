import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
//import javax.swing.SwingUtilities;

import java.sql.Statement;
import java.util.concurrent.TimeUnit;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Login implements ActionListener {
	//String testVariable= "hello";
	private static JLabel userLabel;
	private static JTextField userText;
	private static JLabel passwordLabel;
	private static JPasswordField passwordText;
	private static JButton button;
	private static JLabel success;
	private static JFrame frame1;
	private static JPanel panel;
	static Connection login_conn = GUIDriver.my_conn;
	

	public void launchloginScreen() {
		frame1 = new JFrame();
		panel = new JPanel(); 
		frame1.setSize(528,260);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel.setLayout(null);
		
	    userLabel = new JLabel("UserId");
		userLabel.setBounds(10,20,80,25);
		panel.add(userLabel);
		
		userText = new JTextField();
		userText.setBounds(100,20,165,25);
		panel.add(userText);
		
		passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(10,50,80,25);
		panel.add(passwordLabel);
		
		passwordText = new JPasswordField();
		passwordText.setBounds(100, 50, 165, 25);
		panel.add(passwordText);
		
		button = new JButton("Login");	
		button.setBounds(130, 80, 80, 25);
		button.addActionListener(new Login());
		button.setActionCommand("button1");
		
		panel.add(button);
		
		success = new JLabel("");
		success.setBounds(10,110,300,25);
		panel.add(success);
		
		JLabel lblNewLabel_3 = new JLabel("New label");
		
		lblNewLabel_3.setIcon(new ImageIcon("C:\\Users\\user\\OneDrive\\Alan\\1st year (Sem 1 and Sem 2)\\Pictures\\shop1.jpg"));
		lblNewLabel_3.setBackground(Color.YELLOW);
		lblNewLabel_3.setBounds(0, 0, 528, 260);
		frame1.getContentPane().add(lblNewLabel_3);
		panel.add(lblNewLabel_3);
		
		frame1.add(panel);
		
		
		frame1.setVisible(true);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String user = userText.getText();
		@SuppressWarnings("deprecation")
		String password = passwordText.getText();
		@SuppressWarnings("unused")
		String actionCommand=((JButton) e.getSource()).getActionCommand();
		Statement stmt= null;
	
			String my_sql;
			my_sql="select user_name from inv_users where user_id='"+user+"'and user_pass='"+password+"'";
			System.out.println(my_sql);
	       try {
	    	stmt=login_conn.createStatement();
			ResultSet rs = stmt.executeQuery(my_sql);
			int rowCount = 0;
		      while (rs.next()) {
		           rowCount++;
		      }
		      if(rowCount>0) {
		    	  success.setText("Login Successful!");
		    	 /* SwingUtilities.updateComponentTreeUI(frame1);
		    	  frame1.invalidate();
		    	  frame1.validate();
		    	  frame1.repaint();*/
		    	  
		    	  System.out.println("Login Successful");
		    	 try {
					TimeUnit.SECONDS.sleep(0);
				} catch (InterruptedException e1) {
					
					e1.printStackTrace();
				}
		    	   rs.close();
			      stmt.close();
			    landingPage launch = new landingPage();
			      System.out.println("Before Landing");
			  	launch.launchlandingPage();
			    System.out.println("After Landing");
			    frame1.dispose();
			  	
			  	//frame1.dispatchEvent(new WindowEvent(frame1,WindowEvent.WINDOW_CLOSING));
			  	
			  
			  	
		      }
		      else {
		    	  success.setText("Login Failed");
		      }
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
		}

}
