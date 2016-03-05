import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

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
        controlPrint();

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
            int maxBodySize = 4096000;//2MB (default is 1MB) 0 for unlimited size
            doc = Jsoup.connect(urlToAnalyze)
                    .maxBodySize(maxBodySize)
                    //.data("ft[max_amount]","50","ft[item][0]","1","ft[item][1]","1","ft[item][2]","1","ft[item][3]","1","ft[item][4]","1","ft[item][5]","1","ft[item][6]","1","ft[item][7]","1","ft[item][8]","1","ft[item][9]","1","ft[item][10]","1","ft[item][11]","1","ft[item][12]","1","ft[item][13]","1","ft[item][14]","1","ft[item][15]","1","ft[item][16]","1","ft[item][17]","1","ft[item][18]","1","ft[item][19]","1","ft[item][20]","1","ft[item][21]","1","ft[item][22]","1","ft[item][23]","1","ft[cgrp][0]","1","ft[type]","2","ft[time]","2","ft[show]","all","ft[sklonuj]","on","ft[from_date]",fromDate,"ft[to_date]",toDate,"ft[what]",searchKeyword,"ft[from]","50","s1","0","s2","0","s3","0","s4","0","s5","0","s6","S","m","")
                    .data("p","00000S",
                            "ft[max_amount]","50",
                            "ft[item][0]","1",
                            "ft[item][1]","1",
                            "ft[item][2]","1",
                            "ft[item][3]","1",
                            "ft[item][4]","1",
                            "ft[item][5]","1",
                            "ft[item][6]","1",
                            "ft[item][7]","1",
                            "ft[item][8]","1",
                            "ft[item][9]","1",
                            "ft[item][10]","1",
                            "ft[item][11]","1",
                            "ft[item][12]","1",
                            "ft[item][13]","1",
                            "ft[item][14]","1",
                            "ft[item][15]","1",
                            "ft[item][16]","1",
                            "ft[item][17]","1",
                            "ft[item][18]","1",
                            "ft[item][19]","1",
                            "ft[item][20]","1",
                            "ft[item][21]","1",
                            "ft[item][22]","1",
                            "ft[item][23]","1",
                            "ft[cgrp][0]","1",
                            "ft[type]","2",
                            "ft[time]","2",
                            "ft[show]","all",
                            "ft[sklonuj]","on",
                            "ft[from_date]",fromDate,
                            "ft[to_date]",toDate,
                            "ft[what]","uhli",
                            "ft[order]","0",
                            "ft[from]","50")
                    .timeout(60000).post();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
