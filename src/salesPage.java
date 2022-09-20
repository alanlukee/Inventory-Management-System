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
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class salesPage implements ActionListener {
	static Connection sales_conn = GUIDriver.my_conn;
	private JFrame frame;
	private static JTable table;
	private static DefaultTableModel model;
	private static JTextField textbillno;
	private static JTextField textsalequantity;
	private static JTextField billtotaltextField;
	//private static JTextField textunitsaleprice;
	private static JComboBox<String> productcomboBox;
	private static JComboBox<String> customercombobox;
	private static String btn_add_label="ADD";
	private static String btn_remove_label="REMOVE";
	//private static String btn_save_label="SAVE";
	
	private static int item_no=0;
	private static int bill_tot=0;
	
	public void launchSales() {
		table = new JTable();
		Object[] columns ={"item_no","product_code","sales_quantity","purchase_id","unit_sales_price"};
		
		model= new DefaultTableModel();
		frame = new JFrame("SALES RECORD");
		frame.getContentPane().setBackground(new Color(233, 150, 122));
		frame.setBounds(100,100,717,472);
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
		pane.setBounds(10,119,576,266);
		frame.getContentPane().add(pane);
		
		textbillno = new JTextField();
		textbillno.setBounds(174, 23, 96, 28);
		frame.getContentPane().add(textbillno);
		textbillno.setColumns(10);
		
		productcomboBox = new JComboBox<String>();
		productcomboBox.setBounds(174, 81, 96, 28);
		frame.getContentPane().add(productcomboBox);
		
		Statement stmt1=null;
		ResultSet rs1;
		String my_sql1="select product_code from Product";
		try {
		stmt1=sales_conn.createStatement();
		rs1 = stmt1.executeQuery(my_sql1);
		System.out.println("Check");
		while(rs1.next()) {
			String pro_code = rs1.getString("product_code");
			System.out.println(pro_code);
			productcomboBox.addItem(pro_code);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		
		customercombobox = new JComboBox<String>();
		customercombobox.setBounds(345, 22, 96, 28);
		frame.getContentPane().add(customercombobox);
		
		Statement stmt2=null;
		ResultSet rs2;
		String my_sql2="select customer_id from customer";
		try {
		stmt2=sales_conn.createStatement();
		rs2 = stmt2.executeQuery(my_sql2);
		System.out.println("Check");
		while(rs2.next()) {
			String cus_id = rs2.getString("customer_id");
			System.out.println(cus_id);
			customercombobox.addItem(cus_id);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		
		
		textsalequantity = new JTextField();
		textsalequantity.setBounds(345, 82, 96, 28);
		frame.getContentPane().add(textsalequantity);
		textsalequantity.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Bill No:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(200, 10, 45, 13);
		frame.getContentPane().add(lblNewLabel);
		
		Statement stmt3;
		try {
			stmt3=sales_conn.createStatement();
			ResultSet rs3;
			String my_sql3="SELECT nextval('bill_no') bill_no";
			rs3=stmt3.executeQuery(my_sql3);
			rs3.next();
			int billno=rs3.getInt("bill_no");
			System.out.println(billno);
			textbillno.setText(String.valueOf(billno));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		JLabel lblNewLabel_1 = new JLabel("Product Id");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(184, 61, 73, 13);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Customer Id");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(358, 10, 73, 13);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Sale Quantity");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(355, 60, 74, 13);
		frame.getContentPane().add(lblNewLabel_3);
		
		JButton btn_add = new JButton("ADD");
		btn_add.setBounds(596, 147, 73, 28);
		frame.getContentPane().add(btn_add);
		
		JButton btn_remove = new JButton("REMOVE");
		btn_remove.setBounds(596, 208, 86, 28);
		frame.getContentPane().add(btn_remove);
		
		billtotaltextField = new JTextField();
		billtotaltextField.setBounds(518, 395, 64, 30);
		frame.getContentPane().add(billtotaltextField);
		billtotaltextField.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Bill Total");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(592, 404, 52, 13);
		frame.getContentPane().add(lblNewLabel_4);
		
		//JButton btn_save = new JButton("SAVE");
		//btn_save.setBounds(287, 395, 85, 30);
		//frame.getContentPane().add(btn_save);
		
		//btn_save.addActionListener(new salesPage());
		//btn_save.setActionCommand(btn_save_label);
		
		btn_add.addActionListener(new salesPage());
		btn_add.setActionCommand(btn_add_label);
		
		btn_remove.addActionListener(new salesPage());
		btn_remove.setActionCommand(btn_remove_label);

		
		frame.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand=((JButton) e.getSource()).getActionCommand();
		System.out.println(actionCommand);
		if(actionCommand==btn_add_label) {
			int tot_availability = 0;
			Statement stmt;
			ResultSet rs;
			String passed;
			passed=(String) productcomboBox.getSelectedItem();
			String my_sql="select\r\n" + 
		       		"max( D.category_code) category_code,\r\n" + 
		       		"max(B.product_code) product_code,\r\n" + 
		       		"max(B.purchase_id) purchase_id,\r\n" + 
		       		"min(B.purchase_quantity) purchase_quantity,\r\n" + 
		       		"sum(coalesce(A.sales_quantity,0))sales_quantity,\r\n" + 
		       		"max(B.unit_purchase_price) unit_purchase_price,\r\n" + 
		       		"(ROUND(((max(B.unit_purchase_price)*(100+max(D.category_margin)))/100),2)) sales_price,\r\n" + 
		       		"(min(B.purchase_quantity)-sum(coalesce(A.sales_quantity,0))) availability \r\n" + 
		       		"from purchase_record B left outer join sales_record A on A.purchase_id=B.purchase_id , Product C, Category D\r\n" + 
		       		"where B.product_code=C.product_code and C.category_code= D.category_code\r\n" + 
		       		"and B.product_code='"+passed+"'\r\n" + 
		       		"group by B.purchase_id\r\n" + 
		       		"having (min(B.purchase_quantity)-sum(coalesce(A.sales_quantity,0)))>0\r\n" + 
		       		"order by B.product_code asc,B.purchase_id asc";
			
			try {
				stmt=sales_conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				//Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(my_sql);
				while(rs.next()) {
						tot_availability=tot_availability +rs.getInt("availability");	
				}
				System.out.println(tot_availability);
				int sale_quant=Integer.parseInt(textsalequantity.getText());
				System.out.println("sale quantity is"+sale_quant);
				if(sale_quant>tot_availability) {
					//System.out.println("Hello");
					JOptionPane.showMessageDialog(frame,"Insufficient Product Availability");	
				}
				else {
					int allocation=0;
					Statement stmt4 = null;
					System.out.println("Allocation started");
					rs.first();
					Object[] row = new Object[5];
					while( sale_quant>0) {
						//System.out.println("inside alloc loop");
						System.out.println(rs.getInt("availability"));
						if(sale_quant<=rs.getInt("availability")){
							allocation=sale_quant;
							sale_quant=sale_quant-allocation; //means sale quantity becomes zero
							System.out.println("if allocation= "+allocation+", salequantity"+sale_quant);
							item_no++;
							row[0]=item_no;
					    	row[1]=productcomboBox.getSelectedItem();
					    	row[2]=allocation;
					    	row[3]=rs.getInt("purchase_id");
					    	row[4]=rs.getInt("sales_price");
					    	bill_tot=bill_tot+(allocation*rs.getInt("sales_price"));
							billtotaltextField.setText(String.valueOf(bill_tot));
					    	model.addRow(row);
					    	
					    	
					    	stmt4 =sales_conn.createStatement();
					    	String my_sql4="insert into sales_record values("+textbillno.getText()+","+item_no+","+rs.getInt("purchase_id")+","+allocation+","+rs.getInt("sales_price")+")";                           
							System.out.println(my_sql4);
							stmt4.executeUpdate(my_sql4);	
							
						}
						else {
							allocation=rs.getInt("availability");
							sale_quant=sale_quant-allocation;
							System.out.println("else allocation="+allocation+",sale_quantity"+sale_quant);
							item_no++;
							row[0]=item_no;
					    	row[1]=productcomboBox.getSelectedItem();
					    	row[2]=allocation;
					    	row[3]=rs.getInt("purchase_id");
					    	row[4]=rs.getInt("sales_price");
					    	bill_tot=bill_tot+(allocation*rs.getInt("sales_price"));
							billtotaltextField.setText(String.valueOf(bill_tot));
					    	model.addRow(row);
				
					    	Statement stmt5=sales_conn.createStatement();
					    	String my_sql5="insert into sales_record values("+textbillno.getText()+","+item_no+","+rs.getInt("purchase_id")+","+allocation+","+rs.getInt("sales_price")+")"; 					    	System.out.println(my_sql5);
					    	stmt5.executeUpdate(my_sql5);
					    	
						}
						rs.next();
					}
					
				}	
			
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}	
		}
		else if(actionCommand==btn_remove_label) {
			Statement stmt7=null;
			
			int i = table.getSelectedRow();
			if(i>=0) {
				try {
					stmt7=sales_conn.createStatement();
					String my_sql7;
					my_sql7="delete from sales_record where bill_no="+textbillno.getText()+" and item_no="+model.getValueAt(i,0);
					Integer saleqt= (Integer)model.getValueAt(i, 2);
					Integer salepri= (Integer)model.getValueAt(i, 4);
					bill_tot=bill_tot-( saleqt*salepri);
					billtotaltextField.setText(Integer.toString(bill_tot));
					System.out.println(my_sql7);
					stmt7.executeUpdate(my_sql7);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
				model.removeRow(i);
			}else {
				JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
			}
			
			
			
		}
		
	}

}
