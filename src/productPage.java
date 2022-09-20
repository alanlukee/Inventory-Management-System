import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class productPage implements ActionListener{
	static Connection product_conn = GUIDriver.my_conn;
	private  static JFrame frame;
	private static JTextField textproductcode;
	private  static JTextField textproductdesp;
	private  static JTable table;
	private  static DefaultTableModel model;
	private static JComboBox<String> categorycombobox;
	
	private static String btn_update_label="UPDATE";
	private static String btn_create_label="CREATE";
	private static String btn_load_label="LOAD Sel.";
	private static String btn_clear_label="CLEAR";

	public void launchProduct() {
		table = new JTable();
		Object[] columns ={"product_code","category_code","product_desp"};
		
		model= new DefaultTableModel();
		JFrame frame = new JFrame("PRODUCT");
		frame.getContentPane().setBackground(new Color(250, 128, 114));
		frame.setBounds(100,100,850,556);
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
		pane.setBounds(10,10,659,347);
		frame.getContentPane().add(pane);
		
		textproductcode = new JTextField();
		textproductcode.setBounds(38, 408, 114, 35);
		frame.getContentPane().add(textproductcode);
		textproductcode.setColumns(10);
		
		/*textcategorycode = new JTextField();
		textcategorycode.setBounds(224, 408, 114, 35);
		frame.getContentPane().add(textcategorycode);
		textcategorycode.setColumns(10);*/
		
		categorycombobox = new JComboBox<String>();
		categorycombobox.setBounds(224, 408, 114, 35);
		frame.getContentPane().add(categorycombobox);
		Statement stmt1=null;
		ResultSet rs1;
		String my_sql1="select category_code from Category";
		try {
		stmt1=product_conn.createStatement();
		rs1 = stmt1.executeQuery(my_sql1);
		System.out.println("Check");
		while(rs1.next()) {
			String cat_codes = rs1.getString("category_code");
			System.out.println(cat_codes);
			categorycombobox.addItem(cat_codes);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		
		/*categorycombobox.addItem("a");
		categorycombobox.addItem("b");
		categorycombobox.addItem("c");
		categorycombobox.addItem("d");
		categorycombobox.setSelectedItem("c");*/
		
		textproductdesp = new JTextField();
		textproductdesp.setBounds(425, 408, 140, 35);
		frame.getContentPane().add(textproductdesp);
		textproductdesp.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Product Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(49, 379, 94, 19);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Category Code");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(236, 382, 85, 16);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Product Desp");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(449, 385, 81, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btn_load = new JButton("LOAD Sel.");
		btn_load.setBounds(713, 304, 95, 35);
		frame.getContentPane().add(btn_load);
		
		JButton btn_clear = new JButton("CLEAR");
		btn_clear.setBounds(713, 378, 85, 28);
		frame.getContentPane().add(btn_clear);
		
		JButton btn_update = new JButton("UPDATE");
		btn_update.setBounds(608, 466, 95, 28);
		frame.getContentPane().add(btn_update);
		
		JButton btn_create = new JButton("CREATE");
		btn_create.setBounds(713, 466, 95, 28);
		frame.getContentPane().add(btn_create);
		
		btn_update.addActionListener(new productPage());
		btn_update.setActionCommand(btn_update_label);
		
		btn_create.addActionListener(new productPage());
		btn_create.setActionCommand(btn_create_label);
		
		btn_load.addActionListener(new productPage());
		btn_load.setActionCommand(btn_load_label);
		
		btn_clear.addActionListener(new productPage());
		btn_clear.setActionCommand(btn_clear_label);
		
		Object[] row = new Object[3];
		Statement stmt= null;
		String my_sql="select * from Product";
		System.out.println(my_sql);
	    ResultSet rs;
	    try {
			stmt=product_conn.createStatement();
			rs = stmt.executeQuery(my_sql);
			
		      while (rs.next()) {
		    		row[0]=rs.getString("product_code");
					row[1]=rs.getString("category_code");
					row[2]=rs.getString("product_desp");
					model.addRow(row);     
		      }
		} catch (SQLException e) {
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
			if(textproductcode.getText().isEmpty()){
				JOptionPane.showMessageDialog(frame,"No Data for updation");	
			}
			else {
				Statement stmt=null;
				ResultSet rs;
				String my_sql="select * from Product where product_code='"+textproductcode.getText()+"'";
				System.out.println(my_sql);
				int i=0;
				try {
					stmt=product_conn.createStatement();
					rs=stmt.executeQuery(my_sql);
					while(rs.next()) {
						i=i+1;
					}
					rs.close();
			        stmt.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				if(i>0) {
					my_sql="update Product set category_code='"+categorycombobox.getName()+"',"
							+ "product_desp='"+textproductdesp.getText()+"' where product_code='"+textproductcode.getText()+"'";
					System.out.println(my_sql);
					try {
						stmt=product_conn.createStatement();
						stmt.executeUpdate(my_sql);
						stmt.close();
						System.out.println(model.getRowCount());
						for(int j=0;j<model.getRowCount()-1;j++) {
							System.out.println(j);
							if(textproductcode.getText().equals((String)model.getValueAt(j, 0))) {
								model.removeRow(j);
								Object[] row = new Object[3];
						    	row[0]=textproductcode.getText();
						    	row[1]=categorycombobox.getName();
						    	row[2]=textproductdesp.getText();
						    	model.addRow(row);
						    	break;
							}
							
						}	
						JOptionPane.showMessageDialog(frame,"Product succesfully updated!");
				    	//frame.revalidate();
					}
					
					catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else {
					//data does not exists
					JOptionPane.showMessageDialog(frame,"Product Code does not exists");	
				}
			}
		}
		if(actionCommand==btn_create_label) {
			//create button
			if(textproductcode.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame,"No Data for creation");	
			}
			else {
				String my_sql="select * from Product where product_code='"+textproductcode.getText()+"'";
				Statement stmt= null;
				ResultSet rs;
				int i=0;
				try {
					stmt=product_conn.createStatement();
					rs = stmt.executeQuery(my_sql);
					while(rs.next()) {
						i=i+1;
					}
					 rs.close();
			         stmt.close();	
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
							if(i==0) {
							  my_sql="insert into Product values('"+textproductcode.getText()+"','"+categorycombobox.getSelectedItem()+"','"+textproductdesp.getText()+"')";
							  System.out.println(my_sql);
								try {
									System.out.println("ALAN1");
									stmt=product_conn.createStatement();
									stmt.executeUpdate(my_sql);
									stmt.close();
									System.out.println("ALAN2");
									//category_conn.commit();
							    	Object[] row = new Object[3];
							    	row[0]=textproductcode.getText();
							    	row[1]=categorycombobox.getSelectedItem();
							    	row[2]=textproductdesp.getText();
							    	
							    	model.addRow(row);
							    	//frame.revalidate();
							    	System.out.println("ALAN3");
							    	
							    	JOptionPane.showMessageDialog(frame,"Product succesfully created!");
							    	System.out.println("ALAN4");
							    	 
							    	System.out.println("ALAN5");
							    	
									
								} catch (SQLException e1) {
									System.out.println("ALAN6");
									e1.printStackTrace();
								}
								  
								System.out.println("ALAN7");
							}
							
							else {
								JOptionPane.showMessageDialog(frame,"Product already exists!");	
							}	
			}
			
		}
		if(actionCommand==btn_load_label) {
			//load
			int i = table.getSelectedRow();
			if(i>=0) {
				textproductcode.setText((String) model.getValueAt(i, 0));
				categorycombobox.setSelectedItem((String) model.getValueAt(i, 1));
				textproductdesp.setText((String) model.getValueAt(i, 2));
			}else {
				JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
			}
			
			
		}
		if(actionCommand==btn_clear_label) {
			//clear button
			textproductcode.setText("");
			categorycombobox.setSelectedItem((String) model.getValueAt(0, 1));
			textproductdesp.setText("");
		}
		
		
		
	
		
	}

}
