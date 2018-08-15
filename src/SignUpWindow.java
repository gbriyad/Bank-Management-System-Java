import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class SignUpWindow extends JFrame implements ActionListener
{
	
	Database db = Database.getInstance();
	
	JTextField firstNameT = new JTextField("",20);
	JTextField lastNameT = new JTextField("",20);
	JTextField email = new JTextField("",20);
	JTextField phoneNo = new JTextField("",20);
	JTextField NID = new JTextField("",20);
	JTextField address = new JTextField("",20);
	JTextField occupation = new JTextField("",20);

	JRadioButton maleB  = new JRadioButton("male");
	JRadioButton femaleB  = new JRadioButton("female");
	ButtonGroup bg = new ButtonGroup();
	
	JComboBox<String> accountType = new JComboBox<String>();
	JComboBox<Integer> day = new JComboBox<Integer>();
	JComboBox<Integer> month = new JComboBox<Integer>();
	JComboBox<Integer> year = new JComboBox<Integer>();
	
	
	JLabel imgLabel = new JLabel(new ImageIcon("signup.jpg"));
	
	JPanel jp = new JPanel();
	JPanel jp1 = new JPanel();
	JPanel jp2 = new JPanel();
	JPanel jp3 = new JPanel();
	JPanel jp4 = new JPanel();
	JPanel jp5 = new JPanel();
	JPanel jp6 = new JPanel();
	JPanel jp7 = new JPanel();
	JPanel jp8 = new JPanel();
	JPanel jp9 = new JPanel();
	JPanel jp10 = new JPanel();
	
	JButton backB= new JButton("Back");
	JButton signUpB = new JButton("Create Account");
	
	public SignUpWindow()
	{
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we)
			{
				db.saveData();
				System.exit(0);
			}
		});
		this.setSize(690, 620);
	    this.setVisible(true);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setLayout(new GridLayout(1, 2, 10,0));
	    
	    this.add(imgLabel);
	    this.add(jp);
	    
	    jp.setLayout(new GridLayout(0, 1,10,20));
	    jp.add(new JLabel("Fill up the Form below", JLabel.CENTER));
	    
	    accountType.addItem("Savings Account");
	    accountType.addItem("Current Account");
	    jp.add(accountType);
	    
	    jp.add(jp1);
	    jp1.setLayout(new GridLayout(0, 2,5,0));
	    jp1.add(new JLabel("First name"));
	    jp1.add(firstNameT);
	    jp.add(jp9);
	    jp9.setLayout(new GridLayout(0, 2,5,0));
	    jp9.add(new JLabel("Last name"));
	    jp9.add(lastNameT);
	    
	    
	    jp.add(jp2);
	    bg.add(maleB);
	    bg.add(femaleB);
	    jp2.add(maleB);
	    jp2.add(femaleB);
	    
	    jp.add(jp3);
	    generateDate();
	    jp3.add(new JLabel("Birth Date: "));
	    jp3.add(day);
	    jp3.add(month);
	    jp3.add(year);

	    jp.add(jp4);
	    jp4.setLayout(new GridLayout(0, 2,5,0));
	    jp4.add(new JLabel("E-Mail: "));
	    jp4.add(email);
	    
	    jp.add(jp5);
	    jp5.setLayout(new GridLayout(0, 2,5,0));
	    jp5.add(new JLabel("Phone No: "));
	    jp5.add(phoneNo);
	    
	    jp.add(jp6);
	    jp6.setLayout(new GridLayout(0, 2,5,0));
	    jp6.add(new JLabel("NID No: "));
	    jp6.add(NID);
	    
	    jp.add(jp7);
	    jp7.setLayout(new GridLayout(0, 2,5,0));
	    jp7.add(new JLabel("Occupation: "));
	    jp7.add(occupation);
	    
	    jp.add(jp8);
	    jp8.setLayout(new GridLayout(0, 2,5,0));
	    jp8.add(new JLabel("Full Address: "));
	    jp8.add(address);
	    
	    jp.add(jp10);
	    jp10.setLayout(new GridLayout(0, 2, 5,0));
	    backB.addActionListener(this);
	    jp10.add(backB);
	    signUpB.addActionListener(this);
	    jp10.add(signUpB);
	}

	void generateDate()
	{
		for(int i=1;i<=31;i++)
		{
			day.addItem(i);
		}
		for(int i=1;i<=12;i++)
		{
			month.addItem(i);
		}
		for(int i=1900;i<=2018;i++)
		{
			year.addItem(i);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		System.out.println("hm");
		if(e.getActionCommand().equals("Back"))
		{
			this.dispose();
			new LoginWindow();
		}
		else if(e.getActionCommand().equals("Create Account"))
		{
			if(isFormFilled())
			{
				createNewAccount();
				this.dispose();
				new LoginWindow();
			}
			else
			{
				JOptionPane.showMessageDialog(null,"Please fill up all the fields","Failed", JOptionPane.ERROR_MESSAGE );
			}
		}
	}
	
	boolean isFormFilled()
	{
		if(firstNameT.getText().isEmpty() || lastNameT.getText().isEmpty() 
				|| email.getText().isEmpty() || phoneNo.getText().isEmpty()
				|| NID.getText().isEmpty() || address.getText().isEmpty() || occupation.getText().isEmpty())
		{
			return false;
		}
		if(!maleB.isSelected() && !femaleB.isSelected())
		{
			return false;
		}
		return true;
	}
	
	void createNewAccount()
	{
		String sex;
		Date d;
		Account ac;
		
		if(maleB.isSelected())
			sex="male";
		else
			sex="female";
		
		d=new Date((int)year.getSelectedItem(),(int)month.getSelectedItem(),(int)day.getSelectedItem());
		
		UserInformation u= new UserInformation(firstNameT.getText(), lastNameT.getText(), email.getText(),
				phoneNo.getText(), NID.getText(), address.getText(), occupation.getText(), 
				sex, d);
		
		if(accountType.getSelectedItem().equals("Savings Account"))
		{
			ac= new SavingsAccount(u);
			db.addNewAccount(ac);
		}
		else
		{
			ac = new CurrentAccount(u);
			db.addNewAccount(ac);
		}
		JOptionPane.showMessageDialog(null,"Account No:"+ac.getAccuntNo()+"\nPIN:"+ac.getPIN(),"Success", JOptionPane.INFORMATION_MESSAGE );
		
		
	}
}
