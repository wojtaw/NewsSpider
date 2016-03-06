import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wojtaw on 04/03/16.
 */
public class IhnedScraper {
    private Document doc = null;
    private ArrayList<ArticleLinks> articleList = new ArrayList<ArticleLinks>();
    private String searchKeyword;
    private int oneYearNumberOfResults = 0;
    private int totalNumberOfResults = 0;
    private String urlToAnalyze = "http://vyhledavani.ihned.cz/index.php";

    public IhnedScraper(){

    }

    public ArrayList<ArticleLinks> scrapeKeyword(String keyword){
        this.searchKeyword = keyword;

        String searchYears[][]={{"2007-01-01","2008-01-01"},{"2008-01-02","2009-01-01"},{"2009-01-02","2010-01-01"},{"2010-01-02","2010-12-30"}};
        //for (int i = 0; i < searchYears.length; i++) {
        int i = 1;
            System.out.println("Searching for results in YEAR "+i);
            String dateFrom = searchYears[i][0];
            String dateTo = searchYears[i][1];
            //getNumberOfResults(dateFrom,dateTo);
            performSearch(dateFrom,dateTo);
        //}
        System.out.println("Article fetching finished, total: "+articleList.size());
        return articleList;
    }

    private void getNumberOfResults(String dateFrom, String dateTo){
        loadWebsite(0,dateFrom,dateTo);
        Element count = doc.select("div.count").first();
        String countText = count.text();
        oneYearNumberOfResults = Integer.parseInt(countText.substring(countText.indexOf('/')+2,countText.length()));
        totalNumberOfResults += oneYearNumberOfResults;
    }

    private void performSearch(String dateFrom, String dateTo){
        loadWebsite(50,dateFrom,dateTo);
        //controlPrint();

        /*
        for (int i = 0; i < oneYearNumberOfResults; i+=50) {
            loadWebsite(i,dateFrom,dateTo);
            controlPrint();
            //listResults();
        }
        */
        System.out.println("Results reported "+ oneYearNumberOfResults);
        System.out.println("Articles fetched: "+articleList.size());
    }

    private void listResults(){
        Elements newsHeadlines = doc.select("h2");
        Element tmpElement;
        ArticleLinks tmpArticle;
        for(Element el: newsHeadlines){
            tmpElement = el.select("a").first();
            tmpArticle = new ArticleLinks("http://ihned.cz"+tmpElement.attr("href"),searchKeyword,tmpElement.text());
            if(el.className().equals("lock")) tmpArticle.setPaid(true);
            articleList.add(tmpArticle);

            if(tmpArticle.isPaid())
                System.out.println("Keyword: "+tmpArticle.getSearchKeyword()+" found new FREE article: "+tmpArticle.getTitle()+" with link "+tmpArticle.getUrlLink());
            else
                System.out.println("Keyword: "+tmpArticle.getSearchKeyword()+" found new Paid article: "+tmpArticle.getTitle()+" with link "+tmpArticle.getUrlLink());
            /*
            */
        }
    }

    private void controlPrint(){
        Elements es = doc.select("body");
        System.out.println("load from jsoup connect using maxBodySize= " + es.get(0).toString().length());
        System.out.println(es.toString());
    }

    private void loadWebsite(int startFrom, String fromDate, String toDate){
        try {
            System.out.println("FromDate "+fromDate+" "+toDate+" "+startFrom);

            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(loadHTTPContent()));

            StringBuffer result = new StringBuffer();
            String line = "";
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            System.out.println(result.toString());

            //doc = Jsoup.parse(loadHTTPContent(), "UTF8", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private InputStream loadHTTPContent(){
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlToAnalyze);

        // add header
        //post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-Type", "text/html; charset=UTF-8");


        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("sn", "C02G8416DRJM"));
        urlParameters.add(new BasicNameValuePair("cn", ""));
        urlParameters.add(new BasicNameValuePair("locale", ""));
        urlParameters.add(new BasicNameValuePair("caller", ""));
        urlParameters.add(new BasicNameValuePair("num", "12345"));

        HttpResponse response;
        try {
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            response = httpclient.execute(post);
            return response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
