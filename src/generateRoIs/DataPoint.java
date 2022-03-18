package generateRoIs;

public class DataPoint {

	private String time;
	private double Latitude;
	private double Longitude;
	private int id = -1;
	public int clusterID = -1;
	public double LOF = -1;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public DataPoint( double latitude, double longitude, String time) {
		super();
		this.time = time;
		Latitude = latitude;
		Longitude = longitude;
	}
	
	public DataPoint( String latitude, String longitude, String time) {
		super();
		this.time = time;
		
		Latitude = Double.parseDouble(latitude);
		Longitude = Double.parseDouble(longitude);
	}
	public DataPoint( double latitude, double longitude) {
		super();
		Latitude = latitude;
		Longitude = longitude;
	}
	
	public DataPoint( double latitude, double longitude, double LOF) {
		super();
		Latitude = latitude;
		Longitude = longitude;
		this.LOF = LOF; 
	}
	public DataPoint( double latitude, double longitude, int id) {
		super();
		this.Latitude = latitude;
		this.Longitude = longitude;
		this.id = id;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public double getLatitude() {
		return Latitude;
	}
	public void setLatitude(double latitude) {
		Latitude = latitude;
	}
	public double getLongitude() {
		return Longitude;
	}
	public void setLongitude(double longitude) {
		Longitude = longitude;
	}
	public long getTimeInSecondsForEachDay() {
		String[] time = getTime().split(":"); 
		long abstime = Integer.parseInt(time[0]) * 3600 + Integer.parseInt(time[1]) * 60 + Integer.parseInt(time[2]);
		
		return abstime;
	}
	
	@Override
	public String toString() {
		return ""+Latitude+","+Longitude+","+LOF ;
	}
	
}
