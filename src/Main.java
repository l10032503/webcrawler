import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

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

        specCrawler(urlList2);

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



    static void specCrawler(List<String> urlList){
        System.setProperty("webdriver.chrome.driver","C:\\Selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //int urlList_size = urlList.size();
        int urlList_size = 50;
        int dataList_size = 0;
        String[][][] dataArr = new String[urlList_size][][];

        String[] urlListArr = urlList.toArray(new String[urlList.size()]);

        for(int i=0 ; i<urlList_size ; i++){
            String URL = urlListArr[i];
            driver.get(URL);
            WebElement notebookName = driver.findElement(By.className("prod_tit"));
            WebElement myTable = driver.findElement(By.className("spec_tbl"));
            List<WebElement> oneLine = myTable.findElements(By.tagName("tr"));
            int oneLine_size = oneLine.size();

            ArrayList<String[]> dataList = new ArrayList<String[]>();
            dataList.add(new String[]{"상품명", notebookName.getText()});

            for(int j=0 ; j<oneLine_size ; j++){
                List<WebElement> name = oneLine.get(j).findElements(By.tagName("th"));
                int name_size = name.size();
                if(name_size == 2){
                    List<WebElement> value = oneLine.get(j).findElements(By.tagName("td"));
                    dataList.add(new String[]{name.get(0).getText(), value.get(0).getText()});
                    dataList.add(new String[]{name.get(1).getText(), value.get(1).getText()});
                }
            }

            dataList_size = dataList.size();
            dataArr[i] = new String[dataList_size][2];
            for(int j=0; j<dataList_size; j++) {
                dataArr[i][j][0] = dataList.get(j)[0];
                dataArr[i][j][1] = dataList.get(j)[1];
            }
        }

        driver.quit();

        for(int i=0 ; i<urlList_size ; i++){
            System.out.println("/// "+(i+1)+" 번째 노트북의 상세스펙 입니다 ///");
            for(int j=0 ; j<dataList_size ; j++)
                System.out.print("{"+dataArr[i][j][0] + " = " +dataArr[i][j][1]+"} ");
            System.out.println("");
        }
    }
}
