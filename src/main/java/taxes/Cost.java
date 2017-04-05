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



    @RequestMapping(path = "/save", method = {RequestMethod.POST})
    public String saveTransaction(@RequestBody Transaction lineToSave) throws IOException {
        return CostProcessor.saveProcessor(lineToSave);
    }

    @RequestMapping(path = "/test", method = {RequestMethod.GET})
    public List<Transaction> reader() throws IOException {
        return CostProcessor.readerProcessor();
    }

    @RequestMapping(path = "/taxesAll", method = {RequestMethod.GET})
    public static BigDecimal taxesAll() throws IOException, ParseException {
        return CostProcessor.taxesAllProcessor();
    }

    @RequestMapping(path = "/taxesFromDate/{date}", method = {RequestMethod.GET})
    public String taxesFromDate(@PathVariable String date) throws IOException, ParseException {
        return CostProcessor.taxesFromDateProcessor(date);
    }

    @RequestMapping(path = "/taxesWithDates/{number}", method = {RequestMethod.GET})
    public BigDecimal taxesWithDates(@PathVariable String number) throws IOException, ParseException {
        return CostProcessor.taxesWithDatesProcessor(number);
    }

/////////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(path = "/income", method = {RequestMethod.GET})
    public List<Transaction> income() throws IOException {
        return CostProcessor.incomeProcessor();
    }

    @RequestMapping(path = "/costs", method = {RequestMethod.GET})
    public List<Transaction> costs() throws IOException {
        return CostProcessor.costsProcessor();
    }

    @RequestMapping(path = "/incomeSum", method = {RequestMethod.GET})
    public double incomeSum() throws IOException {
        return CostProcessor.incomeSumProcessor();
    }

    @RequestMapping(path = "/costsSum", method = {RequestMethod.GET})
    public double costSum() throws IOException {
        return CostProcessor.costSumProcessor();
    }

    @RequestMapping(path = "/costsLast5", method = {RequestMethod.GET})
    public List<Transaction> costsLast30() throws IOException, ParseException {
        return CostProcessor.costsLast30Processor();
    }

    @RequestMapping(path = "/incomeLast5", method = {RequestMethod.GET})
    public List<Transaction> incomeLast5() throws IOException, ParseException {
        return CostProcessor.incomeLast5Processor();
    }

}
