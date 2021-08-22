package com.api.controller.ecos;

import com.core.ecos.service.EcosBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/ecosapi")
public
class EcosBatchController {
    @Autowired
    private EcosBatchService ecosBatchService;

    @GetMapping("/schema")
    private ResponseEntity getTemplates() {
        return ResponseEntity.ok().body(ecosBatchService.retrieveSchema());
    }

//    @GetMapping("schema/update")
//    @LogExecutionTime
//    ResponseEntity<> updateSchema()  {
//        KrBankRequest krBankRequest = KrBankRequest("", "", "", "",
//                "", "", 1, 1000);
//        KrBankSchema krBankSchemas = krBankApiService.getSchemaFromAPI(krBankRequest)
//        if (krBankSchemas != null) {
//            krBankApiService.saveSchema(krBankSchemas)
//        }
//
//        return ResponseEntity
//            .ok()
//            .body(krBankSchemas);
//    }
//    @GetMapping("/{date}")
//    private fun getTemplateById(@PathVariable date: String): ResponseEntity<Any> {
//        return ResponseEntity
//                .ok()
//                .body(apiService.getData(date))
//    }
//
//    @GetMapping("")
//    private fun getTemplateByName(@RequestParam(value = "name") name: String): ResponseEntity<Any?> {
//        return ResponseEntity
//                .ok()
//                .body(apiService.getTemplateByName(name))
//    }
//
//    @PostMapping("")
//    private fun postTemplate(@RequestBody templateModel: TemplateModel): ResponseEntity<Any> {
//        apiService.saveTemplate(templateModel)
//        return ResponseEntity
//                .ok()
//                .body(true)
//    }
}
