package demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import demo.domain.Stage2;

public interface Stage2Repository extends ElasticsearchRepository<Stage2, String>{

	 
	
}
