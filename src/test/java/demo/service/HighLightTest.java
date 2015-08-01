package demo.service;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.SpringDataElasticDemoApplication;
import demo.domain.Stage2;
import demo.repository.Stage2Repository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataElasticDemoApplication.class)
public class HighLightTest {
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	Stage2Repository repository;
	@Autowired
	Stage2Service service;

	@Before
	public void setUp()
	{
		Stage2 stage2 = new Stage2();
		stage2.setId("2");
		stage2.setContent("hello world");
		repository.save(stage2);

	}

	@Test
	public void 하이라이트테스트() throws Exception
	{

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(termQuery("content", "hello"))
				.withHighlightFields(new HighlightBuilder.Field("content")).build();
		Page<Stage2> stage2 = elasticsearchTemplate.queryForPage(searchQuery, Stage2.class, new SearchResultMapper() {
			@Override
			public <T> FacetedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable)
			{
				List<Stage2> chunk = new ArrayList<Stage2>();
				for (SearchHit searchHit : response.getHits()) {
					if (response.getHits().getHits().length <= 0) {
						return null;
					}
					Stage2 user = new Stage2();
					user.setId(searchHit.getId());
					user.setContent((String) searchHit.getSource().get("content"));
					user.setHilightedContent(searchHit.getHighlightFields().get("content").fragments()[0].toString());
					chunk.add(user);
				}
				if (chunk.size() > 0) {
					return new FacetedPageImpl<T>((List<T>) chunk);
				}
				return null;
			}
		});

		List<Stage2> content = stage2.getContent();
		for (Stage2 stage22 : content) {
			System.out.println(stage22);
			if (stage22.getId().equals("2")) {
				assertEquals("<em>hello</em> world", stage22.getHilightedContent());
			}
		}

	}
	
	@Test
	public void 서비스테스트() throws Exception
	{
		Page<Stage2> stage2 = service.getByContentKeyword("hello");
		List<Stage2> content = stage2.getContent();
		for (Stage2 stage22 : content) {
			System.out.println(stage22);
			if (stage22.getId().equals("2")) {
				assertEquals("<em>hello</em> world", stage22.getHilightedContent());
			}
		}
		
	}

}
