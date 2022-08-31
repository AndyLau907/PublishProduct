package utils;

import net.sf.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 代码搬运
 * https://blog.csdn.net/weixin_28979345/article/details/114756650
 */
public class Translator {

    public static String translate(String langFrom, String langTo,
                            String word) throws Exception {
        String url = "https://translate.googleapis.com/translate_a/single?" +
                "client=gtx&" +
                "sl=" + langFrom +
                "&tl=" + langTo +
                "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return parseResult(response.toString());
    }

    private static String parseResult(String inputJson) throws Exception {
        /*
         * inputJson for word ‘hello‘ translated to language Hindi from English-
         * [[["??????","hello",,,1]],,"en"]
         * We have to get ‘?????? ‘ from this json.
         */
        JSONArray jsonArray = JSONArray.fromObject(inputJson);

        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
// JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);
        String result = "";
        for (int i = 0; i < jsonArray2.size(); i++) {
            result += ((JSONArray) jsonArray2.get(i)).get(0).toString();
        }
        return result;

    }

}