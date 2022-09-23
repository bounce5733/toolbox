package com.nbcb.toolbox.project.service;

import com.nbcb.toolbox.project.domain.Process;
import com.nbcb.toolbox.project.domain.SubProject;
import com.nbcb.toolbox.project.repository.ProcessRepository;
import com.nbcb.toolbox.project.repository.SubProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/**
 * ProjectProcessService
 *
 * @author: yh.jiang
 * @time: 2022/9/22 17:13
 */
@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private SubProjectRepository subProjectRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public void save(Process process) {
        // 新增则修改子项阶段
        if (null == process.getId()) {
            SubProject subProject = subProjectRepository.findById(process.getSubProjectId()).get();
            subProject.setPhase(process.getPhase());
            subProjectRepository.save(subProject);
        }
        processRepository.save(process);
    }
}
