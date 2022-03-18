package generateRoIs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SimpleHierarchicalClustering {
	double[][] distanceMatrix;
	public List<Cluster> clusters = new ArrayList<Cluster>();
	public double stopFactor = 0.2;
	public double minimalStopDistance = 150;

	public static double euclidianDistance(DataPoint pt1, DataPoint pt2) {
		double diffLat = pt1.getLatitude()-pt2.getLatitude();
		double diffLong =  pt1.getLongitude() - pt2.getLongitude();

		return Math.sqrt(Math.pow(diffLat, 2)+ Math.pow(diffLong, 2));
	}

	public static double euclidianSquaredDistance(DataPoint pt1, DataPoint pt2) {
		if (pt1.equals(pt2)) {
			return 0.0d;
		}
		double diffLat = pt1.getLatitude()-pt2.getLatitude();
		double diffLong =  pt1.getLongitude() - pt2.getLongitude();

		return Math.pow(diffLat, 2)+ Math.pow(diffLong, 2);
	}
	
	public static  double greatCircleDistance(DataPoint pt1, DataPoint pt2) {

		double a_x_point = pt1.getLatitude();
		double a_y_point = pt1.getLongitude();
		double b_x_point = pt2.getLatitude();
		double b_y_point = pt2.getLongitude();
		Double R = new Double(6371);
		Double dlat = (b_x_point - a_x_point) * Math.PI / 180;
		Double dlon = (b_y_point - a_y_point) * Math.PI / 180;
		Double aDouble = Math.sin(dlat / 2) * Math.sin(dlat / 2)
				+ Math.cos(a_x_point * Math.PI / 180)
				* Math.cos(b_x_point * Math.PI / 180) * Math.sin(dlon / 2)
				* Math.sin(dlon / 2);
		Double cDouble = 2 * Math.atan2(Math.sqrt(aDouble),
				Math.sqrt(1 - aDouble));
		//double d = Math.round((R * cDouble) * (double) 1000); // FIXME: DONE set value from 10000 to 1000
		double d = (R * cDouble) * (double) 1000; // FIXME: DONE set value from 10000 to 1000
		return d;

	}

	public void cluster(List<DataPoint> dataPoints) {
		// init the distance matrix
		distanceMatrix = new double[dataPoints.size()][dataPoints.size()];
		for(int i=0; i<dataPoints.size();i++) {
			for(int j=0; j<dataPoints.size();j++) {
				distanceMatrix[i][j] = euclidianSquaredDistance(dataPoints.get(i), dataPoints.get(j));
			}
			// set id as key for the distance matrix
			dataPoints.get(i).setId(i);
		}

		// init each datapoint as own cluster
		int clusterIDCountr = 0;
		for (DataPoint dataPoint : dataPoints) {
			HashSet<DataPoint> clusterPoints = new HashSet<DataPoint>();
			clusterPoints.add(dataPoint);
			clusters.add(new Cluster(clusterIDCountr++, dataPoint));
		}
		while(true) { 
			// find the pair of clusters with min distance to each other
			double minDistance = Double.MAX_VALUE;
			Cluster clusterWithMinDistanceSource = null;
			Cluster clusterWithMinDistanceTarget = null;
			double currentDistance = 0d;
			for (Cluster sourceCluster : clusters) {
				//Iterator<Cluster> targetClusterIter =  clusters.iterator();
				for ( Cluster targetCluster :clusters) {
					if (sourceCluster == targetCluster) {
						continue;
					}
					//currentDistance = euclidianSquaredDistance(sourceCluster.getCentroid(), targetCluster.getCentroid());
					currentDistance = euclidianDistance(sourceCluster.getCentroid(), targetCluster.getCentroid());
					if (currentDistance <= minDistance) {
						minDistance = currentDistance;
						clusterWithMinDistanceSource = sourceCluster;
						clusterWithMinDistanceTarget = targetCluster;
					}
					
				}// end of while
			}// end of for
			
			// ************************* NEW stop condition *************************
			//if (minDistance >= minimalStopDistance) {
			double mindistanceInMeters = greatCircleDistance(clusterWithMinDistanceSource.getCentroid(), clusterWithMinDistanceTarget.getCentroid());
//			System.out.println("after for for mindist in meters: "+mindistanceInMeters);
//			System.out.println(clusterWithMinDistanceSource.getCentroid()+" "+clusterWithMinDistanceSource.id+":"+ clusterWithMinDistanceTarget.getCentroid()+" "+clusterWithMinDistanceTarget.id);
//			System.out.println("after for for clusters: "+getNumberOfClusterWithMoreThanOnePoint());
//			
			if (mindistanceInMeters >= minimalStopDistance) {
//				System.out.println(mindistanceInMeters);
				return;
			}

			// add target to source and remove target cluster from list
			if (clusterWithMinDistanceSource != null && clusterWithMinDistanceTarget != null) {
				clusterWithMinDistanceSource.addPoints(clusterWithMinDistanceTarget);
				clusters.remove(clusterWithMinDistanceTarget);
			}
		}// end of while
	}

	/*private boolean stopCondition() {
		return true;
	}
	
	private boolean stopConditionBasedOnSinglePointClusterPercentage(int totalClusterCount) {
		int counter = 0;
		// count how many clusters of size one exist
		for (Cluster cluster : clusters) {
			if (cluster.clusterPoints.size()==1) {
				counter++;
			}
		}
		//System.out.println("single clusters left:"+counter);
		return counter >=(totalClusterCount*stopFactor);// true if less than or equal that xx% are single clusters left
	}*/
	
	public int getNumberOfClusterWithMoreThanOnePoint() {
		int numberOfClusterWithMoreThanOnePoint = 0;
		for (Cluster cluster : clusters) {
			if (cluster.clusterPoints.size()>1) {
				numberOfClusterWithMoreThanOnePoint++;
			}
		}
		return numberOfClusterWithMoreThanOnePoint;
	}
	
	public double[][] toDoubleArray() {
		/** we have as many centroids as clusters and each centroid had 2 attributes*/
		double[][] centroids = new double[clusters.size()][2];
		int centroidIndex = 0;
		for (Cluster cluster : clusters) {
			centroids[centroidIndex][0] = cluster.getCentroid().getLatitude();
			centroids[centroidIndex][1] = cluster.getCentroid().getLongitude();
		}
		return centroids;
	}
	
	public double[][] toDoubleArrayForMultiPointClusters() {
		/** we have as many centroids as clusters and each centroid had 2 attributes*/
		double[][] centroids = new double[clusters.size()][2];
		int centroidIndex = 0;
		for (Cluster cluster : clusters) {
			if (cluster.clusterPoints.size() == 1) {
				continue;
			}
			centroids[centroidIndex][0] = cluster.getCentroid().getLatitude();
			centroids[centroidIndex][1] = cluster.getCentroid().getLongitude();
		}
		return centroids;
	}
	
	public List<Cluster> getMultiPointClusters() {
		/** we have as many centroids as clusters and each centroid had 2 attributes*/
		List<Cluster> multiPointClusters = new ArrayList<Cluster>(); 
		for (Cluster cluster : clusters) {
			if (cluster.clusterPoints.size()>1) {
				multiPointClusters.add(cluster);
			}
		}
		return multiPointClusters;
	}
	
	@Override
	public String toString() {
		
		return super.toString();
	}
}
