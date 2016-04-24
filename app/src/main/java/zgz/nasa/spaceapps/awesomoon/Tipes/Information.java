package zgz.nasa.spaceapps.awesomoon.Tipes;

/**
 * Created by dani on 23/04/16.
 */
public class Information {
    private int idInfo;
    private String URIImageInfo;
    private String titleInfo;
    private String bodyInfo;

    public Information(int idInfo,String URIImageInfo, String titleInfo, String bodyInfo){
        this.idInfo = idInfo;
        this.URIImageInfo = URIImageInfo;
        this.titleInfo = titleInfo;
        this.bodyInfo = bodyInfo;
    }

    public int getIdInfo(){
        return idInfo;
    }

    public String getURIImageInfo(){
        return URIImageInfo;
    }

    public String getTitleInfo(){
        return titleInfo;
    }

    public String getBodyInfo(){
        return bodyInfo;
    }

}
