package detectStayPoints;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class FormatConverterGeolife {	
	private int j;
	
	public void processFilesOfAllUsers(String pathOfInputDirectory, String pathOfOutputDirectory, List<String> list, double distanceThreshold, double timeThreshold, double mergingThreshold, JTextArea progressJTextArea) {
		StayPointDetection spd = new StayPointDetection(distanceThreshold, timeThreshold, mergingThreshold);
		StringBuilder sb = new StringBuilder();
    	for(String aSubDirectory : list) {    		
    		UserStatistics stat = processFilesOfOneUser(spd, pathOfInputDirectory + File.separator + aSubDirectory + File.separator + "Trajectory", pathOfOutputDirectory, progressJTextArea, aSubDirectory);
    		sb.append(stat.toString());
    	}
    	
    	try {
    	File StatOutDir = new File(pathOfOutputDirectory + "/Stats/SourceDataAndStayPoints");
		if(!StatOutDir.exists()) {
			StatOutDir.mkdirs();
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(StatOutDir + "/StayPoints-" + spd.getDistanceThreshold() + "_" + spd.getTimeThreshold() + "_" + spd.getMergerThreshold() + ".txt", true));		
		bw.write(sb.toString());
		bw.close();
    	} catch(IOException e) {
    		EventQueue.invokeLater(new Runnable() {
            	public void run() {
            		JOptionPane.showMessageDialog(null, "An error occurred when writing stats of stay points.", "Output error", JOptionPane.ERROR_MESSAGE);
            	}        	
            });
    	}    	
	}
	
    private UserStatistics processFilesOfOneUser(StayPointDetection spd, String indir, String outdir, final JTextArea progressJTextArea, final String userID) {
    	UserStatistics stat = new UserStatistics();
    	stat.setUserID(userID);
    	
        File indirfile = new File(indir); // input dir        
        String[] files = indirfile.list(); // get all the files of a user        
        
        TreeMap<String, LinkedList<String>> filemap = new TreeMap<String, LinkedList<String>>();

        EventQueue.invokeLater(new Runnable() {
        	public void run() {
        		progressJTextArea.append("**************************************************************\n");
                progressJTextArea.append("Pre-processing files of the user: " + userID + "\n");
        	}        	
        });        
       
		for (int i = 0; i < files.length; i++) {
			String date = files[i].substring(0, 8);
			if (filemap.containsKey(date))
				filemap.get(date).add(files[i]);
			else {
				LinkedList<String> filelist = new LinkedList<String>();
				filelist.add(files[i]);
				filemap.put(date, filelist);
			}
		}
		stat.setNumberOfDays(filemap.size());

		LinkedList<LinkedList<GPSPoint>> allGPSPointsOfOneUser = new LinkedList<LinkedList<GPSPoint>>();
		Set<String> days = filemap.keySet();
        Iterator<String> itr = days.iterator();
		try {
			while (itr.hasNext()) {
				final LinkedList<GPSPoint> gpspoints = new LinkedList<GPSPoint>(); // GPS points set
				
				final String date = itr.next();
				final LinkedList<String> filelist = filemap.get(date);
				
				for (j = 0; j < filelist.size(); j++) {
					EventQueue.invokeAndWait(new Runnable() {
			        	public void run() {
			        		progressJTextArea.append("Processing file " + filelist.get(j) + " ...\n");
			        	}        	
			        }); 
					
					BufferedReader br = new BufferedReader(new FileReader(new File(indir + File.separator + filelist.get(j))));
					for (int k = 0; k < 6; k++)
						br.readLine();

					String line;
					while ((line = br.readLine()) != null) {
						String[] fields = line.split(",");
		                GPSPoint point = new GPSPoint();
		                // set the latitude and longitude
		                point.setLatitude(Double.parseDouble(fields[0]));
		                point.setLongitude(Double.parseDouble(fields[1]));
		                // get the time stamp for each GPS points
		                String[] time = fields[6].split(":");
		                // transfer the time stamp into absolute time for each GPS point
		                int abstime = Integer.parseInt(time[0]) * 3600 + Integer.parseInt(time[1]) * 60 + Integer.parseInt(time[2]);
		                point.setTime(abstime);

		                gpspoints.add(point);
					}					
						
					br.close();
				}				
				
				EventQueue.invokeLater(new Runnable() {
		        	public void run() {
		        		progressJTextArea.append("The number of GPS points on " + date + " is: " + gpspoints.size() + "\n");
		        	}        	
		        }); 
				allGPSPointsOfOneUser.add(gpspoints);
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		LinkedList<LinkedList<StayPoint>> allStayPointsOfOneUser = spd.detectStayPointsForOneUser(allGPSPointsOfOneUser, userID, progressJTextArea, days, stat);
		StringBuilder sb = new StringBuilder();
		Iterator<LinkedList<StayPoint>> anIterator = allStayPointsOfOneUser.iterator();
		for(String aDay : filemap.keySet()) {
			sb.append(aDay + " ");
			LinkedList<StayPoint> stayPointsOfTheDay = anIterator.next();
			sb.append(stayPointsOfTheDay.size() + " " + getWeekBit(aDay) + " ");			
			for(StayPoint aStayPoint : stayPointsOfTheDay) {				
				sb.append(aStayPoint.getArrivalTime() + " " + aStayPoint.getLatitude() + " " + aStayPoint.getLongitude() + " ");				
			}
			sb.append("\n");
		}		
		
		try {
			File SPOutDir = new File(outdir + "/StayPoints/" + spd.getDistanceThreshold() + "_" + spd.getTimeThreshold() + "_" + spd.getMergerThreshold());
			if(!SPOutDir.exists()) {
				SPOutDir.mkdirs();
			}
			
			BufferedWriter bw = new BufferedWriter(new FileWriter(SPOutDir + File.separator + userID + ".txt"));
			EventQueue.invokeLater(new Runnable() {
	        	public void run() {
	        		progressJTextArea.append("Outputing the stay point file of the user: " + userID + "\n");
	        	}        	
	        }); 
			bw.write(sb.toString());
			bw.close();			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
        	public void run() {
        		progressJTextArea.append("Finished processing the user: " + userID + "\n");
        	}        	
		}); 
		
		return stat;
	}    
    
    private String getWeekBit(String date)
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");
		DateTime dateTime = new DateTime();
		dateTime= dateTimeFormatter.parseDateTime(date);
		int dayOfWeek = dateTime.getDayOfWeek();
		if(dayOfWeek < 6)
		{
			return "1";
		}
		else
		{
			return "0";
		}
	}
}
