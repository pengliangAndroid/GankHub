package com.wstrong.mygank.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public class GankSearchDataResp implements Serializable {


    /**
     * count : 10
     * error : false
     * results : []
     */

    private int count;
    private boolean error;
    private List<GankSearchData> results;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankSearchData> getResults() {
        return results;
    }

    public void setResults(List<GankSearchData> results) {
        this.results = results;
    }
}
