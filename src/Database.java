import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Database
{
	private static Database instance;
	ArrayList<Account> account = new ArrayList<Account>();
	
	
	public static Database getInstance()
	{
		if(instance==null)
		{
			instance=new Database();
		}
		return instance;
	}
	
	Account getAccount(String n)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n))
			{
				return account.get(i);
			}
		}
		
		return null;
	}
	
	Account getAccount(String n, String p)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n) && account.get(i).getPIN().equals(p))
			{
				return account.get(i);
			}
		}
		
		return null;
	}
	
	void addNewAccount(Account ac)
	{
		this.account.add(ac);
	}
	
	boolean isAccountNumberUnique(String n)
	{
		for(int i=0;i<account.size();i++)
		{
			if(account.get(i).getAccuntNo().equals(n))
			{
				return false;
			}
		}
		
		return true;
	}
	
	void saveData()
	{
		System.out.println("saved");
		try
		{
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File("AccountList.txt")));
			for(int i=0;i<account.size();i++)
			{
				bw.write(String.valueOf(account.get(i)));
			}
			bw.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	void printAccounts()
	{
		for(int i=0;i<account.size();i++)
		{
			System.out.println(account.get(i).getAccuntNo() +" "+ account.get(i).getPIN());
		}
	}
	
	void loadData()
	{
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("AccountList.txt")));
			String type;
			DateFormat df = new SimpleDateFormat("EEE MMM dd kk:mm:ss zzz yyyy");
			
			while((type=br.readLine()) != null)
			{
				Account ac;
				
				if(type.equals(String.valueOf(Account.SAVINGS_ACCOUNT)))
				{
					ac=new SavingsAccount(br.readLine(),br.readLine(),Double.parseDouble(br.readLine()),
							new UserInformation(br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine(),
									br.readLine(), br.readLine(), br.readLine(), df.parse(br.readLine())));
					ac.isActivated=Boolean.getBoolean(br.readLine());
				}
				else
				{
					ac=new CurrentAccount(br.readLine(),br.readLine(),Double.parseDouble(br.readLine()),
							new UserInformation(br.readLine(), br.readLine(), br.readLine(), br.readLine(), br.readLine(),
									br.readLine(), br.readLine(), br.readLine(), df.parse(br.readLine())));
					ac.isActivated=Boolean.getBoolean(br.readLine());
				}
				addNewAccount(ac);
				
			}
			br.close();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (NumberFormatException e)
		{	
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}
}
