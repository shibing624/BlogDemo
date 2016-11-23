package xm.list;

import java.util.*;

/**
 * 链表实现
 * @author xuming
 */
public class LinkListDemo {
    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();
        list.add("sdf0");
        list.add("sdf1");
        list.add("sdf2");
        list.add("sdf3");

        list.set(3,"df");
        list.addLast("d");
        list.pollFirst();
        list.push("ff");
        list.peekFirst();
        list.removeFirst();
        list.get(1);

        list.push("fsd");
        list.getFirst();
        list.getLast();
        System.out.println("1<<10:1024?  "+(1<<10)+"  "+(2<<10)+"  "+(2>>10)+"  "+(Math.pow(2,3))+"  "+(Math.pow(2,10)));
        boolean isContains = list.contains("fsd");
        if(isContains){
            int index = list.indexOf("fsd");
            int index2 = list.indexOf("sdf2");
        }
        HashMap<String,String> hm = new HashMap<>();
        hm.put("d","dd");
        hm.put("s","df");
        hm.get(1);

        Iterator iter = hm.entrySet().iterator();
        while(iter.hasNext()){
            Map.Entry entry=(Map.Entry)iter.next();
            entry.getKey();
            entry.getValue();
        }
        list.forEach(i->i.contains("d"));

        ArrayDeque deque = new ArrayDeque(list);
        String s = deque.toArray().toString();
        System.out.println(s);
        Vector ssss = new Vector();
        ArrayList sd = new ArrayList();
        TreeSet set = new TreeSet();




    }


}
