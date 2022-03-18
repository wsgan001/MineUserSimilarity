package ui;

public class Dataset {
	private String name;
	private String type;
	private String inputPath;
	private String outputPath;
	
	public Dataset(String aName, String aType, String anInputPath, String anOutputPath) {
		name = aName;
		type = aType;
		inputPath = anInputPath;
		outputPath = anOutputPath;
	}
	
	public String getName() {
		return name;
	}	
	
	public String getType() {
		return type;
	}
	
	public String getInputPath() {
		return inputPath;
	}
	
	public String getOutputPath() {
		return outputPath;
	}
	
	public void setName(String aName) {
		name = aName;
	}
	
	public void setType(String aType) {
		type = aType;
	}
	
	public void setInputPath(String anInputPath) {
		inputPath = anInputPath;
	}
	
	public void setOutputPath(String anOutputPath) {
		outputPath = anOutputPath;
	}
	
	public String toString() {
		return name;
	}
}
