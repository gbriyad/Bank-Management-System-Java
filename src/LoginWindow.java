import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class LoginWindow extends JFrame implements ActionListener
{
	
	JPasswordField passF = new JPasswordField("",20);
	JTextField accountNoT = new JTextField("",20);
	JButton loginB = new JButton("Login"), signUpB= new JButton("Create a new Account");
	JLabel imgLabel = new JLabel(new ImageIcon("bank.jpg"));
	Database db = Database.getInstance();
	JPanel jp = new JPanel();
	JPanel jp2= new JPanel();
	
	public LoginWindow()
	{
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				db.saveData();
				System.exit(0);
			}
		});
		db.printAccounts();
	    //this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.setSize(500, 550);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new GridLayout(1, 2));
	    
	    loginB.addActionListener(this);
	    signUpB.addActionListener(this);
	    
	    this.add(imgLabel);
	    this.add(jp);
	    
	    jp.setLayout(new GridLayout(3, 1));
	    jp.add(new JLabel());
	    jp.add(jp2);
	    jp.add(new JLabel());
	    //jp2.setLayout(new BoxLayout(jp2,BoxLayout.Y_AXIS));
	    jp2.add(new JLabel("Account No: "));
	    jp2.add(accountNoT);
	    jp2.add(new JLabel("Password "));
	    jp2.add(passF);
	    jp2.add(loginB);
	    jp2.add(new JLabel("OR"));
	    jp2.add(signUpB);
	    this.revalidate();
	}
	
	public static void main(String args[])
	{
		
		Database.getInstance().loadData();
		new LoginWindow();
	}

	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Login"))
		{
			Account ac;
			if((ac=db.getAccount(accountNoT.getText(), passF.getText()))!=null)
			{
				this.dispose();
				new AccountDashboard(ac);
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Account number & Password didn't Match!","Login Failed",JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getActionCommand().equals("Create a new Account"))
		{
			this.dispose();
			new SignUpWindow();
		}
	}
}
