package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import team2.sofa.sofa.model.Address;
import team2.sofa.sofa.model.Client;
import team2.sofa.sofa.service.Login;
import team2.sofa.sofa.service.UpdateClient;

import java.util.Map;

@Controller
@SessionAttributes({"sessionclient", "connector"})
public class PersonalPageController {

    @Autowired
    UpdateClient updateClient;
    @Autowired
    Login login;

    @PostMapping(value = "updateHandler")
    public String updateHandler(@RequestParam int id, Model model) {
        Client client = updateClient.findClient(id);
        model.addAttribute("client", client);
        return "edit_client";
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
        Address checkedAddress = updateClient.checkAddress(input);
        if (checkedAddress.getId() == 0){
            client = updateClient.changeAddress(client, checkedAddress);
            login.checkAndLoadConnector(client,model);
            model.addAttribute("sessionclient", client);
            model.addAttribute("nrBusiness", login.countBusinessAccounts(client));
            model.addAttribute("nrPrivate", login.countPrivateAccounts(client));
            return "client_view";
        }
        model.addAttribute("address", checkedAddress);
        model.addAttribute("clientId", clientId);
        model.addAttribute("error", "bedoelde u misschien dit adres?");
        return "change_address";
    }

    @PostMapping(value = "updateClient")
    public String processUpdate(@RequestParam Map<String, Object> input, Model model) {
        Client client = updateClient.findClient(Integer.valueOf(input.get("id").toString()));
        String newUsername = input.get("username").toString();
        if (updateClient.usernameExists(newUsername)) {
            client = updateClient.processChanges(client, input);
            login.checkAndLoadConnector(client,model);
            model.addAttribute("username", "uw gebruikersnaam is niet gewijzigd");
            model.addAttribute("sessionclient", client);
            model.addAttribute("nrBusiness", login.countBusinessAccounts(client));
            model.addAttribute("nrPrivate", login.countPrivateAccounts(client));
            return "client_view";
        }
        client.setUsername(input.get("username").toString());
        client = updateClient.processChanges(client, input);
        login.checkAndLoadConnector(client,model);
        model.addAttribute("sessionclient", client);
        model.addAttribute("nrBusiness", login.countBusinessAccounts(client));
        model.addAttribute("nrPrivate", login.countPrivateAccounts(client));
        return "client_view";
    }
}