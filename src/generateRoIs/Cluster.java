package generateRoIs;

import java.util.HashSet;
import java.util.Set;

public class Cluster {
	public int id ;
	public Set<DataPoint>  clusterPoints = new HashSet<DataPoint>();

	private DataPoint centroid = null; 

	public Cluster(int id) {
		super();
		this.id = id;
	}

	public Cluster(int id, DataPoint dataPoint) {
		super();
		this.id = id;
		clusterPoints.add(dataPoint);
		centroid = dataPoint;
	}

	public void addPoint(DataPoint point) {
		clusterPoints.add(point);
		centroid = calcCentroidforCluster(clusterPoints);
	}

	public void addPoints(Cluster otherCluster) {
		clusterPoints.addAll(otherCluster.clusterPoints);
		centroid = calcCentroidforCluster(clusterPoints);
	}


	public static DataPoint calcCentroidforCluster(Set<DataPoint> cluster) {
		double SummedLongs = 0;
		double SummedLats = 0;

		if (cluster.size()==1)
		{
			return (DataPoint) cluster.iterator().next();
		}

		for (DataPoint dataPoint : cluster) {
			SummedLats	+= dataPoint.getLatitude();
			SummedLongs += dataPoint.getLongitude();
		}


		double centroidLat = SummedLats / (double)cluster.size();
		double centroidLong = SummedLongs / (double)cluster.size();

		return new DataPoint(centroidLat, centroidLong);
	}

	public DataPoint getCentroid() {
		return centroid;
	} 

	public  DataPoint getMinPoint() {
		Double minLat =  Double.MAX_VALUE;
		Double minLong =  Double.MAX_VALUE;

		for (DataPoint pt: clusterPoints) {
			if (minLat >= pt.getLatitude()){
				minLat  = pt.getLatitude();
			}

			if (minLong >= pt.getLongitude()){
				minLong  = pt.getLongitude();
			}
		}

		return new DataPoint(minLat, minLong);
	}

	public  DataPoint getMaxPoint() {
		Double maxLat =  Double.MIN_VALUE;
		Double maxLong =  Double.MIN_VALUE;

		for (DataPoint pt: clusterPoints) {
			if (maxLat <= pt.getLatitude()){
				maxLat  = pt.getLatitude();
			}

			if (maxLong <= pt.getLongitude()){
				maxLong  = pt.getLongitude();
			}
		}

		return new DataPoint(maxLat, maxLong);
	}



	@Override
	public String toString() {
		return " #" +clusterPoints.size()+" points center:"+centroid;
	}

}
