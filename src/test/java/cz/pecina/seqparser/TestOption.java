/* TestOption.java
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

public class TestOption extends TestCase {

  public void testOption1() {
    String[][] succ = {
        {"a", null}, {"A", null}, {"_", null}, {"aa", null}, {"a0", null}, {"a_", null}, {null, "a"}, {null, "A"},
        {null, "_"}, {null, "aa"}, {null, "a0"}, {null, "a_"}, {null, "a-b"}, {null, "a-b0-d"}, {null, "a-0-d"},
        {null, "a-bc-d0f"}, {null, "ab-ce-e0g"}};
    String[][] fail = {
        {null, null}, {"", null}, {"0abc", null}, {"-abc", null}, {"a-bc", null}, {"0abc", null}, {"-abc", null},
        {"á", null}, {null, ""}, {null, "0abc"}, {null, "-abc"}, {null, "0abc"}, {null, "-abc"}, {null, "abc-"},
        {null, "a--bc"}, {null, "ab--c"}, {null, "á"}};
    for (String[] t : succ) {
      try {
        new Option(t[0], t[1]);
      } catch (ParseException e) {
        fail(String.format("\"%s\"/\"%s\"", t[0], t[1]));
      }
    }
    for (String[] t : fail) {
      try {
        new Option(t[0], t[1]);
        fail(String.format("\"%s\"/\"%s\"", t[0], t[1]));
      } catch (ParseException expected) { }
    }
  }

  public void testOption2() {
    int[][] succ = {{0, 0}, {1, 1}, {0, 1}, {0, 10}, {3, 4}};
    int[][] fail = {{-1, 0}, {-20, -1}, {3, 2}};
    for (int[] t : succ) {
      try {
        new Option("a", "a", t[0], t[1]);
      } catch (ParseException e) {
        fail(String.format("\"%d\"/\"%d\"", t[0], t[1]));
      }
    }
    for (int[] t : fail) {
      try {
        new Option("a", "a", t[0], t[1]);
        fail(String.format("\"%d\"/\"%d\"", t[0], t[1]));
      } catch (ParseException expected) { }
    }
  }

  public void testOption3() {
    try {
      assertEquals(new Option("a", null, 2).getMinParameters(), 2);
      assertEquals(new Option("a", null, 2).getMaxParameters(), 2);
      assertEquals(new Option("a", null).getMinParameters(), 0);
      assertEquals(new Option("a", null).getMaxParameters(), 0);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetShortOpt() {
    try {
      assertEquals(new Option("a", null).getShortOpt(), "a");
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetLongOpt() {
    try {
      assertEquals(new Option(null, "a").getLongOpt(), "a");
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetName() {
    try {
      assertEquals(new Option("a", null).getName(), "a");
      assertEquals(new Option(null, "a").getName(), "a");
      assertEquals(new Option("a", "b").getName(), "b");
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testSubOptions() {
    try {
      Option o = new Option("a", null);
      assertSame(o.addSubOption(ParameterType.String), o);
      assertSame(o.addSubOption(ParameterType.Integer), o);
      assertEquals(o.getSubOptions().size(), 2);
      assertEquals(o.getSubOptions().get(0), ParameterType.String);
      assertEquals(o.getSubOptions().get(1), ParameterType.Integer);
      assertEquals(o.getSubOption(0), ParameterType.String);
      assertEquals(o.getSubOption(1), ParameterType.Integer);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testKwSubOptions() {
    try {
      Option o = new Option("a", null);
      assertSame(o.addKwSubOption("b", ParameterType.String), o);
      assertSame(o.addKwSubOption("c", ParameterType.Integer), o);
      assertEquals(o.getKwSubOptions().size(), 2);
      assertEquals(o.getKwSubOptions().get("b"), ParameterType.String);
      assertEquals(o.getKwSubOptions().get("c"), ParameterType.Integer);
      assertNull(o.getKwSubOptions().get("d"));
      assertEquals(o.getKwSubOption("b"), ParameterType.String);
      assertEquals(o.getKwSubOption("c"), ParameterType.Integer);
      assertNull(o.getKwSubOption("d"));
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetMinParameters() {
    try {
      assertEquals(new Option("a", null, 2, 3).getMinParameters(), 2);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }

  public void testGetMaxParameters() {
    try {
      assertEquals(new Option("a", null, 2, 3).getMaxParameters(), 3);
    } catch (ParseException e) {
      fail(e.getMessage());
    }
  }
}
