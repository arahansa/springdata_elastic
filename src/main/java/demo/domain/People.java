package demo.domain;


import org.neo4j.cypher.internal.compiler.v2_1.ast.Return;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName="geopeoples", type="geopeople")
public class People {
	
	@Id
	private String id;
	
	private String name;
	private GeoPoint location;
	
	private String img;
	private String message;
	
	public People() {
	
	}
	public People(String id, String name, GeoPoint geopoint){
		super();
		this.id = id;
		this.name = name;
		this.location = geopoint;
	}
	

	public People(String id, String name, double lat, double lon) {
		super();
		this.id = id;
		this.name = name;
		setLocation(new GeoPoint(lat, lon));
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

	public GeoPoint getLocation()
	{
		return location;
	}

	public void setLocation(GeoPoint location)
	{
		this.location = location;
	}
	
	

	public String getImg()
	{	
		switch (this.name) {
			case "앤":return "01_ann.png";
			case "비비안수":return "02_vivian.png";
			case "스칼렛":return "03_scarlet.png";
			case "니콜라스":return "04_nicol.png";
			case "톰":return "05_tom.png";
			case "미니언":return "06_minion.png";
			default : return "06_minion.png"; 
		}
	}
	
	public String getMessage()
	{
		switch (this.name) {
		case "앤":return "당신은 서울역 에서 앤 해서웨이와 만나 스쳐지나가게 됩니다.";
		case "비비안수":return "당신은 비비안수를 페럼타워 세미나에서 만나 스쳐지나갑니다.";
		case "스칼렛":return "당신은 인천역에서 스칼렛 요한슨을 만나지만, 스쳐지나갑니다.";
		case "니콜라스":return "당신은 니콜라스를 강남역에서 만나지만, 그는 당신에게 관심이 없습니다.";
		case "톰":return "당신은 톰을 신촌역에서 만나지만, 그는 곧 새로운 여행을 떠날 것입니다. ";
		case "미니언":return "당신은 용산역에서 미니언을 만나지만, 그는 곧 해피밀세트로 팔려갈 운명입니다. ";
		default : return "당신은 용산역에서 미니언을 만나지만, 그는 곧 해피밀세트로 팔려갈 운명입니다. "; 
	}
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public void setImg(String img)
	{
		this.img = img;
	}
	@Override
	public String toString()
	{
		return "People [id=" + id + ", name=" + name + ", location=" + location + "]";
	}
	
	
	
	
	

}
