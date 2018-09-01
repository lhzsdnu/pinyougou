package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.pinyougou.entity.Address;
import com.pinyougou.mapper.AddressMapper;
import com.pinyougou.pojo.PageResult;
import com.pinyougou.sellergoods.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Autowired
    private AddressMapper addressMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Address> findAll() {
        return addressMapper.selectList(new EntityWrapper<Address>());
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {

        //分页查询
        Wrapper<Address> entity = new EntityWrapper<Address>();
        Page<Address> page = new Page<Address>(pageNum, pageSize);

        List<Address> pageInfoList = addressMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(addressMapper.selectPage(page, entity));

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
    public void add(Address address) {
        addressMapper.insert(address);
    }


    /**
     * 修改
     */
    @Override
    public void update(Address address) {
        addressMapper.updateById(address);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Address findOne(Long id) {
        return addressMapper.selectById(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            addressMapper.deleteById(id);
        }
    }


    @Override
    public PageResult findPage(Address address, int pageNum, int pageSize) {

        //分页查询
        Wrapper<Address> entity = new EntityWrapper<Address>();

        if (address != null) {

            if (address.getUserId() != null && address.getUserId().length() > 0) {
                entity.like("user_id", address.getUserId());
                //entity.andUserIdLike("%" + address.getUserId() + "%");
            }
            if (address.getProvinceId() != null && address.getProvinceId().length() > 0) {
                entity.like("province_id", address.getProvinceId());
                //criteria.andProvinceIdLike("%" + address.getProvinceId() + "%");
            }
            if (address.getCityId() != null && address.getCityId().length() > 0) {
                entity.like("city_id", address.getCityId());
                //criteria.andCityIdLike("%" + address.getCityId() + "%");
            }
            if (address.getTownId() != null && address.getTownId().length() > 0) {
                entity.like("town_id", address.getTownId());
                //criteria.andTownIdLike("%" + address.getTownId() + "%");
            }
            if (address.getMobile() != null && address.getMobile().length() > 0) {
                entity.like("mobile", address.getMobile());
                //criteria.andMobileLike("%" + address.getMobile() + "%");
            }
            if (address.getAddress() != null && address.getAddress().length() > 0) {
                entity.like("address", address.getAddress());
                //criteria.andAddressLike("%" + address.getAddress() + "%");
            }
            if (address.getContact() != null && address.getContact().length() > 0) {
                entity.like("contact", address.getContact());
                //criteria.andContactLike("%" + address.getContact() + "%");
            }
            if (address.getIsDefault() != null && address.getIsDefault().length() > 0) {
                entity.like("is_default", address.getIsDefault());
                //criteria.andIsDefaultLike("%" + address.getIsDefault() + "%");
            }
            if (address.getNotes() != null && address.getNotes().length() > 0) {
                entity.like("notes", address.getNotes());
                //criteria.andNotesLike("%" + address.getNotes() + "%");
            }
            if (address.getAlias() != null && address.getAlias().length() > 0) {
                entity.like("alias", address.getAlias());
                //criteria.andAliasLike("%" + address.getAlias() + "%");
            }
        }

        Page<Address> page = new Page<Address>(pageNum, pageSize);

        List<Address> pageInfoList = addressMapper.selectPage(page, entity);
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        page.setRecords(addressMapper.selectPage(page, entity));

        // 封装分页结果对象
        PageResult result = new PageResult();
        result.setTotal(page.getTotal());
        result.setRows(pageInfoList);

        return result;

    }

}
