package cs2114.restaurant;

import android.net.Uri;
import sofia.content.ContentViewer;
import sofia.widget.ImageView;
import realtimeweb.yelp.DetailedLocation;
import android.widget.TextView;
import android.widget.RatingBar;
import android.widget.Button;
import realtimeweb.yelp.Business;
import android.widget.EditText;
import realtimeweb.yelp.BusinessQuery;
import realtimeweb.yelp.BusinessSearch;
import realtimeweb.yelp.SearchResponse;
import realtimeweb.yelp.exceptions.BusinessSearchException;
import realtimeweb.yelp.BusinessSearchListener;
import sofia.app.Screen;

// -------------------------------------------------------------------------
/**
 * This program pulls information from the yelp servers and provides information
 * from the city being searched
 *
 * @author (Chris Conley)
 * @version (2013.05.27)
 */
public class RestaurantScreen
    extends Screen
    implements BusinessSearchListener
{
    // ~ Fields ................................................................

    private CircularLinkedList<Business> businessList;
    private BusinessSearch               businessSearch;
    private EditText                     searchField;
    private Button                       previous;
    private Button                       next;
    private Button                       viewMap;
    private ImageView                    imageView;
    private RatingBar                    ratingBar;
    private TextView                     numRatings;
    private TextView                     restaurantName;
    private String                       search;
    private DetailedLocation             location;


    // ~ Public methods ........................................................

    /**
     * This method sets up the screen.
     */
    public void initialize()
    {
        businessList = new CircularLinkedList<Business>();
        businessSearch = BusinessSearch.getInstance();
        businessSearch.connect(
            "Wx0IUSaalEk5vRnawpTtyw",
            "RWamWIDLFs0XTpbJO5T7hfshnT8",
            "uE0lvpUarqF7wgq6R3IH4n0iQyIBAkym",
            "ZgGEmpOE0iDDD1NINDMuuiDLCAo");
        next.setEnabled(false);
        previous.setEnabled(false);
        viewMap.setEnabled(false);
    }


    /**
     * This method is solely used for testing purposes.
     *
     * @return the CircularLinkedList
     */
    public CircularLinkedList<Business> getList()
    {
        return businessList;
    }


    /**
     * Adds a restaurant to the business list
     *
     * @param response
     *            the response from the business search
     */
    public void businessSearchCompleted(SearchResponse response)
    {
        businessList.clear();
        for (Business business : response.getBusinesses())
        {
            businessList.add(business);
        }

        if (businessList.size() == 0)
        {
            clearScreen();
        }
        else
        {
            next.setEnabled(true);
            previous.setEnabled(true);
            viewMap.setEnabled(true);
            this.getBusinessData();
        }
    }


    /**
     * Method called if the search fails
     *
     * @param exception
     *            the exception to appear with failed search
     */
    public void businessSearchFailed(BusinessSearchException exception)
    {
        clearScreen();
    }


    /**
     * Updates app when the user finishes entering data into the search field
     */
    public void searchFieldEditingDone()
    {
        search = searchField.getText().toString();
        businessSearch.searchBusinesses(
            new BusinessQuery(search),
            new BusinessSearchGUIAdapter(this));

    }


    /**
     * Updates app to next item in the list
     */
    public void nextClicked()
    {
        businessList.next();
        this.getBusinessData();
    }


    /**
     * Updates app to previous item in list
     */
    public void previousClicked()
    {
        businessList.previous();
        this.getBusinessData();
    }


    /**
     * Updates app to show geographic location of current item
     */
    public void viewMapClicked()
    {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        String address = "http://maps.google.com/maps?q=" + lat + "," + lon;
        Uri uri = Uri.parse(address);
        new ContentViewer(uri).start(this);
    }


    /**
     * Handles all of the GUI changes
     */
    private void getBusinessData()
    {
        Business current = businessList.getCurrent();
        Uri imageURI;

        String name = current.getName();
        float rating = current.getRating();
        int reviewCount = current.getReviewCount();
        String imageUrl = current.getImageUrl();
        location = current.getLocation();
        if (imageUrl != null)
        {
            imageURI = Uri.parse(imageUrl);
            imageView.setImageURI(imageURI);
        }
        else
        {
            imageView.setImageResource(R.drawable.no_photo);
        }

        restaurantName.setText(name);
        ratingBar.setRating(rating);
        numRatings.setText(String.valueOf(reviewCount) + " ratings");
    }


    /**
     * Handles a case where an invalid search is entered.
     */
    private void clearScreen()
    {
        businessList.clear();
        ratingBar.setRating(0);
        imageView.setImageResource(R.drawable.no_photo);
        restaurantName.setText("Restaurant Name");
        numRatings.setText("# ratings");
        next.setEnabled(false);
        previous.setEnabled(false);
        viewMap.setEnabled(false);
    }
}
