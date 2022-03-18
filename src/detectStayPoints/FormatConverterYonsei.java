package detectStayPoints;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTextArea;

public class FormatConverterYonsei {
	/*private static int counterOfStayPoints = 0;
	private static int counterOfDays = 0;*/
	/*private static double minLatitude = Double.MAX_VALUE;
	private static double minLongitude = Double.MAX_VALUE;
	private static double maxLatitude = Double.MIN_VALUE;
	private static double maxLongitude = Double.MIN_VALUE;*/
	
	public void processFilesOfAllUsers(String pathOfInputDirectory, String pathOfOutputDirectory, JTextArea progressJTextArea)
	{		
		File inputDirectory = new File(pathOfInputDirectory);
		File[] inputFiles = inputDirectory.listFiles();
		File outputDirectory = new File(pathOfOutputDirectory);
		if(!outputDirectory.exists())
		{
			outputDirectory.mkdirs();
		}
		
		for(File aFile : inputFiles)
		{
			progressJTextArea.append("Processing the file " + aFile.getName() + "...\n");
			
			LinkedList<String> contentsOfInputFile = readFile(aFile);
			ArrayList<String> contentsAfterProcessingDate = processDate(contentsOfInputFile);

			//used for testing
			/*for(String aLine : contentsAfterProcessingDate)
			{
			System.out.println(aLine);			
			}*/		

			ArrayList<String> contentsAfterRemovingInvalidStayPoints = removeInvalidStayPoints(contentsAfterProcessingDate);		

			/*//used for testing
			for(String aLine : contentsAfterRemovingInvalidStayPoints)
			{
				System.out.println(aLine);			
			}*/

			LinkedList<StayPoint> allStayPointsOfOneUser = convertStringsToStayPoints(contentsAfterRemovingInvalidStayPoints);

			String contentToOutput = convertFormat(allStayPointsOfOneUser);
						
			try 
			{				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputDirectory + File.separator + aFile.getName()));
				bufferedWriter.write(contentToOutput);
				bufferedWriter.close();
				progressJTextArea.append("Done\n");
			} 
			catch (IOException e) 
			{			
				e.printStackTrace();
			}
		}
		
		/*try
		{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputDirectory + File.separator + "statistics.txt"));
			bufferedWriter.write("minLatitude:" + minLatitude + " minLongitude: " + minLongitude + " maxLatitude: " + maxLatitude + " maxLongitude: " + maxLongitude);
			bufferedWriter.close();
			System.out.println("Done");
		}
		catch (IOException e) 
		{			
			e.printStackTrace();
		}*/
	}
	
	private LinkedList<StayPoint> convertStringsToStayPoints(ArrayList<String> contentsAfterRemovingInvalidStayPoints)
	{
		LinkedList<StayPoint> allStayPointsOfOneUser = new LinkedList<StayPoint>();
		for(String aStringForAStayPoint : contentsAfterRemovingInvalidStayPoints)
		{
			String[] fields = aStringForAStayPoint.split(",");
			String[] subfieldsOfField3 = fields[3].split(":");
			String[] subfieldsOfField4 = fields[4].split(":");
			String[] subfieldsOfField0 = fields[0].split(" ");
			String[] subfieldsOfField1 = fields[1].split(" ");
			try
			{
				StayPoint aStayPoint = new StayPoint(Double.parseDouble(subfieldsOfField3[1]), Double.parseDouble(subfieldsOfField4[1]), convertTimeFormat(subfieldsOfField0[1]), convertTimeFormat(subfieldsOfField1[1]), subfieldsOfField0[0].replace("-", ""));
				allStayPointsOfOneUser.add(aStayPoint);
				
				/*if(aStayPoint.getLatitude() < minLatitude)
					minLatitude = aStayPoint.getLatitude();
				if(aStayPoint.getLongitude() < minLongitude)
					minLongitude = aStayPoint.getLongitude();
				if(aStayPoint.getLatitude() > maxLatitude)
					maxLatitude = aStayPoint.getLatitude();
				if(aStayPoint.getLongitude() > maxLongitude)
					maxLongitude = aStayPoint.getLongitude();*/
			}
			catch(NumberFormatException e)
			{
				System.out.println("The string " + aStringForAStayPoint + " is malformatted!");
				e.printStackTrace();				
			}			
		}
		return allStayPointsOfOneUser;		
	}
	
	//remove stay points whose latitude or longitude value is invalid
	private ArrayList<String> removeInvalidStayPoints(ArrayList<String> contentsAfterProcessingDate)
	{
		ArrayList<String> contentsAfterRemovingInvalidStayPoints = new ArrayList<String>();

		for(String aStayPoint : contentsAfterProcessingDate)
		{
			String[] fields = aStayPoint.split(",");
			if(!((fields[3].contains("Unknown")) || (fields[3].contains("0.000")) || (fields[4].contains("Unknown")) || (fields[4].contains("0.000")) || (fields[4].contains("-"))))
			{
				contentsAfterRemovingInvalidStayPoints.add(aStayPoint);
			}
		}

		return contentsAfterRemovingInvalidStayPoints;
	}
	
	private String convertFormat(LinkedList<StayPoint> allStayPointsOfOneUser)
	{		
		TreeMap<String, LinkedList<StayPoint>> map = new TreeMap<String, LinkedList<StayPoint>>();
		for(StayPoint aStayPoint : allStayPointsOfOneUser)
		{
			String date = aStayPoint.getDate();
			if(map.containsKey(date))
			{
				map.get(date).add(aStayPoint);
			}
			else
			{
				LinkedList<StayPoint> newDay = new LinkedList<StayPoint>();
				newDay.add(aStayPoint);
				map.put(date, newDay);
			}
		}
		
		/*counterOfDays = map.size();
		StringBuilder statistics = new StringBuilder(counterOfStayPoints + "," + counterOfDays + "\n" + "\n");
		double maxDistanceOfTheUser = Double.MIN_VALUE;
		double minDistanceOfTheUser = Double.MAX_VALUE;*/
		
		for(Map.Entry<String, LinkedList<StayPoint>> anEntry : map.entrySet())
		{
			LinkedList<StayPoint> values = anEntry.getValue();
			for(int index = 0; index < (values.size() - 1); index++)
			{
				StayPoint currentPoint = values.get(index);
				StayPoint nextPoint = values.get(index + 1);
				if(currentPoint.distanceTo(currentPoint) < 200)
				{
					StayPoint newPoint = currentPoint.merge(nextPoint);
					values.remove(currentPoint);
					values.remove(nextPoint);
					values.add(index, newPoint);
				}				
			}
		}
		
		StringBuilder aStringBuilder = new StringBuilder();
		for(Map.Entry<String, LinkedList<StayPoint>> anEntry : map.entrySet())
		{
			LinkedList<StayPoint> values = anEntry.getValue();
			String date = anEntry.getKey();
			aStringBuilder.append(date);
			aStringBuilder.append(" ");
			aStringBuilder.append(values.size());			
			aStringBuilder.append(" "/* + getWeekBit(date) + " "*/);
			
			/*ArrayList<DataPoint> stayPointsInOneDay = new ArrayList<DataPoint>();
			double maxDistance = Double.MIN_VALUE;
			double minDistance = Double.MAX_VALUE;*/		
			
			for(StayPoint aStayPoint : values)
			{				
				aStringBuilder.append(aStayPoint.getArrivalTime());
				aStringBuilder.append(" ");
				aStringBuilder.append(aStayPoint.getLatitude());
				aStringBuilder.append(" ");				
				aStringBuilder.append(aStayPoint.getLongitude());
				aStringBuilder.append(" ");
				
				/*DataPoint aDataPoint = new DataPoint(Double.parseDouble(subfieldsOfField3[1]), Double.parseDouble(subfieldsOfField4[1]));
				stayPointsInOneDay.add(aDataPoint);*/
			}	
			aStringBuilder.append("\n");
			
			/*for(DataPoint point1: stayPointsInOneDay)
			{
				for(DataPoint point2: stayPointsInOneDay)
				{
					if(point1 == point2)
					{
						continue;
					}
					else
					{
						double distance = point1.distanceTo(point2);
						if(distance > maxDistance)
						{
							maxDistance = distance;
						}
						if(distance < minDistance)
						{
							minDistance = distance;
						}
					}
				}
			}
			
			if(maxDistance > maxDistanceOfTheUser)
			{
				maxDistanceOfTheUser = maxDistance;
			}
			if(minDistance < minDistanceOfTheUser)
			{
				minDistanceOfTheUser = minDistance;
			}
			
			statistics.append(anEntry.getKey() + "," + maxDistance + "," + minDistance + "\n");*/
		}
		/*statistics.append("\n" + maxDistanceOfTheUser + "," + minDistanceOfTheUser);
		
		String pathnameOfStatisticsFile = "C:\\Documents and Settings\\pen\\Desktop\\NewDataset5\\Statistics9.csv";
		try 
		{
			BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathnameOfStatisticsFile));
			bufferedWriter.write(statistics.toString());
			bufferedWriter.close();
			System.out.println("Done");
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}*/
		
		return aStringBuilder.toString();
	}
	
	/*private static String getWeekBit(String date)
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
	}*/
	
	private int convertTimeFormat(String time)
	{
		String[] fields = time.split(":");
		int absoluteTimeInSecond = -1;
		try
		{
			absoluteTimeInSecond = Integer.parseInt(fields[0]) * 3600 + Integer.parseInt(fields[1]) * 60 + Integer.parseInt(fields[2]);
		}
		catch(NumberFormatException e)
		{
			e.printStackTrace();
		}
		
		return absoluteTimeInSecond;
	}
	
	private LinkedList<String> readFile(File aFile)
	{
		LinkedList<String> contentsOfInputFile = new LinkedList<String>();
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(aFile));
			String aLine = null;
			while((aLine = bufferedReader.readLine()) != null)
			{
				contentsOfInputFile.add(aLine);
				/*counterOfStayPoints++;*/
			}
			bufferedReader.close();			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return contentsOfInputFile;
	}
	
	private ArrayList<String> processDate(LinkedList<String> contentsOfInputFile)
	{
		ArrayList<String> contentsAfterProcessingDate = new ArrayList<String>();
		for(String aLine : contentsOfInputFile)
		{
			String[] fields = aLine.split(", ");
			String[] subfieldsOfField1 = fields[1].split(" ~ ");
			String[] subfieldsOfSubfield0 = subfieldsOfField1[0].split(" ");
			String[] subfieldsOfSubfield1 = subfieldsOfField1[1].split(" ");
			if(subfieldsOfSubfield0[0].equals(subfieldsOfSubfield1[0]))
			{
				String stayPointWithoutProcessingDate = subfieldsOfField1[0] + "," + subfieldsOfField1[1] + "," + fields[2] + "," + fields[3] + "," + fields[4];
				contentsAfterProcessingDate.add(stayPointWithoutProcessingDate);
			}
			else
			{
				String stayPoint1AfterProcessingDate = subfieldsOfField1[0] + "," + subfieldsOfSubfield0[0] + " 23:59:59," + fields[2] + "," + fields[3] + "," + fields[4];
				contentsAfterProcessingDate.add(stayPoint1AfterProcessingDate);
				String stayPoint2AfterProcessingDate = subfieldsOfSubfield1[0] + " 00:00:00," + subfieldsOfField1[1] + "," + fields[2] + "," + fields[3] + "," + fields[4];
				contentsAfterProcessingDate.add(stayPoint2AfterProcessingDate);
			}
		}
		return contentsAfterProcessingDate;
	}	
}
