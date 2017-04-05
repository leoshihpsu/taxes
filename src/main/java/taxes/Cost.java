package taxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/taxes")
public class Cost {


    public static final String INCOME = "income";


    @RequestMapping(path = "/save", method = {RequestMethod.POST})
    public String saveTransaction(@RequestBody Transaction lineToSave) throws IOException {

        BigDecimal tempCost = new BigDecimal(lineToSave.getCost());
        lineToSave.setVat(tempCost);
        File file = new File("test.txt");
        Writer writer = new FileWriter(file, true);  // mozna tu wyzerowac plik false
        writer.write(lineToSave + System.lineSeparator());
        writer.flush();
        return "new transaction saved";
    }


    @RequestMapping(path = "/test", method = {RequestMethod.GET})
    public List<Transaction> reader() throws IOException {
        List<Transaction> list = new ArrayList<Transaction>();
        File file = new File("test.txt");
        Scanner sc = new Scanner(file);
        ObjectMapper mapper = new ObjectMapper();
        while (sc.hasNextLine()) {
            Transaction newTransaction = mapper.readValue(sc.nextLine(), Transaction.class);
            list.add(newTransaction);

        }
        return list;
    }

    @RequestMapping(path = "/income", method = {RequestMethod.GET})
    public List<Transaction> income() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("income")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
    }

    @RequestMapping(path = "/costs", method = {RequestMethod.GET})
    public List<Transaction> costs() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("cost")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
    }

    @RequestMapping(path = "/incomeSum", method = {RequestMethod.GET})
    public double incomeSum() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
    }

    @RequestMapping(path = "/costsSum", method = {RequestMethod.GET})
    public double costSum() throws IOException {
        double sumTmp = reader().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();
        return sumTmp;

    }

    @RequestMapping(path = "/costsLast5", method = {RequestMethod.GET})
    public List<Transaction> costsLast30() throws IOException, ParseException {
        List<Transaction> temp30 = reader().stream().filter(s -> s.getType().equals("cost")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
        int L = 5;
        List<Transaction> newList = new ArrayList<Transaction>(temp30.subList(0, L));
        return newList;
    }

    @RequestMapping(path = "/incomeLast5", method = {RequestMethod.GET})
    public List<Transaction> incomeLast5() throws IOException, ParseException {
        List<Transaction> temp30 = reader().stream().filter(s -> s.getType().equals("income")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
        int L = 5;
        List<Transaction> newList = new ArrayList<Transaction>(temp30.subList(0, L));
        return newList;
    }

@RequestMapping(path = "/taxesAll", method = {RequestMethod.GET})
    public BigDecimal taxesAll() throws IOException, ParseException {
        double incomeTemp = reader().stream().filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
        double costTemp = reader().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(incomeTemp);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
    BigDecimal tmpVatToPay = new BigDecimal(costTemp);
    BigDecimal vatToPay = tmpVatToPay.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
    BigDecimal vatTotal = vatPaid.subtract(vatToPay);
        return vatTotal;
    }

    @RequestMapping(path = "/taxesFromDate/{date}", method = {RequestMethod.GET})
    public String taxesFromDate(@PathVariable String date) throws IOException, ParseException {

        double XdayIncome = reader().stream().filter(s -> s.getType().equals("income"))
                .filter(s -> (LocalDate.parse(s.getDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .isAfter(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))))).mapToDouble(Transaction::getCost).sum();

        double XdayCost = reader().stream().filter(s -> s.getType().equals("cost"))
                .filter(s -> (LocalDate.parse(s.getDate(),DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .isAfter( LocalDate.parse(date,DateTimeFormatter.ofPattern("yyyy-MM-dd"))))).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(XdayIncome);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPaid = new BigDecimal(XdayCost);
        BigDecimal vatToPaid = tmpVatToPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal XdayVatTotal = vatPaid.subtract(vatToPaid);


        return "income: " + XdayIncome + " cost: " + XdayCost + " Taxy: " + XdayVatTotal;
    }

    @RequestMapping(path = "/taxesWithDates/{number}", method = {RequestMethod.GET})
    public BigDecimal taxesWithDates(@PathVariable String number) throws IOException, ParseException {

        Predicate<Transaction> withDates = t -> {
            LocalDate dt = LocalDate.parse(t.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LocalDate now = LocalDate.now();
            int days = Period.between(dt, now).getDays();
            if (ChronoUnit.DAYS.between(dt, now) <= Integer.parseInt(number)) {
                return true;
            } else {
                return false;
            }
        };

        double incomeTemp = reader().stream().filter(withDates).filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
        double costTemp = reader().stream().filter(withDates).filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(incomeTemp);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPay = new BigDecimal(costTemp);
        BigDecimal vatToPay = tmpVatToPay.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal vatTotal = vatPaid.subtract(vatToPay);
        return vatTotal;
    }


}
