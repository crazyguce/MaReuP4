package com.BailleulTanguy.MaReu.service;

import android.content.Context;

import com.BailleulTanguy.MaReu.di.DI;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;


import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class InitializeRoomTest {

    private MeetingApiService mApi;

    @Mock
    Context contextMock;

    @Before
    public void setup() {
        initMocks(this);
        mApi = DI.getMeetingApiService();
    }

    @Test
    public void initializeRoomsWithSuccess() {
        int nbRoomExpected = 10;

        //Avant initialisation on doit avoir 0 room
        int nbGetRooms = mApi.getRooms().size();
        assertEquals(0,nbGetRooms);

        //Après initialisation on doit avoir 10 rooms
        mApi.initializeRooms(contextMock);
        nbGetRooms = mApi.getRooms().size();

        assertEquals(nbGetRooms,nbRoomExpected);
    }
}
