package noah.buildermall.serializable.bean;

import java.io.Serializable;

public class ItemHtmlContentSerialBean implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 2584377710840704653L;
    public String htmlContent;
    public int item_id;
    public int s_id;

    public String getHtmlContent() {
        return htmlContent;
    }
    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
    public int getItem_id() {
        return item_id;
    }
    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
    public int getS_id() {
        return s_id;
    }
    public void setS_id(int s_id) {
        this.s_id = s_id;
    }


}
