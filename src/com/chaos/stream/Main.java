package com.chaos.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Anatoly Chernysh
 */
public class Main {

    class Article {

        private String title;

        private List<String> tags = new ArrayList<String>();

        public List<String> getTags() {
            return tags;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String toString() {
            return "Article{" +
                    "title='" + title + '\'' +
                    ", tags=" + tags +
                    '}';
        }
    }

    private List<Article> articles = new ArrayList<>();

    public Optional<Article> getFirstJavaArticle() {
        return articles.stream()
                .filter(article -> article.getTags().contains("Java"))
                .findFirst();
    }

    public Optional<Article> getFirst(Predicate<Article> predicate) {
        return articles.stream()
                .filter(predicate)
                .findFirst();
    }

    public Article fetchLatestArticle() {
        return articles.get(articles.size() - 1);
    }

    public void testStreamFiltering() {
        Article article1 = new Article();
        article1.setTitle("Comparison C# vs PHP");
        article1.getTags().add("C#");
        article1.getTags().add("PHP");
        articles.add(article1);

        Article article2 = new Article();
        article2.setTitle("JavaScript in action");
        article2.getTags().add("JavaScript");
        articles.add(article2);

        Article article3 = new Article();
        article3.setTitle("Comparison C++ vs Java");
        article3.getTags().add("C++");
        article3.getTags().add("Java");
        articles.add(article3);

        System.out.println("Find first java article or fetch last if not found: " + getFirstJavaArticle().orElseGet(this::fetchLatestArticle));

        System.out.println("Find first java article and apply function to result: " + getFirstJavaArticle().map(Article::getTitle));

        System.out.println("Find first java article with predicate: " + getFirst(article -> article.getTags().contains("Java")));

        Function<String, Predicate<Article>> basedOnTag = tag -> article -> article.getTags().contains(tag);
        System.out.println("Find first PHP article with function predicate: " + getFirst(basedOnTag.apply("PHP")));
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.testStreamFiltering();
    }
}
