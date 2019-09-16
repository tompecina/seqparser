/* TestParameterType.java
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
 * The source code is available from <https://github.com/tompecina/pdf>.
 */

package cz.pecina.seqparser;

import junit.framework.TestCase;

public class TestParameterType extends TestCase {

  public void testString() {
    String[] succ = {"", "str"};
    String[] fail = {null};
    for (String t : succ) {
      assertTrue(t, ParameterType.String.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.String.getType().check(t));
    }
  }

  public void testInteger() {
    String[] succ = {"0", "00", "007", "-0", "+6", "42"};
    String[] fail = {null, "", "abc", "0xaa", "0.", "1e4", "--0", "--1"};
    for (String t : succ) {
      assertTrue(t, ParameterType.Integer.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.Integer.getType().check(t));
    }
  }

  public void testPosInteger() {
    String[] succ = {"007", "1", "+6", "42"};
    String[] fail = {null, "", "0", "00", "-6", "-1", "abc", "0xaa", "0.", "--0", "--1", "1e4"};
    for (String t : succ) {
      assertTrue(t, ParameterType.PosInteger.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.PosInteger.getType().check(t));
    }
  }

  public void testNonNegInteger() {
    String[] succ = {"007", "0", "00", "1", "6", "+6", "42"};
    String[] fail = {null, "", "-6", "-1", "abc", "0xaa", "0.", "--0", "--1", "1e4"};
    for (String t : succ) {
      assertTrue(t, ParameterType.NonNegInteger.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.NonNegInteger.getType().check(t));
    }
  }

  public void testIntegerRange() {
    String[] succ = {"007", "0", "00", "1", "6", "+6", "42"};
    String[] fail = {null, "", "-6", "-1", "abc", "0xaa", "0.", "--0", "--1", "1e4"};
    for (String t : succ) {
      assertTrue(t, ParameterType.IntegerRange(0, Integer.MAX_VALUE).getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.IntegerRange(0, Integer.MAX_VALUE).getType().check(t));
    }
    succ = new String[] {"0", "00", "1", "-1", "-2"};
    fail = new String[] {null, "", "007", "42", "abc", "0xaa", "0.", "--0", "--1", "1e4", "6", "+6", "-6"};
    for (String t : succ) {
      assertTrue(t, ParameterType.IntegerRange(-2, 1).getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.IntegerRange(-2, 1).getType().check(t));
    }
  }

  public void testFloat() {
    String[] succ = {
        "0", "00", "007", "-0", "+6", "42", "0.", ".0", "0.0", "-.0", "1e4", "3.14", "-3.14", "NaN", "-NaN", "+NaN",
        "0f", "0d", "Infinity", "-Infinity", "+Infinity"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-Inf", "Inf", "+Inf", "NAN", "-INF", "INF", "+INF", "INFINITY",
        "-INFINITY", "+INFINITY"};
    for (String t : succ) {
      assertTrue(t, ParameterType.Float.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.Float.getType().check(t));
    }
  }

  public void testPosFloat() {
    String[] succ = {"007", "+6", "42", "1e4", "3.14", "Infinity", "+Infinity"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "-1", "0", "00", "-0", "0.", ".0", "0.0", "-.0",
        "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf", "NAN", "-INF", "INF", "+INF", "0f", "0d", "-Infinity"};
    for (String t : succ) {
      assertTrue(t, ParameterType.PosFloat.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.PosFloat.getType().check(t));
    }
  }

  public void testNonNegFloat() {
    String[] succ = {
        "0", "00", "007", "-0", "+6", "42", "0.", ".0", "0.0", "-.0", "1e4", "3.14", "Infinity", "+Infinity", "0f", "0d"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "-1", "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf",
        "NAN", "-INF", "INF", "+INF", "-Infinity"};
    for (String t : succ) {
      assertTrue(t, ParameterType.NonNegFloat.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.NonNegFloat.getType().check(t));
    }
  }

  public void testFloatRange() {
    String[] succ = {"0", "00", "-0", "0.", ".0", "0.0", "-.0", "1e-4", "-1", "0f", "0d"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf", "NAN",
        "-INF", "INF", "+INF", "007", "+6", "42", "1e4", "3.14", "Infinity", "+Infinity",};
    for (String t : succ) {
      assertTrue(t, ParameterType.FloatRange(-1.5f, 2.5f).getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.FloatRange(-1.5f, 2.5f).getType().check(t));
    }
  }

  public void testDouble() {
    String[] succ = {
        "0", "00", "007", "-0", "+6", "42", "0.", ".0", "0.0", "-.0", "1e4", "3.14", "-3.14", "NaN", "-NaN", "+NaN",
        "0f", "0d", "Infinity", "-Infinity", "+Infinity"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-Inf", "Inf", "+Inf", "NAN", "-INF", "INF", "+INF", "INFINITY",
        "-INFINITY", "+INFINITY"};
    for (String t : succ) {
      assertTrue(t, ParameterType.Double.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.Double.getType().check(t));
    }
  }

  public void testPosDouble() {
    String[] succ = {"007", "+6", "42", "1e4", "3.14", "Infinity", "+Infinity"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "-1", "0", "00", "-0", "0.", ".0", "0.0", "-.0",
        "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf", "NAN", "-INF", "INF", "+INF", "0f", "0d", "-Infinity"};
    for (String t : succ) {
      assertTrue(t, ParameterType.PosDouble.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.PosDouble.getType().check(t));
    }
  }

  public void testNonNegDouble() {
    String[] succ = {
        "0", "00", "007", "-0", "+6", "42", "0.", ".0", "0.0", "-.0", "1e4", "3.14", "Infinity", "+Infinity", "0f", "0d"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "-1", "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf",
        "NAN", "-INF", "INF", "+INF", "-Infinity"};
    for (String t : succ) {
      assertTrue(t, ParameterType.NonNegDouble.getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.NonNegDouble.getType().check(t));
    }
  }

  public void testDoubleRange() {
    String[] succ = {"0", "00", "-0", "0.", ".0", "0.0", "-.0", "1e-4", "-1", "0f", "0d"};
    String[] fail = {
        null, "", "abc", "0xaa", "--0", "--1", ".", "-3.14", "NaN", "-NaN", "+NaN", "-Inf", "Inf", "+Inf", "NAN",
        "-INF", "INF", "+INF", "007", "+6", "42", "1e4", "3.14", "Infinity", "+Infinity",};
    for (String t : succ) {
      assertTrue(t, ParameterType.DoubleRange(-1.5, 2.5).getType().check(t));
    }
    for (String t : fail) {
      assertFalse(t, ParameterType.DoubleRange(-1.5, 2.5).getType().check(t));
    }
  }
}
