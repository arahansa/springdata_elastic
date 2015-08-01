package demo.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.domain.People;

public interface PeopleRepository extends ElasticsearchRepository<People, String>{

}
