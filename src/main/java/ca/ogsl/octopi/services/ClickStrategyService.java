/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;

public class ClickStrategyService {

  private GenericDao genericDao = new GenericDao();

  public List<ClickStrategy> listClickStrategies() {
    return this.genericDao.getAllEntities(ClickStrategy.class);
  }

  public ClickStrategy getClickStrategy(Integer id) throws AppException {
    ClickStrategy clickStrategy = this.genericDao.getEntityFromId(id, ClickStrategy.class);
    if (clickStrategy == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return clickStrategy;
  }

  public ClickStrategy postCreateClickStrategy(ClickStrategy clickStrategy) throws AppException {
    ValidationUtil.validateBean(clickStrategy, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(clickStrategy);
  }
}
