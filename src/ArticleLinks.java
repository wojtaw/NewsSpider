/**
 * Created by wojtaw on 05/03/16.
 */
public class ArticleLinks implements java.io.Serializable{
    private String urlLink;
    private String searchKeyword;
    private String title;
    private boolean isPaid;

    public ArticleLinks(String urlLink, String searchKeyword){
        this.urlLink = urlLink;
        this.searchKeyword = searchKeyword;
    }

    public ArticleLinks(String urlLink, String searchKeyword, String title){
        this.title = title;
        this.urlLink = urlLink;
        this.searchKeyword = searchKeyword;
    }

    public String getUrlLink() {
        return urlLink;
    }

    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
