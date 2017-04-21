/*
 * Bean Validation API
 *
 * License: Apache License, Version 2.0
 * See the license.txt file in the root directory or <http://www.apache.org/licenses/LICENSE-2.0>.
 */
package javax.validation.examples.metadata;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * @author Emmanuel Bernard
 * @author Gunnar Morling
 */
public class Book {

    public interface FirstLevelCheck {}
    public interface SecondLevelCheck {}

    public static class Chapter {
        //[...]
    }

    private String title;
    private String description;
    private Map<@Valid Chapter, @Size(min=1) List<@NotBlank String>> keywordsPerChapter;

    @Valid
    @NotNull
    private Author author;

    @Valid
    public Book(
            String title,
            @Size(max=30) String description,
            @Valid
            @ConvertGroup(from=Default.class, to=SecondLevelCheck.class)
            Author author) {
        //[...]
    }

    public Book() { //[...]
    }

    @NotEmpty(groups={FirstLevelCheck.class, Default.class})
    @Size(max=30)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setAuthor(String description) {
        this.description = description;
    }

    @ValidInterval(startParameter=1, endParameter=2)
    public void addChapter(String title, int startPage, int endPage) {
        //[...]
    }
}
