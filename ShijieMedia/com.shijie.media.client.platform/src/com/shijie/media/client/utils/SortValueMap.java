package com.shijie.media.client.utils;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class SortValueMap<K,V> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2673102715390682059L;
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport( this );

	private ArrayList<V> list = new ArrayList<V>();
	private HashMap<K,V> map = new HashMap<K,V>();
	private Comparator<? super V> comparator;
	
	public SortValueMap(Comparator<? super V> sort){
		this.comparator = sort;
	}
	
	public void put(K key,V value){
		map.put(key, value);
		list.add(value);
		Collections.sort(list,comparator);
		pcs.firePropertyChange("add", value, list.indexOf(value));
	}

	public void remove(K key){
		V value = map.get(key);
		int i = list.indexOf(value);
		list.remove(map.remove(key));
		pcs.firePropertyChange("remove", value, i);
	}
	
	public Collection<V> values(){
		return list;
	}
	
	public V get(K key){
		return map.get(key);
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener){
        this.pcs.removePropertyChangeListener(listener);
    }
}
