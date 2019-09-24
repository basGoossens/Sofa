package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.service.UpdateClient;

import java.util.Map;

@Controller
public class PersonalPageController {

    @Autowired
    UpdateClient updateClient;

    @PostMapping(value = "updateHandler")
    public String updateHandler(@RequestParam int id, Model model) {
        Client client = updateClient.findClient(id);
        model.addAttribute("client", client);
        return "edit_client_personalpage";
    }

    @PostMapping(value = "changeAddressForm")
    public String changeAddress(@RequestParam int clientId, @RequestParam int addressId, Model model) {
        Address address = updateClient.findAddress(addressId);
        model.addAttribute("address", address);
        model.addAttribute("clientId", clientId);
        return "change_address";
    }

    @PostMapping(value = "changeAddress")
    public String processAddress(@RequestParam Map<String, String> input, @RequestParam int clientId, Model model) {
        Client client = updateClient.findClient(clientId);
        Address address = client.getAddress();
        client = updateClient.changeAddress(client, address, input);
        model.addAttribute("client", client);
        model.addAttribute("account", new Account());
        return "client_view";
    }

    @PostMapping(value = "updateClient")
    public String processUpdate(@RequestParam Map<String, Object> input, Model model) {
        Client client = updateClient.findClient(Integer.valueOf(input.get("id").toString()));
        String newUsername = input.get("username").toString();
        if (updateClient.usernameExists(newUsername)) {
            client = updateClient.processChanges(client, input);
            model.addAttribute("username", "uw gebruikersnaam is niet gewijzigd");
            model.addAttribute("client", client);
            model.addAttribute("account", new Account());
            return "client_view";
        }
        client.setUsername(input.get("username").toString());
        model.addAttribute("client", client);
        model.addAttribute("account", new Account());
        return "client_view";
    }
}