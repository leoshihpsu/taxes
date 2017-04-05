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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
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
        return reader().stream().filter(s -> s.getType().equals("income")).sorted((s1,s2)->s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());

    }

    @RequestMapping(path = "/costs", method = {RequestMethod.GET})
    public List<Transaction> costs() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("cost")).sorted((s1,s2)->s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());

    }

    @RequestMapping(path = "/incomeSum", method = {RequestMethod.GET})
    public double incomeSum() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();

    }

    @RequestMapping(path = "/costsSum", method = {RequestMethod.GET})
    public double costSum() throws IOException {
        return reader().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();

    }

}
