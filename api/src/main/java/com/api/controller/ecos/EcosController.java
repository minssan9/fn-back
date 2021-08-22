package com.api.controller.ecos;

import com.core.ecos.domain.EcosData;
import com.core.ecos.domain.EcosSchema;
import com.core.ecos.repo.EcosDataRepo;
import com.core.ecos.repo.EcosSchemaRepo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "ecos data source example", description = "This controller is for the test")
@RestController("ecos")
class EcosController {

    @Autowired
    private EcosSchemaRepo ecosSchemaRepo;
    @Autowired
    private EcosDataRepo ecosDataRepo;


    @GetMapping("schema")
    private ResponseEntity getSchema(Pageable pageable  ) {

        Page<EcosSchema> ecosSchemas = ecosSchemaRepo.findAll(pageable);
        return ResponseEntity
                .ok()
            .body(ecosSchemas );
    }

    @Operation(summary = "Get Data by Date",
        description = "Get data index from by using period",
        responses = {
            @ApiResponse(responseCode = "200",
                description = "User's information",
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EcosData.class))),
            @ApiResponse(responseCode = "404", description = "ecos not found",
                content = @Content(schema = @Schema(format = "")))
        }
    )
    @GetMapping("data/{code}/{option1}/{startDate}/{endDate}")
    private ResponseEntity getData(@PathVariable String code, @PathVariable String option1, @PathVariable String startDate, @PathVariable String endDate, Pageable pageable) {
//        KrBankRequest krBankRequest = new KrBankRequest("","","","","","" ,"DD",startPos, endPos);
        Page<EcosData> krBankDatas = ecosDataRepo.findByStatCodeAndItemCode1AndTimeBetween(code, option1, startDate, endDate, pageable);

        return ResponseEntity
            .ok()
            .body(krBankDatas);
    }
//    @GetMapping("/{date}")
//    private  getTemplateById(@PathVariable date: String): ResponseEntity<Any> {
//        return ResponseEntity
//                .ok()
//                .body(apiService.getData(date))
//    }
//
//    @GetMapping("")
//    private  getTemplateByName(@RequestParam(value = "name") name: String): ResponseEntity<Any?> {
//        return ResponseEntity
//                .ok()
//                .body(apiService.getTemplateByName(name))
//    }
//
//    @PostMapping("")
//    private  postTemplate(@RequestBody templateModel: TemplateModel): ResponseEntity<Any> {
//        apiService.saveTemplate(templateModel)
//        return ResponseEntity
//                .ok()
//                .body(true)
//    }
}
