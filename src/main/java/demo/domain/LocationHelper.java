package demo.domain;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class LocationHelper {

	
	public static GeoPoint getLocationByName(String name){
		switch (name) {
		case "ferum":
			return new GeoPoint(37.5671d, 126.9839d); // 페럼타워 37.567188, 126.983904
		case "seoul" :
			return new GeoPoint(37.5552d, 126.9707d); // 37.555226, 126.970750
		case "incheon" :
			return new GeoPoint(37.4765d, 126.6168d); // 37.476560, 126.616877
		case "gangnam" :
			return new GeoPoint(37.4981d, 126.0278d); // 37.498121, 127.027862
		case "shinchon" :
			return new GeoPoint(37.5552d, 126.9370d); // 37.555285, 126.937009
		case "yongsan" : 
			return new GeoPoint(37.5298d, 126.9646d); // 37.529813, 126.964673
		default:
			return new GeoPoint(37.5671d, 126.9839d); // 페럼타워 37.567188, 126.983904
		}
	}
	
}
