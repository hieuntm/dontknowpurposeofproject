package com.app.entity;

public class BaseAttribute implements BaseAttributeIf {
    Integer level;
    Double hp;
    Double mp;
    Double basePhysicalDamageOnHit;
    Double baseMagicDamageOnHit;

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public void setLevel(Integer level) {
        this.level = level;
    }

    @Override
    public Double getHp() {
        return hp;
    }

    @Override
    public void setHp(Double hp) {
        this.hp = hp;
    }

    @Override
    public Double getMp() {
        return mp;
    }

    @Override
    public void setMp(Double mp) {
        this.mp = mp;
    }

    @Override
    public Double getBasePhysicalDamageOnHit() {
        return basePhysicalDamageOnHit;
    }

    @Override
    public void setBasePhysicalDamageOnHit(Double basePhysicalDamageOnHit) {
        this.basePhysicalDamageOnHit = basePhysicalDamageOnHit;
    }

    @Override
    public Double getBaseMagicDamageOnHit() {
        return baseMagicDamageOnHit;
    }

    @Override
    public void setBaseMagicDamageOnHit(Double baseMagicDamageOnHit) {
        this.baseMagicDamageOnHit = baseMagicDamageOnHit;
    }

}
