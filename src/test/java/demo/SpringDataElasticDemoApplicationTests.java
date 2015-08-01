package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.domain.Stage2;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataElasticDemoApplication.class)
public class SpringDataElasticDemoApplicationTests {

	@Test
	public void contextLoads() {
		Stage2 stage2 = Stage2.Initialize();
		System.out.println(stage2.getContent());
		
	}

}
