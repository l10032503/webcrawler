import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DanawaPageLink danawaPageLink = new DanawaPageLink();
        List<String> urlList = new ArrayList<>();
        System.out.println("Hello World!");
        urlList.addAll(danawaPageLink.makePageLinkSelenium());

        System.out.println("====================urllist=============================");
        System.out.println(urlList);
    }
}
