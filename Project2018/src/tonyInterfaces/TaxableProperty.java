package tonyInterfaces;
//A tax square that you pay tax on
public class TaxableProperty extends NamedLocation implements Taxable {

	int flat_amount, percentage;
	TaxableProperty(String name, Locatable leftSquare, Locatable rightSquare, int percentage, int flat) {
		super(name, leftSquare, rightSquare);
		this.flat_amount = flat;
		this.percentage = percentage;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getIncomePercentage() {
		// TODO Auto-generated method stub
		return percentage;
	}

	@Override
	public int getFlatAmount() {
		// TODO Auto-generated method stub
		return flat_amount;
	}

}
