package zgz.nasa.spaceapps.awesomoon.Tipes;

/**
 * Created by dani on 23/04/16.
 */
public class Information {
    private int idImageInfo;
    private String titleInfo;

    public Information(int idImageInfo, String titleInfo){
        this.idImageInfo = idImageInfo;
        this.titleInfo = titleInfo;
    }

    public int getIdImageInfo(){
        return idImageInfo;
    }

    public String getTitleInfo(){
        return titleInfo;
    }

}
