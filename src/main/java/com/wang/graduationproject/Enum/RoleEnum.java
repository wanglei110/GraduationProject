package com.wang.graduationproject.Enum;

/**
 * @author wanglei
 * 账号角色枚举类
 * */
public enum RoleEnum {

    /**
     * 专业负责人
     * */
    PRO_LEADER(0,"专业负责人"),
    TEACHING_SECRETARY(1,"教学秘书"),
    TEACHERS(2,"教师"),
    ADMIN(3,"管理员");

    /**
     * 角色
     * */
    private Integer role;

    /**
     * 角色名字
     * */
    private String roleName;

    RoleEnum(Integer role,String roleName){
        this.role=role;
        this.roleName=roleName;
    }

    public Integer getRole(){
        return role;
    }

    public String getRoleName(){
        return roleName;
    }

    public static RoleEnum match(int key) {

        RoleEnum result = null;

        for (RoleEnum s : values()) {
            if (s.getRole()==key) {
                result = s;
                break;
            }
        }

        return result;
    }

    public static RoleEnum catchMessage(String msg) {

        RoleEnum result = null;

        for (RoleEnum s : values()) {
            if (s.getRoleName().equals(msg)) {
                result = s;
                break;
            }
        }

        return result;
    }

}
