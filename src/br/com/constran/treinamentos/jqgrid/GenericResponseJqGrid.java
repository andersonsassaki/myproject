package br.com.constran.treinamentos.jqgrid;

import java.util.ArrayList;
import java.util.List;

public class GenericResponseJqGrid {

    private Boolean success;
    private List<String> message;

    public GenericResponseJqGrid() {
        message = new ArrayList<String>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.add(message);
    }

    public static GenericResponseJqGrid processResponse(boolean success) {

    	GenericResponseJqGrid response = new GenericResponseJqGrid();
        response.setSuccess(success);

        // Check if successful
        if ( success == true ) {
            response.setMessage("Operação realizada com sucesso!");
        } else {
            response.setMessage("Falha na operação!");
        }

        return response;

    }
}
