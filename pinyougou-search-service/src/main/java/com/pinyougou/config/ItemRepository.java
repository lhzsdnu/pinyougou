package com.pinyougou.config;

import com.pinyougou.entity.Item;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends SolrCrudRepository<Item, String> {

}