import java.util.HashMap;

public class Static {
    public static String[] type = {"DOC", "PDF", "XLS", "PPT", "DV", "PV", "OV", "Email", "OCR", "Cloud", "Barcode","SprSheet"};
    public static String[] names={"Amy","Andy","Annika","Abel","Nina","Lisa","Kylie","William","Simple"};
    public static HashMap<String, String> map;

    static {
        map = new HashMap<>();
        String[] type = {"DOC", "PDF", "XLS", "PPT", "DV", "PV", "OV", "Email", "OCR", "Cloud", "Barcode","SprSheet"};
        String[] typeStr = {"SPIREDOC", "SPIREPDF", "SPIREXLS", "SPIREPPT", "SPIREDOCVIEWER", "SPIREPDFVIEWER",
                "SPIREOFFICEVIEWER", "SPIREEMAIL", "SPIREOCR", "SPIRECLOUD", "SPIREBARCODE","SPREADSHEET"};
        for (int i = 0; i < type.length; i++) {
            map.put(type[i], typeStr[i]);
        }
    }

}
