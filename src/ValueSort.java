import java.util.Comparator;

public class ValueSort implements Comparator<Value> {

	@Override
	/**
	 * Comparator that will consider the orderings of cards based on the value, 
	 * ordering largest to smallest
	 * @param v1 the first value to compare
	 * @param v2 the second value to compare
	 * 
	 * @requires
	 * 	v1 and v2 are not null
	 * 
	 * @ensures
	 * 	[1, -1, or 0 is returned]
	 */
	public int compare(Value v1, Value v2) {
		if(v1.number > v2.number)
		{
			return -1;
		}
		else if (v1.number < v2.number)
		{
			return 1;
		}
		return 0;
	}

}
