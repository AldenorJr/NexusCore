package br.com.nexus.main.core.DTO;

public class VipsDTO {

    private String name;
    private String prefix;
    private String color;
    private String priority;

    public VipsDTO(String name, String prefix, String color, String priority) {
        this.name = name;
        this.prefix = prefix;
        this.color = color;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
