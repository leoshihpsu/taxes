package java.taxes;

import junitparams.JUnitParamsRunner;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taxes.Cost;
import taxes.CostProcessor;

@SpringBootTest
@RunWith(JUnitParamsRunner.class)
class CostTest {

    @Mock
    private CostProcessor costProcessor;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new Cost(new CostProcessor())).build();
    }


}