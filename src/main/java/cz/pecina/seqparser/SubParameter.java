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
  private String value;

  /**
   * Gets the sub-option.
   *
   * @return the sub-option
   */
  public SubOption getSubOption() {
    return subOption;
  }

  /**
   * Checks if the value is empty.
   *
   * @return <code>true</code> of the value is empty
   */
  public boolean isEmpty() {
    return (value == null) || (value.length() == 0);
  }

  /**
   * Gets the raw (string) value.
   *
   * @return the raw (string) value
   */
  public String getAsString() {
    return value;
  }

  /**
   * Gets the value as integer.
   *
   * @return the integer value
   */
  public int getAsInt() {
    return Integer.parseInt(value);
  }

  /**
   * Gets the value as float.
   *
   * @return the float value
   */
  public float getAsFloat() {
    return Float.parseFloat(value);
  }

  /**
   * Gets the value as double.
   *
   * @return the double value
   */
  public double getAsDouble() {
    return Double.parseDouble(value);
  }

  /**
   * Creates and parses the sub-parameter.
   *
   * @param str the input string
   * @param subOption the sub-option describing the sub-parameter
   * @throws ParseException on parse error
   */
  public SubParameter(final String str, final SubOption subOption) throws ParseException {
    this.value = str;
    this.subOption = subOption;
    if (!subOption.getType().check(str)) {
      throw new ParseException("Invalid parameter value: " + str);
    }
  }
}
