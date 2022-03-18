package generateRoIs;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import net.sf.javaml.core.Dataset;
import net.sf.javaml.core.DefaultDataset;
import net.sf.javaml.core.Instance;
import net.sf.javaml.tools.weka.ToWekaUtils;
import weka.core.Instances;
import weka.filters.Filter;

public class StayPointClustering {
	private List<DataPoint> dataPoints;
	private int deletionPercentage;
	private String lowerBound;
	private String upperBound;
	
	public StayPointClustering(int aDeletionPercentage, String aLowerBound, String anUpperBound) {
		dataPoints = new ArrayList<DataPoint>();
		deletionPercentage = aDeletionPercentage;
		lowerBound = aLowerBound;
		upperBound = anUpperBound;
	}

	public void transFormat(ui.Dataset dataset, List<String> list, final JTextArea textArea, String selectedPara/*, int dataset*/)/* throws Exception */{
		dataPoints.clear();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Reading source data...\n");
			}
		});	
		
		double minLatitude = Double.MAX_VALUE;
		double minLongitude = Double.MAX_VALUE;
		double maxLatitude = Double.MIN_VALUE;
		double maxLongitude = Double.MIN_VALUE;
		
		for(String aUser : list) {
			if(selectedPara != null) {
				try {
					BufferedReader br = new BufferedReader(new FileReader(dataset.getOutputPath() + "/StayPoints/" + selectedPara + "/" + aUser + ".txt"));
					String line;
					while ((line = br.readLine()) != null) {
						String[] fields = line.split(" ");
						for(int j = 0; j < Integer.parseInt(fields[1]); j++) {
							double lat = Double.parseDouble(fields[4 + 3 * j]);
							double lngt = Double.parseDouble(fields[5 + 3 * j]);							
							dataPoints.add(new DataPoint(lat, lngt));
							
							if (lat < minLatitude) {
								minLatitude = lat;
							}
							if (lat > maxLatitude) {
								maxLatitude = lat;
							}
							if (lngt < minLongitude) {
								minLongitude = lngt;
							}
							if (lngt > maxLongitude) {
								maxLongitude = lngt;
							}
						}					
					}
					br.close();
				} catch(FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Cannot find the stay point file of the user " + aUser + ".", "File not found", JOptionPane.ERROR_MESSAGE);
					return;
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "An error occurred when reading the stay point file of the user " + aUser + ".", "File input error", JOptionPane.ERROR_MESSAGE);
					return;
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The stay point file of the user " + aUser + " is malformatted.", "File format error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} else {
				try {
					BufferedReader br = new BufferedReader(new FileReader(dataset.getInputPath() + "/" + aUser + ".txt"));
					String line;
					while ((line = br.readLine()) != null) {
						String[] fields = line.split(" ");
						for(int j = 0; j < Integer.parseInt(fields[1]); j++) {
							double lat = Double.parseDouble(fields[4 + 3 * j]);
							double lngt = Double.parseDouble(fields[5 + 3 * j]);
							dataPoints.add(new DataPoint(lat, lngt));
							
							if (lat < minLatitude) {
								minLatitude = lat;
							}
							if (lat > maxLatitude) {
								maxLatitude = lat;
							}
							if (lngt < minLongitude) {
								minLongitude = lngt;
							}
							if (lngt > maxLongitude) {
								maxLongitude = lngt;
							}
						}					
					}
					br.close();
				} catch(FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, "Cannot find the stay point file of the user " + aUser + ".", "File not found", JOptionPane.ERROR_MESSAGE);
					return;
				} catch(IOException e) {
					JOptionPane.showMessageDialog(null, "An error occurred when reading the stay point file of the user " + aUser + ".", "File input error", JOptionPane.ERROR_MESSAGE);
					return;
				} catch(NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "The stay point file of the user " + aUser + " is malformatted.", "File format error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}

		/*// Read all stay points into 'dataPoints'
		File indir = new File(outputdir);
		String[] files = indir.list();
		for (int i = 0; i < files.length; i++) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader(new File(inputdir + File.separator + files[i])));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			String line;
			try {
				while ((line = br.readLine()) != null) {
					String[] fields = line.split(" ");
					for(int j = 0; j < Integer.parseInt(fields[1]); j++) {
						double lat = Double.parseDouble(fields[4 + 3 * j]);
						double lngt = Double.parseDouble(fields[5 + 3 * j]);
						dataPoints.add(new DataPoint(lat, lngt));
					}					
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Finished reading source data\n");
				textArea.append("The number of stay points to be clustered is: " + dataPoints.size() + "\n");
			}
		});					

		// **************************************************************************************************
		// ************************************** Setup variable data *****************************************
		// **************************************************************************************************
		/*double minimalStopDistance = 100;

		if (dataPoints.size() <= 1000) {
			// deletionPercentage = 30;
			// stopFactorForSHC = 0.1;
			// lowerBoundK = 10;
			// upperBoundK = 50;
			minimalStopDistance = 100;
		} else if (dataPoints.size() >= 3000) {
			// deletionPercentage = 30;
			// stopFactorForSHC = 0.005;
			// lowerBoundK = 20;
			// upperBoundK = 100;
			minimalStopDistance = 100;
		}*/

		// **************************************************************************************************
		// ************************************** Removing Outliers	*****************************************
		// **************************************************************************************************
		// Transform the list of datapoints to jml dataset
		Dataset gpsPoints = toJMLDataset(dataPoints);

		// Applying the lof filtering.
		dataPoints = removeOutliersUsingLOF(gpsPoints, textArea);
		//writeDataPointsToFile(dataPoints, outputdir, "StayPointsWithLOF.csv");
		
		// **************************************************************************************************
		// ******************************************* CLUSTERING *******************************************
		// **************************************************************************************************
		final SimpleHierarchicalClustering shc = new SimpleHierarchicalClustering();		
		shc.minimalStopDistance = 150;

		shc.cluster(dataPoints);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("The number of clusters is: " + shc.clusters.size() + "\n");
				textArea.append("The number of clusters that contain more than one point each is:" + shc.getNumberOfClusterWithMoreThanOnePoint() + "\n");
			}
		});			
		
		assert shc.getNumberOfClusterWithMoreThanOnePoint() == shc.toDoubleArrayForMultiPointClusters().length;

		// ******************************************* Output Region Information *****************************
		if(shc.getNumberOfClusterWithMoreThanOnePoint() == 0) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					JOptionPane.showMessageDialog(null, "There is no valid RoI to output.", "No output", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			return;
		}
		
		StringBuilder sbStats = new StringBuilder();
		sbStats.append(minLatitude + "\n" + minLongitude + "\n" + maxLatitude + "\n" + maxLongitude + "\n" + shc.getMultiPointClusters().size());
		outputRegionFromCluster(dataset, shc.getMultiPointClusters(), textArea, list, selectedPara, sbStats.toString());
	}

	// add data to dataset
	private void add(Dataset data, String label, double x, double y) {
		double[] values = { x, y };
		SPInstance in = new SPInstance(values);
		in.setLabel(label);
		data.add(in);
	}

	private void outputRegionFromCluster(ui.Dataset dataset, List<Cluster> clusters, final JTextArea textArea, List<String> list, String selectedPara, String stats) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Outputing RoIs...\n");
			}
		});
		
		StringBuilder sb = new StringBuilder();
		Map<Integer, DataPoint> minLatLongs = new HashMap<Integer, DataPoint>();
		Map<Integer, DataPoint> maxLatLongs = new HashMap<Integer, DataPoint>();
		Map<Integer, Integer> numberOfPointsInCluster = new HashMap<Integer, Integer>();

		Set<Integer> clusterIDs = new HashSet<Integer>();

		for (Cluster cluster : clusters) {
			clusterIDs.add(cluster.id);
			// store min lat long for each cluster
			minLatLongs.put(cluster.id, cluster.getMinPoint());
			// store max lat long for each cluster
			maxLatLongs.put(cluster.id, cluster.getMaxPoint());

			numberOfPointsInCluster.put(cluster.id,	cluster.clusterPoints.size());
		}
		
		/*double minLatitude = Double.MAX_VALUE;
		double minLongitude = Double.MAX_VALUE;
		double maxLatitude = Double.MIN_VALUE;
		double maxLongitude = Double.MIN_VALUE;*/
		
		StringBuilder sb2 = new StringBuilder();
		for(int i = 0; i < list.size(); i++) {
			if(i == list.size() - 1) {
				sb2.append(list.get(i));
			} else {
				sb2.append(list.get(i) + "_");
			}						
		}
		sb2.append("-" + deletionPercentage + "_" + lowerBound + "_" + upperBound + ".txt");
		String fileName = sb2.toString();
		
		BufferedWriter bw = null;
		if(dataset.getType().equals("GPS")) {
			File outputFolder = new File(dataset.getOutputPath() + "/RoIs/" + selectedPara);
			if(!outputFolder.exists()) {
				outputFolder.mkdirs();
			}		

			try {
				bw = new BufferedWriter(new FileWriter(new File(outputFolder + "/" + fileName)));
			} catch (IOException e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "Cannot open the RoI file to output.", "File output error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				});
			}
		} else {
			File outputFolder = new File(dataset.getOutputPath() + "/RoIs");
			if(!outputFolder.exists()) {
				outputFolder.mkdirs();
			}		

			try {
				bw = new BufferedWriter(new FileWriter(new File(outputFolder + "/" + fileName)));
			} catch (IOException e) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						JOptionPane.showMessageDialog(null, "Cannot open the RoI file to output.", "File output error", JOptionPane.ERROR_MESSAGE);
						return;
					}
				});
			}
		}
		
		// create output string for each cluster
		int clusterRanking = 0;
		for (int clusterID : clusterIDs) {
			//double regionDiagonal = SimpleHierarchicalClustering.greatCircleDistance(minLatLongs.get(clusterID), maxLatLongs.get(clusterID));
			sb.append(clusterRanking++);
			sb.append(" ");
			double minLatitudeThisCluster = minLatLongs.get(clusterID).getLatitude();
			/*if(minLatitudeThisCluster < minLatitude) {
				minLatitude = minLatitudeThisCluster;
			}*/
			sb.append(minLatitudeThisCluster);
			sb.append(" ");
			double minLongitudeThisCluster = minLatLongs.get(clusterID).getLongitude();
			/*if(minLongitudeThisCluster < minLongitude) {
				minLongitude = minLongitudeThisCluster;
			}*/
			sb.append(minLongitudeThisCluster);
			sb.append(" ");
			double maxLatitudeThisCluster = maxLatLongs.get(clusterID).getLatitude();
			/*if(maxLatitudeThisCluster > maxLatitude) {
				maxLatitude = maxLatitudeThisCluster;
			}*/
			sb.append(maxLatitudeThisCluster);
			sb.append(" ");
			double maxLongitudeThisCluster = maxLatLongs.get(clusterID).getLongitude();
			/*if(maxLongitudeThisCluster > maxLongitude) {
				maxLongitude = maxLongitudeThisCluster;
			}*/
			sb.append(maxLongitudeThisCluster);
			sb.append("\n");
			/*sb.append(" ");
			sb.append(numberOfPointsInCluster.get(clusterID));
			sb.append(" ");
			sb.append(regionDiagonal);*/
		}
		try {
			bw.write(sb.toString().trim());
			bw.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An error occurred while writing the RoI file.", "File output error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Finished outputing RoIs...\n");
			}
		});		
		
		/*StringBuilder sb3 = new StringBuilder();
		sb3.append(minLatitude + "\n" + minLongitude + "\n" + maxLatitude + "\n" + maxLongitude + "\n" + clusters.size());*/
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Outputing stats of the RoIs...\n");
			}
		});		
		File statOutputFolder;
		if(dataset.getType().equals("GPS")) {
			statOutputFolder = new File(dataset.getOutputPath() + "/Stats/RoIs/" + selectedPara);
			if(!statOutputFolder.exists()) {
				statOutputFolder.mkdirs();
			}
		} else {
			statOutputFolder = new File(dataset.getOutputPath() + "/Stats/RoIs/");
			if(!statOutputFolder.exists()) {
				statOutputFolder.mkdirs();
			}
		}
		
		try {
			bw = new BufferedWriter(new FileWriter(statOutputFolder + "/" + fileName));
			bw.write(stats);
			bw.close();
		} catch(IOException e) {
			JOptionPane.showMessageDialog(null, "An error occurred while writing the RoI stat file.", "File output error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Finished outputing stats of the RoIs...\n");
			}
		});		
	}

	public List<DataPoint> removeOutliersUsingLOF(Dataset SPinstances, final JTextArea textArea)/* throws Exception */{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("Calculating LOF values and remove outliers...\n");
			}
		});
		
		// bring dataset in weka format for LOF filtering
		ToWekaUtils toWekaformagt = new ToWekaUtils(SPinstances);
		Instances wekaDatasetFormat = toWekaformagt.getDataset();
		final ArrayList<DataPoint> list = new ArrayList<DataPoint>();

		LOF lofFilter = new LOF();

		// Values set according to the paper Enhancing data analysis with noise removal		
		lofFilter.setMinPointsLowerBound(lowerBound);
		lofFilter.setMinPointsUpperBound(upperBound);
		
		// Set input format for the instances. Which attributes it has. Just  following the sample.
		try {
			lofFilter.setInputFormat(wekaDatasetFormat);
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		// Executing the filter on the data.
		Instances gpsPointsWithRemovedOutliers = null;
		try {
			gpsPointsWithRemovedOutliers = Filter.useFilter(wekaDatasetFormat, lofFilter);
			for (weka.core.Instance instance : gpsPointsWithRemovedOutliers) {
				double Lat = 0;
				double Long = 0;
				double LOF = 0;
				for (int i = 0; i < instance.numAttributes(); i++) {
					// Lat
					if (i == 0) {
						Lat = instance.value(i);
					}
					// Long
					if (i == 1) {
						Long = instance.value(i);
					}
					// LOF
					if (i == 2) {
						LOF = instance.value(i);
					}

				}
				list.add(new DataPoint(Lat, Long, LOF));
			}
		} catch (Exception ex) {
			/*throw ex;*/
			ex.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				textArea.append("The number of instances:" + list.size() + "\n");
			}
		});		
		
		// Sorted in descending LOF order.
		Collections.sort(list, new Comparator<DataPoint>() {
			@Override
			public int compare(DataPoint o1, DataPoint o2) {
				return Double.compare(o1.LOF, o2.LOF);
			}
		});
		
		int absolutDeletionCount = Math.round((list.size() * deletionPercentage * 0.01f));
		List<DataPoint> deleteList = new ArrayList<DataPoint>(list.size());
		for (int index = list.size() - absolutDeletionCount; index < list.size(); index++) {
			// from the last XX % delete those that have a LOF higher than 1 indicating an outlier.
			if (list.get(index).LOF >= 1) {
				deleteList.add(list.get(index));
			}
		}

		list.removeAll(deleteList);
		return list;
	}

	public List<Cluster> toClusters(Dataset[] dclusters) {
		List<Cluster> clusters = new ArrayList<Cluster>();
		int clusterCounter = 0;
		for (Dataset cluster : dclusters) {
			Cluster newCluster = new Cluster(clusterCounter++);
			for (Instance instance : cluster) {
				SPInstance inst = (SPInstance) instance;
				newCluster.addPoint(new DataPoint(inst.value(0), inst.value(1)));
			}
			clusters.add(newCluster);
		}
		return clusters;
	}

	public Dataset toJMLDataset(List<DataPoint> gpsPoints) {
		Dataset spdata = new DefaultDataset();

		for (DataPoint pt : gpsPoints) {
			add(spdata, "", pt.getLatitude(), pt.getLongitude());
		}

		return spdata;
	}

	public List<DataPoint> toDataPoints(Dataset gpsPoints) {
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (Instance instance : gpsPoints) {
			dataPoints.add(new DataPoint(instance.get(0), instance.get(1)));
		}

		return dataPoints;
	}

	public void writeDataPointsToFile(List<DataPoint> datapoints, String folderName, String fileName) {
		StringBuilder sb = new StringBuilder("Latitude,Longitude,LOF\n");
		for (DataPoint dataPoint : datapoints) {
			sb.append(dataPoint);
			sb.append("\n");
		}

		CommonFileOperations.writeToFile(folderName, fileName, sb.toString());
	}
}
