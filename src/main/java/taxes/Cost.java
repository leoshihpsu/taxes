package taxes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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


}
