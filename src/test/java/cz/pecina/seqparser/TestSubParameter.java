/* TestSubParameter.java
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

import junit.framework.TestCase;

public class TestSubParameter extends TestCase {

  public void testSubParameter() {
    try {
      new SubParameter(null, ParameterType.String);
      fail("null string");
    } catch (ParseException expected) { }
    try {
      new SubParameter("a", ParameterType.Integer);
      fail("invalid string");
    } catch (ParseException expected) { }
    try {
      new SubParameter("42", ParameterType.Integer);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetSubOption() {
    try {
      assertSame(new SubParameter("a", ParameterType.String).getSubOption(), ParameterType.String);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testIsEmpty() {
    try {
      assertTrue(new SubParameter("", ParameterType.String).isEmpty());
      assertFalse(new SubParameter(" ", ParameterType.String).isEmpty());
      assertFalse(new SubParameter("0", ParameterType.String).isEmpty());
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsString() {
    try {
      assertEquals(new SubParameter("a", ParameterType.String).getAsString(), "a");
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsInt() {
    try {
      assertEquals(new SubParameter("-18", ParameterType.Integer).getAsInt(), -18);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsFloat() {
    try {
      assertEquals(new SubParameter("4.5", ParameterType.Float).getAsFloat(), 4.5f);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetAsDouble() {
    try {
      assertEquals(new SubParameter("-18.5", ParameterType.Double).getAsDouble(), -18.5);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
