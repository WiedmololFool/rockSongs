package com.max.rockSongs.model;

public class Album
{   private String name;
    private Author author;
    private int year;


    public Album(String name, Author author, int year)
    {
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public Author getAuthor()
    {
        return author;
    }

    public void setAuthor(Author author)
    {
        this.author = author;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }


    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "Album{" +
                "name='" + name + '\'' +
                ", author=" + author +
                ", year=" + year +
                '}';
    }
}
