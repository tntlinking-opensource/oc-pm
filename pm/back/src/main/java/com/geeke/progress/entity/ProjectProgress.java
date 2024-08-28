package com.geeke.progress.entity;

import lombok.Data;

@Data
public class ProjectProgress {

    private String projectId;

    private String milestoneId;

    private String milestoneName;

    private Integer progressRate;

    private Double milestoneProgress;

}
