/* CommandLine.java
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
import java.util.List;
import java.util.logging.Logger;

/**
 * Command line object, results of parsing.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public final class CommandLine extends ArrayList<Parameter> {

  // static logger
  private static final Logger log = Logger.getLogger(CommandLine.class.getName());

  // for description see Object
  @Override
  public String toString() {
    return "CommandLine";
  }

  /** List of remaining (unparsed) arguments. */
  private final List<String> remArgs = new ArrayList<>();

  /**
   * Gets the list of parameters.
   *
   * @return the list of parameters
   */
  public List<Parameter> getParameters() {
    return this;
  }

  /**
   * Adds a parameter.
   *
   * @param parameter the parameter
   */
  void addParameter(final Parameter parameter) {
    add(parameter);
  }

  /**
   * Gets the list of remaining (unparsed) arguments.
   *
   * @return the list of remaining (unparsed) arguments
   */
  public List<String> getRemArgs() {
    return remArgs;
  }

  /**
   * Adds a string to the list of remaining arguments.
   *
   * @param arg argument to be added
   */
  void addRemArg(final String arg) {
    remArgs.add(arg);
  }
}
