package com.innogrid.tabcloudit.terraform.controller;

import com.innogrid.tabcloudit.terraform.service.OrganizationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tf-cloud/organizations")
public class OrganizationsController {

    private final OrganizationsService service;

    @Operation(summary = "Get list of Organizations", description = "전체 오거너제이션 리스트를 조회합니다.", tags = "Organizations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/")
    public JSONObject getListOrganizations() {
        return service.getListOrganizations();
    }

    @Operation(
            summary = "Get an Organization", description = "이름으로 특정 오거너제이션을 조회합니다.", tags = "Organizations",
            responses = @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json"))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/{orgName}")
    public JSONObject showAnOrganization(
            @Parameter(description = "Organization Name", required = true, example = "example-org-6e5038") @PathVariable String orgName
    ) {
        return service.showAnOrganization(orgName);
    }

    @Operation(summary = "Create an Workspace",
            description = "워크스페이스를 생성합니다.  \n" +
            "RequestBody의 Json 데이터 예제는 아래와 같습니다.  \n  \n" +
            "<pre><b>{" +
                    "   \"data\": {" +
                    "       \"attributes\": {  \n" +
                    "           \"name\": \"example-workspace\",  \n" +
                    "           \"resource-count\": 0  \n" +
                    "       },  \n" +
                    "       \"type\": \"workspaces\"  \n" +
                    "   }" +
                    "}</b></pre>", tags = "Workspaces",
            responses = @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json"))
    )
    @PostMapping("/{orgId}/workspaces")
    public JSONObject createWorkspace(
            @Parameter(description = "Organization Id", required = true, example = "example-org-6e5038") @PathVariable String orgId,
            @Parameter(description = "Organization Payload", required = true) @RequestBody JSONObject payload
    ) {
        return service.createWorkspace(orgId, payload);
    }

}