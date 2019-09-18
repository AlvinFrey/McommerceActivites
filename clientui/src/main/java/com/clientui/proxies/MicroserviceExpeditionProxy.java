package com.clientui.proxies;

import com.clientui.beans.ExpeditionBean;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "zuul-server")
@RibbonClient(name = "microservice-expedition")
public interface MicroserviceExpeditionProxy {

    @GetMapping( value = "/microservice-expedition/suivi/{id}")
    ExpeditionBean etatExpedition(@PathVariable("id") int id);

    @PostMapping(value = "/microservice-expedition/suivi/add")
    ResponseEntity<ExpeditionBean> sendExpedition(@RequestBody ExpeditionBean expedition);

    @PutMapping(value = "/microservice-expedition/suivi/update")
    void updateExpedition(@RequestBody ExpeditionBean expedition);

}
