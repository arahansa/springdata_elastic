package demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "members", type = "member")
public class Member {

	@Id
	private String id;

	private String name;

	private String ideal;

	

	public Member() {
		super();
	}
	

	public Member(String name, String ideal) {
		super();
		this.name = name;
		this.ideal = ideal;
	}


	public Member(String id, String name, String ideal) {
		super();
		this.id = id;
		this.name = name;
		this.ideal = ideal;
	}
	
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getIdeal()
	{
		return ideal;
	}

	public void setIdeal(String ideal)
	{
		this.ideal = ideal;
	}

	@Override
	public String toString()
	{
		return "Member [id=" + id + ", name=" + name + ", ideal=" + ideal + "]";
	}

}
