/* Parameter.java
 *
 * Copyright (C) 2019, Tomas Pecina <tomas@pecina.cz>
 *
 * This file is part of cz.pecina.seqparser, a sequential command-line parser.
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
 * The source code is available from <https://github.com/tompecina/seqparser>.
 */

package cz.pecina.seqparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Parameter value and sub-parameters.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public final class Parameter {

  // static logger
  private static final Logger log = Logger.getLogger(Parameter.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "Parameter \"" + getOption().getName() + "\"";
  }

  /** The option describing the parameter. */
  private final Option option;

  /** List of sub-parameters. */
  private final List<SubParameter> subParameters = new ArrayList<>();

  /** Map of keyword sub-parameters. */
  private final Map<String, SubParameter> kwSubParameters = new HashMap<>();

  /**
   * Gets the option describing the parameter.
   *
   * @return the option describing the parameter
   */
  public Option getOption() {
    return option;
  }

  /**
   * Gets the list of sub-parameters.
   *
   * @return the list of sub-parameters
   */
  public List<SubParameter> getSubParameters() {
    return subParameters;
  }

  /**
   * Gets the number of sub-parameters.
   *
   * @return the number of sub-parameters
   */
  public int getNumSubParameters() {
    return subParameters.size();
  }

  /**
   * Gets a sub-parameter.
   *
   * @param idx the index
   * @return the sub-parameter
   */
  public SubParameter getSubParameter(final int idx) {
    return subParameters.get(idx);
  }

  /**
   * Adds a sub-parameter.
   *
   * @param subParameter the sub-parameter
   */
  void addSubParameter(final SubParameter subParameter) {
    subParameters.add(subParameter);
  }

  /**
   * Gets the map of keyword sub-parameters.
   *
   * @return the map of keyword sub-parameters
   */
  public Map<String, SubParameter> getKwSubParameters() {
    return kwSubParameters;
  }

  /**
   * Gets the number of keyword sub-parameters.
   *
   * @return the number of keyword sub-parameters
   */
  public int getNumKwSubParameters() {
    return kwSubParameters.size();
  }

  /**
   * Gets a keyword sub-parameter.
   *
   * @param key the keyword
   * @return the sub-parameter
   */
  public SubParameter getKwSubParameter(final String key) {
    return kwSubParameters.get(key);
  }

  /**
   * Checks if a keyword sub-parameter is set.
   *
   * @param key the keyword
   * @return <code>true</code> if the sub-parameter is set
   */
  public boolean hasKwSubParameter(final String key) {
    return kwSubParameters.containsKey(key);
  }

  /**
   * Adds a keyword sub-parameter.
   *
   * @param key the keyword
   * @param subParameter the keyword sub-parameter
   */
  void addKwSubParameter(final String key, final SubParameter subParameter) {
    kwSubParameters.put(key, subParameter);
  }

  /**
   * Creates the parameter object.
   *
   * @param option the option describing the parameter
   */
  Parameter(final Option option) {
    this.option = option;
  }
}
