/* TestParameter.java
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

public class TestParameter extends TestCase {

  public void testGetOption() {
    try {
      Option o = new Option("a", "b");
      assertSame(new Parameter(o).getOption(), o);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testSubParameters() {
    try {
      Parameter p = new Parameter(new Option("a", "b"));
      SubParameter s1 = new SubParameter("a", ParameterType.String);
      SubParameter s2 = new SubParameter("99", ParameterType.Integer);
      p.addSubParameter(s1);
      p.addSubParameter(s2);
      assertEquals(p.getSubParameters().size(), 2);
      assertEquals(p.getNumSubParameters(), 2);
      assertEquals(p.getSubParameters().get(0), s1);
      assertEquals(p.getSubParameters().get(1), s2);
      assertEquals(p.getSubParameter(0), s1);
      assertEquals(p.getSubParameter(1), s2);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testKwSubParameters() {
    try {
      Parameter p = new Parameter(new Option("a", "b"));
      SubParameter s1 = new SubParameter("a", ParameterType.String);
      SubParameter s2 = new SubParameter("99", ParameterType.Integer);
      p.addKwSubParameter("x", s1);
      p.addKwSubParameter("y", s2);
      assertEquals(p.getKwSubParameters().size(), 2);
      assertEquals(p.getNumKwSubParameters(), 2);
      assertEquals(p.getKwSubParameters().get("x"), s1);
      assertEquals(p.getKwSubParameters().get("y"), s2);
      assertEquals(p.getKwSubParameter("x"), s1);
      assertEquals(p.getKwSubParameter("y"), s2);
      assertTrue(p.hasKwSubParameter("x"));
      assertTrue(p.hasKwSubParameter("y"));
      assertFalse(p.hasKwSubParameter("z"));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
