package taxes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class Redirect {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public View index(Model model) {
        return new RedirectView("swagger-ui.html");
    }
}
