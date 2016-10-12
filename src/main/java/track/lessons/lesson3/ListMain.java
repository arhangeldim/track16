package track.lessons.lesson3;

/**
 *
 */
public class ListMain {

    public static void main(String[] args) {
        LinkedList linkedList = new LinkedList();

        for (int i = 0; i < 5; i++) {
            linkedList.add(i);
        }

        System.out.println(linkedList);
        System.out.println(linkedList.get(0));
        System.out.println(linkedList.get(3));
        System.out.println(linkedList.remove(1));
        System.out.println(linkedList);
        System.out.println(linkedList.remove(linkedList.size() - 1));
        System.out.println(linkedList);
        System.out.println(linkedList.remove(0));
        System.out.println(linkedList);
        System.out.println(linkedList.remove(0));
        System.out.println(linkedList.remove(0));
        System.out.println(linkedList);

        for (int i = 0; i < 5; i++) {
            linkedList.push(i);
        }
        System.out.println(linkedList);
        System.out.println(linkedList.pop());

        DynamicList dynamicList = new DynamicList();

        for (int i = 0; i < 12; i++) {
            dynamicList.add(i);
        }

        System.out.println(dynamicList);
        System.out.println(dynamicList.get(0));
        System.out.println(dynamicList.get(3));
        System.out.println(dynamicList.remove(1));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(dynamicList.size() - 1));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
        System.out.println(dynamicList);
        System.out.println(dynamicList.remove(0));
System.out.println(dynamicList);

//        List list = new DynamicList();
//        list.add(1);
//        list.add(2);
//        list.add(10);
//        int first = list.remove(0);

    }
}
