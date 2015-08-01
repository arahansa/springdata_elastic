package demo.service;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.SpringDataElasticDemoApplication;
import demo.domain.People;
import demo.domain.Stage2;
import demo.repository.PeopleRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataElasticDemoApplication.class)
public class ElasticGeoTEst {
	
	
	@Autowired PeopleRepository repository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Before
	public void setUp()
	{
		
		//37.567299, 126.983850 을지로 페럼타워
		// 을지로입구역 37.566048, 126.982616
		People people = new People("1", "arahansa", 37.5672d, 126.9838d);
		People people2 = new People("2", "arahansa", 37.5660d, 126.9826d);
		
		elasticsearchTemplate.deleteIndex(People.class);
		elasticsearchTemplate.createIndex(People.class);
		elasticsearchTemplate.putMapping(People.class);
		elasticsearchTemplate.refresh(People.class, true);
		
		elasticsearchTemplate.index(new IndexQueryBuilder().withId(people.getId()).withObject(people).build());
		elasticsearchTemplate.index(new IndexQueryBuilder().withId(people2.getId()).withObject(people2).build());
		elasticsearchTemplate.refresh(People.class, true);
		
	}
	
	@Test
	public void 위치기반검색테스트() throws Exception
	{
		// 기업은행 37.566414, 126.986521
		CriteriaQuery geoLocationCriteriaQuery = new CriteriaQuery(
                new Criteria("location").within(new GeoPoint(37.5664d, 126.9865d), "1km"));
		
		
		List<People> geoPeople = elasticsearchTemplate.queryForList(geoLocationCriteriaQuery, People.class);
		for (People people : geoPeople) {
			System.out.println(people);
		}
	}

}
