package com.BailleulTanguy.MaReu.utils;

import android.content.Context;

import com.BailleulTanguy.MaReu.di.DI;
import com.BailleulTanguy.MaReu.model.Meeting;
import com.BailleulTanguy.MaReu.model.Room;
import com.BailleulTanguy.MaReu.service.MeetingApiService;
import com.BailleulTanguy.MaReu.service.MeetingApiServiceException;
import com.BailleulTanguy.MaReu.utilstest.MeetingUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static com.BailleulTanguy.MaReu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.BailleulTanguy.MaReu.utils.DateConverter.convertDateTimeStringToCalendar;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.MockitoAnnotations.initMocks;

public class SortOrFilterTest {

    private MeetingApiService mApi;
    private List<Meeting> mMeetings;
    private List<Room> mRooms;

    @Mock
    Context contextMock;

    @BeforeEach
    public void setup() throws MeetingApiServiceException {
        initMocks(this);

        mApi = DI.getMeetingApiService();
        assertThat(mApi, notNullValue());

        mApi.initializeRooms(contextMock);

        mRooms = mApi.getRooms();
        assertNotNull(mRooms);
        assertEquals(10,mRooms.size());

        mApi = MeetingUtils.addFakeValidMeetingsLongList(mApi);
        mMeetings = mApi.getMeetings();
        assertNotNull(mMeetings);
        assertEquals(10,mMeetings.size());
    }

    @AfterEach
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }

    @Test
    public void givenRooms_whenFilterSelected_thenFilterByRoomsWithSuccess() {
        assertEquals(10,mMeetings.size());

        assertEquals("POSEIDON",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ZEUS",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("DEMETER",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(9).getRoom().getNameRoom());

        // HADES , ARES, POSEIDON
        List<Room> lListRoomsSelected = MeetingUtils.generate3Rooms();

        mMeetings = SortOrFilter.filterMeetingRoom(mMeetings, lListRoomsSelected);
        assertEquals(5,mMeetings.size());

        assertEquals("HADES",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("POSEIDON",mMeetings.get(4).getRoom().getNameRoom());

    }

    @Test
    public void givenDate_whenFilterSelected_thenFilterByDateWithSuccess()  {
        assertEquals(10,mMeetings.size());

        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),mMeetings.get(0).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(1).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(2).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(3).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(4).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(5).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(6).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(7).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(8).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(9).getDate());

        // 01/08/2020
        mMeetings = SortOrFilter.filterMeetingDate(mMeetings, convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"));
        assertEquals(3,mMeetings.size());

        assertEquals("VENUS",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(2).getRoom().getNameRoom());

    }

    @Test
    public void givenRoomSortAscendant_whenSortSelected_thenSortWithSuccess() {
        assertEquals(10,mMeetings.size());

        assertEquals("POSEIDON",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ZEUS",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("DEMETER",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(9).getRoom().getNameRoom());

        mMeetings = SortOrFilter.sortMeetingRoomAsc(mMeetings);

        assertEquals(10,mMeetings.size());

        assertEquals("ARES",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(0).getDate());
        assertEquals("ARES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("DEMETER",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(4).getDate());
        assertEquals("HADES",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("POSEIDON",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals("ZEUS",mMeetings.get(9).getRoom().getNameRoom());

    }

    @Test
    public void givenRoomSortDescendant_whenSortSelected_thenSortWithSuccess() {
        assertEquals(10,mMeetings.size());

        assertEquals("POSEIDON",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ZEUS",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("DEMETER",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(9).getRoom().getNameRoom());

        mMeetings = SortOrFilter.sortMeetingRoomDesc(mMeetings);

        assertEquals(10,mMeetings.size());

        assertEquals("ZEUS",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("POSEIDON",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(4).getDate());
        assertEquals("HADES",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(4).getDate());
        assertEquals("GAIA",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("DEMETER",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(8).getDate());
        assertEquals("ARES",mMeetings.get(9).getRoom().getNameRoom());

    }

    @Test
    public void givenDateSortFromRecentToOlder_whenSortSelected_thenSortWithSuccess() {
        assertEquals(10,mMeetings.size());

        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),mMeetings.get(0).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(1).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(2).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(3).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(4).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(5).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(6).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(7).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(8).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(9).getDate());

        mMeetings = SortOrFilter.sortMeetingDateRecentToOlder(mMeetings);

        assertEquals(10,mMeetings.size());

        assertEquals("DEMETER",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(1).getDate());
        assertEquals("POSEIDON",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("PLUTON",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(5).getDate());
        assertEquals("ZEUS",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(9).getRoom().getNameRoom());
    }

    @Test
    public void givenDateSortFromOlderToRecent_whenSortSelected_thenSortWithSuccess()  {
        assertEquals(10,mMeetings.size());

        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"30/08/2020"),mMeetings.get(0).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(1).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(2).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(3).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(4).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"22/08/2020"),mMeetings.get(5).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(6).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(7).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(8).getDate());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),mMeetings.get(9).getDate());

        mMeetings = SortOrFilter.sortMeetingDateOlderToRecent(mMeetings);

        assertEquals(10,mMeetings.size());

        assertEquals("HADES",mMeetings.get(0).getRoom().getNameRoom());
        assertEquals("GAIA",mMeetings.get(1).getRoom().getNameRoom());
        assertEquals("VENUS",mMeetings.get(2).getRoom().getNameRoom());
        assertEquals("ZEUS",mMeetings.get(3).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(4).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/08/2020"),mMeetings.get(4).getDate());
        assertEquals("PLUTON",mMeetings.get(5).getRoom().getNameRoom());
        assertEquals("ARES",mMeetings.get(6).getRoom().getNameRoom());
        assertEquals("POSEIDON",mMeetings.get(7).getRoom().getNameRoom());
        assertEquals("HADES",mMeetings.get(8).getRoom().getNameRoom());
        assertEquals(convertDateTimeStringToCalendar(CST_FORMAT_DATE,"15/09/2020"),mMeetings.get(8).getDate());
        assertEquals("DEMETER",mMeetings.get(9).getRoom().getNameRoom());

    }

}
