import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
/*
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClientBuilder;
*/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DanawaPageLink {

    private WebDriver driver;

    private static final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    //private static final String WEB_DRIVER_PATH = "C:/Users/hyoen/IdeaProjects/webcrawler/74/chromedriver.exe";
    private static final String WEB_DRIVER_PATH = "C:\\Selenium\\chromedriver.exe";


    private String base_url;

    public DanawaPageLink(){
        super();
        System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);
        ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        driver = new ChromeDriver(options);
        base_url = "http://prod.danawa.com/list/?cate=112758";
    }

    protected List<String> makePageLinkSelenium(){
        List<String> Links = new ArrayList<>();
        int i=0;
        try {
            driver.get(base_url);

            String html = driver.getPageSource();
            Document doc = Jsoup.parse(html);
            Element productListArea = doc.getElementById("productListArea");
            Elements product_lists = productListArea.getElementsByClass("product_list");
            Elements prod_main_infos = productListArea.getElementsByClass("prod_info");
            Elements a = new Elements();
            Elements a2 = new Elements();
            String url = new String();
            List<WebElement> elementList = new ArrayList<WebElement>();

            for(int page10 = 1; page10<18; page10++){
                System.out.println("\n\n\n****page10: " + page10);

                for(int page = 1; page<=10; page++){
                    driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
                    Thread.sleep(20*1000);
                    html = driver.getPageSource();
                    doc = Jsoup.parse(html);
                    System.out.println(page);
                    productListArea = doc.getElementById("productListArea");
                    prod_main_infos = productListArea.getElementsByClass("prod_info");
                    elementList = driver.findElements(By.className("num"));
                    WebElement nextpage = driver.findElement(By.className("nav_next"));
                    for(Element prod_main_info : prod_main_infos){
                        Elements prod_info = prod_main_info.getElementsByClass("prod_name");
                        Elements prod_names = prod_info.tagName("p");
                        //System.out.println(prod_names);
                        a = prod_names.tagName("a");
                        for(Element aElement : a){
                            a2 = aElement.getElementsByTag("a");
                            url = a2.attr("href");
                            if(!url.isEmpty()){
                                i++;
                                System.out.println("////////////////////////////url: " + url);
                                Links.add(url);
                            }
                        }

                    }
                    System.out.println(i);

                    switch (page){
                        case 1:
                            elementList.get(1).click();
                            break;
                        case 2:
                            elementList.get(2).click();
                            break;
                        case 3:
                            elementList.get(3).click();
                            break;
                        case 4:
                            elementList.get(4).click();
                            break;
                        case 5:
                            elementList.get(5).click();
                            break;
                        case 6:
                            elementList.get(6).click();
                            break;
                        case 7:
                            elementList.get(7).click();
                            break;
                        case 8:
                            elementList.get(8).click();
                            break;
                        case 9:
                            elementList.get(9).click();
                            break;
                        case 10:
                            System.out.println("next page");
                            nextpage.click();
                            break;
                    }
                }
            }

            /*for(Element prod_main_info : prod_main_infos){
                Elements prod_info = prod_main_info.getElementsByClass("prod_name");
                Elements prod_names = prod_info.tagName("p");
                //System.out.println(prod_names);
                a = prod_names.tagName("a");
                for(Element aElement : a){
                    a2 = aElement.getElementsByTag("a");
                    url = a2.attr("href");
                    if(!url.isEmpty()){
                        i++;
                        System.out.println("////////////////////////////url: " + url);
                        Links.add(url);
                    }
                }

            }
            System.out.println(i);*/


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.close();
        }

        return Links;
    }






    /*public static String getCurrentData(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return sdf.format(new Date());

    }

    public List<String> makePageLink(){
        List<String> Links = new ArrayList<>();

        try{
            // 1. 가져오기전 시간 찍기
            System.out.println(" Start Date : " + getCurrentData());

            // 2. 가져올 HTTP 주소 세팅
            HttpPost http = new HttpPost("http://prod.danawa.com/list/?cate=11316920");

            // 3. 가져오기를 실행할 클라이언트 객체 생성
            HttpClient httpClient = HttpClientBuilder.create().build();

            // 4. 실행 및 실행 데이터를 Response 객체에 담음
            HttpResponse response = httpClient.execute(http);

            // 5. Response 받은 데이터 중, DOM 데이터를 가져와 Entity에 담음
            HttpEntity entity = response.getEntity();

            // 6. Charset을 알아내기 위해 DOM의 컨텐트 타입을 가져와 담고 Charset을 가져옴
            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();



            // 7. DOM 데이터를 한 줄씩 읽기 위해 Reader에 담음 (InputStream / Buffered 중 선택은 개인취향)
            BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), charset));

            // 8. 가져온 DOM 데이터를 담기위한 그릇
            StringBuffer sb = new StringBuffer();

            // 9. DOM 데이터 가져오기
            String line = "";

            while((line=br.readLine()) != null){
                sb.append(line+"\n");
            }

            //System.out.println(sb.toString());
            Document doc = Jsoup.parse(sb.toString());
            Element body = doc.body();
            System.out.println(body.toString());
            //Document doc2 = Jsoup.connect("http://prod.danawa.com/list/?cate=11316920").get();
            System.out.println("test\n\n");
            Element productListArea = doc.getElementById("productListArea");
            System.out.println(productListArea.toString());
            System.out.println("test\n\n");
            Elements product_list_wrap = doc.getElementsByClass("product_list_wrap");
            System.out.println(product_list_wrap.toString());
            System.out.println("\n\nprod name");
            Elements prod_name = doc.getElementsByClass("prod_name");
            System.out.println(prod_name.toString());

        } catch (IOException e){
            e.printStackTrace();
        }

        return Links;
    }*/

}
