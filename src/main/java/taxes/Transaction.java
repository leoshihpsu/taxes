package taxes;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Transaction {
    private Double cost;
    private BigDecimal vat;
    private String type;
    private String date;
    private String description;

    public Transaction(){}

    public Transaction(Double cost,  String type, String date, String description) {
        this.cost = cost;
        //vat = cost * 0.23;
        this.type = type;
        this.description = description;
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
        // this.date = LocalDate.parse(date, dtf);
        this.date = date;
    }


    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal cost) {

        this.vat = cost.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        //this.vat = cost.multiply(new BigDecimal(0.23), new MathContext(3));
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
                ", \"type\": " + "\"" + type + "\"" +
                ", \"date\": " + "\"" + date + "\"" +
                ", \"description\": " + "\"" + description + "\" " +
                "}";
    }
}
