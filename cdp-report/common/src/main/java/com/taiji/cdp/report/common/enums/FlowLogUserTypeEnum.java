package com.taiji.cdp.report.common.enums;

public enum FlowLogUserTypeEnum {
    AUTONOMOUS_REGION("0","自治区"),
    ALLIANCE_CITY("1","盟市");

    private String userType;
    private String desc;

    FlowLogUserTypeEnum(String userType, String desc) {
        this.userType = userType;
        this.desc = desc;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FlowLogUserTypeEnum userTypeOf(String userType) {
        switch (userType) {
            case "1":
                return FlowLogUserTypeEnum.AUTONOMOUS_REGION;
            case "0":
                return FlowLogUserTypeEnum.ALLIANCE_CITY;
            default:
                return null;
        }
    }

}
