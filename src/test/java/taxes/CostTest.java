package taxes;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.Suite;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import taxes.Cost;
import taxes.CostProcessor;
import taxes.Transaction;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest

public class CostTest {

    public CostTest(){
        super();
    }

 /*   @Mock
    private CostProcessor costProcessor;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new Cost(costProcessor)).build();

    }
*/
/*    @Test
    @Parameters({
            "pieski dwa",
            "kotki dwa"
    })
    public void test(String text) throws Exception {

        Mockito.when(fileProcessor.delete()).thenReturn(true);
        mockMvc.perform(delete("/file"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(true)))
                .andDo(print());

        Mockito.when(fileProcessor.save(Mockito.eq(text))).thenReturn(true,false);
        mockMvc.perform(post("/file").content("pieski dwa"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(true)))
                .andDo(print());
        mockMvc.perform(post("/file").content(text))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(false)))
                .andDo(print());

        Mockito.verify(fileProcessor, Mockito.times(2)).save(text);

        Mockito.when(fileProcessor.read()).thenReturn(Arrays.asList(text));
        mockMvc.perform(get("/file"))
                .andExpect(jsonPath("[0]", equalTo(text)))
                .andExpect(status().isOk())
                .andDo(print());
    }*/


    @Test
    public void testSave() throws Exception {
        CostProcessor.setFile(new File("test.txt"));
        CostProcessor.saveProcessor(new taxes.Transaction((double) 150,"income","2017-04-02","dsa"));
        List<taxes.Transaction> list = CostProcessor.readerProcessor();
        assertEquals("2017-04-02", list.get(0).getDate());

    }
    
    @Test
    public void testReader() throws Exception {
        CostProcessor.setFile(new File("test.txt"));
        CostProcessor.readerProcessor();
        List<taxes.Transaction> list = new ArrayList();
        list.add(0, new taxes.Transaction((double) 150,"income","2017-04-02","dsa"));
        assertEquals("2017-04-02", list.get(0).getDate());

    }

/*    @Test
    public void testPost() throws Exception{
        //new File("spring.txt").delete();
        mockMvc.perform(delete("/file"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.post("/file").content("leonidas"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(get("/file"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]", equalTo("leonidas")))
                .andDo(MockMvcResultHandlers.print());
    }*/


}
