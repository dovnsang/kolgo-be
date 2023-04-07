package com.dtu.kolgo.service;

import com.dtu.kolgo.dto.request.EnterpriseUpdateRequest;
import com.dtu.kolgo.dto.response.EnterpriseResponse;
import com.dtu.kolgo.dto.response.WebResponse;
import com.dtu.kolgo.model.Enterprise;

import java.util.List;

public interface EnterpriseService {

    void save(Enterprise ent);

    List<EnterpriseResponse> getAll();

    Enterprise getById(int entId);

    EnterpriseResponse getResponseById(int entId);

    WebResponse update(int entId, EnterpriseUpdateRequest request);

    WebResponse delete(int entId);

}
