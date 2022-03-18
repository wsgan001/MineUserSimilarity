package generateRoIs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommonFileOperations {

	public static void createFolderIfNotExisting(File folder) {
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	public static void createFolderIfNotExisting(String foldername) {
		File folder = new File(foldername);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	
	public static List<String> getTextFileAsLines(File textFileHandle) {
		List<String> lines = new ArrayList<String>();
		try{
			BufferedReader br = new BufferedReader(new FileReader( textFileHandle ));
			String strLine = "";
			//Read File Line By Line
			while ((strLine = br.readLine()) != null)   {
				// save each line as element in list
				lines.add(strLine);
			}
			//Close the BufferedReader
			br.close();
		}catch (Exception e){//Catch exception if any
			System.err.println("Error: " + e.getMessage());
			return lines;
		}
		return lines;
	}
	
	
	public static boolean writeToFile(String outputFolderName, String outputFileName, String content) {
		// write content to file.
		BufferedWriter bw = null;
		try {
			createFolderIfNotExisting(outputFolderName);
			bw = new BufferedWriter(new FileWriter(outputFolderName+File.separator+outputFileName));
			//System.out.println("Writing ouput file: "+outputFileName);
			bw.write(content);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			// write failed.
			return false;
		}
		return true;
	}
}
