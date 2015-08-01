package demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.facet.request.TermFacetRequestBuilder;
import org.springframework.data.elasticsearch.core.facet.result.Term;
import org.springframework.data.elasticsearch.core.facet.result.TermResult;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import demo.domain.Member;
import demo.repository.MemberRepository;

@Service
public class MemberService {
	private static final String ID = "id";
	private static final int numPage = 5;
	private static final String TERMS = "terms_";
	@Autowired
	MemberRepository repository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;

	public void save(Member member)
	{
		repository.save(member);
	}

	public Iterable<Member> getAll()
	{
		return repository.findAll();
	}
	
	public Page<Member> getAllWithPage(int page){
		Sort sort= new Sort(Direction.DESC, ID);
		PageRequest pageRequest = new PageRequest(page, numPage, sort);
		return repository.findAll(pageRequest);
	}

	public List<Member> getByName(String name)
	{
		return repository.findByName(name);
	}

	public List<Member> getByIdeal(String ideal)
	{
		return repository.findByIdeal(ideal);
	}

	public List<Term> getMemberfacetTermsByField(String field)
	{
		String facetName = TERMS+field;
		SearchQuery searchQuery = new NativeSearchQueryBuilder().withFacet(
				new TermFacetRequestBuilder(facetName).applyQueryFilter().fields(field).build()).build();
		FacetedPage<Member> result = elasticsearchTemplate.queryForPage(searchQuery, Member.class);
		TermResult facet = (TermResult) result.getFacet(facetName);
		return facet.getTerms();
	}
	
	public void delete(String id){
		repository.delete(id);
	}
}
