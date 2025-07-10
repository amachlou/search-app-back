package com.amachlou.search_app_back.web;

import com.amachlou.search_app_back.services.ProductSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sync")
public class SyncController {

    private ProductSyncService syncService;

    public SyncController(ProductSyncService syncService) {
        this.syncService = syncService;
    }

    @GetMapping
    public String sync() {
        syncService.syncDatabaseToElastic();
        return "*****************************************" +
               "***************** Synced! ***************" +
               "*****************************************";
    }
}

