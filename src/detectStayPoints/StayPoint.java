package detectStayPoints;

public class StayPoint 
{
	public StayPoint() { }
	
	public StayPoint(double someLatitude, double someLongitude, int someArrivalTime, int someLeavingTime, String someDate)
	{
		latitude = someLatitude;
		longitude = someLongitude;
		arrivalTime = someArrivalTime;
		leavingTime = someLeavingTime;
		date = someDate;
	}
	
    public double getLatitude() 
    {
        return latitude;
    }

    public void setLatitude(double aLatitude) 
    {
        latitude = aLatitude;
    }

    public double getLongitude() 
    {
        return longitude;
    }

    public void setLongitude(double aLongitude) 
    {
        longitude = aLongitude;
    }

    public int getArrivalTime() 
    {
        return arrivalTime;
    }

    public void setArrivalTime(int anArrivalTime) 
    {
        arrivalTime = anArrivalTime;
    }

    public int getLeavingTime() 
    {
        return leavingTime;
    }

    public void setLeavingTime(int aLeavingTime) 
    {
        leavingTime = aLeavingTime;
    }

    public String getDate() 
    {
        return date;
    }

    public void setDate(String aDate) 
    {
        date = aDate;
    }
    
    //Haversine formula for calculating the distance between two GPS points
    public double distanceTo(StayPoint point2) 
    {
        final double radiusOfEarth = 6372.8; // In kilometers
        double latitudeOfPoint1 = latitude;
        double longitudeOfPoint1 = longitude;
        double latitudeOfPoint2 = point2.getLatitude();
        double longitudeOfPoint2 = point2.getLongitude();
        
        double radianOfLatitudeDifference = Math.toRadians(latitudeOfPoint2 - latitudeOfPoint1);
        double radianOfLongitudeDifference = Math.toRadians(longitudeOfPoint2 - longitudeOfPoint1);
        double part1 = Math.sin(radianOfLatitudeDifference / 2);
        double part2 = Math.sin(radianOfLongitudeDifference / 2);
        double part3 = part1 * part1 + Math.cos(Math.toRadians(latitudeOfPoint1)) * Math.cos(Math.toRadians(latitudeOfPoint2)) * part2 * part2;
        double distance = 2 * Math.asin(Math.sqrt(part3)) * radiusOfEarth * 1000;        
        return distance;
    }

    //merge two stay points
    public StayPoint merge(StayPoint point) 
    {
        StayPoint s = new StayPoint();
        double newLatitude = (latitude + point.getLatitude()) / 2;
        double newLongitude = (longitude + point.getLongitude()) / 2;
        int newArrivalTime = (arrivalTime <= point.getArrivalTime()) ? arrivalTime : point.getArrivalTime();
        int newLeavingTime = (leavingTime <= point.getLeavingTime()) ? point.getLeavingTime(): leavingTime;
        s.setLatitude(newLatitude);
        s.setLongitude(newLongitude);
        s.setArrivalTime(newArrivalTime);
        s.setLeavingTime(newLeavingTime);
        s.setDate(date);
        return s;
    }
    
    private double latitude; 
    private double longitude; 
    private int arrivalTime; 
    private int leavingTime; 
    private String date; 
}