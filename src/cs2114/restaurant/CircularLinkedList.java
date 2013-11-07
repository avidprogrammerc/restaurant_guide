package cs2114.restaurant;

import java.util.NoSuchElementException;
import java.util.Iterator;

// -------------------------------------------------------------------------
/**
 * This class represents a doubly linked circular list.
 *
 * @author Chris Conley
 * @version 2013.03.26
 * @param <E>
 *            The data type
 */
public class CircularLinkedList<E>
    implements CircularList<E>
{
    private int     size;
    private Node<E> current;


    /**
     * Create a new CircularLinkedList object.
     */
    public CircularLinkedList()
    {
        size = 0;
    }


    /**
     * Adds a new item to the list.
     *
     * @param newItem
     *            the data of the node being added
     */
    public void add(E newItem)
    {
        Node<E> newNode = new Node<E>(newItem);
        if (size == 0)
        {
            current = newNode;
            current = newNode.join(current);
        }
        else
        {
            Node<E> newNode2 = current.previous();
            newNode.join(newNode2.split());
            newNode2.join(newNode);
            current = newNode;
        }
        size += 1;
    }


    /**
     * Clears the list.
     */
    public void clear()
    {
        current = new Node<E>(null);
        size = 0;
    }


    /**
     * Gets the data of the current element in the list.
     *
     * @return the data
     */
    public E getCurrent()
    {
        if (size > 0)
        {
            return current.data();
        }
        else
        {
            throw new NoSuchElementException("The list is empty.");
        }
    }


    /**
     * Moves the currentItem to the next element in the list.
     */
    public void next()
    {
        if (size > 0)
        {
            current = current.next();
        }
    }


    /**
     * Moves the currentItem to the previous element in the list.
     */
    public void previous()
    {
        if (size > 0)
        {
            current = current.previous();
        }
    }


    /**
     * Gets the data of the current element in the list and sets the currentItem
     * to null.
     *
     * @return the data
     */
    public E removeCurrent()
    {
        if (size == 0)
        {
            throw new NoSuchElementException("The list is empty.");

        }
        else if (size == 1)
        {
            E newNode = current.data();

            this.clear();
            return newNode;
        }
        else
        {
            Node<E> nextNode = current.next();
            Node<E> previousNode = current.previous();
            Node<E> node3 = previousNode.split();
            node3.split();
            previousNode.join(nextNode);
            current = nextNode;
            size--;
            return node3.data();
        }
    }


    /**
     * The size of the list.
     *
     * @return the size
     */
    public int size()
    {
        return size;
    }


    /**
     * The list iterator.
     *
     * @return new CircularLinkedListIterator
     */
    public Iterator<E> iterator()
    {
        return new CircularLinkedListIterator(this.current);
    }


    /**
     * The toString method
     *
     * @return the readable string
     */
    public String toString()
    {
        Iterator<E> itr = this.iterator();
        if (size == 0)
        {
            return "[]";
        }
        else
        {
            String solution = "[";
            for (int i = 0; i < size - 1; i++)
            {
                solution = solution + itr.next() + ", ";
            }
            return solution + itr.next() + "]";
        }
    }


    /**
     * This is a nested Iterator class.
     *
     * @author Chris Conley
     * @version (2013.04.03)
     */
    private class CircularLinkedListIterator
        implements Iterator<E>
    {
        private int     index;
        private Node<E> iterCurrent;


        /**
         * Creates new CircularLinkedListIterator
         *
         * @param c
         */
        public CircularLinkedListIterator(Node<E> c)
        {
            iterCurrent = c;
            index = 0;
        }


        /**
         * This method tells if the list is not empty.
         *
         * @return true if not empty, false if empty
         */
        public boolean hasNext()
        {
            if (index < size)
            {
                return true;
            }
            return false;
        }


        /**
         * This method gets the data of the next element in the list.
         *
         * @return the data
         */
        public E next()
        {
            if (hasNext())
            {

                E data = iterCurrent.data();
                index = index + 1;
                iterCurrent = iterCurrent.next();
                return data;
            }
            else
            {
                throw new NoSuchElementException("There is no such element");
            }
        }


        /**
         * The remove method that does not have to be implemented.
         */
        public void remove()
        {
            throw new UnsupportedOperationException("You can't remove an item");
        }

    }
}
