/**
 * Created by wojtaw on 04/03/16.
 */
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.customsearch.model.Search;

import java.util.List;

public class GoogleSearch {
    final private String GOOGLE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?";
    //final private String GOOGLE_SEARCH_URL = "https://www.google.com/cse/publicurl?";

    //api key
    final private String API_KEY = "AIzaSyCfl_jX6c8XLLvrcUGHhFZWIR9iUFcE6AQ";
    //custom search engine ID
    final private String SEARCH_ENGINE_ID = "011717935562844990226:puxg1aixykc";
    //public search engine ID
    //final private String SEARCH_ENGINE_ID = "009462381166450434430:awjijlwzhjs";

    final private String FINAL_URL= GOOGLE_SEARCH_URL + "key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID;

    public GoogleSearch(){
        System.out.println("Search ready");

        String searchKeyWord = "NWR";
        List<Result> resultList = getSearchResult(searchKeyWord);
        if(resultList != null && resultList.size() > 0){
            System.out.println("Found results: "+resultList.size());
            for(Result result: resultList){
                System.out.println(result.getHtmlTitle());
                System.out.println(result.getLink());
                System.out.println(result.getKind());
                //System.out.println(result.getHtmlSnippet());
                System.out.println("----------------------------------------");
            }
        }
    }


    public List<Result> getSearchResult(String keyword) {
        // Set up the HTTP transport and JSON factory
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();
        //HttpRequestInitializer initializer = (HttpRequestInitializer)new CommonGoogleClientRequestInitializer(API_KEY);
        Customsearch customsearch = new Customsearch(httpTransport, jsonFactory, null);

        List<Result> resultList = null;
        try {
            Customsearch.Cse.List list = customsearch.cse().list(keyword);
            list.setKey(API_KEY);
            list.setCx(SEARCH_ENGINE_ID);
            list.setHl("cs");
            list.setSort("date:r:20070301:20100430");
            list.setSiteSearch("ihned.cz");

            //num results per page
            list.setNum(10L);

            //for pagination
            //list.setStart(0L);

            Search results = list.execute();
            resultList = results.getItems();

        } catch (Exception e) {
            e.printStackTrace();
        }


        return resultList;

    }

}
