package org.sjsu.cmpe.beans;

import java.io.Serializable;

public class ZillowPropertyDetailStage implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 2547367040680886415L;
    private long zpid;
    private long pageViewCount;
    private int imageCount;
    private String homeDescription;

    /**
     * @return the zpid
     */
    public long getZpid() {
        return zpid;
    }

    /**
     * @param zpid
     *            the zpid to set
     */
    public void setZpid(long zpid) {
        this.zpid = zpid;
    }

    /**
     * @return the pageViewCount
     */
    public long getPageViewCount() {
        return pageViewCount;
    }

    /**
     * @param pageViewCount
     *            the pageViewCount to set
     */
    public void setPageViewCount(long pageViewCount) {
        this.pageViewCount = pageViewCount;
    }

    /**
     * @return the imageCount
     */
    public int getImageCount() {
        return imageCount;
    }

    /**
     * @param imageCount
     *            the imageCount to set
     */
    public void setImageCount(int imageCount) {
        this.imageCount = imageCount;
    }

    /**
     * @return the homeDescription
     */
    public String getHomeDescription() {
        return homeDescription;
    }

    /**
     * @param homeDescription
     *            the homeDescription to set
     */
    public void setHomeDescription(String homeDescription) {
        this.homeDescription = homeDescription;
    }

}
