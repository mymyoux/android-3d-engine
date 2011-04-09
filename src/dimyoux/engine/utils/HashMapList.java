package dimyoux.engine.utils;

import java.util.LinkedHashMap;
/**
 * Combines HashMap access with List access
 *
 * @param <K> Key
 * @param <V> Object
 */
public class HashMapList<K, V> extends LinkedHashMap<K, V> {

	/**
	 * Serial Version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Return an Object by its ID
	 * @param id ID
	 * @return Object or null if ID is greater than size()
	 */
	@SuppressWarnings("unchecked")
	public V get(int id)
	{
		return id>-1 && size()>id?(V)values().toArray()[id]:null;
	}
	/**
	 * Removes the Object indexed by the ID
	 * @param id ID
	 * @return Object or null
	 */
	public V remove(int id)
	{
		return remove(get(id));
	}

}
