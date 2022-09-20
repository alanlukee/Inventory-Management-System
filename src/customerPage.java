import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//import javax.swing.table.JTableHeader;

public class customerPage implements ActionListener {
	static Connection customer_conn = GUIDriver.my_conn;
	private  static JFrame frame;
	private  static JTextField textcustomerid;
	private  static JTextField textcustomername;
	private  static JTextField textcustomeraddress;
	private static JTable table;
	private static DefaultTableModel model;
	
	private static String btn_update_label="UPDATE";
	private static String btn_create_label="CREATE";
	private static String btn_load_label="LOAD Sel.";
	private static String btn_clear_label="CLEAR";
	public void launchcustomerPage() {
		table = new JTable();
		Object[] columns ={"customer_id","customer_name","customer_address"};
		model= new DefaultTableModel();
		frame = new JFrame("CUSTOMERS");
		frame.setBounds(100,100,702,519);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		model.setColumnIdentifiers(columns);
		table.setModel(model);
		table.setSelectionBackground(Color.red);
		table.setSelectionForeground(Color.white);
		table.setRowHeight(30);
		table.setAutoCreateRowSorter(true);
		JScrollPane pane = new JScrollPane(table);
		pane.setBounds(10,10,560,292);
		frame.getContentPane().add(pane);
		
		JLabel lblNewLabel = new JLabel("Customer id");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(29, 328, 122, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Customer name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(172, 332, 109, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Customer address");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(326, 330, 122, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btn_update = new JButton(btn_update_label);
		btn_update.setBounds(456, 426, 96, 28);
		frame.getContentPane().add(btn_update);
		
		JButton btn_create = new JButton(btn_create_label);
		btn_create.setBounds(565, 426, 88, 28);
		frame.getContentPane().add(btn_create);
		
		textcustomerid = new JTextField();
		textcustomerid.setBounds(31, 361, 96, 34);
		frame.getContentPane().add(textcustomerid);
		textcustomerid.setColumns(10);
		
		textcustomername = new JTextField();
		textcustomername.setBounds(158, 362, 142, 34);
		frame.getContentPane().add(textcustomername);
		textcustomername.setColumns(10);
		
		textcustomeraddress = new JTextField();
		textcustomeraddress.setBounds(326, 364, 109, 28);
		frame.getContentPane().add(textcustomeraddress);
		textcustomeraddress.setColumns(10);
		
		JButton btn_load = new JButton(btn_load_label);
		btn_load.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_load.setBounds(582, 305, 96, 34);
		frame.getContentPane().add(btn_load);
		
		JButton btn_clear = new JButton(btn_clear_label);
		btn_clear.setBounds(590, 349, 88, 34);
		frame.getContentPane().add(btn_clear);
		
		
		btn_update.addActionListener(new customerPage());
		btn_update.setActionCommand(btn_update_label);
		
		btn_create.addActionListener(new customerPage());
		btn_create.setActionCommand(btn_create_label);
		
		btn_load.addActionListener(new customerPage());
		btn_load.setActionCommand(btn_load_label);
		
		btn_clear.addActionListener(new customerPage());
		btn_clear.setActionCommand(btn_clear_label);
		
		Object[] row = new Object[3];
		Statement stmt= null;
		String my_sql="select * from Customer";
		System.out.println(my_sql);
	    ResultSet rs;
			try {
				stmt=customer_conn.createStatement();
				rs = stmt.executeQuery(my_sql);
				
			      while (rs.next()) {
			    		row[0]=rs.getString("customer_id");
						row[1]=rs.getString("customer_name");
						row[2]=rs.getString("customer_address");
						model.addRow(row);     
			      }
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  frame.setVisible(true);
		  }

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand=((JButton) e.getSource()).getActionCommand();
		System.out.println(actionCommand);
		if(actionCommand==btn_update_label) {
			//update button	
		
			updateCustomer();
		}
		else if(actionCommand==btn_create_label){
			//create button
			
			createCustomer();
			}
		else if(actionCommand==btn_load_label){
			//load button
		
			loadCustomer();
			
			}
		else if(actionCommand==btn_clear_label){
			clearCustomer();
			}
		
		frame.revalidate();	
	}
	
	private void clearCustomer() {
		textcustomerid.setText("");
		textcustomername.setText("");
		textcustomeraddress.setText("");	
	}
	private void loadCustomer() {
		int i = table.getSelectedRow();
		if(i>=0) {
			textcustomerid.setText((String) model.getValueAt(i, 0));
			textcustomername.setText((String) model.getValueAt(i, 1));
			textcustomeraddress.setText((String) model.getValueAt(i, 2));
		}else {
			JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
		}
		
	}
	private void createCustomer() {
		if(textcustomerid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame,"No Data for creation");	
		}
		else{
				String my_sql = "select * from Customer where customer_id='"+textcustomerid.getText()+"'";
				System.out.println(my_sql);
				Statement stmt= null;
				ResultSet rs;
				int i=0;
				try {
					stmt=customer_conn.createStatement();
					rs = stmt.executeQuery(my_sql);
					 while (rs.next()) {
						 i=i+1;	 	 
					 }
					 rs.close();
			         stmt.close();	 
				}
				catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(i==0) {
					//data can be inserted
					my_sql="insert into Customer values('"+textcustomerid.getText()+"','"+textcustomername.getText()+"','"+ textcustomeraddress.getText()+"')";
					 
					System.out.println(my_sql);
					try {
						stmt=customer_conn.createStatement();
						stmt.executeUpdate(my_sql);
						stmt.close();
					 	//category_conn.commit();
				    	Object[] row = new Object[3];
				    	row[0]=textcustomerid.getText();
				    	row[1]=textcustomername.getText();
				    	row[2]=textcustomeraddress.getText();
				    	
				    	model.addRow(row);
				    	
				    	JOptionPane.showMessageDialog(frame,"Customer succesfully created!");
				    	//frame.revalidate();   
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}	
				}	
				else {
					//data is duplicate
					JOptionPane.showMessageDialog(frame,"Customer id already exists");	
				}
			}
		
	}
	private void updateCustomer() {
		if(textcustomerid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame,"No Data for updation");	
		}
		else{
			String my_sql = "select * from Customer where customer_id='"+textcustomerid.getText()+"'";
			System.out.println(my_sql);
			Statement stmt= null;
			ResultSet rs;
			int i=0;
			try {
				stmt=customer_conn.createStatement();
				rs = stmt.executeQuery(my_sql);
				 while (rs.next()) {
					 i=i+1;	 	 
				 }
				 rs.close();
		         stmt.close();	 
			}
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(i==1) {
				//data exists and can be updated
				my_sql="update Customer set customer_name='" +textcustomername.getText()+ "',customer_address='" +textcustomeraddress.getText()+"'  where customer_id='"+textcustomerid.getText()+"'";
				System.out.println(my_sql);
				try {
					stmt=customer_conn.createStatement();
					stmt.executeUpdate(my_sql);
					stmt.close();
					//category_conn.commit();
			    	System.out.println(model.getRowCount());
			    	for(int j=0; j<=model.getRowCount()-1;j++) {
			    		System.out.println(j);
			    		if(textcustomerid.getText().equals((String)model.getValueAt(j, 0))){
			    			model.removeRow(j);
			    			Object[] row = new Object[3];
					    	row[0]=textcustomerid.getText();
					    	row[1]=textcustomername.getText();
					    	row[2]=textcustomeraddress.getText();
					    	model.addRow(row);
			    			break;
			    		}
			    	}
			    	JOptionPane.showMessageDialog(frame,"Customer succesfully updated!");
			    	frame.revalidate(); 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
			else {
				//data does not exists
				JOptionPane.showMessageDialog(frame,"Customer Id does not exists");	
			}
		}	
		
	}
		
}
	
		
		

