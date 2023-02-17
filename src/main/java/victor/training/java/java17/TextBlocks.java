package victor.training.java.java17;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.web.servlet.MockMvc;
import victor.training.java.stream.order.entity.Order;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TextBlocks {

   interface SomeSpringDataRepo extends JpaRepository<Order, Long> {
      @Query(""" 
            SELECT t2 FROM TeachingActivity a JOIN a.teachers t3 WHERE 
            a.id IN (SELECT c.id FROM StudentsYear y JOIN y.courses c WHERE y.id = :yearId) 
            OR a.id IN (SELECT lab.id FROM StudentsYear y JOIN y.groups g JOIN g.labs lab WHERE y.id = :yearId)
            """) // java 17 ❤️
      List<Order> complexQuery(long id1, long id2); // bogus
   }

   @Autowired
   MockMvc mockMvc;
   @Test
   void test() throws Exception {
      mockMvc.perform(post("/product/search")
              .contentType("application/json")
              .content("""
                      {"name":"somth", 
                      "campu": 2}
                      """) // add one more criteria
          )
          .andExpect(status().isOk()) // 200
          .andExpect(jsonPath("$", hasSize(1)));
   }

   // TODO + test of Loan Pattern ->
}
