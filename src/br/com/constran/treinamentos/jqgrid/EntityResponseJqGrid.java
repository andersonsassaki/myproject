package br.com.constran.treinamentos.jqgrid;

import java.util.List;

public class EntityResponseJqGrid {

    private String page;
    private String total;
    private String records;

    private List rows;

    public EntityResponseJqGrid() {

    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getRecords() {
        return records;
    }

    public void setRecords(String records) {
        this.records = records;
    }

    public List getRows() {
        return rows;
    }


    public void setRows(List rows) {
        this.rows = rows;

    }
}
