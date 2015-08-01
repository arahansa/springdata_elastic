package demo.service;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import demo.domain.LocationHelper;
import demo.domain.People;
import demo.domain.Stage2;
import demo.repository.Stage2Repository;

@Service
public class Stage2Service {
	
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private Stage2Repository repository;
	
	public Page<Stage2> getByContentKeyword(String keyword){
		SearchQuery searchQuery  = new NativeSearchQueryBuilder()
		.withQuery(termQuery("content", keyword))
		.withHighlightFields(new HighlightBuilder.Field("content"))
		.build();
		Page<Stage2> stage2 = elasticsearchTemplate.queryForPage(searchQuery, Stage2.class , new SearchResultMapper() {
			@Override
			public <T> FacetedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
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
		
		return stage2;
	}
	
	public Stage2 getOne(String id){
		return repository.findOne(id);
	}
	
	public List<People> getNearPeopleWithLocation(String name){
		GeoPoint geoPoint = LocationHelper.getLocationByName(name);
		CriteriaQuery geoLocationCriteriaQuery =
				new CriteriaQuery(new Criteria("location").within(geoPoint, "1km"));
		List<People> geoPeople = elasticsearchTemplate.queryForList(geoLocationCriteriaQuery, People.class);
		return geoPeople;
	}

}
