import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.ImageIcon;
import javax.swing.JButton;

public class landingPage implements ActionListener {
	private static JButton button1;
	private static JButton button2;
	private static JButton button3;
	private static JButton button4;
	private static JButton button5;
	private static JButton button6;
	private static JButton button7;
	
	
	private static String button1_label="Category";
	private static String button2_label="Products";
	private static String button3_label="Products Availability";
	private static String button4_label="Inventory";
	private static String button5_label="Sale";
	private static String button6_label="Customer";
	private static String button7_label="Vendor";
	

		public void launchlandingPage() {
		
		JFrame frame = new JFrame("Inventory System");
		//frame.getContentPane().setBackground(new Color(233, 150, 122));
		JPanel panel = new JPanel(); 
		panel.setBackground(Color.green);
		
		frame.setBounds(300,100,700,400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(panel);
		panel.setLayout(null);
		
		JLabel label = new JLabel("INVENTORY MANAGEMENT SYSTEM");
		label.setBounds(240,10,300,25);
		panel.add(label);
		
		button1 = new JButton(button1_label);
		button1.setBounds(50, 50, 100, 25);
		
		
		button2 = new JButton(button2_label);
		button2.setBounds(500, 50, 100, 25);
		
		button3 = new JButton(button3_label);
		button3.setBounds(240, 90, 200, 50);
		
		button4 = new JButton(button4_label);
		button4.setBounds(50, 200, 100, 50);
		
		button5 = new JButton(button5_label);
		button5.setBounds(500, 200, 100, 50);
		
		button6 = new JButton(button6_label);
		button6.setBounds(275, 200, 100, 25);
		
		button7 = new JButton(button7_label);
		button7.setBounds(275, 300, 100, 25);
		
		

		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.add(button5);
		panel.add(button6);
		panel.add(button7);
		
		
		
		button1.addActionListener(new landingPage());
		button1.setActionCommand(button1_label);
		
		button2.addActionListener(new landingPage());
		button2.setActionCommand(button2_label);
		
		button3.addActionListener(new landingPage());
		button3.setActionCommand(button3_label);
		
		button4.addActionListener(new landingPage());
		button4.setActionCommand(button4_label);
		
		button5.addActionListener(new landingPage());
		button5.setActionCommand(button5_label);
		
		button6.addActionListener(new landingPage());
		button6.setActionCommand(button6_label);
		
		button7.addActionListener(new landingPage());
		button7.setActionCommand(button7_label);
		
	
		
		frame.setVisible(true);
		

	}

		@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand=((JButton) e.getSource()).getActionCommand();
		System.out.println(actionCommand);
		if(actionCommand==button1_label) {
			//Category button
			categoryPage category = new categoryPage();
			category.launchCategory();
			
		}
		else if(actionCommand==button2_label){
			//Product button
			productPage product = new productPage();
			product.launchProduct();
			}
		else if(actionCommand==button3_label){
			//Product availability button
			product_availabilityPage prod_avail = new product_availabilityPage();
			prod_avail.launchproductAvailability();
			}
		else if(actionCommand==button4_label){
			//inventory page button
			inventoryPage inventory = new inventoryPage();
			inventory.launchInventory();
			}
		else if(actionCommand==button5_label){
			//Sales button
			salesPage sales = new salesPage();
			sales.launchSales();
			}
		else if(actionCommand==button6_label) {
			//customer button
			customerPage customer = new customerPage();
			customer.launchcustomerPage();
		}
		else if(actionCommand==button7_label) {
			//vendor button
			vendorPage vendor = new vendorPage();
			vendor.launchvendorPage();
		}
		
		
	}

}
