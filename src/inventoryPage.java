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

public class inventoryPage implements ActionListener {
	static Connection inventory_conn = GUIDriver.my_conn;
	private JFrame frame;
	private static JTable table;
	private static JTextField textpurchasequantity;
	private static JTextField textunitpurchaseprice;
	private static JComboBox<String> productcomboBox;
	private static JComboBox<String> vendorcomboBox;
	private static DefaultTableModel model;
	
	private static String btn_add_label="ADD";
	private static String btn_remove_label="REMOVE";
	private static String btn_save_label="SAVE";
	public void launchInventory() {
		table = new JTable();
		Object[] columns ={"product_code","vendor_id","purchase_quantity","unit_purchase_price"};
		
		model= new DefaultTableModel();
		frame = new JFrame("PURCHASE RECORD");
		frame.getContentPane().setBackground(new Color(135, 206, 250));
		frame.setBounds(100,100,723,522);
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
		pane.setBounds(46,140,593,257);
		frame.getContentPane().add(pane);
		
		JButton btn_save = new JButton(btn_save_label);
		btn_save.setBounds(294, 415, 83, 30);
		frame.getContentPane().add(btn_save);
		
		JButton btn_add = new JButton(btn_add_label);
		btn_add.setBounds(435, 100, 85, 30);
		frame.getContentPane().add(btn_add);
		
		JButton btn_remove = new JButton(btn_remove_label);
		btn_remove.setBounds(541, 100, 98, 30);
		frame.getContentPane().add(btn_remove);
		
		btn_save.addActionListener(new inventoryPage());
		btn_save.setActionCommand(btn_save_label);
		
		btn_add.addActionListener(new inventoryPage());
		btn_add.setActionCommand(btn_add_label);
		
		btn_remove.addActionListener(new inventoryPage());
		btn_remove.setActionCommand(btn_remove_label);
		
		productcomboBox = new JComboBox<String>();
		productcomboBox.setBounds(77, 46, 83, 30);
		frame.getContentPane().add(productcomboBox);
		
		Statement stmt1=null;
		ResultSet rs1;
		String my_sql1="select product_code from Product";
		try {
		stmt1=inventory_conn.createStatement();
		rs1 = stmt1.executeQuery(my_sql1);
		System.out.println("Check");
		while(rs1.next()) {
			String prod_codes = rs1.getString("product_code");
			System.out.println(prod_codes);
			productcomboBox.addItem(prod_codes);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		
		textpurchasequantity = new JTextField();
		textpurchasequantity.setBounds(345, 49, 104, 26);
		frame.getContentPane().add(textpurchasequantity);
		textpurchasequantity.setColumns(10);
		
		textunitpurchaseprice = new JTextField();
		textunitpurchaseprice.setBounds(499, 49, 96, 25);
		frame.getContentPane().add(textunitpurchaseprice);
		textunitpurchaseprice.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Product Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(77, 21, 76, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Purchase Quantity");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(345, 22, 104, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Unit Purchase Price");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(492, 22, 114, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Vendor id");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(221, 23, 57, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		vendorcomboBox = new JComboBox<String>();
		vendorcomboBox.setBounds(211, 46, 83, 30);
		frame.getContentPane().add(vendorcomboBox);
		Statement stmt2=null;
		ResultSet rs2;
		String my_sql2="select vendor_id from Vendor";
		try {
		stmt2=inventory_conn.createStatement();
		rs2 = stmt2.executeQuery(my_sql2);
		System.out.println("Check");
		while(rs2.next()) {
			String vendor_code= rs2.getString("vendor_id");
			System.out.println(vendor_code);
			vendorcomboBox.addItem(vendor_code);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		frame.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand=((JButton) e.getSource()).getActionCommand();
		System.out.println(actionCommand);
		if(actionCommand==btn_add_label) {
			if(textpurchasequantity.getText().isEmpty() || textunitpurchaseprice.getText().isEmpty() ) {
				JOptionPane.showMessageDialog(frame,"No Purchase quantity or Unit Purchase Price is mentioned");
			}
			else {	
			Object[] row = new Object[4];
	    	row[0]=productcomboBox.getSelectedItem();
	    	row[1]=vendorcomboBox.getSelectedItem();
	    	row[2]=textpurchasequantity.getText();
	    	row[3]=textunitpurchaseprice.getText();
	    	model.addRow(row);
			}
			
		}
		
		else if(actionCommand==btn_remove_label) {
			int i = table.getSelectedRow();
			if(i>=0) {
				model.removeRow(i);
			}else {
				JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
			}
			
		}
		else if(actionCommand==btn_save_label) {
			String my_sql;
			Statement stmt;
			
			for(int j=0; j<=model.getRowCount()-1;j++) {
				try {
					stmt=inventory_conn.createStatement();
					my_sql="insert into purchase_record(product_code,vendor_id,purchase_quantity,unit_purchase_price) values('"+model.getValueAt(j, 0)+"','"+model.getValueAt(j, 1)+"','"+model.getValueAt(j, 2)+"','"+model.getValueAt(j, 3)+"')";                    
					System.out.println(my_sql);
					stmt.executeUpdate(my_sql);
					stmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
		
	}

}
