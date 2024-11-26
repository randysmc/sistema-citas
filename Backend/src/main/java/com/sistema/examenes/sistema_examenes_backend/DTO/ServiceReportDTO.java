package com.sistema.examenes.sistema_examenes_backend.DTO;

public class ServiceReportDTO {

    private String serviceName;
    private Long usageCount;

    // Constructor
    public ServiceReportDTO(String serviceName, Long usageCount) {
        this.serviceName = serviceName;
        this.usageCount = usageCount;
    }

    // Getters y setters
    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Long usageCount) {
        this.usageCount = usageCount;
    }
}
