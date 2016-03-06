import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
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

    private void loadWebsite(int startFrom, String fromDate, String toDate) {
        System.out.println("FromDate "+fromDate+" "+toDate+" "+startFrom);

        System.out.println(loadHTTPContent());

        //doc = Jsoup.parse(loadHTTPContent(), "UTF8", "");
    }

    private String loadHTTPContent(){
        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(urlToAnalyze);

        post.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        post.setHeader("accept-charset", "UTF-8");
        //post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");


        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        List<NameValuePair> urlParameters2 = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("ft[max_amount]","50"));
        urlParameters.add(new BasicNameValuePair("ft[item][0]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][1]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][2]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][3]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][4]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][5]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][6]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][7]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][8]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][9]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][10]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][11]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][12]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][13]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][14]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][15]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][16]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][17]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][18]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][19]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][20]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][21]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][22]","1"));
        urlParameters.add(new BasicNameValuePair("ft[item][23]","1"));
        urlParameters.add(new BasicNameValuePair("ft[cgrp][0]","1"));
        urlParameters.add(new BasicNameValuePair("ft[type]","2"));
        urlParameters.add(new BasicNameValuePair("ft[time]","2"));
        urlParameters.add(new BasicNameValuePair("ft[show]","all"));
        urlParameters.add(new BasicNameValuePair("ft[sklonuj]","on"));
        urlParameters.add(new BasicNameValuePair("ft[from_date]","2008-01-01"));
        urlParameters.add(new BasicNameValuePair("ft[to_date]","2009-01-01"));
        urlParameters.add(new BasicNameValuePair("ft[what]","NWR"));
        urlParameters.add(new BasicNameValuePair("s1","0"));
        urlParameters.add(new BasicNameValuePair("s2","0"));
        urlParameters.add(new BasicNameValuePair("s3","0"));
        urlParameters.add(new BasicNameValuePair("s4","0"));
        urlParameters.add(new BasicNameValuePair("s5","0"));
        urlParameters.add(new BasicNameValuePair("s6","0"));
        urlParameters.add(new BasicNameValuePair("m",""));
        HttpResponse response;
        try {
            //post.setEntity(new UrlEncodedFormEntity(urlParameters));
            post.setEntity(new UrlEncodedFormEntity(urlParameters));
            System.out.println(post.getURI());
            response = httpclient.execute(post);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
