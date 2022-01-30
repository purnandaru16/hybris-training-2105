package org.training.core.dao.impl;

import de.hybris.platform.servicelayer.internal.dao.AbstractItemDao;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import org.apache.commons.collections.CollectionUtils;
import org.training.core.dao.SupplierDao;
import org.training.core.model.SupplierModel;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

public class SupplierDaoImpl extends AbstractItemDao implements SupplierDao {
    @Resource
    FlexibleSearchService flexibleSearchService;

    @Override
    public List<SupplierModel> getAllSupplier(){
        final FlexibleSearchQuery flexibleSearchQuery = new FlexibleSearchQuery("SELECT {pk} from {Supplier}");
        final SearchResult<SupplierModel> result = this.search(flexibleSearchQuery);
        if (CollectionUtils.isNotEmpty(result.getResult())) {
            return result.getResult();
        }
        return Collections.emptyList();
    }
}
