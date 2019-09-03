package team2.sofa.sofa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import team2.sofa.sofa.model.Client;

@Controller
public class DashboardClientController {

    //Ge terug naat de overview pagina van Client//
    @GetMapping(value = "client_view")
    public String backToClientView(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);
        return "client-view";

    }
}
