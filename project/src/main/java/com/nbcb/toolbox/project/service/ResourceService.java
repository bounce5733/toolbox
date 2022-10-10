package com.nbcb.toolbox.project.service;

import com.nbcb.toolbox.project.domain.Resource;
import com.nbcb.toolbox.project.domain.ResourceHis;
import com.nbcb.toolbox.project.repository.ResourceHisRepository;
import com.nbcb.toolbox.project.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * ResourceService
 *
 * @author: yh.jiang
 * @time: 2022/10/9 09:35
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceHisRepository resourceHisRepository;

    /**
     * 释放资源，记录资源
     *
     * @param id
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void release(int id) {
        Resource resource = resourceRepository.findById(id).get();
        resourceHisRepository.save(ResourceHis.builder().personnelId(resource.getPersonnelId())
                .subProjectId(resource.getSubProjectId()).currentRation(resource.getCurrentRation())
                .startDate(resource.getStartDate()).endDate(resource.getEndDate()).build());
        resourceRepository.deleteById(id);
    }
}
