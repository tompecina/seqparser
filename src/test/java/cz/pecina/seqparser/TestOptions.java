/* TestOptions.java
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

import junit.framework.TestCase;

public class TestOptions extends TestCase {

  public void testToString() {
    assertEquals("Options", new Options().toString());
  }

  public void testGetOption() {
    Options o = new Options();
    try {
      Option s = new Option("a", null);
      Option l = new Option(null, "b");
      o.addOption(s).addOption(l);
      assertSame(s, o.getOptionShort("a"));
      assertSame(l, o.getOptionLong("b"));
    } catch (ParseException e) {
      fail();
    }
  }

  public void testAddOption() {
    Options o;
    Option s, l;

    o = new Options();
    try {
      s = new Option("a", null);
      l = new Option(null, "b");
      o.addOption(s).addOption(l);
      try {
        o.addOption(new Option("a", "c"));
        fail();
      } catch (ParseException expected) { }
      try {
        o.addOption(new Option("c", "b"));
        fail();
      } catch (ParseException expected) { }
    } catch (ParseException e) {
      fail();
    }

    o = new Options();
    try {
      s = o.addOption("a", null);
      l = o.addOption(null, "b");
      try {
        o.addOption("a", "c");
        fail();
      } catch (ParseException expected) { }
      try {
        o.addOption("c", "b");
        fail();
      } catch (ParseException expected) { }
    } catch (ParseException e) {
      fail();
    }

    o = new Options();
    try {
      s = o.addOption("a", "b");
      assertEquals("a", s.getShortOpt());
      assertEquals("b", s.getLongOpt());
      assertEquals(0, s.getMinParameters());
      assertEquals(0, s.getMaxParameters());
    } catch (ParseException e) {
      fail();
    }

    o = new Options();
    try {
      s = o.addOption("a", "b", 1);
      assertEquals("a", s.getShortOpt());
      assertEquals("b", s.getLongOpt());
      assertEquals(1, s.getMinParameters());
      assertEquals(1, s.getMaxParameters());
    } catch (ParseException e) {
      fail();
    }
    o = new Options();
    try {
      s = o.addOption("a", "b", 1, 2);
      assertEquals("a", s.getShortOpt());
      assertEquals("b", s.getLongOpt());
      assertEquals(1, s.getMinParameters());
      assertEquals(2, s.getMaxParameters());
    } catch (ParseException e) {
      fail();
    }
  }
}
