package com.firefly.cloud.web.controller;

import com.firefly.cloud.service.dto.StorageDirDto;
import com.firefly.cloud.service.vo.StorageDirVo;
import com.firefly.cloud.service.StorageDirService;
import com.firefly.common.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/storage/dir")
@Validated
public class StorageDirController {
    
    @Autowired
    private StorageDirService storageDirService;
    
    @PostMapping
    public HttpResponse<Boolean> createDir(@RequestBody @Valid StorageDirDto storageDirDto) {
        boolean success = storageDirService.createDir(storageDirDto);
        return HttpResponse.success(success);
    }
    
    @PutMapping
    public HttpResponse<Boolean> updateDir(@RequestBody @Valid StorageDirDto storageDirDto) {
        boolean success = storageDirService.updateDir(storageDirDto);
        return HttpResponse.success(success);
    }
    
    @DeleteMapping("/{id}")
    public HttpResponse<Boolean> deleteDir(@PathVariable Long id) {
        boolean success = storageDirService.deleteDir(id);
        return HttpResponse.success(success);
    }
    
    @GetMapping("/{id}")
    public HttpResponse<StorageDirVo> getDir(@PathVariable Long id) {
        StorageDirVo dirVo = storageDirService.getDir(id);
        return HttpResponse.success(dirVo);
    }
    
    @GetMapping("/user/{userId}")
    public HttpResponse<List<StorageDirVo>> getDirsByUserId(@PathVariable Long userId) {
        List<StorageDirVo> dirs = storageDirService.getDirsByUserId(userId);
        return HttpResponse.success(dirs);
    }
} 