package com.app.entity;

public interface BaseAttributeIf {

    public Integer getLevel();

    public void setLevel(Integer level);

    public Double getHp();

    public void setHp(Double hp);

    public Double getMp();

    public void setMp(Double mp);

    public Double getBasePhysicalDamageOnHit();

    public void setBasePhysicalDamageOnHit(Double basePhysicalDamageOnHit);

    public Double getBaseMagicDamageOnHit();

    public void setBaseMagicDamageOnHit(Double baseMagicDamageOnHit);

}
