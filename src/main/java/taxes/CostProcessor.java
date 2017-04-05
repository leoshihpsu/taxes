package taxes;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PathVariable;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CostProcessor {

    public static String saveProcessor(Transaction lineToSave) throws IOException {

        BigDecimal tempCost = new BigDecimal(lineToSave.getCost());
        lineToSave.setVat(tempCost);
        File file = new File("test.txt");
        Writer writer = new FileWriter(file, true);
        writer.write(lineToSave + System.lineSeparator());
        writer.flush();
        return "new transaction saved";
    }

    public static List<Transaction> readerProcessor() throws IOException {
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

    public static List<Transaction> incomeProcessor() throws IOException {
        return readerProcessor().stream().filter(s -> s.getType().equals("income")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
    }

    public static List<Transaction> costsProcessor() throws IOException {
        return readerProcessor().stream().filter(s -> s.getType().equals("cost")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
    }

    public static double incomeSumProcessor() throws IOException {
        return readerProcessor().stream().filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
    }

    public static double costSumProcessor() throws IOException {
        double sumTmp = readerProcessor().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();
        return sumTmp;

    }

    public static List<Transaction> costsLast30Processor() throws IOException, ParseException {
        List<Transaction> temp30 = readerProcessor().stream().filter(s -> s.getType().equals("cost")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
        int L = 5;
        List<Transaction> newList = new ArrayList<Transaction>(temp30.subList(0, L));
        return newList;
    }

    public static List<Transaction> incomeLast5Processor() throws IOException, ParseException {
        List<Transaction> temp30 = readerProcessor().stream().filter(s -> s.getType().equals("income")).sorted((s1, s2) -> s2.getCost().compareTo(s1.getCost())).collect(Collectors.toList());
        int L = 5;
        List<Transaction> newList = new ArrayList<Transaction>(temp30.subList(0, L));
        return newList;
    }

    public static BigDecimal taxesAllProcessor() throws IOException, ParseException {
        double incomeTemp = readerProcessor().stream().filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
        double costTemp = readerProcessor().stream().filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(incomeTemp);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPay = new BigDecimal(costTemp);
        BigDecimal vatToPay = tmpVatToPay.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal vatTotal = vatPaid.subtract(vatToPay);
        return vatTotal;
    }

    public static String taxesFromDateProcessor(@PathVariable String date) throws IOException, ParseException {

        double XdayIncome = readerProcessor().stream().filter(s -> s.getType().equals("income"))
                .filter(s -> (LocalDate.parse(s.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .isAfter(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))))).mapToDouble(Transaction::getCost).sum();

        double XdayCost = readerProcessor().stream().filter(s -> s.getType().equals("cost"))
                .filter(s -> (LocalDate.parse(s.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .isAfter(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))))).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(XdayIncome);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPaid = new BigDecimal(XdayCost);
        BigDecimal vatToPaid = tmpVatToPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal XdayVatTotal = vatPaid.subtract(vatToPaid);

        return "income: " + XdayIncome + " cost: " + XdayCost + " Taxy: " + XdayVatTotal;
    }

    public static BigDecimal taxesWithDatesProcessor(@PathVariable String number) throws IOException, ParseException {

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

        double incomeTemp = readerProcessor().stream().filter(withDates).filter(s -> s.getType().equals("income")).mapToDouble(Transaction::getCost).sum();
        double costTemp = readerProcessor().stream().filter(withDates).filter(s -> s.getType().equals("cost")).mapToDouble(Transaction::getCost).sum();

        BigDecimal tmpVatPaid = new BigDecimal(incomeTemp);
        BigDecimal vatPaid = tmpVatPaid.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal tmpVatToPay = new BigDecimal(costTemp);
        BigDecimal vatToPay = tmpVatToPay.multiply(new BigDecimal(0.23)).setScale(2, RoundingMode.CEILING);
        BigDecimal vatTotal = vatPaid.subtract(vatToPay);
        return vatTotal;
    }
}
