package ar.com.manflack.manflackshops.domain.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import ar.com.manflack.manflackshops.app.dto.ClientDTO;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepository
{
    private static final HashMap<String, ClientDTO> localStorage = new HashMap<>();

    public ClientDTO saveAndFlush(ClientDTO client)
    {
        localStorage.put(client.getDocumentType() + client.getDocumentNumber(), client);
        return client;
    }

    public ClientDTO get(String documentType, String documentNumber)
    {
        return localStorage.get(documentType + documentNumber);
    }

    public List<ClientDTO> getAll()
    {
        return new ArrayList<>(localStorage.values());
    }
}
