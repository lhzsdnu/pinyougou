package com.pinyougou.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Seller;
import com.pinyougou.mapper.SellerMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 栾宏志
 * @since 2018-08-08
 */
@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class SellerServiceImpl extends ServiceImpl<SellerMapper, Seller> implements SellerService {

    @Autowired
    private SellerMapper sellerMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Seller> findAll() {
        return sellerMapper.selectList(new EntityWrapper<Seller>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        //分页查询
        Wrapper<Seller> entity = new EntityWrapper<Seller>();
        Page<Seller> page = new Page<Seller>(pageNum, pageSize);

        List<Seller> pageInfoList = sellerMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(sellerMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

    /**
     * 增加
     */
    @Override
    public void add(Seller seller) {
        sellerMapper.insert(seller);
    }


    /**
     * 修改
     */
    @Override
    public void update(Seller seller) {
        sellerMapper.updateById(seller);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Seller findOne(Long id) {
        return sellerMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            sellerMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Seller seller, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Seller> entity = new EntityWrapper<Seller>();

        if (seller != null) {
            if (seller.getSellerId() != null && seller.getSellerId().length() > 0) {
                entity.like("seller_id", seller.getSellerId());
                //criteria.andSellerIdLike("%" + seller.getSellerId() + "%");
            }
            if (seller.getName() != null && seller.getName().length() > 0) {
                entity.like("name", seller.getName());
                //criteria.andNameLike("%" + seller.getName() + "%");
            }
            if (seller.getNickName() != null && seller.getNickName().length() > 0) {
                entity.like("nick_name", seller.getNickName());
                //criteria.andNickNameLike("%" + seller.getNickName() + "%");
            }
            if (seller.getPassword() != null && seller.getPassword().length() > 0) {
                entity.like("password", seller.getPassword());
                //criteria.andPasswordLike("%" + seller.getPassword() + "%");
            }
            if (seller.getEmail() != null && seller.getEmail().length() > 0) {
                entity.like("email", seller.getEmail());
                //criteria.andEmailLike("%" + seller.getEmail() + "%");
            }
            if (seller.getMobile() != null && seller.getMobile().length() > 0) {
                entity.like("mobile", seller.getMobile());
                //criteria.andMobileLike("%" + seller.getMobile() + "%");
            }
            if (seller.getTelephone() != null && seller.getTelephone().length() > 0) {
                entity.like("telephone", seller.getTelephone());
                //criteria.andTelephoneLike("%" + seller.getTelephone() + "%");
            }
            if (seller.getStatus() != null && seller.getStatus().length() > 0) {
                entity.like("status", seller.getStatus());
                //criteria.andStatusLike("%" + seller.getStatus() + "%");
            }
            if (seller.getAddressDetail() != null && seller.getAddressDetail().length() > 0) {
                entity.like("address_detail", seller.getAddressDetail());
                //criteria.andAddressDetailLike("%" + seller.getAddressDetail() + "%");
            }
            if (seller.getLinkmanName() != null && seller.getLinkmanName().length() > 0) {
                entity.like("linkman_name", seller.getLinkmanName());
                //criteria.andLinkmanNameLike("%" + seller.getLinkmanName() + "%");
            }
            if (seller.getLinkmanQq() != null && seller.getLinkmanQq().length() > 0) {
                entity.like("linkman_qq", seller.getLinkmanQq());
                //criteria.andLinkmanQqLike("%" + seller.getLinkmanQq() + "%");
            }
            if (seller.getLinkmanMobile() != null && seller.getLinkmanMobile().length() > 0) {
                entity.like("linkman_mobile", seller.getLinkmanMobile());
                //criteria.andLinkmanMobileLike("%" + seller.getLinkmanMobile() + "%");
            }
            if (seller.getLinkmanEmail() != null && seller.getLinkmanEmail().length() > 0) {
                entity.like("linkman_email", seller.getLinkmanEmail());
                //criteria.andLinkmanEmailLike("%" + seller.getLinkmanEmail() + "%");
            }
            if (seller.getLicenseNumber() != null && seller.getLicenseNumber().length() > 0) {
                entity.like("license_number", seller.getLicenseNumber());
                //criteria.andLicenseNumberLike("%" + seller.getLicenseNumber() + "%");
            }
            if (seller.getTaxNumber() != null && seller.getTaxNumber().length() > 0) {
                entity.like("tax_number", seller.getTaxNumber());
                //criteria.andTaxNumberLike("%" + seller.getTaxNumber() + "%");
            }
            if (seller.getOrgNumber() != null && seller.getOrgNumber().length() > 0) {
                entity.like("org_number", seller.getOrgNumber());
                //criteria.andOrgNumberLike("%" + seller.getOrgNumber() + "%");
            }
            if (seller.getLogoPic() != null && seller.getLogoPic().length() > 0) {
                entity.like("logo_pic", seller.getLogoPic());
                //criteria.andLogoPicLike("%" + seller.getLogoPic() + "%");
            }
            if (seller.getBrief() != null && seller.getBrief().length() > 0) {
                entity.like("brief", seller.getBrief());
                //criteria.andBriefLike("%" + seller.getBrief() + "%");
            }
            if (seller.getLegalPerson() != null && seller.getLegalPerson().length() > 0) {
                entity.like("legal_person", seller.getLegalPerson());
                //criteria.andLegalPersonLike("%" + seller.getLegalPerson() + "%");
            }
            if (seller.getLegalPersonCardId() != null && seller.getLegalPersonCardId().length() > 0) {
                entity.like("legal_person_card_id", seller.getLegalPersonCardId());
                // criteria.andLegalPersonCardIdLike("%" + seller.getLegalPersonCardId() + "%");
            }
            if (seller.getBankUser() != null && seller.getBankUser().length() > 0) {
                entity.like("bank_user", seller.getBankUser());
                //criteria.andBankUserLike("%" + seller.getBankUser() + "%");
            }
            if (seller.getBankName() != null && seller.getBankName().length() > 0) {
                entity.like("bank_name", seller.getBankName());
                // criteria.andBankNameLike("%" + seller.getBankName() + "%");
            }

        }

        Page<Seller> page = new Page<Seller>(pageNum, pageSize);

        List<Seller> pageInfoList = sellerMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(sellerMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;
    }

}
