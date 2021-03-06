package com.BailleulTanguy.MaReu.service;

import android.content.Context;
import android.graphics.Color;

import com.BailleulTanguy.MaReu.R;
import com.BailleulTanguy.MaReu.di.DI;
import com.BailleulTanguy.MaReu.model.Meeting;
import com.BailleulTanguy.MaReu.model.Room;
import com.BailleulTanguy.MaReu.utilstest.MeetingUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;

import java.util.Arrays;

import static com.BailleulTanguy.MaReu.service.FakeMeetingApiService.CST_FORMAT_DATE;
import static com.BailleulTanguy.MaReu.service.FakeMeetingApiService.CST_FORMAT_DATE_TIME;
import static com.BailleulTanguy.MaReu.utils.DateConverter.convertDateTimeStringToCalendar;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


@Tag("MeetingApiServiceAddMeeting")
//Meeting = new meeting; reference = reference meeting
public class AddMeetingTest {
    private MeetingApiService mApi;
    private String mReturn;

    @Mock
    Context contextMock;

    @BeforeEach
    public void setup() throws MeetingApiServiceException {
        mReturn = null;

        initMocks(this);
        mApi = DI.getMeetingApiService();
        assertThat(mApi, notNullValue());

        mApi = MeetingUtils.generateReferenceMeeting(mApi);

    }

    @AfterEach
    public void tearDown() {
        mApi = DI.getMeetingApiServiceNewInstance();
    }


    @DisplayName("Case 1 : Meeting Start = Reference start")
    @Test
    public void givenNewMeeting_whenSameStart_thenFail() {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);

    }

    @DisplayName("Case 2 : Meeting end = Reference End")
    @Test
    public void givenNewMeeting_whenSameEnd_thenFail()  {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 08:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);

    }

    @DisplayName("Case 3 : Meeting start before Reference start and Meeting end before Reference start")
    @Test
    public void givenNewMeeting_whenBeforeReference_thenCreateMeeting() {

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 04:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",mReturn);

    }

    @DisplayName("Case 4 : Meeting start before Reference start and Meeting end same Reference end")
    @Test
    public void givenNewMeeting_whenEndSameReferenceStart_thenCreateMeeting()  {

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",mReturn);

    }

    @DisplayName("Case 5 : Meeting start before Reference start and " +
            "Meeting end before Reference end and after Reference start")
    @Test
    public void givenNewMeeting_whenEndDuringReference_thenFail() {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);
    }

    @DisplayName("Case 6 : Meeting start before Reference start and Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenReferenceDuringNewMeeting_thenFail() {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 05:30:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);

    }

    @DisplayName("Case 7 : Meeting start after Reference start and Meeting end before Reference end")
    @Test
    public void givenNewMeeting_whenNewMeetingDuringReference_thenFail() {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 06:30:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:30:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);

    }
    @DisplayName("Case 8 : Meeting start after Reference start and before Reference end and" +
            "Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenReferenceEndDuringNewMeeting_thenFail() {

        when(contextMock.getString(R.string.err_meeting_room_not_free))
                .thenReturn("La salle de réunion n\'est pas disponible");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 07:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("La salle de réunion n\'est pas disponible",mReturn);

    }

    @DisplayName("Case 9 : Meeting start = Reference end and Meeting end after Reference end")
    @Test
    public void givenNewMeeting_whenStartSameReferenceEnd_thenCreateMeeting() {

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 08:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",mReturn);

    }

    @DisplayName("Case 10 : Meeting start and end after Reference end")
    @Test
    public void givenNewMeeting_whenAfterReference_thenCreateMeeting() {

        when(contextMock.getString(R.string.action_add_meeting))
                .thenReturn("Réunion ajoutée");

        Meeting lMeeting = new Meeting(new Room("GAIA", Color.argb(100,0,150,135)),
                "Sujet10 reunion avec quarante caractères",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 09:00:00:00"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME,"01/08/2020 10:00:00:00"),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        try {
            mApi.addMeeting(lMeeting);
            mReturn = contextMock.getString(R.string.action_add_meeting);
        } catch (MeetingApiServiceException pE) {
            mReturn = "La salle de réunion n\'est pas disponible";
        }
        assertEquals("Réunion ajoutée",mReturn);

    }


    @ParameterizedTest
    @CsvSource({"01/08/2020 06:00:00:00,01/08/2020 07:00:00:00",
            "01/08/2020 07:00:00:00,01/08/2020 08:00:00:00",
            "01/08/2020 05:00:00:00,01/08/2020 07:00:00:00",
            "01/08/2020 05:30:00:00,01/08/2020 09:00:00:00",
            "01/08/2020 06:30:00:00,01/08/2020 07:30:00:00",
            "01/08/2020 07:00:00:00,01/08/2020 09:00:00:00"})
    void givenMeeting_whenMeetingTimeInCommonWithReference_thenFail(String pStart, String pEnd) {
        final Meeting lMeeting = new Meeting(
                new Room("GAIA", Color.argb(100,0,150,135)),
                "Réunion non valide ",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME, pStart),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME, pEnd),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        Executable lExecutable = new Executable() {
            @Override
            public void execute() throws Throwable {
                mApi.addMeeting(lMeeting);
            }};

        assertThrows(MeetingApiServiceException.class,lExecutable);
    }

    @ParameterizedTest
    @CsvSource({"01/08/2020 04:00:00:00,01/08/2020 05:00:00:00",
            "01/08/2020 05:00:00:00,01/08/2020 06:00:00:00",
            "01/08/2020 08:00:00:00,01/08/2020 09:00:00:00",
            "01/08/2020 09:00:00:00,01/08/2020 10:00:00:00"})
    void givenMeeting_whenMeetingTimeNotInCommonWitgReference_thenSuccess(String pStart, String pEnd) {
        final Meeting lMeeting = new Meeting(
                new Room("GAIA", Color.argb(100,0,150,135)),
                "Réunion valide ",
                convertDateTimeStringToCalendar(CST_FORMAT_DATE,"01/08/2020"),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME, pStart),
                convertDateTimeStringToCalendar(CST_FORMAT_DATE_TIME, pEnd),
                Arrays.asList("toto@gmail.com","titi@gmail.com"));

        Executable lExecutable = new Executable() {
            @Override
            public void execute() throws Throwable {
                mApi.addMeeting(lMeeting);
            }};

        assertDoesNotThrow(lExecutable);
    }
}
