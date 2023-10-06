package br.com.nexus.main.core.object;

import br.com.nexus.main.core.launches.spigot.enums.EconomyEnum;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.math.BigDecimal;

@Entity("Tags")
public class TagsModel {

    @Id
    private String ID;
    private String name;
    private String prefix;
    private String permissionUse;
    private BigDecimal price;
    private EconomyEnum economyEnum;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public EconomyEnum getEconomyEnum() {
        return economyEnum;
    }

    public void setEconomyEnum(EconomyEnum economyEnum) {
        this.economyEnum = economyEnum;
    }

}