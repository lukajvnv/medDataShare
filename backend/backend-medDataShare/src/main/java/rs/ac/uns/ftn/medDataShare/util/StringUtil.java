package rs.ac.uns.ftn.medDataShare.util;

public class StringUtil {

    public static String getIdPart(String id){
        return id.substring(id.lastIndexOf("/") + 1);
    }
}
