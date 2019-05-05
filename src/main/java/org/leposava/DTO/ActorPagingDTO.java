package org.leposava.DTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class ActorPagingDTO {

    List<ActorDTO> actorDTOList;
    private long totalRecord;
    private int pageSize;
    private double pages;
    private double pageOn;
    private double noOfPages;
    private String pageRatio;
    private String nextPage;
    private String currentPage;
    private String prevPage;

    public List<ActorDTO> getActorDTOList() {
        return actorDTOList;
    }

    public void setActorDTOList(List<ActorDTO> actorDTOList) {
        this.actorDTOList = actorDTOList;
    }

    public long getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(long totalRecord) {
        this.totalRecord = totalRecord;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public double getPages() {
        return pages;
    }

    public void setPages(double pages) {
        this.pages = pages;
    }

    public double getPageOn() {
        return pageOn;
    }

    public void setPageOn(double pageOn) {
        this.pageOn = pageOn;
    }

    public double getNoOfPages() {
        return noOfPages;
    }

    public void setNoOfPages(double noOfPages) {
        this.noOfPages = noOfPages;
    }

    public String getPageRatio() {
        return pageRatio;
    }

    public void setPageRatio(String pageRatio) {
        this.pageRatio = pageRatio;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getPrevPage() {
        return prevPage;
    }

    public void setPrevPage(String prevPage) {
        this.prevPage = prevPage;
    }
}
