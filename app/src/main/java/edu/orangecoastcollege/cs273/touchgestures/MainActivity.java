package edu.orangecoastcollege.cs273.touchgestures;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * This activity will display a log of all the gestures made on the device, and will update with
 * each gesture made. The user can also clear the log.
 *
 * @author Derek Tran
 * @version 1.0
 * @since November 2, 2017
 */
public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener
{

    private ScrollView gesturesScrollView;

    private TextView gesturesLogTextView;
    private TextView singleTapTextView;
    private TextView doubleTapTextView;
    private TextView longPressTextView;
    private TextView scrollTextView;
    private TextView flingTextView;

    private int singleTaps = 0, doubleTaps = 0, longPresses = 0, scrolls = 0, flings = 0;

    // Member variable to detect gestures
    private GestureDetector mGestureDetector;

    private boolean isButtonClicked = false;

    /**
     * Initializes <code>MainActivity</code> by inflating its UI.
     *
     * @param savedInstanceState Bundle containing the data it recently supplied in
     *                           onSaveInstanceState(Bundle) if activity was reinitialized after
     *                           being previously shut down. Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gesturesScrollView = (ScrollView) findViewById(R.id.gesturesScrollView);
        gesturesLogTextView = (TextView) findViewById(R.id.gesturesLogTextView);
        singleTapTextView = (TextView) findViewById(R.id.singleTapTextView);
        doubleTapTextView = (TextView) findViewById(R.id.doubleTapTextView);
        longPressTextView = (TextView) findViewById(R.id.longPressTextView);
        scrollTextView = (TextView) findViewById(R.id.scrollTextView);
        flingTextView = (TextView) findViewById(R.id.flingTextView);

        mGestureDetector = new GestureDetector(gesturesScrollView.getContext(), this);
    }

    /**
     * Sends the touch event to all the children in ViewGroup:
     * e.g. ScrollView down to the TextView
     *
     * @param ev The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        super.dispatchTouchEvent(ev);
        return mGestureDetector.onTouchEvent(ev);
    }

    /**
     * Handles single-tap gesture. Not part of a double tap.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent)
    {
        if (!isButtonClicked)
        {
            // Display the message and increment the counter
            gesturesLogTextView.append("\nSingleTap gesture occurred");
            singleTapTextView.setText(String.valueOf(++singleTaps));
        }
        else isButtonClicked = false;   // reset after button is clicked to log onSingleTapConfirmed
        return true;
    }

    /**
     * Handles double-tap gesture. Double-tap is the succession of two
     * single tap gestures within a duration.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent)
    {
        gesturesLogTextView.append("\nDoubleTap gesture occurred");
        doubleTapTextView.setText(String.valueOf(++doubleTaps));
        return true;
    }

    /**
     * During a double tap, another event occurs (including move)
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent)
    {
        return false;
    }

    /**
     * User made contact with device. Every gesture begins with onDown.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onDown(MotionEvent motionEvent)
    {
        gesturesLogTextView.append("\nDown event occurred");
        return true;
    }

    /**
     * Down event where user does not let go, short duration of time.
     *
     * @param motionEvent The motion event triggering the touch.
     */
    @Override
    public void onShowPress(MotionEvent motionEvent)
    {
        gesturesLogTextView.append("\nShowPress event occurred");
    }

    /**
     * Similar to an onSingleTapConfirmed, but it could be part of a double tap.
     *
     * @param motionEvent The motion event triggering the touch.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent)
    {
        gesturesLogTextView.append("\nSingleTapUp event occurred");
        return true;
    }

    /**
     * Down event, followed by a press and lateral movement, without letting go.
     *
     * @param motionEvent  The event where the scroll originated.
     * @param motionEvent1 The event where the scroll stopped.
     * @param distanceX    The distance in X direction (pixels).
     * @param distanceY    The distance in Y direction (pixels).
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float distanceX, float distanceY)
    {
        gesturesLogTextView.append("\nScroll gesture occurred, distanceX = " + distanceX + ", distanceY = " + distanceY);
        scrollTextView.setText(String.valueOf(++scrolls));
        return true;
    }

    /**
     * Down event, followed by a long hold.
     *
     * @param motionEvent The motion event triggering the touch.
     */
    @Override
    public void onLongPress(MotionEvent motionEvent)
    {
        gesturesLogTextView.append("\nLongPress gesture occurred");
        longPressTextView.setText(String.valueOf(++longPresses));
    }

    /**
     * Similar to a scroll, with faster velocity and user releases contact with device.
     *
     * @param motionEvent  The event where the scroll originated.
     * @param motionEvent1 The event where the scroll stopped.
     * @param velocityX    Velocity in the X direction.
     * @param velocityY    Velocity in the Y direction.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float velocityX, float velocityY)
    {
        gesturesLogTextView.append("\nFling gesture occurred, velocityX = " + velocityX + ", velocityY = " + velocityY);
        flingTextView.setText(String.valueOf(++flings));
        return true;
    }

    /**
     * Clears the log and resets the count of each gesture.
     *
     * @param v The view that called this method.
     */
    public void clearAll(View v)
    {
        singleTaps = 0;
        doubleTaps = 0;
        longPresses = 0;
        scrolls = 0;
        flings = 0;

        gesturesLogTextView.setText("");
        singleTapTextView.setText("0");
        doubleTapTextView.setText("0");
        longPressTextView.setText("0");
        scrollTextView.setText("0");
        flingTextView.setText("0");

        isButtonClicked = true;

    }
}
