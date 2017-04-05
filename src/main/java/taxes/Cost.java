package taxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/taxes")
public class Cost {


    public static final String INCOME = "income";
    List<Transaction> list = new ArrayList<Transaction>();

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
        return reader().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();
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

        // metoda z konwersja lub mozna poprostu sume po get.vat
        BigDecimal tmpVatPaid = new BigDecimal(incomeTemp);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPaid = new BigDecimal(costTemp);
        BigDecimal vatToPaid = tmpVatToPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);

        BigDecimal vatTotal = vatToPaid.subtract(vatPaid);


        return vatTotal;
    }

}
