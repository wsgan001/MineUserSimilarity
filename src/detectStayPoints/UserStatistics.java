package detectStayPoints;

public class UserStatistics 
{
    private String userID;
    private int numberOfDays, numberOfStayPoints, maximalNumberOfStayPointsInADay, minimalNumberOfStayPointsInADay;

    public UserStatistics() 
    {
        minimalNumberOfStayPointsInADay = Integer.MAX_VALUE;
    }
    
    public String toString() {
    	return userID + " " + numberOfDays + " " + numberOfStayPoints + " " + minimalNumberOfStayPointsInADay + " " + maximalNumberOfStayPointsInADay+ " " + ((double)numberOfStayPoints / numberOfDays) + "\n";
    }

    public String getUserID() 
    {
        return userID;
    }

    public void setUserID(String aUserID) 
    {
        userID = aUserID;
    }

    public int getNumberOfDays() 
    {
        return numberOfDays;
    }
    
    public void setNumberOfDays(int someNumberOfDays) 
    {
        numberOfDays = someNumberOfDays;
    }
    
    public int getNumberOfStayPoints() 
    {
        return numberOfStayPoints;
    }

    public void setNumberOfStayPoints(int someNumberOfStayPoints) 
    {
        numberOfStayPoints = someNumberOfStayPoints;
    }

    public int getMaximalNumberOfStayPointsInADay() 
    {
        return maximalNumberOfStayPointsInADay;
    }

    public void setMaximalNumberOfStayPointsInADay(int someNumberOfStayPointsInADay) 
    {
        maximalNumberOfStayPointsInADay = someNumberOfStayPointsInADay;
    }

    public int getMinimalNumberOfStayPointsInADay() 
    {
        return minimalNumberOfStayPointsInADay;
    }

    public void setMinimalNumberOfStayPointsInADay(int someNumberOfStayPointsInADay) 
    {
        minimalNumberOfStayPointsInADay = someNumberOfStayPointsInADay;
    }
    
    public double calculateAverageNumberOfStayPointsPerDay() {
    	return (double)numberOfStayPoints / numberOfDays;
    }
}
