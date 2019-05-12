import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DanawaPageLink danawaPageLink = new DanawaPageLink();
        List<String> urlList = new ArrayList<>();
        System.out.println("Hello World!");
        urlList.addAll(danawaPageLink.makePageLinkSelenium());

        List<String> urlList2 = new ArrayList<>();
        urlList2 = deleteDuplicate(urlList);

        System.out.println("====================urllist=============================");
        int i = 0;

        for(String url : urlList2){
            System.out.println((i++) +": "+  url);
        }
    }

    public static List<String> deleteDuplicate(List<String> list){
        List<String> filterlist = new ArrayList<String>();
        int i = 0;

        for (String element : list) {
            //System.out.println(i++);
            // If this element is not present in newList
            // then add it
            if (!filterlist.contains(element)) {

                filterlist.add(element);
            }
        }

        // return the new list
        return filterlist;
    }
}
