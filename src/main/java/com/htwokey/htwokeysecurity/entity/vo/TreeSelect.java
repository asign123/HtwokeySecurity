package com.htwokey.htwokeysecurity.entity.vo;

import com.htwokey.htwokeysecurity.entity.AdminMenu;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hchbo
 * @date 2023/4/15 21:36
 */
public class TreeSelect {

    /** 节点ID */
    private String value;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    private List<TreeSelect> children;

    public TreeSelect() {}

    public TreeSelect(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public TreeSelect(String value, String label, List<TreeSelect> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    public TreeSelect(AdminMenu menu)
    {
        this.value = menu.getId().toString();
        this.label = menu.getTitle();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<TreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSelect> children) {
        this.children = children;
    }
}
