package com.haier.haikehui.entity.duodu;

/**
 * author : Wislie
 * e-mail : 254457234@qq.comn
 * date   : 2020/8/25 3:19 PM
 * desc   : 栋
 * version: 1.0
 */
public class BuildingBean {
    private Long buildingId;
    private String buildingName;
    private String buildingNo;
    private Integer depId;
    private String depName;
    private String updateTime;
    public void setBuildingId(Long buildingId) {
        this.buildingId = buildingId;
    }
    public Long getBuildingId() {
        return buildingId;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }
    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }
    public String getBuildingNo() {
        return buildingNo;
    }

    public void setDepId(Integer depId) {
        this.depId = depId;
    }
    public Integer getDepId() {
        return depId;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
    public String getDepName() {
        return depName;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getUpdateTime() {
        return updateTime;
    }
}
