package team2.sofa.sofa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.Connector;
import team2.sofa.sofa.model.dao.AccountDao;
import team2.sofa.sofa.model.dao.ConnectorDao;
import team2.sofa.sofa.service.ConnectingService;
import team2.sofa.sofa.service.Login;

import java.util.Map;

@Controller
@SessionAttributes({"sessionclient", "connect", "nrBusiness", "nrPrivate"})
public class ConnectController {

    @Autowired
    ConnectingService cs;
    @Autowired
    AccountDao accountDao;
    @Autowired
    ConnectorDao connectorDao;
    @Autowired
    Login login;

    /**
     * mapping vanuit clientdash board naar connect_accounts
     * vanuit inlog van degene die een nieuwe rekeninghouder wil toevoegen aan rekening waarop is ingelogd.
     * @param id id van het account dat is geselecteerd door de ingelogd gebruiker om een extra rekeninghouder toe te voegen
     * @param model
     * @return verwijzing naar pagina waarin username en beveiligingscode kan worden ingevoerd.
     */
    @PostMapping(value = "ConnectAccount")
    public String connectAccounts(@RequestParam int id, Model model) {
        Account account = cs.getAccount(id);
        model.addAttribute("account", account);
        return "connect_accounts";
    }

    /**
     * handler voor verwerken van input die is gegeven door orignele rekeninghouder.
     * de ingevoerde gegevens (IBAN rekening, beveiligingscode en username extra rekeninghouder)
     * worden opgeslagen in de tabel 'connector'
     * @param body een map waarin de gegevens zitten die door de gebruiker zijn ingevoerd
     * @param model
     * @return
     */
    @PostMapping(value = "ConnectForm")
    public String connectHandeler(@RequestParam Map<String, Object> body, Model model) {
        if (cs.checkUserName(body)) {
            //check op eigen naam en op voorkomen van username in db
            Account account = cs.saveCoupling(body);
            model.addAttribute("account", account);
            return "dashboard_client";
        } else {
            Account account = accountDao.findAccountByIban(body.get("bankaccount").toString());
            model.addAttribute("account", account);
            model.addAttribute("wrong", "Gebruikernaam is niet juist");
        return "connect_accounts";
        }
    }

    /**
     * mapping die aan knop hangt die alleen zichtbaar is als de gebruikersnaam voorkomt in de tabel "connector"
     * indien dit zo is. verschijnt de knop "voeg rekenig toe" in client-view.
     * het connector opbject wordt ingeladen en meegegeven aan het volgende scherm
     * waarin de nieuwe rekeninghouder de ontvangen beveiligingscode kan invoeren
     * @param id van het connector object in de DB
     * @param model
     * @return
     */
    @PostMapping(value = "NewConnection")
    public String matchAccounts(@RequestParam int id, Model model) {
        Connector connector = cs.getConnection(id);
        model.addAttribute("connection", connector);
        return "connect_accounts";
    }

    /**
     * nadat de toekomstig mede rekeninghouder het IBAN en juiste beveiligingscode heeft ingevoerd.
     * wordt de ingelogde gebruiker gekoppeld als mede eigenaar van de rekening.
     * @param body ingevoerde gegevens in het formulier
     * @param model
     * @return
     */
    @PostMapping(value = "ConnectValidate")
    public String checkMatch(@RequestParam Map<String, Object> body, Model model) {
        //Onderstaande methode stopt alle instanties waarbij de username voorkomt in een list
        //en checkt op IBAN en security code
        int id = Integer.valueOf(body.get("idconnect").toString());
        Connector c = connectorDao.findById(id).get();
        if (c.getSecurityCode().equals(body.get("accesscode").toString())
                && c.getIban().equals(body.get("banknr").toString())) {
                //call service method to update client, account en connector
                model = cs.processCoupling(c, model);
                model.addAttribute("connect", connectorDao.findConnectorsByUsername(c.getUsername()));
                return "client_view";
        }
        model.addAttribute("connection", c);
        model.addAttribute("wrong", "accescode is niet juist");
        return "connect_accounts";
    }

}
