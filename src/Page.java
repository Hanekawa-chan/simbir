import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Page {
    public String content;

    public Page(String url) {
        content = getUrl(url);
        if(content==null) {
            System.out.println("Произошла ошибка. Неправильный URL или нет соединения.");
        }
    }

    public String getUrl(String url) {
        URLConnection connection;
        String content = null;
        try {
            connection = new URL(url).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
            scanner.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return content;
    }

    public void delete() {
        StringBuilder sb = new StringBuilder(content);
        int i,l;
        i = 0;
        while(i < sb.length()) {
            if (sb.substring(i,i + 7).equals("<script")) {
                l = i;
                while(!sb.substring(l, l + 9).equals("</script>")) {
                    l++;
                }
                l += 9;
                sb.delete(i, l);
            }
            else {
                i++;
            }
            if (i + 7 > sb.length()) {
                break;
            }
        }
        i = 0;
        while(i < sb.length()) {
            if (sb.substring(i,i + 6).equals("<style")) {
                l = i;
                while(!sb.substring(l, l + 8).equals("</style>")) {
                    l++;
                }
                l += 8;
                sb.delete(i, l);
            }
            else {
                i++;
            }
            if (i + 6 > sb.length()) {
                break;
            }
        }
        i = 0;
        while(i < sb.length()) {
            if (sb.charAt(i)=='<') {
                while(sb.charAt(i)!='>') {
                    sb.deleteCharAt(i);
                }
                sb.deleteCharAt(i);
            }
            else {
                i++;
            }
        }
        content = sb.toString();
    }

    public void splitAndCount() {
        String[] words = content.split("\\s*(\\s|,|!|\\.)\\s*");
        for(int i = 0; i < words.length; i++) {
            System.out.println(words[i]);
        }
        int k;
        StringBuilder sb;
        StringBuffer sbuf = new StringBuffer();
        for (int i = 0; i < words.length; i++) {
            sb = new StringBuilder(words[i]);
            while (sb.indexOf("\"") != -1) {
                sb.deleteCharAt(sb.indexOf("\""));
            }
            while (sb.indexOf("«") != -1) {
                sb.deleteCharAt(sb.indexOf("«"));
            }
            while (sb.indexOf("»") != -1) {
                sb.deleteCharAt(sb.indexOf("»"));
            }
            while (sb.indexOf("(") != -1) {
                sb.deleteCharAt(sb.indexOf("("));
            }
            while (sb.indexOf(")") != -1) {
                sb.deleteCharAt(sb.indexOf(")"));
            }
            while (sb.indexOf(";") != -1) {
                sb.deleteCharAt(sb.indexOf(";"));
            }
            while (sb.indexOf(":") != -1) {
                sb.deleteCharAt(sb.indexOf(":"));
            }
            while (sb.indexOf("?") != -1) {
                sb.deleteCharAt(sb.indexOf("?"));
            }
            while (sb.indexOf("-") != -1) {
                sb.deleteCharAt(sb.indexOf("-"));
            }
            while (sb.indexOf("-") != -1) {
                sb.deleteCharAt(sb.indexOf("-"));
            }
            while (sb.indexOf("–") != -1) {
                sb.deleteCharAt(sb.indexOf("–"));
            }
            while (sb.indexOf("—") != -1) {
                sb.deleteCharAt(sb.indexOf("—"));
            }
            while (sb.indexOf("‑") != -1) {
                sb.deleteCharAt(sb.indexOf("‑"));
            }
            while (sb.indexOf("/") != -1) {
                sb.deleteCharAt(sb.indexOf("/"));
            }
            while (sb.indexOf("|") != -1) {
                sb.deleteCharAt(sb.indexOf("|"));
            }
            while (sb.indexOf(" ") != -1) {
                sb.deleteCharAt(sb.indexOf(" "));
            }
            Matcher matcher = Pattern.compile("&#+\\d").matcher(sb.toString());
            String trap = null;
            while (matcher.find()) {
                trap = sb.substring(matcher.start(), matcher.end());
            }
            try {
                if (sb.indexOf(trap) != -1) {
                    sb.delete(sb.indexOf(trap), sb.indexOf(trap) + trap.length());
                }
            }
            catch(Exception e) {
            }
            words[i] = sb.toString();
        }
        sbuf.append(words[0]);
        for(int i = 1; i < words.length; i++) {
            sbuf.append(" " + words[i]);
        }
        sb = new StringBuilder(sbuf.toString());
        content = sb.toString().toUpperCase();
        showAsList();
    }

    public void show() {
        System.out.println(content);
    }

    public void showAsList() {
        String[] words = content.split(" ");
        List<String> list = new LinkedList<String>(Arrays.asList(words));
        int k;
        Map<String, Integer> map = new HashMap<>();
        for(int i = 0; i < list.size(); i++) {
            k = 0;
            if(!list.get(i).isEmpty()) {
                map.put(list.get(i), k);
                for (int j = 0; j < words.length; j++) {
                    if (list.get(i).equals(words[j])) {
                        k += 1;
                        map.replace(list.get(i), k);
                    }
                }
            }
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }
}
