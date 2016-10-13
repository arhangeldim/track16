package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) throws Exception {
        
        List list = new DynamicList();
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }

        System.out.println(list.size());

        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.println(list.get(i));
        }

    }
}
