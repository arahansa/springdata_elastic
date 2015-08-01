package demo.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.domain.Member;

public interface MemberRepository 
		extends ElasticsearchRepository<Member, String>{
	public List<Member> findByName(String name);
	public List<Member> findByIdeal(String ideal);
}
