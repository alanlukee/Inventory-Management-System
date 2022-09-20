import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
//import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
//import javax.swing.JComboBox;

public class product_availabilityPage implements ActionListener {
	static Connection product_avail = GUIDriver.my_conn;
	private  static JTable table;
	private  static DefaultTableModel model;
	private  static JFrame frame;
	private static JComboBox<String> productcomboBox;
	
	public void launchproductAvailability() {
		 table = new JTable();
		Object[] columns ={"category_code","product_code","purchase_id","purchase_quantity","sales_quantity","unit_purchase_price","sales_price","availability"};
		
		model= new DefaultTableModel();
		frame = new JFrame("Products Availability");
		frame.getContentPane().setBackground(new Color(233, 150, 122));
		frame.setBounds(100,100,712,481);
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
		pane.setBounds(10,218,680,170);
		frame.getContentPane().add(pane);
		
		productcomboBox = new JComboBox<String>();
		productcomboBox.setBounds(279, 52, 108, 30);
		frame.getContentPane().add(productcomboBox);
		
		Statement stmt1=null;
		ResultSet rs1;
		String my_sql1="select product_code from Product";
		try {
		stmt1=product_avail.createStatement();
		rs1 = stmt1.executeQuery(my_sql1);
		System.out.println("Check");
		productcomboBox.addItem("ALL");
		while(rs1.next()) {
			String prod_codes = rs1.getString("product_code");
			//System.out.println(prod_codes);
			productcomboBox.addItem(prod_codes);	
		}
		}
		catch (SQLException e1) {
		e1.printStackTrace();	
		}
		
		JLabel lblNewLabel = new JLabel("Product Code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(285, 23, 90, 19);
		frame.getContentPane().add(lblNewLabel);
		
		frame.setVisible(true);
		
		productcomboBox.addActionListener(new product_availabilityPage());
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object c = (Object)e.getSource();
		if(c==productcomboBox) {
			JComboBox<String> cb =(JComboBox<String>)e.getSource();
			String passed = (String)cb.getSelectedItem();
			System.out.println(passed);
			String my_sql;
			if(productcomboBox.getSelectedItem().equals("ALL")) {
				System.out.println("inside all");
			
			 my_sql="select\r\n" + 
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
			 		"group by B.purchase_id\r\n" + 
			 		"having (min(B.purchase_quantity)-sum(coalesce(A.sales_quantity,0)))>0\r\n" + 
			 		"order by B.product_code asc,B.purchase_id asc";
			}
			else {
				       my_sql="select\r\n" + 
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
			
			}
			
			Statement stmt= null;
			ResultSet rs;
			Object[] row = new Object[8];
			System.out.println(my_sql);
			
			try {
				stmt=product_avail.createStatement();
				rs = stmt.executeQuery(my_sql);
				int rowCount;
				boolean loopFlag=true;
				while(loopFlag) {
					 rowCount=model.getRowCount();
					if(rowCount>0) {
					model.removeRow(rowCount-1);	
					}
					else {
						loopFlag=false;
					}	 
				}
			      while (rs.next()) {
			    		row[0]=rs.getString("category_code");
			    		row[1]=rs.getString("product_code");
						row[2]=rs.getString("purchase_id");
						row[3]=rs.getString("purchase_quantity");
						row[4]=rs.getString("sales_quantity");
						row[5]=rs.getString("unit_purchase_price");
						row[6]=rs.getString("sales_price");
						row[7]=rs.getString("availability");
						model.addRow(row);     
			      }
			} catch (SQLException e1) {
				e1.printStackTrace();
			}	
		}	
	}
}
