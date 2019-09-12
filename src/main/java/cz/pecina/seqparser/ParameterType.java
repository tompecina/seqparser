/* ParameterType.java
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

/**
 * Parameter type.
 *
 * @author Tomáš Pecina
 * @version 1.0.0
 */
public interface ParameterType {

  /**
   * Type-checks the string.
   *
   * @return <code>true</code> if correct type
   */
  public abstract boolean check(final java.lang.String str);

  /**
   * Predefined string type.
   */
  public ParameterType String = new ParameterType() {
      public boolean check(final java.lang.String str) {
        return true;
      }
    };

  /**
   * Predefined integer type.
   */
  public ParameterType Integer = new ParameterType() {
      public boolean check(final java.lang.String str) {
        try {
          java.lang.Integer.parseInt(str);
          return true;
        } catch (final NumberFormatException exception) {
          return false;
        }
      }
    };

  /**
   * Predefined positive integer type.
   */
  public ParameterType PosInteger = new ParameterType() {
      public boolean check(final java.lang.String str) {
        try {
          final int res = java.lang.Integer.parseInt(str);
          return res > 0;
        } catch (final NumberFormatException exception) {
          return false;
        }
      }
    };

  /**
   * Predefined float type.
   */
  public ParameterType Float = new ParameterType() {
      public boolean check(final java.lang.String str) {
        try {
          java.lang.Float.parseFloat(str);
          return true;
        } catch (final NumberFormatException exception) {
          return false;
        }
      }
    };

  /**
   * Predefined positive float type.
   */
  public ParameterType PosFloat = new ParameterType() {
      public boolean check(final java.lang.String str) {
        try {
          final float res = java.lang.Float.parseFloat(str);
          return res > 0;
        } catch (final NumberFormatException exception) {
          return false;
        }
      }
    };
}
