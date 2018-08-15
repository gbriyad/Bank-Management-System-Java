
public class CurrentAccount extends Account
{
	public CurrentAccount(UserInformation u)
	{
		super(u);
		setMinBalance(1000);
		setWithdrawalLimit(1000, 50000);
		setBalance(1000);
	}
	
	public CurrentAccount(String an, String pin,double balance, UserInformation u)
	{
		this(u);
		super.setAccountNo(an);
		super.setPIN(pin);
		super.setBalance(balance);
	}

	void setMinBalance(double a)
	{
		minBalance=a;
	}

	void setWithdrawalLimit(double l,double h)
	{
		minWithdrawal=l;
		maxWithdrawal=h;
	}
	
	int getAccountType()
	{
		return Account.CURRENT_ACCOUNT;
	}
}
