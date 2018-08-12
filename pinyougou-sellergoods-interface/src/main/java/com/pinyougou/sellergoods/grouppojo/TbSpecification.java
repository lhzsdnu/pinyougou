package com.pinyougou.sellergoods.grouppojo;

import com.pinyougou.entity.Specification;
import com.pinyougou.entity.SpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合实体类
 */
public class TbSpecification implements Serializable {

    private Specification specification;
    private List<SpecificationOption> specificationOptionList;

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }
}
