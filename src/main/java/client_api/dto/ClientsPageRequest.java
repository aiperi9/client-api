package client_api.dto;

import client_api.entity.Client;
import lombok.Data;

import java.util.List;

@Data
public class ClientsPageRequest {
    private int totalPages;
    private long totalElements;
    private List<Client> list;

    public ClientsPageRequest(List<Client> list, int totalPages, long totalElements) {
        this.list = list;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }
}

