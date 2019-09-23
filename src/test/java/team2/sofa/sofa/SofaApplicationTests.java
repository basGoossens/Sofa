package team2.sofa.sofa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import team2.sofa.sofa.model.User;
import team2.sofa.sofa.model.Client;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SofaApplicationTests {

    @Test
    public void contextLoads() {
        User testClient = new Client(0, "Billy Bob", "van de", "Febo",
                null, null, null, null, null, null);
        String expected = "BB VAN DE FEBO";
        String actual = testClient.getTenaamstelling();
        Assert.assertEquals(expected, actual);

    }

}
