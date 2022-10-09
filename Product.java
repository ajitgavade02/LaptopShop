import java.sql.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
public class Product extends JFrame implements ActionListener
{
	Container co;
	JButton b1,b2,b3,b4;
	JTextField t1,t2,t3,t4,t5;
	JComboBox c1,c2;
	JLabel l1,l2,l3,l4,l5,l6,l7,l8;
	JTable tbl;
	DefaultTableModel model;
	JScrollPane pane;
	Connection con;
	PreparedStatement ps;
	public Product()
	{
	setLayout(null);
	setVisible(true);
	setSize(1500,800);
	setTitle("Products");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	co=getContentPane();
	co.setBackground(Color.white);

	model=new DefaultTableModel();
	tbl=new JTable(model);
	model.addColumn("Prod_ID");
	model.addColumn("Prod_Name");
	model.addColumn("Quantity");
	model.addColumn("Price");
	model.addColumn("Date");
	model.addColumn("Comp_Name");
	model.addColumn("Warranty");

	String p[]={"Intel Dual core","Intel i3","Intel i5","Intel i7","Intel i9","Headphones","Battery","Charger"};
	String w[]={"1 Year","2 Year","3 Year","4 Year","5 Year"};

	b1=new JButton("HOME");
	b2=new JButton("ADD");
	b3=new JButton("UPDATE");
	b4=new JButton("DELETE");

	c1=new JComboBox(p);
	c2=new JComboBox(w);
	
	l1=new JLabel("Product ID");
	l2=new JLabel("Quantity");
	l3=new JLabel("Date");
	l4=new JLabel("Warranty");
	l5=new JLabel("PRODUCT");
	l6=new JLabel("Product Name");
	l7=new JLabel("Price");
	l8=new JLabel("Company Name");
	
	t1=new JTextField();
	t2=new JTextField();
	t3=new JTextField();
	t4=new JTextField();
	t5=new JTextField();

	l5.setBounds(600,20,200,50);
	l5.setFont(new Font("Verdana",Font.BOLD,30));
	l1.setBounds(100,100,150,30);
	l2.setBounds(100,150,150,50);
	l3.setBounds(100,200,150,50);
	l4.setBounds(100,250,150,50); 
	l6.setBounds(700,100,150,30);
	l7.setBounds(700,150,150,50);
	l8.setBounds(700,200,150,50);

	t1.setBounds(250,100,150,30);
	t2.setBounds(250,150,150,30);
	t3.setBounds(250,200,150,30);
	c2.setBounds(250,250,150,30);
	c1.setBounds(900,100,150,30);
	t4.setBounds(900,150,150,30);
	t5.setBounds(900,200,150,30);

	b1.setBounds(100,20,100,30);
	b2.setBounds(250,350,100,30);
	b3.setBounds(450,350,100,30);
	b4.setBounds(650,350,100,30);

	pane=new JScrollPane(tbl);
	pane.setBounds(50,450,1200,200);

	
	co.add(b1);	co.add(b2);	co.add(b3);	co.add(b4);	
	co.add(l1);	co.add(l2);	co.add(l3);	co.add(l4);	co.add(l5);	co.add(l6);	co.add(l7);	co.add(l8);
	co.add(t1);	co.add(t2);	co.add(t3);	co.add(c2);	co.add(t4);	co.add(t5);	co.add(c1);
	co.add(pane);
	b1.addActionListener(this);	b2.addActionListener(this);	b3.addActionListener(this);	b4.addActionListener(this);
select();
	}	
	public void set()
	{
		try
		{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");	
		con=DriverManager.getConnection("jdbc:odbc:mob_dsn");
	
			
		}
		catch(Exception exc){ }
	}

	public void clear()
	{
	t1.setText("");
	t2.setText("");
	t3.setText("");
	t4.setText("");
	t5.setText("");
	}
	public void select()
	{
		set();
		   try
		 {
           		 ps= con.prepareStatement("select * from tbl_product ");
            		ResultSet rs = ps.executeQuery();
		model.setRowCount(0);
       		     while(rs.next())
		{
		 try
		 {
              		  model.addRow(new Object[]{rs.getInt(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5), rs.getString(6),rs.getString(7)});
		  } catch (Exception e){  }
           		 }

      		  } catch (Exception e){  }
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b2)
		{
		set();
			try
			{
				ps=con.prepareStatement("insert into tbl_product values(?,?,?,?,?,?,?)");
				ps.setInt(1,Integer.parseInt(t1.getText()));
				ps.setString(2,c1.getItemAt(c1.getSelectedIndex()).toString());
				ps.setString(3,t2.getText());
				ps.setString(4,t4.getText());
				ps.setString(5,t3.getText());
				ps.setString(6,t5.getText());
				ps.setString(7,c2.getItemAt(c2.getSelectedIndex()).toString());
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this," Record inserted successfully ...");
				con.close();
				select();
				 clear();
			}
			catch(Exception exc){}
		}
		
		if(e.getSource()==b3)
		{
		set();
			try
			{
			
				ps=con.prepareStatement("update tbl_product set prod_nm=?,quantity=?,price=?,d=?,comp_nm=?,warranty=? where prod_id=? ");
				ps.setString(1,c1.getItemAt(c1.getSelectedIndex()).toString());
				ps.setString(2,t2.getText());
				ps.setString(3,t4.getText());
				ps.setString(4,t3.getText());
				ps.setString(5,t5.getText());
				ps.setString(6,c2.getItemAt(c2.getSelectedIndex()).toString());
				ps.setInt(7,Integer.parseInt(t1.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this," Record updated successfully ...");
				con.close();
				select();
				 clear();
			}
			catch(Exception exc){}
		}

		if(e.getSource()==b4)
		{
		set();
			try
			{
			
				ps=con.prepareStatement("delete from tbl_product where prod_id=? ");
				ps.setInt(1,Integer.parseInt(t1.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this," Record deleted successfully ...");
				con.close();
				select();
				 clear();
			}
			catch(Exception exc){}
		}
		if(e.getSource()==b1)
		{
		Home h=new Home();
		h.setVisible(true);
		this.setVisible(false);
		}
	}
	
	public static void main(String arg[])
	{
	new Product();
	}
}