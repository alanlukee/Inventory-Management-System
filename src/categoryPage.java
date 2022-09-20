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

public class categoryPage implements ActionListener {
	static Connection category_conn = GUIDriver.my_conn;
	private  static JFrame frame;
	private  static JTextField textcategorycode;
	private  static JTextField textcategorydesp;
	private  static JTextField textcategorymargin;
	private static JTable table;
	private static DefaultTableModel model;
	
	private static String btn_update_label="UPDATE";
	private static String btn_create_label="CREATE";
	private static String btn_load_label="LOAD Sel.";
	private static String btn_clear_label="CLEAR";

	public void launchCategory() {
		table = new JTable();
		Object[] columns ={"category_code","category_desp","category_margin"};
		
		model= new DefaultTableModel();
		frame = new JFrame("CATEGORY");
		frame.getContentPane().setBackground(new Color(222, 184, 135));
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
		
		JLabel lblNewLabel = new JLabel("Category code");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(29, 328, 122, 28);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Category Desp.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(172, 332, 109, 20);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Category Margin");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(326, 330, 122, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btn_update = new JButton(btn_update_label);
		btn_update.setBounds(456, 426, 96, 28);
		frame.getContentPane().add(btn_update);
		
		JButton btn_create = new JButton(btn_create_label);
		btn_create.setBounds(565, 426, 88, 28);
		frame.getContentPane().add(btn_create);
		
		textcategorycode = new JTextField();
		textcategorycode.setBounds(31, 361, 96, 34);
		frame.getContentPane().add(textcategorycode);
		textcategorycode.setColumns(10);
		
		textcategorydesp = new JTextField();
		textcategorydesp.setBounds(158, 362, 142, 34);
		frame.getContentPane().add(textcategorydesp);
		textcategorydesp.setColumns(10);
		
		textcategorymargin = new JTextField();
		textcategorymargin.setBounds(326, 364, 109, 28);
		frame.getContentPane().add(textcategorymargin);
		textcategorymargin.setColumns(10);
		
		JButton btn_load = new JButton(btn_load_label);
		btn_load.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btn_load.setBounds(582, 305, 96, 34);
		frame.getContentPane().add(btn_load);
		
		JButton btn_clear = new JButton(btn_clear_label);
		btn_clear.setBounds(590, 349, 88, 34);
		frame.getContentPane().add(btn_clear);
		
		
		btn_update.addActionListener(new categoryPage());
		btn_update.setActionCommand(btn_update_label);
		
		btn_create.addActionListener(new categoryPage());
		btn_create.setActionCommand(btn_create_label);
		
		btn_load.addActionListener(new categoryPage());
		btn_load.setActionCommand(btn_load_label);
		
		btn_clear.addActionListener(new categoryPage());
		btn_clear.setActionCommand(btn_clear_label);
		
		Object[] row = new Object[3];
		Statement stmt= null;
		String my_sql="select * from Category";
		System.out.println(my_sql);
	    ResultSet rs;
			try {
				stmt=category_conn.createStatement();
				rs = stmt.executeQuery(my_sql);
				
			      while (rs.next()) {
			    		row[0]=rs.getString("category_code");
						row[1]=rs.getString("category_desp");
						row[2]=rs.getString("category_margin");
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
			if(textcategorycode.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame,"No Data for updation");	
			}
			else{
				String my_sql = "select * from Category where category_code='"+textcategorycode.getText()+"'";
				System.out.println(my_sql);
				Statement stmt= null;
				ResultSet rs;
				int i=0;
				try {
					stmt=category_conn.createStatement();
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
				if(i>0) {
					//data exists and can be updated
					String margin =textcategorymargin.getText(); 
					if(margin.isEmpty()) {
						margin="0";
					}
					 my_sql="update Category set category_desp='" +textcategorydesp.getText()+ "',category_margin=" +textcategorymargin.getText()+"  where category_code='"+textcategorycode.getText()+"'";
					 
					System.out.println(my_sql);
					try {
						stmt=category_conn.createStatement();
						stmt.executeUpdate(my_sql);
						stmt.close();
						//category_conn.commit();
				    	System.out.println(model.getRowCount());
				    	for(int j=0; j<=model.getRowCount()-1;j++) {
				    		System.out.println(j);
				    		if(textcategorycode.getText().equals((String)model.getValueAt(j, 0))){
				    			model.removeRow(j);
				    			Object[] row = new Object[3];
						    	row[0]=textcategorycode.getText();
						    	row[1]=textcategorydesp.getText();
						    	row[2]=margin;
						    	model.addRow(row);
				    			break;
				    		}
				    	}
				    	JOptionPane.showMessageDialog(frame,"Category succesfully updated!");
				    	frame.revalidate(); 
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}	
				else {
					//data does not exists
					JOptionPane.showMessageDialog(frame,"Category Code does not exists");	
				}
			}	
		}
		else if(actionCommand==btn_create_label){
			//create button
			if(textcategorycode.getText().isEmpty()) {
				JOptionPane.showMessageDialog(frame,"No Data for creation");	
			}
			else{
					String my_sql = "select * from Category where category_code='"+textcategorycode.getText()+"'";
					System.out.println(my_sql);
					Statement stmt= null;
					ResultSet rs;
					int i=0;
					try {
						stmt=category_conn.createStatement();
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
						String margin =textcategorymargin.getText(); 
						if(margin.isEmpty()) {
							margin="0";
						}
						 my_sql="insert into Category values('"+textcategorycode.getText()+"','"+textcategorydesp.getText()+"',"+ margin+")";
						 
						System.out.println(my_sql);
						try {
							stmt=category_conn.createStatement();
							stmt.executeUpdate(my_sql);
							stmt.close();
						 	//category_conn.commit();
					    	Object[] row = new Object[3];
					    	row[0]=textcategorycode.getText();
					    	row[1]=textcategorydesp.getText();
					    	row[2]=margin;
					    	
					    	model.addRow(row);
					    	
					    	JOptionPane.showMessageDialog(frame,"Category succesfully created!");
					    	//frame.revalidate();   
						} 
						catch (SQLException e1) {
							e1.printStackTrace();
						}	
					}	
					else {
						//data is duplicate
						JOptionPane.showMessageDialog(frame,"Category Code already exists");	
					}
				}
			}
		else if(actionCommand==btn_load_label){
			//load button
			int i = table.getSelectedRow();
			if(i>=0) {
				textcategorycode.setText((String) model.getValueAt(i, 0));
				textcategorydesp.setText((String) model.getValueAt(i, 1));
				textcategorymargin.setText((String) model.getValueAt(i, 2));
			}else {
				JOptionPane.showMessageDialog(frame,"No Row Selected in the Table");
			}
			
			}
		else if(actionCommand==btn_clear_label){
			//clear button
			textcategorycode.setText("");
			textcategorydesp.setText("");
			textcategorymargin.setText("");
			
			}
		
		frame.revalidate();	
	}
}
		
		
		
		
		
