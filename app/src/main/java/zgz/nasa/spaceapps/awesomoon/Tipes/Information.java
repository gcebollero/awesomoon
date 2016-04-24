package zgz.nasa.spaceapps.awesomoon.Tipes;

/**
 * Created by dani on 23/04/16.
 */
public class Information {
    private String URIImageInfo;
    private String titleInfo;

    public Information(String URIImageInfo, String titleInfo){
        this.URIImageInfo = URIImageInfo;
        this.titleInfo = titleInfo;
    }

    public String getIdImageInfo(){
        return URIImageInfo;
    }

    public String getTitleInfo(){
        return titleInfo;
    }

}
