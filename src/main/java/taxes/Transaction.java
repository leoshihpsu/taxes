package taxes;


public class Transaction {
	private Double cost;
	private Double vat;
	private String type;
	private String date;
	private String description;
	
	/*public Transaction(Double cost, Double vat, String type, String date, String description) {
		this.cost = cost;
		this.vat = vat;
		this.type = type;
		this.description = description;
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
	    this.date = LocalDate.parse(date, dtf);

	}*/
	
	
	
	
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getVat() {
		return vat;
	}
	public void setVat(Double vat) {
		this.vat = vat;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	@Override
	public String toString() {
		return "{ " +
				" \"cost\": " + cost +
				", \"vat\": " + vat +
				", \"type\": "+ "\"" + type + "\"" +
				", \"date\": " + "\"" + date + "\"" +
				", \"description\": "+ "\"" + description + "\" " +
				"}";
	}
}
