package com.innogrid.tabcloudit.terraform.service;

import com.innogrid.tabcloudit.terraform.api.RestfulClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationsService {
    private final RestfulClient restfulClient;
    @Value("${terraform.cloud.api.url}")
    private String url;
    @Value("${terraform.cloud.api.token}")
    private String token;

    public JSONObject getListOrganizations(
//            String url,
//            String token
    ) {
        return restfulClient.JsonApiWithGet(url, "organizations", token);
    }

    public JSONObject showAnOrganization(
            String orgName
    ) {
        return restfulClient.JsonApiWithGet(url, "organizations/" + orgName, token);
    }

    public JSONObject createWorkspace(
            String orgId,
            JSONObject payload
    ) {
        return restfulClient.JsonApiWithPayload(url, "organizations/" + orgId + "/workspaces", token, payload);
    }
}
