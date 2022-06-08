package com.app.entity.characters;

import com.app.constant.CommonConstant;
import com.app.constant.MageConstant;
import com.app.entity.BaseAttribute;

public class Mage extends BaseAttribute {

    public Mage() {
        this.setLevel(CommonConstant.DEFAULT_LEVEL);
        this.setHp(MageConstant.HP);
        this.setMp(MageConstant.MP);
    }


}
