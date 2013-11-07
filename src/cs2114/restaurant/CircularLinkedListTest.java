package cs2114.restaurant;

import java.util.Iterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 * Tests the CircularLinkedList class.
 *
 * @author Chris Conley
 * @version 2013.03.26
 */
public class CircularLinkedListTest
    extends student.TestCase
{
    private CircularLinkedList<String> list;
    private String                     a;
    private String                     b;
    private String                     c;


    /**
     * This code is run before each test
     */
    public void setUp()
    {
        list = new CircularLinkedList<String>();
        a = "a";
        b = "b";
        c = "c";
    }


    /**
     * Tests the add method
     */
    public void testAdd()
    {
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals(3, list.size());
    }


    /**
     * Tests the getCurrent method
     */
    public void testGetCurrent()
    {
        Exception occurred = null;
        try
        {
            list.getCurrent();
        }
        catch (Exception e)
        {
            occurred = e;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals("The list is empty.", occurred.getMessage());

        list.add(a);
        list.add(b);
        assertEquals(b, list.getCurrent());
    }


    /**
     * Tests the removeCurrent method
     */
    public void testRemoveCurrent()
    {
        Exception occurred = null;
        try
        {
            list.removeCurrent();
        }
        catch (Exception e)
        {
            occurred = e;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals("The list is empty.", occurred.getMessage());

        list.add(a);
        list.removeCurrent();
        assertEquals(0, list.size());

        list.add(a);
        list.add(b);
        list.add(c);
        String ref = list.removeCurrent();
        assertEquals(c, ref);
        assertEquals(b, list.getCurrent());
    }


    /**
     * Tests the next method
     */
    public void testNext()
    {
        list.next();
        assertEquals(0, list.size());

        list.add(a);
        list.add(b);
        list.next();
        assertEquals(a, list.getCurrent());
    }


    /**
     * Tests the previous method
     */
    public void testPrevious()
    {
        list.previous();
        assertEquals(0, list.size());

        list.add(a);
        list.add(b);
        list.next();
        list.previous();
        assertEquals(b, list.getCurrent());
    }


    /**
     * Tests the clear method
     */
    public void testClear()
    {
        list.add(a);
        list.add(b);
        list.clear();
        assertEquals(0, list.size());
    }


    /**
     * Tests the toString method.
     */
    public void testToString()
    {
        assertEquals("[]", list.toString());
        list.add(a);
        list.add(b);
        list.add(c);
        assertEquals("[c, b, a]", list.toString());
    }


    /**
     * Tests the hasNext method
     */
    public void testHasNext()
    {
        Iterator<String> itr = list.iterator();
        list.add("a");
        list.add("b");
        itr = list.iterator();
        assertTrue(itr.hasNext());
    }


    /**
     * Tests the next method
     */
    public void testNextItr()
    {
        list.add("a");
        list.add("b");
        Iterator<String> itr = list.iterator();
        assertEquals(b, itr.next());
    }


    /**
     * Tests the Exception error in the next method
     */
    public void testNextException()
    {
        Iterator<String> itr = list.iterator();
        Exception occurred = null;
        try
        {
            itr.next();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof NoSuchElementException);
        assertEquals("There is no such element", occurred.getMessage());
    }


    /**
     * Tests the Exception error in the remove method
     */
    public void testRemove()
    {
        Iterator<String> itr = list.iterator();
        Exception occurred = null;
        try
        {
            itr.remove();
        }
        catch (Exception exception)
        {
            occurred = exception;
        }
        assertNotNull(occurred);
        assertTrue(occurred instanceof UnsupportedOperationException);
        assertEquals("You can't remove an item", occurred.getMessage());
    }
}
