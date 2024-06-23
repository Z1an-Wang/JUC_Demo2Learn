package ThreadSafety.HashSetTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class _3SynchronizedHashSet<T> {
	private static final int DEFAULT_NUM_BUCKETS = 1024;
	private int size;
	private final List<List<T>> buckets;

	public _3SynchronizedHashSet(int numBuckets) {
		buckets = new ArrayList<>(numBuckets);
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(null);
		}
		size = 0;
	}

	public _3SynchronizedHashSet() {
		this(DEFAULT_NUM_BUCKETS);
	}

	public boolean contains(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			return false;
		}
		synchronized (bucket) {
			return bucket.contains(item);
		}
	}

	public boolean add(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			synchronized (this) {
				bucket = getBucket(item);
				if (bucket == null) { // Another thread might have made the bucket in the meantime
					bucket = new LinkedList<>();
					buckets.set(getBucketIndex(item), bucket);
				}
			}
		}
		synchronized (bucket) { // Ensure exclusive access to this bucket
			if (!bucket.contains(item)) {
				bucket.add(item);
				size++; // Problem: concurrent updates to different buckets can cause races on this variable
				return true;
			}
			return false;
		}
	}

	public boolean remove(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			return false;
		}
		synchronized (bucket) {
			boolean result = bucket.remove(item);
			if (result) {
				size--;
			}
			return result;
		}
	}

	public int size() {
		return size;
	}

	private List<T> getBucket(T item) {
		return buckets.get(getBucketIndex(item));
	}

	private int getBucketIndex(T item) {
		return Math.abs(item.hashCode()) % buckets.size();
	}

	public String toString() {
		String result = "size: " + size + ", elements: { ";
		boolean first = true;
		for (List<T> b : buckets) {
			if (b == null) {
				continue;
			}
			synchronized (b) { // Ensure exclusive access to the bucket
				for (T t : b) {
					if (!first) {
						result += ", ";
					}
					first = false;
					result += t.toString();
				}
			}
		}
		result += " }";
		return result;
	}
}
