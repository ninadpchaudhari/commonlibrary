/*
 * CTimeScaleList.java
 * Created on 2011/06/05
 * Copyright(c) 2011 Yoshiaki Matsuzawa, Shizuoka University. All rights reserved.
 */
package clib.common.time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import clib.common.utils.ICChecker;

/**
 * @author macchan
 * 
 */
public class CTimeOrderedList<T extends ICTimeOrderable> implements List<T>,
		Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<T> elements = new ArrayList<T>();

	public CTimeOrderedList() {
	}

	public CTimeOrderedList(List<T> list) {
		for (T t : list) {
			add(t);
		}
	}

	public boolean add(T element) {
		return elements.add(element);
	}

	public boolean hasElement(CTime time) {
		T t = getFirst();
		if (t == null) {
			return false;
		}
		return time.afterAndEqual(t.getTime());
	}

	public List<T> getElements() {
		return new ArrayList<T>(elements);
	}

	// public int indexOf(CTime time) {
	// T t = searchElement(time, new CNullChecker<T>());
	// if (t == null) {
	// return -1;
	// } else {
	// return elements.indexOf(t);
	// }
	//
	// // //binary search!
	// // int left = 0;
	// // int right = elements.size();
	// // while (left < right) {
	// // int center = (left + right) / 2;
	// // T t = elements.get(center);
	// // }
	// //
	// //return -1;
	// }

	public CTimeOrderedList<T> searchElements(final CTimeRange range,
			final ICChecker<T> checker) {
		CTimeOrderedList<T> list = select(new ICChecker<T>() {
			public boolean check(T t) {
				return checker.check(t) && range.isIncluding(t.getTime());
			};
		});
		return list;
	}

	public CTimeOrderedList<T> searchElements(final CTimeRange range) {
		CTimeOrderedList<T> list = select(new ICChecker<T>() {
			public boolean check(T t) {
				return range.isIncluding(t.getTime());
			};
		});
		return list;
	}

	public T searchElement(CTime time) {
		return searchElementBefore(time);
	}

	public T searchElement(CTime time, ICChecker<T> checker) {
		return searchElementBefore(time, checker);
	}

	public T searchElementBefore(CTime time, ICChecker<T> checker) {
		CTimeOrderedList<T> list = select(checker);
		return list.searchElementBefore(time);
	}

	public T searchElementAfter(CTime time, ICChecker<T> checker) {
		CTimeOrderedList<T> list = select(checker);
		return list.searchElementAfter(time);
	}

	public T searchElementBefore(CTime time) {
		int size = elements.size();
		for (int i = size - 1; i >= 0; i--) {
			T t = elements.get(i);
			if (time.afterAndEqual(t.getTime())) {
				return t;
			}
		}
		return null;
	}

	public T searchElementAfter(CTime time) {
		int size = elements.size();
		for (int i = 0; i < size; i++) {
			T t = elements.get(i);
			if (time.beforeAndEqual(t.getTime())) {
				return t;
			}
		}
		return null;
	}

	public CTimeOrderedList<T> select(ICChecker<T> selecter) {
		CTimeOrderedList<T> newList = new CTimeOrderedList<T>();
		for (T t : elements) {
			if (selecter.check(t)) {
				newList.add(t);
			}
		}
		return newList;
	}

	public T getFirst() {
		assert elements.size() > 0;
		if (elements.size() <= 0) {
			return null;
		}
		// return elements.getFirst();
		return elements.get(0);
	}

	public T getLast() {
		assert elements.size() > 0;
		// return elements.getLast();
		return elements.get(elements.size() - 1);
	}

	public int size() {
		return elements.size();
	}

	// ----------------------------------------

	public void add(int index, T element) {
		elements.add(index, element);
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends T> c) {
		return elements.addAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection<? extends T> c) {
		return elements.addAll(index, c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#clear()
	 */
	public void clear() {
		elements.clear();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#get(int)
	 */
	public T get(int index) {
		return elements.get(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return elements.indexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#iterator()
	 */
	public Iterator<T> iterator() {
		return elements.iterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return elements.lastIndexOf(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return elements.toArray();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#toArray(T[])
	 */
	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return elements.remove(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> c) {
		return elements.removeAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> c) {
		return elements.retainAll(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public T set(int index, T element) {
		return elements.set(index, element);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#remove(int)
	 */
	public T remove(int index) {
		return elements.remove(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator()
	 */
	public ListIterator<T> listIterator() {
		return elements.listIterator();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator<T> listIterator(int index) {
		return elements.listIterator(index);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.List#subList(int, int)
	 */
	public List<T> subList(int fromIndex, int toIndex) {
		return elements.subList(fromIndex, toIndex);
	}

}
