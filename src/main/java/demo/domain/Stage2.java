package demo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "stages", type = "stage2")
public class Stage2 {
	
	@Id
	private String id;
	
	private String content;
	private String hilightedContent;
	
	public Stage2() {
	}
	
	
	
	public Stage2(String id, String content) {
		super();
		this.id = id;
		this.content = content;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}
	
	public String getHilightedContent()
	{
		return hilightedContent;
	}



	public void setHilightedContent(String hilightedContent)
	{
		this.hilightedContent = hilightedContent;
	}



	public static Stage2  Initialize(){
		Stage2 stage2 = new Stage2();
		stage2.setId("1");
		
		StringBuilder builder = new StringBuilder();
		for(long i=9788960773000L;i<9788960773650L;i++){
			builder.append(i+" ");
			if(i==9788960773431L){
				builder.append("토비의스프링 ");
			}
		}
		stage2.setContent(builder.toString());
		return stage2;
	}



	@Override
	public String toString()
	{
		return "Stage2 [id=" + id + ", content=" + content + ", hilightedContent=" + hilightedContent + "]";
	}



	
	
	

}
