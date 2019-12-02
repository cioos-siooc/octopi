/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;

public class ClickFormatterService {

  private GenericDao genericDao = new GenericDao();

  public List<ClickFormatter> listClickFormatters() {
    return this.genericDao.getAllEntities(ClickFormatter.class);
  }

  public ClickFormatter getClickFormatter(Integer id) throws AppException {
    ClickFormatter clickFormatter = this.genericDao.getEntityFromId(id, ClickFormatter.class);
    if (clickFormatter == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return clickFormatter;
  }

  public ClickFormatter postCreateClickFormatter(ClickFormatter clickFormatter)
      throws AppException {
    ValidationUtil.validateBean(clickFormatter, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(clickFormatter);

  }
}
