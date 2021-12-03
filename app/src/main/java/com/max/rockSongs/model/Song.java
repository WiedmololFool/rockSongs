package com.max.rockSongs.model;

public class Song
{
    long id;
    private String name;
    private Album album;
    private String description;
    public Song(long id, String name, Album album, String description)
    {
        this.id = id;
        this.name = name;
        this.album = album;
        this.description = description;
    }
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Album getAlbum()
    {
        return album;
    }

    public void setAlbum(Album album)
    {
        this.album = album;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
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
        return "Song{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", album=" + album +
                ", description='" + description + '\'' +
                '}';
    }
}
