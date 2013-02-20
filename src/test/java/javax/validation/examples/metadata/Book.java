/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009-2013, Red Hat, Inc. and/or its affiliates, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package javax.validation.examples.metadata;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * @author Emmanuel Barned
 *  @author Gunnar Morling
 */
public class Book {

    public interface FirstLevelCheck {}
    public interface SecondLevelCheck {}
    
    private String title;
    private String description;

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
