package org.training.core.job;

import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import org.apache.log4j.Logger;
import org.training.core.dao.SupplierDao;
import org.training.core.enums.SupplierStatusEnum;
import org.training.core.model.SupplierModel;

import javax.annotation.Resource;
import java.util.List;

public class SupplierStatusUpdateJobPerformable extends AbstractJobPerformable<CronJobModel> {

    private static final Logger LOG = Logger.getLogger(SupplierStatusUpdateJobPerformable.class);

    @Resource
    ModelService modelService;

    @Resource
    SupplierDao supplierDao;

    @Override
    public PerformResult perform(CronJobModel cronJobModel) {
        List<SupplierModel> supplierModelList = supplierDao.getAllSupplier();
        if (null != supplierModelList) {
            for (SupplierModel supplierModel : supplierModelList) {
                LOG.info(supplierModel.getName());
                updateSupplierStatus(supplierModel);
            }
        }
        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void updateSupplierStatus(SupplierModel supplierModel) {
        if (SupplierStatusEnum.NOT_VERIFIED.equals(supplierModel.getStatus())) {
            supplierModel.setStatus(SupplierStatusEnum.IN_REVIEW);
            modelService.save(supplierModel);
        }
    }
}
