package com.ader1y.i18n.expend;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author zhan yan
 * @date 2023/10/24
 **/
@FeignClient(name = "i18n-center", contextId = "i18nCenterClient")
public interface I18nCenterClient {


    @PostMapping("/resource/pull")
    Map<String, String> pull(@RequestBody List<String> keys);

    @GetMapping("/resource/pull-all")
    Map<String, String> pullAll(@RequestParam String i18nKey);


}
