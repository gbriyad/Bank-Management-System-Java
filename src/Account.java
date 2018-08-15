import java.util.Random;

public abstract class Account
{
	public static final int SAVINGS_ACCOUNT=1;
	public static final int CURRENT_ACCOUNT=2;
	
	public static final int INSUFFICIENT_BALANCE=1;
	public static final int WITHDRAWAL_LIMIT_UNDER=2;
	public static final int WITHDRAWAL_LIMIT_OVER=3;
	
	private String accountNo;
	private String password;
	
	protected double balance;
	protected double minBalance;
	protected double minWithdrawal;
	protected double maxWithdrawal;
	
	protected UserInformation user;
	
	boolean isActivated;

	Account(UserInformation u)
	{
		accountNo=generateUniqueAccountNumber();
		password=generatePIN();
		user=u;
	}
	
	abstract void setMinBalance(double a);
	abstract void setWithdrawalLimit(double l, double h);
	abstract int getAccountType();
	
	double getBalance()
	{
		return balance;
	}
	void setBalance(double b)
	{
		balance=b;
	}
	
	String getAccuntNo()
	{
		return accountNo;
	}
	void setAccountNo(String s)
	{
		accountNo=s;
	}
	String getPIN()
	{
		return password;
	}
	void setPIN(String s)
	{
		password=s;
	}
	
	void activateAccount()
	{
		this.isActivated=true;
	}
	
	String generateUniqueAccountNumber()
	{
		Random r = new Random();
		Database db = Database.getInstance();
		String accountNum=String.valueOf(r.nextInt(10000000)+89999999);
		if(db.isAccountNumberUnique(accountNum))
		{
			return accountNum;
		}
		return generateUniqueAccountNumber();
		
	}
	String generatePIN()
	{
		Random r = new Random();
		return String.valueOf(r.nextInt(1000)+8999);
	}
	
	boolean payBill(double amount)
	{
		if(balance-amount<minBalance)
			return false;
		
		balance-=amount;
		return true;	
	}
	
	void depositMoney(double amount)
	{
		this.balance+=amount;
	}
	
	boolean transferMoney(Account ac, double amount)
	{
		if(balance-amount<minBalance)
			return false;
		
		this.balance-=amount;
		ac.balance+=amount;
		return true;	
	}
	
	int withdrawMoney(double amount)
	{
		if(amount<minWithdrawal)
			return WITHDRAWAL_LIMIT_UNDER;
		if(amount>maxWithdrawal)
			return WITHDRAWAL_LIMIT_OVER;
		if(balance-amount<minBalance)
			return INSUFFICIENT_BALANCE;
		
		balance-=amount;
		return 0;
	}
	
	public String toString()
	{
		return getAccountType()+"\n"+ accountNo + "\n" + password + "\n" + balance + "\n" + user + "\n" + isActivated + "\n";
	}
}
