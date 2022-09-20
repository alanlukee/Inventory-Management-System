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

public class vendorPage implements ActionListener {
	
	static Connection vendor_conn = GUIDriver.my_conn;
	private  static JFrame frame;
	private  static JTextField textvendorid;
	private  static JTextField textvendorname;
	private  static JTextField textvendoraddress;
	private static JTable table;
	private static DefaultTableModel model;
	
	private static String btn_update_label="UPDATE";
	private static String btn_create_label="CREATE";
	private static String btn_load_label="LOAD Sel.";
	private static String btn_clear_label="CLEAR";
	public void launchvendorPage() {
		table = new JTable();
		Object[] columns ={"vendor_id","vendor_name","vendor_address"};
		model= new DefaultTableModel();
		frame = new JFrame("VENDORS");
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
		
		JLabel lblNewLabel = new JLabel("Vendor id");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(29, 328, 122, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Vendor name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(172, 332, 109, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Vendor address");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(326, 330, 122, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btn_update = new JButton(btn_update_label);
		btn_update.setBounds(456, 426, 96, 28);
		frame.getContentPane().add(btn_update);
		
		JButton btn_create = new JButton(btn_create_label);
		btn_create.setBounds(565, 426, 88, 28);
		frame.getContentPane().add(btn_create);
		
		textvendorid = new JTextField();
		textvendorid.setBounds(31, 361, 96, 34);
		frame.getContentPane().add(textvendorid);
		textvendorid.setColumns(10);
		
		textvendorname = new JTextField();
		textvendorname.setBounds(158, 362, 142, 34);
		frame.getContentPane().add(textvendorname);
		textvendorname.setColumns(10);
		
		textvendoraddress = new JTextField();
		textvendoraddress.setBounds(326, 364, 109, 28);
		frame.getContentPane().add(textvendoraddress);
		textvendoraddress.setColumns(10);
		
		JButton btn_load = new JButton(btn_load_label);
		btn_load.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_load.setBounds(582, 305, 96, 34);
		frame.getContentPane().add(btn_load);
		
		JButton btn_clear = new JButton(btn_clear_label);
		btn_clear.setBounds(590, 349, 88, 34);
		frame.getContentPane().add(btn_clear);
		
		
		btn_update.addActionListener(new vendorPage());
		btn_update.setActionCommand(btn_update_label);
		
		btn_create.addActionListener(new vendorPage());
		btn_create.setActionCommand(btn_create_label);
		
		btn_load.addActionListener(new vendorPage());
		btn_load.setActionCommand(btn_load_label);
		
		btn_clear.addActionListener(new vendorPage());
		btn_clear.setActionCommand(btn_clear_label);
		
		Object[] row = new Object[3];
		Statement stmt= null;
		String my_sql="select * from Vendor";
		System.out.println(my_sql);
	    ResultSet rs;
			try {
				stmt=vendor_conn.createStatement();
				rs = stmt.executeQuery(my_sql);
				
			      while (rs.next()) {
			    		row[0]=rs.getString("vendor_id");
						row[1]=rs.getString("vendor_name");
						row[2]=rs.getString("vendor_address");
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
			updateVendor();
		}
		else if(actionCommand==btn_create_label){
			//create button
			createVendor();
			}
		else if(actionCommand==btn_load_label){
			//load button
			loadVendor();
			
			}
		else if(actionCommand==btn_clear_label){
			//clear button
			clearVendor();
			
			}
		
		frame.revalidate();	
	}
	private void createVendor() {
		if(textvendorid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame,"No Data for creation");	
		}
		else{
				String my_sql = "select * from Vendor where vendor_id='"+textvendorid.getText()+"'";
				System.out.println(my_sql);
				Statement stmt= null;
				ResultSet rs;
				int i=0;
				try {
					stmt=vendor_conn.createStatement();
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
					my_sql="insert into Vendor values('"+textvendorid.getText()+"','"+textvendorname.getText()+"','"+ textvendoraddress.getText()+"')";
					 
					System.out.println(my_sql);
					try {
						stmt=vendor_conn.createStatement();
						stmt.executeUpdate(my_sql);
						stmt.close();
					 	//category_conn.commit();
				    	Object[] row = new Object[3];
				    	row[0]=textvendorid.getText();
				    	row[1]=textvendorname.getText();
				    	row[2]=textvendoraddress.getText();
				    	
				    	model.addRow(row);
				    	
				    	JOptionPane.showMessageDialog(frame,"Vendor succesfully created!");
				    	//frame.revalidate();   
					} 
					catch (SQLException e1) {
						e1.printStackTrace();
					}	
				}	
				else {
					//data is duplicate
					JOptionPane.showMessageDialog(frame,"Vendor id already exists");	
				}
			}
		
	}
	private void updateVendor() {
		if(textvendorid.getText().isEmpty()) {
			JOptionPane.showMessageDialog(frame,"No Data for updation");	
		}
		else{
			String my_sql = "select * from Vendor where vendor_id='"+textvendorid.getText()+"'";
			System.out.println(my_sql);
			Statement stmt= null;
			ResultSet rs;
			int i=0;
			try {
				stmt=vendor_conn.createStatement();
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
				my_sql="update Vendor set vendor_name='" +textvendorname.getText()+ "',vendor_address='" +textvendoraddress.getText()+"'  where vendor_id='"+textvendorid.getText()+"'";
				System.out.println(my_sql);
				try {
					stmt=vendor_conn.createStatement();
					stmt.executeUpdate(my_sql);
					stmt.close();
					//category_conn.commit();
			    	System.out.println(model.getRowCount());
			    	for(int j=0; j<=model.getRowCount()-1;j++) {
			    		System.out.println(j);
			    		if(textvendorid.getText().equals((String)model.getValueAt(j, 0))){
			    			model.removeRow(j);
			    			Object[] row = new Object[3];
					    	row[0]=textvendorid.getText();
					    	row[1]=textvendorname.getText();
					    	row[2]=textvendoraddress.getText();
					    	model.addRow(row);
			    			break;
			    		}
			    	}
			    	JOptionPane.showMessageDialog(frame,"Vendor succesfully updated!");
			    	frame.revalidate(); 
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}	
			else {
				//data does not exists
				JOptionPane.showMessageDialog(frame,"Vendor Id does not exists");	
			}
		}	
	}
	private void loadVendor() {
		int i = table.getSelectedRow();
		if(i>=0) {
			textvendorid.setText((String) model.getValueAt(i, 0));
			textvendorname.setText((String) model.getValueAt(i, 1));
			textvendoraddress.setText((String) model.getValueAt(i, 2));
		}else {
			JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
		}
		
	}
	private void clearVendor() {
		textvendorid.setText("");
		textvendorname.setText("");
		textvendoraddress.setText("");
		
	}
	
	
	
	
}
	
		
		

