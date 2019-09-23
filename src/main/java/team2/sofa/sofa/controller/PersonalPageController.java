package team2.sofa.sofa.controller;

import com.sun.net.httpserver.Headers;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.model.dao.ClientDao;
import team2.sofa.sofa.service.NewAccountChecker;
import team2.sofa.sofa.service.UpdateClient;

import javax.validation.Valid;
import java.awt.*;
import java.util.List;
import java.util.Map;

@Controller
public class PersonalPageController {

    @Autowired
    UpdateClient updateClient;
    @Autowired
    ClientDao clientDao;

    private Client currentClient;
    private List<String> errors;

    @PostMapping(value = "updateHandler")
    public String updateHandler(Client client, Model model) {
        currentClient = clientDao.findClientById(client.getId());
        model.addAttribute("updateClient", currentClient);
        model.addAttribute("client", currentClient);
        return "edit_client_personalpage";
    }

    @PostMapping(value = "verwerkUpdate")//, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
    //@ResponseBody
    public String verwerkUpdate (@RequestParam Map<String, Object> body, Client client){
        errors = updateClient.processUpdate(currentClient, body);
        if (errors.isEmpty()) {
            clientDao.save(currentClient);
            return "client_view";
        }
        else {
            //todo show errorlist
            return "edit_client_personalpage";
        }
    }

}