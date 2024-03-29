package tsajf.tailwindblog.utils;

public class StringUtils {

    public static String excerpt(String content) {
        String[] words = content.split("\\s+");
        StringBuilder limitedContent = new StringBuilder();
        for (int i = 0; i < Math.min(words.length, 30); i++) {
            limitedContent.append(words[i]).append(" ");
        }
        return limitedContent.toString().trim() + "...";
    }

}
