package taxes;

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
import java.util.UUID;

@RestController
@RequestMapping("/taxes")
public class Cost {


    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<?> postStudent(@RequestBody String student) {
/*        String id = UUID.randomUUID().toString();
        student.setId(id);
        studentsMap.put(id, student);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/" + id)
                .build().toUri();*/
        //return ResponseEntity.created(location).build();
        return null;
    }

    private Transaction content;
    @RequestMapping(path = "/test", method = {RequestMethod.POST})
    // to wysyła parametr a nie body  public String zapis(@RequestParam(value = "lineToSave") String lineToSave) throws IOException{
    public String zapis(@RequestBody Transaction lineToSave) throws IOException {
        content = lineToSave;
        File file = new File("test.txt");
        Writer writer = new FileWriter(file, true);  // mozna tu wyzerowac plik false
        writer.write(lineToSave+ System.lineSeparator());
        writer.flush();
        return "zapisano";
    }


}
