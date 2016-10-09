package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

	public static void main(String[] args) throws Exception {

		LinkedList<Integer> list = new LinkedList<Integer>();
		DynamicList<Integer> vector = new DynamicList<Integer>();

        list.add((Integer) 5);
        list.add(8);
        System.out.println(list.get(0));
	}
}
