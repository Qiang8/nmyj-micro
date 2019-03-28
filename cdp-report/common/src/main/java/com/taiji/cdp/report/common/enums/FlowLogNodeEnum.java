package com.taiji.cdp.report.common.enums;

public enum FlowLogNodeEnum {
    REPORT("report","信息上报"),
    JUDGE("judge","值班人员研判"),
    CENTER_AUDIT("center_audit","监管中心领导/处领导审批"),
    LEADER_AUDIT("leader_audit","办领导审批"),
    CMD("cmd","值班人员处置"),
    CMD_END("cmd_end","值班人员完成"),
    IGNORE("ignore","值班人员删除");

    private String flowNode;
    private String desc;

    FlowLogNodeEnum(String flowNode, String desc) {
        this.flowNode = flowNode;
        this.desc = desc;
    }

    public String getFlowNode() {
        return flowNode;
    }

    public void setFlowNode(String flowNode) {
        this.flowNode = flowNode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static FlowLogNodeEnum flowNodeOf(String flowNode) {
        switch (flowNode) {
            case "report":
                return FlowLogNodeEnum.REPORT;
            case "judge":
                return FlowLogNodeEnum.JUDGE;
            case "center_audit":
                return FlowLogNodeEnum.CENTER_AUDIT;
            case "leader_audit":
                return FlowLogNodeEnum.LEADER_AUDIT;
            case "cmd":
                return FlowLogNodeEnum.CMD;
            case "cmd_end":
                return FlowLogNodeEnum.CMD_END;
            case "ignore":
                return FlowLogNodeEnum.IGNORE;
            default:
                return null;
        }
    }
}
