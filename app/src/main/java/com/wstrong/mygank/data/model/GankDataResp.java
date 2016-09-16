package com.wstrong.mygank.data.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by pengl on 2016/9/12.
 */
public class GankDataResp implements Serializable {


    /**
     * count : 10
     * error : false
     * results : []
     */

    private boolean error;
    private List<GankData> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<GankData> getResults() {
        return results;
    }

    public void setResults(List<GankData> results) {
        this.results = results;
    }
}
