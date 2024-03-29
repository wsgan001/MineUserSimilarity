package generateRoIs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.javaml.core.AbstractInstance;
import net.sf.javaml.core.Instance;

public class SPInstance extends AbstractInstance implements Instance {

	private static final long serialVersionUID = 3284511291715269081L;

	/* Holds values */
	private double[] attributes;

	private String label;//label
	private double lat; 
	private double lngt; 
	private double arvT; //arrive time
	private double levT; //leave time
	private String date; //date
	
	public double[] getAttributes() {
		return attributes;
	}

	public void setAttributes(double[] attributes) {
		this.attributes = attributes;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLngt() {
		return lngt;
	}

	public void setLngt(double lngt) {
		this.lngt = lngt;
	}

	public double getArvT() {
		return arvT;
	}

	public void setArvT(double arvT) {
		this.arvT = arvT;
	}

	public double getLevT() {
		return levT;
	}

	public void setLevT(double levT) {
		this.levT = levT;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * Creates a new instance with the provide value for the attributes. The
	 * class label will be set to null.
	 * 
	 * @param att
	 *            the value of the instance
	 */
	public SPInstance(double[] att) {
		this(att, null);
	}

	/**
	 * Creates a new instance with the provided attribute values and the
	 * provided class label.
	 * 
	 * @param att
	 *            the attribute values
	 * @param classValue
	 *            the class label
	 */
	public SPInstance(double[] att, Object classValue) {
		super(classValue);
		this.attributes = att.clone();
	}

	/* Hide argumentless constructor */
	private SPInstance() {
	}

	/**
	 * Creates an instance that has space for the supplied number of attributes.
	 * By default all attributes are initialized by zero.
	 * 
	 * @param size
	 *            the number of attributes
	 */
	public SPInstance(int size) {
		this(new double[size]);
	}

	@Override
	public double value(int pos) {
		return attributes[pos];
	}

	@Override
	public void clear() {
		attributes = new double[attributes.length];

	}

	@Override
	public boolean containsKey(Object key) {
		if (key instanceof Integer) {
			int i = (Integer) key;
			return i >= 0 && i < attributes.length;
		} else
			return false;
	}

	@Override
	public boolean containsValue(Object value) {
		if (value instanceof Number) {
			double val = ((Number) value).doubleValue();
			for (int i = 0; i < attributes.length; i++) {
				if (Math.abs(val - attributes[i]) < 0.00000001)
					return true;
			}
		}
		return false;
	}

	@Override
	public Set<java.util.Map.Entry<Integer, Double>> entrySet() {
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		for (int i = 0; i < attributes.length; i++)
			map.put(i, attributes[i]);
		return map.entrySet();
	}

	@Override
	public Double get(Object key) {
		return attributes[(Integer) key];
	}

	@Override
	public boolean isEmpty() {

		return false;
	}

	@Override
	public SortedSet<Integer> keySet() {
		TreeSet<Integer> keys = new TreeSet<Integer>();
		for (int i = 0; i < attributes.length; i++)
			keys.add(i);
		return keys;
	}

	@Override
	public Double put(Integer key, Double value) {
		double val = attributes[key];
		attributes[key] = value;
		return val;

	}

	@Override
	public void putAll(Map<? extends Integer, ? extends Double> m) {
		for (Integer key : m.keySet()) {
			attributes[key] = m.get(key);
		}

	}

	@Override
	public Double remove(Object key) {
		throw new UnsupportedOperationException(
				"Cannot unset values from a dense instance.");
	}

	@Override
	@Deprecated
	public int size() {
		return attributes.length;
	}

	@Override
	public Collection<Double> values() {
		Collection<Double> vals = new ArrayList<Double>();
		for (double v : attributes)
			vals.add(v);
		return vals;
	}

	@Override
	public int noAttributes() {
		return attributes.length;
	}

	@Override
	public String toString() {
		return "{" + Arrays.toString(attributes) + ";" + classValue() + "}";
	}

	@Override
	public void removeAttribute(int i) {
		double[] tmp = attributes.clone();
		attributes = new double[tmp.length - 1];
		System.arraycopy(tmp, 0, attributes, 0, i);
		System.arraycopy(tmp, i + 1, attributes, i, tmp.length - i - 1);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(attributes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final SPInstance other = (SPInstance) obj;
		if (!Arrays.equals(attributes, other.attributes))
			return false;
		return true;
	}

	@Override
	public Instance copy() {
		SPInstance out = new SPInstance();
		out.attributes = this.attributes.clone();
		out.setClassValue(this.classValue());
		return out;
	}

	@Override
	public void removeAttributes(Set<Integer> indices) {
		double[] tmp = attributes.clone();
		attributes = new double[tmp.length - indices.size()];
		int index = 0;
		for (int i = 0; i < tmp.length; i++) {
			if (!indices.contains(i)) {
				attributes[index++] = tmp[i];
			}
		}
	}

}
