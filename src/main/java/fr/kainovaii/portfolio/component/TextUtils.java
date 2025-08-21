package fr.kainovaii.portfolio.component;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component("textUtils")
public class TextUtils {
    public String truncateWords(String text, int maxWords)
    {
        if (text == null || text.isEmpty()) return text;
        String[] words = text.split("\\s+");
        if (words.length <= maxWords) return text;
        return String.join(" ", Arrays.copyOfRange(words, 0, maxWords)) + "...";
    }
}
