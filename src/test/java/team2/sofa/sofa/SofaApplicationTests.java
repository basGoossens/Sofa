package team2.sofa.sofa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import team2.sofa.sofa.model.Account;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.Client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SofaApplicationTests {

    @Test
    public void testGetFullNameAccountOwner() {

        Client testClient = new Client(0, "Billy Bob", "van de", "Febo",
                null, null, null, null, null, null);
        Client testPartner = new Client(0, "Pipi", null, "Langkous",
                null, null, null, null, null, null);
        Account testAccount = new Account("12345", (BigDecimal.valueOf(100.0)), "1234", null );
        List<Client> owners = new ArrayList<>();
        owners.add(testClient);
        owners.add(testPartner);

        testAccount.setOwners(owners);
        String expected = "BB VAN DE FEBO / P LANGKOUS";
        String actual = testAccount.getFullNameAccountOwner();
        Assert.assertEquals(expected, actual);
    }

}
