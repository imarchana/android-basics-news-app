package com.example.android.news;

public class News {

    private String mImageResource;
    private String mTitle;
    private String mAuthor;
    private String mUrl;

    private static final String NO_IMAGE = "NA";

    /**
     * Construct {@link News}
     *
     * @param imageResource
     * @param title
     * @param author
     * @param url
     */
    public News(String imageResource, String title, String author, String url) {
        mImageResource = imageResource;
        mTitle = title;
        mAuthor = author;
        mUrl = url;
    }

    /**
     * Returns the url of the image.
     */
    public String getImageResource() {
        return mImageResource;
    }

    /**
     * Returns the title of the news.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Return the author of the news article.
     */
    public String getAuthor() {
        return mAuthor;
    }

    /**
     * Return the url of the News Article.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Return (Boolean) True if Article has Image, False if article has no image.
     *
     * @return
     */
    public boolean hasImage() {
        return mImageResource != NO_IMAGE;
    }

}
