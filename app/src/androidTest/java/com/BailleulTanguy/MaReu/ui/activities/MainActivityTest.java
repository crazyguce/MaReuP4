package com.BailleulTanguy.MaReu.ui.activities;

import android.content.pm.ActivityInfo;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.BailleulTanguy.MaReu.R;
import com.BailleulTanguy.MaReu.di.DI;
import com.BailleulTanguy.MaReu.service.MeetingApiService;
import com.BailleulTanguy.MaReu.actions.DeleteViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.BailleulTanguy.MaReu.assertion.RecyclerViewItemCountAssertion.withItemCount;
import static com.BailleulTanguy.MaReu.utilstest.InsertGraphicData.addFakeMeeting;
import static com.BailleulTanguy.MaReu.utilstest.IsScreenSw600dp.isScreenSw600dp;
import static com.BailleulTanguy.MaReu.utilstest.IsScreenSw600dp.sIsScreenSw600dp;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private MeetingApiService mApi = null;
    private MainActivity mActivity = null;
    private Calendar mNow ;
    private Calendar mCalDate;
    private static int ITEMS_COUNT = 2;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp()  {

        mActivity = mActivityTestRule.getActivity();
        assertNotNull(mActivity);
        assertThat(mActivity, notNullValue());

        mApi = DI.getMeetingApiService();
        assertNotNull(mApi);

        sIsScreenSw600dp = isScreenSw600dp(mActivity);

        mNow = Calendar.getInstance(Locale.FRANCE);
        mCalDate = (Calendar) mNow.clone();

    }

    @After
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
        mNow = Calendar.getInstance(Locale.FRANCE);
    }

    @Test
    public void givenItem_whenClickOnItem_thenDisplayDetail() {
        int idItemToTest = 0;

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,0,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv), isDisplayed()))
                .perform(actionOnItemAtPosition(idItemToTest, click()));

        onView(allOf(withId(R.id.detail_fragment_layout))).check(matches(isDisplayed()));

        onView(allOf(withId(R.id.room_name),isDisplayed()))
                .check(matches(withText(mApi.getMeetingSelected().getRoom().getNameRoom())));
    }

    @Test
    public void givenItem_whenClickAndValidDeleteAction_thenRemoveItem() {

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate , 2,0,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        mCalDate = (Calendar) mNow.clone();
        addFakeMeeting("PLUTON", "La guerre des étoiles", mCalDate ,5,0,5,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv),isDisplayed())).check(withItemCount(ITEMS_COUNT));

        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(1, new DeleteViewAction()));

        onView(allOf(withText(R.string.msg_delete_meeting))).check(matches(isDisplayed()));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.activity_list_rv))
                .check(matches(not(hasDescendant(withText("PLUTON")))));
        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT-1));

    }

    @Test
    public void givenItem_whenClickAndNoValidDeleteAction_thenItemNotRemoved() {

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,0,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        mCalDate = (Calendar) mNow.clone();
        addFakeMeeting("PLUTON", "La guerre des étoiles", mCalDate,5,0,5,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv),isDisplayed())).check(withItemCount(ITEMS_COUNT));

        //On exécute le DeleteActionView qui appelle la demande de confirmation de suppression
        onView(withId(R.id.activity_list_rv))
                .perform(actionOnItemAtPosition(0, new DeleteViewAction()));

        //On s'assure que le message s'est ouvert
        onView(allOf(withText(R.string.msg_delete_meeting))).check(matches(isDisplayed()));

        //On clique sur annuler pour refuser la suppression
        onView(withId(android.R.id.button2)).perform(click());

        onView(withId(R.id.activity_list_rv)).check(withItemCount(ITEMS_COUNT));
    }

    @Test
    public void givenAddFabAndFrameRight_whenTabletOrPhone_thenNotVisibleOrVisible() {
        if (sIsScreenSw600dp) {
            onView(allOf(withId(R.id.add_fab))).check(matches(not(isDisplayed())));
            onView(allOf(withId(R.id.frame_right))).check(matches(isDisplayed()));
        } else {
            onView(allOf(withId(R.id.add_fab))).check(matches(isDisplayed()));
        }

    }

    @Test
    public void given1Meeting_whenRotateScreen_thenListIsEmpty() {

        addFakeMeeting("ARES", "La guerre des boutons", mCalDate, 2,0,2,
                Arrays.asList("tigrou@disney.com", "geotrouvetout@disney.com", "donald@disney.com"));

        onView(allOf(withId(R.id.activity_list_rv),isDisplayed())).check(withItemCount(1));

        if (!sIsScreenSw600dp) {
            mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

    }

}
