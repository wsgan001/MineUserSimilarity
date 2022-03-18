package detectStayPoints;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JTextArea;

public class StayPointDetection 
{
    private double distanceThreshold;
    private double timeThreshold;
    private double mergerThreshold; 
    
    public double getDistanceThreshold() {
    	return distanceThreshold;
    }
    
    public double getTimeThreshold() {
    	return timeThreshold;
    }
    
    public double getMergerThreshold() {
    	return mergerThreshold;
    }

    public StayPointDetection(double aDistanceThreshold, double aTimeThreshold, double aMergerThreshold) 
    {
        distanceThreshold = aDistanceThreshold;
        timeThreshold = aTimeThreshold;
        mergerThreshold = aMergerThreshold;
        /*statisticsFile = aStatisticsFile;*/
    }

    // input a directory
    public LinkedList<LinkedList<StayPoint>> detectStayPointsForOneUser(LinkedList<LinkedList<GPSPoint>> allGPSPointsOfOneUser, String userID, JTextArea progressJTextArea, Set<String> days, UserStatistics stat) 
    {
    	progressJTextArea.append("Start detecting stay points of the user: " + userID + "...\n");
    	LinkedList<LinkedList<StayPoint>> allStayPointsOfOneUser = new LinkedList<LinkedList<StayPoint>>();
    	
       /* userStatistics = new UserStatistics();
        userStatistics.setNumberOfDays(allGPSPointsOfOneUser.size());
        userStatistics.setUserID(userID);*/
        
        Iterator<String> iterator = days.iterator();
        for(LinkedList<GPSPoint> GPSPointsOfOneDay : allGPSPointsOfOneUser) // read every file
        {
        	String date = iterator.next();
        	LinkedList<StayPoint> stayPointsOfOneDay = detectStayPointsInOneDayForOneUser(GPSPointsOfOneDay, progressJTextArea, userID, date, stat);
        	allStayPointsOfOneUser.add(stayPointsOfOneDay);
        	/*stat.getNumberOfStayPointsPerDay().put(date, stayPointsOfOneDay.size());*/
        }
        
        return allStayPointsOfOneUser;
        /*outputStatistics(userStatistics);*/
    }

    /*private void outputStatistics(UserStatistics aUserStatistics) 
    {
        try 
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(statisticsFile), true));
            bw.write("UID:" + aUserStatistics.getUserID()+ "\tNumofDays:" + aUserStatistics.getNumberOfDays()
                + "\tNumofSP:" + aUserStatistics.getNumberOfStayPoints()+ "\tMaxSP:"
                + aUserStatistics.getMaximalNumberOfStayPointsInADay()+ "\tMinSP:" + aUserStatistics.getMinimalNumberOfStayPointsInADay()+ "\r\n");
            bw.close();
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
    }*/
    
    private LinkedList<StayPoint> detectStayPointsInOneDayForOneUser(LinkedList<GPSPoint> GPSPointsOfOneDay, JTextArea progressJTextArea, String userID, String day, UserStatistics stat) 
    {        
    	progressJTextArea.append("Detecting stay points of the user " + userID + " on the day " + day + "...\n");
        LinkedList<StayPoint> staypoints = new LinkedList<StayPoint>(); // stay points set

        // add first point to stay point set
        StayPoint sp = new StayPoint();
        sp.setLatitude(GPSPointsOfOneDay.get(0).getLatitude());
        sp.setLongitude(GPSPointsOfOneDay.get(0).getLongitude());
        sp.setArrivalTime(GPSPointsOfOneDay.get(0).getTime());
        sp.setLeavingTime(GPSPointsOfOneDay.get(0).getTime());
        staypoints.add(sp);            

        /**
         * algorithm here
         */
        int i = 1;
        int pointNum = GPSPointsOfOneDay.size();
        while (i < pointNum - 1) 
        {
            int j = i + 1;
            int token = 0;

            while (j < pointNum - 1) 
            {
                double dist = GPSPointsOfOneDay.get(i).distanceTo(GPSPointsOfOneDay.get(j));

                if (dist > this.distanceThreshold) 
                {
                    int timeInter = (int) (GPSPointsOfOneDay.get(j-1).getTime() - GPSPointsOfOneDay.get(i).getTime()) / 60;
                    if (timeInter > this.timeThreshold) 
                    {
                        StayPoint s = new StayPoint();
                        calculateAverageCoordinates(s, GPSPointsOfOneDay, i, j);
                        s.setArrivalTime(GPSPointsOfOneDay.get(i).getTime());
                        s.setLeavingTime(GPSPointsOfOneDay.get(j).getTime());
                        staypoints.add(s);
                        i = j;
                        token = 1;
                    }
                    break;
                }
                j = j + 1;
            }// end of inner J loop

            if (token != 1) 
                i = i + 1;            
        }// end of outer I loop
        // add last point into sp set
        StayPoint lastsp = new StayPoint();
        lastsp.setLatitude(GPSPointsOfOneDay.getLast().getLatitude());
        lastsp.setLongitude(GPSPointsOfOneDay.getLast().getLongitude());
        lastsp.setArrivalTime(GPSPointsOfOneDay.getLast().getTime());
        lastsp.setLeavingTime(GPSPointsOfOneDay.getLast().getTime());
        staypoints.add(lastsp);

        // *****************************************************************************
        // ***************************** MERGE stay points *****************************
        // *****************************************************************************
        mergeStayPoints(staypoints);
        /**
         * output the stay points to output file
         */
        /*System.out.println("Number of Stay Points:" + staypoints.size());*/
        stat.setNumberOfStayPoints(stat.getNumberOfStayPoints()+ staypoints.size());
        if(stat.getMaximalNumberOfStayPointsInADay() < staypoints.size())
        	stat.setMaximalNumberOfStayPointsInADay(staypoints.size());
        
        if(stat.getMinimalNumberOfStayPointsInADay() > staypoints.size()) 
        	stat.setMinimalNumberOfStayPointsInADay(staypoints.size());
        
        return staypoints;
    }

    /**
     * compute the center of point i to j
     *
     * @param s
     * @param gpspoints
     * @param i
     * @param j
     */
    private void calculateAverageCoordinates(StayPoint s, LinkedList<GPSPoint> gpspoints, int i, int j) 
    {
        double latsum = 0.0;
        double lngtsum = 0.0;
        int numofpoints = j - i;
        for (int k = i; k < j; k++) 
        {
            GPSPoint cur = gpspoints.get(k);
            latsum += cur.getLatitude();
            lngtsum += cur.getLongitude();
        }

        s.setLatitude(latsum / numofpoints);
        s.setLongitude(lngtsum / numofpoints);
    }

    /*// convert the absolute time to the relative time
    private String convertTimeFormat(double time) 
    {
        String hour = Integer.toString((int) time / 3600);
        String min = Integer.toString((int) (time % 3600) / 60);
        String sec = Integer.toString((int) time % 60);
        return hour + ":" + min + ":" + sec;
    }*/

    // merge staypoints
    public void mergeStayPoints(LinkedList<StayPoint> staypoints) 
    {
        // algorithm get zero stay point
        if (staypoints.size() == 2) 
        {
            // merge two points
            if (staypoints.getFirst().distanceTo(staypoints.getLast()) < mergerThreshold) 
            {
                StayPoint s = staypoints.getFirst().merge(staypoints.getLast());
                staypoints.clear();
                staypoints.add(s);
            }
        } // algorithm get one stay point
        else if (staypoints.size() == 3) 
        {
            if (staypoints.getFirst().distanceTo(staypoints.get(1)) < mergerThreshold) 
            {
                // merge first two points
                StayPoint s = staypoints.getFirst().merge(staypoints.get(1));
                staypoints.removeFirst();
                staypoints.remove(1);
                staypoints.addFirst(s);
            } 
            else if (staypoints.get(1).distanceTo(staypoints.getLast()) < mergerThreshold) 
            {
                // or merge last two points
                StayPoint s = staypoints.get(1).merge(staypoints.getLast());
                staypoints.remove(1);
                staypoints.removeLast();
                staypoints.addLast(s);
            }

            // merge three points
            if (staypoints.size() == 2 && staypoints.getFirst().distanceTo(staypoints.getLast()) < mergerThreshold) 
            {
                StayPoint s = staypoints.getFirst().merge(staypoints.getLast());
                staypoints.clear();
                staypoints.add(s);
            }
        } // algorithm get one than one stay point
        else if (staypoints.size() > 3) 
        {
            if (staypoints.getFirst().distanceTo(staypoints.get(1)) < mergerThreshold) 
            {
                // merge the first two stay points
                StayPoint s = staypoints.getFirst().merge(staypoints.get(1));
                staypoints.removeFirst();
                staypoints.remove(1);
                staypoints.addFirst(s);
            } // FIXME: DONE REMOVED else
            if (staypoints.get(staypoints.size() - 2).distanceTo(staypoints.getLast()) < mergerThreshold) 
            {
                // merge the last two stay points
                StayPoint s = staypoints.get(staypoints.size() - 2).merge(staypoints.getLast());
                staypoints.remove(staypoints.size() - 2);
                staypoints.removeLast();
                staypoints.addLast(s);
            }
        }
    }
}