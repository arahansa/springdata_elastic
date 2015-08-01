package demo.template;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import demo.SpringDataElasticDemoApplication;
import demo.domain.Member;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringDataElasticDemoApplication.class)
public class ElasticTemplateFacetTest {

	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	@Before
	public void setup(){
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
	}
	
	@Test
	public void 엘라스틱템플릿_어그레이게이션_테스트() throws Exception
	{
		String facetName = "term_ideal";
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withFacet(new TermFacetRequestBuilder(facetName).fields("ideal").build()).build();
        FacetedPage<Member> result = elasticsearchTemplate.queryForPage(searchQuery, Member.class);
        
        assertThat(result.getNumberOfElements(), is(equalTo(10)));
        
        
        TermResult facet = (TermResult) result.getFacet(facetName);
        Term term = facet.getTerms().get(0);
        assertThat(term.getTerm(), is("비비안수"));
        assertThat(term.getCount(), is(4));
        
        term = facet.getTerms().get(1);
        assertThat(term.getTerm(), is("미니언"));
        assertThat(term.getCount(), is(3));
        
        term = facet.getTerms().get(2);
        assertThat(term.getTerm(), is("니콜라스"));
        assertThat(term.getCount(), is(2));
        
        term = facet.getTerms().get(3);
        assertThat(term.getTerm(), is("앤"));
        assertThat(term.getCount(), is(1));
        
        
	}
	
	
	
}
