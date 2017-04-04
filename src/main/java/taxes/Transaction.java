package taxes;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {
	private BigDecimal cost;
	private BigDecimal vat;
	private String type;
	private LocalDate date;
	private String description;
	
	public Transaction(BigDecimal cost, BigDecimal vat, String type, String date, String description) {
		this.cost = cost;
		this.vat = vat;
		this.type = type;
		this.description = description;
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
	    this.date = LocalDate.parse(date, dtf);
		
	}
	
	
	
	
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	public BigDecimal getVat() {
		return vat;
	}
	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
