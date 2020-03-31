package com.BailleulTanguy.MaReu.ui.activities;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.BailleulTanguy.MaReu.R;
import com.BailleulTanguy.MaReu.di.DI;
import com.BailleulTanguy.MaReu.model.Meeting;
import com.BailleulTanguy.MaReu.service.MeetingApiService;
import com.BailleulTanguy.MaReu.service.MeetingApiServiceException;

import org.hamcrest.Matchers;
import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.LayoutMatchers.hasEllipsizedText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.BailleulTanguy.MaReu.matchers.ChildAtPositionMatcher.childAtPosition;
import static com.BailleulTanguy.MaReu.assertion.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityWith10MeetingTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private List<Meeting> mMeetings = new ArrayList<>();
    private static int ITEMS_COUNT = 10;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<MainActivity>(MainActivity.class) {
            @Override
            protected void beforeActivityLaunched() {
                mApi = DI.getMeetingApiServiceNewInstance();
                try {
                    mApi.addFakeValidMeetingsLongList();
                } catch (MeetingApiServiceException pE) {
                    pE.printStackTrace();
                }
            }
    };

    @Before
    public void setUp() {
        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mMeetings = mApi.getMeetings();
    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test
    public void given10Meeting_whenTextEllipsizedAndGoodFormat_thenSuccess() {

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));
        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        onView(allOf(withId(R.id.item_description), withText(startsWith("POSEIDON"))))
                .check(matches(withText(mMeetings.get(0).toStringDescription())));

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(withText(mMeetings.get(0).toStringParticipants())));

        onView(allOf(withId(R.id.item_participant), withText(startsWith("loki"))))
                .check(matches(hasEllipsizedText()));

    }



    @Test
    public void given10Meeting_whenFilterByDate_thenShowMeetingWithSameDate () {

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        //Saisie de la date de filtrage
        Calendar lCalDate = Calendar.getInstance(Locale.FRANCE);
        lCalDate.set(2020,8,01);
        lCalDate.set(Calendar.MINUTE,00);
        lCalDate.set(Calendar.SECOND,0);
        lCalDate.set(Calendar.MILLISECOND,0);


        onView(allOf(withId(R.id.action_filter))).perform(click());

        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Date sélectionnée"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        lCalDate.get(Calendar.YEAR),
                        lCalDate.get(Calendar.MONTH),
                        lCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());


        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(3));

        List<Meeting> lMeetings = mApi.getMeetings();
        onView(allOf(withId(R.id.item_description),withText(startsWith("VENUS"))))
                .check(matches(withText(lMeetings.get(6).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("HADES"))))
                .check(matches(withText(lMeetings.get(8).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("GAIA"))))
                .check(matches(withText(lMeetings.get(9).toStringDescription())));

    }

    @Test
    public void given10Meeting_whenResetFilter_thenRemoveAllItems ()  {

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));

        //Saisie de la date de filtrage
        Calendar lCalDate = Calendar.getInstance(Locale.FRANCE);
        lCalDate.set(2020,8,01);
        lCalDate.set(Calendar.MINUTE,00);
        lCalDate.set(Calendar.SECOND,0);
        lCalDate.set(Calendar.MILLISECOND,0);


        onView(allOf(withId(R.id.action_filter))).perform(click());
        ViewInteraction appCompatTextView = onView(
                Matchers.allOf(withId(R.id.title), withText("Date sélectionnée"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(
                        lCalDate.get(Calendar.YEAR),
                        lCalDate.get(Calendar.MONTH),
                        lCalDate.get(Calendar.DAY_OF_MONTH)));
        onView(withText(android.R.string.ok)).perform(click());


        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(3));

        List<Meeting> lMeetings = mApi.getMeetings();
        onView(allOf(withId(R.id.item_description),withText(startsWith("VENUS"))))
                .check(matches(withText(lMeetings.get(6).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("HADES"))))
                .check(matches(withText(lMeetings.get(8).toStringDescription())));
        onView(allOf(withId(R.id.item_description), withText(startsWith("GAIA"))))
                .check(matches(withText(lMeetings.get(9).toStringDescription())));

        onView(allOf(withId(R.id.action_filter))).perform(click());

        ViewInteraction appCompatTextView1 = onView(
                Matchers.allOf(withId(R.id.title), withText("Enlever le filtre"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatTextView1.perform(click());

        onView(allOf(withId(R.id.activity_list_rv))).check(matches(isDisplayed()));

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));
    }
}
