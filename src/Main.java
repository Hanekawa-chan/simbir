import java.util.*;

public class Main {

    public static void main(String[] args) {
        String url;
        Scanner scanner = new Scanner(System.in);
        url = scanner.next();
        Page page = new Page(url);
        if(page.content!=null) {
            page.delete();
            page.splitAndCount();
        }
    }
}
