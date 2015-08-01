package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

import demo.domain.LocationHelper;
import demo.domain.Member;
import demo.domain.People;
import demo.domain.Stage2;
import demo.repository.Stage2Repository;

@SpringBootApplication
public class SpringDataElasticDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataElasticDemoApplication.class, args);
    }

    @Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired Stage2Repository repository;
    
    
	@Override
	public void run(String... arg0) throws Exception
	{
		// --------------------------------------------------------------
		elasticsearchTemplate.deleteIndex(Stage2.class);
		elasticsearchTemplate.createIndex(Stage2.class);
		elasticsearchTemplate.putMapping(Stage2.class);
		elasticsearchTemplate.refresh(Stage2.class, true);
		Stage2 stage2 = Stage2.Initialize();
		elasticsearchTemplate.index(new IndexQueryBuilder().withId(stage2.getId()).withObject(stage2).build());
		elasticsearchTemplate.refresh(Stage2.class, true);
		
		
		// --------------------------------------------------------------
		elasticsearchTemplate.deleteIndex(Member.class);
		elasticsearchTemplate.createIndex(Member.class);
		elasticsearchTemplate.putMapping(Member.class);
		elasticsearchTemplate.refresh(Member.class, true);
		
		Member[] members = {
				new Member("1", "arahansa", "비비안수"), 
				new Member("2", "holyeye", "비비안수"), 
				new Member("3", "arawn", "비비안수"),
				new Member("4", "park", "비비안수"),
				new Member("5", "maylee", "미니언"),
				new Member("6", "kim", "미니언"),
				new Member("7", "lee", "미니언"),
				new Member("8", "oh", "니콜라스"),
				new Member("9", "ahn", "니콜라스"),
				new Member("10", "bae", "앤"),
		}; 
		for (int i = 0; i < members.length; i++) {
			elasticsearchTemplate.index(new IndexQueryBuilder().withId(members[i].getId()).withObject(members[i]).build());
		}
		elasticsearchTemplate.refresh(Member.class, true);
		
		// --------------------------------------------------------------
		
		
		
		People[] peoples = {
				new People("1", "비비안수", LocationHelper.getLocationByName("ferum")),
				new People("2", "앤", LocationHelper.getLocationByName("seoul")),
				new People("3", "스칼렛", LocationHelper.getLocationByName("incheon")),
				new People("4", "니콜라스", LocationHelper.getLocationByName("gangnam")),
				new People("5", "톰", LocationHelper.getLocationByName("shinchon")),
				new People("6", "미니언", LocationHelper.getLocationByName("yongsan"))
		};
		
		elasticsearchTemplate.deleteIndex(People.class);
		elasticsearchTemplate.createIndex(People.class);
		elasticsearchTemplate.putMapping(People.class);
		elasticsearchTemplate.refresh(People.class, true);
		
		for (int i = 0; i < peoples.length; i++) {
			elasticsearchTemplate.index(new IndexQueryBuilder().withId(peoples[i].getId()).withObject(peoples[i]).build());
		}
		elasticsearchTemplate.refresh(People.class, true);
		
	}
}
