/* SubParameter.java
 *
 * Copyright (C) 2015-19, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.pdf, a suite of PDF processing applications.
 *
 * This application is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * The source code is available from <https://github.com/tompecina/pdf>.
 */

package cz.pecina.seqparser;

import java.util.logging.Logger;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.TypeHandler;

/**
 * Sub-parameter value.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public class SubParameter {

  // static logger
  private static final Logger log = Logger.getLogger(SubParameter.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "SubParameter";
  }

  // fields
  private SubOption subOption;
  private String rawValue;
  private Object value;

  /**
   * Gets the sub-option.
   *
   * @return the sub-option
   */
  public SubOption getSubOption() {
    return subOption;
  }

  /**
   * Gets the raw (string) value.
   *
   * @return the raw value
   */
  public String getRawValue() {
    return rawValue;
  }

  /**
   * Gets the converted value.
   *
   * @return the converted value
   */
  public Object getValue() {
    return value;
  }

  /**
   * Creates and parses the sub-parameter.
   *
   * @param str the input string
   * @param subOption the sub-option describing the sub-parameter
   * @throws ParseException on parse error
   */
  public SubParameter(final String str, final SubOption subOption) throws ParseException {
    this.rawValue = str;
    this.subOption = subOption;
    final Class<?> type = subOption.getType();
    if (type == String.class) {
      value = str;
    } else {
      value = TypeHandler.createValue(str, type);
    }
  }
}
