/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Language;
import ca.ogsl.octopi.util.AppConstants;
import java.util.List;

public class LanguageService {

  private GenericDao genericDao = new GenericDao();

  public List<Language> listLanguages() {
    return this.genericDao.getAllEntities(Language.class);
  }

  public Language getLanguage(String code) throws AppException {
    Language language = this.genericDao.getEntityFromCode(code, Language.class);
    if (language == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return language;
  }
}
