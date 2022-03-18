package detectStayPoints;

public class GPSPoint 
{
    private double latitude, longitude;
    private int time;
    private String date;

    public GPSPoint(double aLatitude, double aLongitude, int aTime, String aDate) 
    {
        latitude = aLatitude; 
        longitude = aLongitude;
        time = aTime;
        date = aDate;
    }

    public GPSPoint() { }

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

    public int getTime() 
    {
        return time;
    }

    public void setTime(int aTime) 
    {
        time = aTime;
    }

    public String getDate() 
    {
        return date;
    }

    public void setDate(String aDate) 
    {
        date = aDate;
    }

    // Haversine formula for calculating the distance between two GPS points
    public double distanceTo(GPSPoint point2) 
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
}
