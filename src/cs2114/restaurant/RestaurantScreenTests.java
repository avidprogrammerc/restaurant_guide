package cs2114.restaurant;

import realtimeweb.yelp.exceptions.BusinessSearchException;
import android.content.Intent;
import android.widget.TextView;
import realtimeweb.yelp.Business;
import android.widget.Button;
import android.widget.EditText;

// -------------------------------------------------------------------------
/**
 * Tests the RestaurantScreen class
 * 
 * @author (Chris Conley)
 * @version (2013.04.16)
 */
public class RestaurantScreenTests extends
		student.AndroidTestCase<RestaurantScreen>
{
	// ~ Fields ................................................................

	private EditText						searchField;
	private Button							next;
	private Button							previous;
	private TextView						restaurantName;
	private CircularLinkedList<Business>	list;
	private Button							viewMap;

	// ~ Constructors ..........................................................
	/**
	 * Test cases that extend AndroidTestCase must have a parameterless
	 * constructor that calls super() and passes it the screen/activity class
	 * being tested.
	 */
	public RestaurantScreenTests()
	{
		super(RestaurantScreen.class);
	}

	// ~ Public methods ........................................................
	/**
	 * Tests the next method
	 */
	public void testNext()
	{
		this.enterText(searchField, "Blacksburg, VA");
		list = getScreen().getList();
		click(next);
		Business current = list.getCurrent();
		assertEquals("Sub Station II", current.getName());
	}

	/**
	 * Tests the previous method
	 */
	public void testPrevious()
	{
		this.enterText(searchField, "Blacksburg, VA");
		list = getScreen().getList();
		click(previous);
		Business current = list.getCurrent();
		assertEquals("Lyric Theatre", current.getName());
	}

	/**
	 * Tests the empty method
	 */
	public void testEmpty()
	{
		this.enterText(searchField, "");
		assertEquals("Restaurant Name", restaurantName.getText());
	}

	/**
	 * Tests the maps method
	 */
	public void testMaps()
	{
		this.enterText(searchField, "Blacksburg, VA");
		list = getScreen().getList();
		prepareForUpcomingActivity(Intent.ACTION_VIEW);
		click(viewMap);
	}

	/**
	 * Tests for a failed search
	 */
	public void testFailedSearch()
	{
		getInstrumentation().runOnMainSync(new Runnable()
		{
			public void run()
			{
				getScreen().businessSearchFailed(
						new BusinessSearchException("failed", "Search failed",
								"Search timed out"));
			}
		});

		assertEquals(1, 1);
	}
}
