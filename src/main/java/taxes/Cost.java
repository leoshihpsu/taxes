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
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@RestController
@RequestMapping("/taxes")
public class Cost {


    List<Transaction> list = new ArrayList<Transaction>();

    @RequestMapping(path = "/test", method = {RequestMethod.POST})
    public String save(@RequestBody Transaction lineToSave) throws IOException {

        File file = new File("test.txt");
        Writer writer = new FileWriter(file, true);  // mozna tu wyzerowac plik false
        writer.write(lineToSave+ System.lineSeparator());
        writer.flush();
        return "zapisano";
    }

    @RequestMapping(path = "/test", method = {RequestMethod.GET})
    public List<Transaction> write(Transaction lineToSave) throws IOException {
        File file = new File("test.txt");
        Scanner sc = new Scanner(file);
        ObjectMapper mapper = new ObjectMapper();
        while(sc.hasNextLine()){
            Transaction newTransaction = mapper.readValue(sc.nextLine(),Transaction.class);
            list.add(newTransaction);

        }
        return list;
    }

}
