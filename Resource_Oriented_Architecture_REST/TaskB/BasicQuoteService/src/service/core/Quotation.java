package service.core;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */
public class Quotation {
	public Quotation(String company, String reference, double price) {
		this.company = company;
		this.reference = reference;
		this.price = price;
		
	}
	
	public void merge(Quotation quotation) {
		if(price == 0)
			price = quotation.price;
		if(company == null)
			company = quotation.company;
		if(reference == null)
			reference = quotation.reference;
	}
	
	public String company;
	public String reference;
	public double price;
}
