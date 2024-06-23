package ThreadSafety.HashSetTemplate;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Implement a hash set manually to demonstrate the non-thread-safety.
 * Referenced from http://www.doc.ic.ac.uk/~afd/teaching/Concurrency/JavaCode.tgz
 */
public class _1MyBadHashSet<T> {
	private static final int DEFAULT_NUM_BUCKETS = 1024;
	private int size;
	private final List<List<T>> buckets;

	public _1MyBadHashSet(int numBuckets) {
		buckets = new ArrayList<>(numBuckets);
		for (int i = 0; i < numBuckets; i++) {
			buckets.add(null);
		}
		size = 0;
	}

	public _1MyBadHashSet() {
		this(DEFAULT_NUM_BUCKETS);
	}

	public boolean contains(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			return false;
		}
		return bucket.contains(item);
	}

	public boolean add(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			bucket = new LinkedList<>();
			buckets.set(getBucketIndex(item), bucket);
		}
		if (!bucket.contains(item)) {
			bucket.add(item);
			size++;
			return true;
		}
		return false;
	}

	public boolean remove(T item) {
		List<T> bucket = getBucket(item);
		if (bucket == null) {
			return false;
		}
		boolean result = bucket.remove(item);
		if (result) {
			size--;
		}
		return result;
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
			for (T t : b) {
				if (!first) {
					result += ", ";
				}
				first = false;
				result += t.toString();
			}
		}
		result += " }";
		return result;
	}
}
