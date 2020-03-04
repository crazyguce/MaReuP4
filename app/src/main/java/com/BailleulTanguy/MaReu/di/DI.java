package com.BailleulTanguy.MaReu.di;

import com.BailleulTanguy.MaReu.service.FakeMeetingApiService;
import com.BailleulTanguy.MaReu.service.MeetingApiService;

/**
 * Created by Bailleul Tanguy on 02/02/2020
 */
public class DI {

    /**
     * Constructor du Dependencies Injection
     */
    private DI(){}

    /**
     * Déclaration d'une nouvelle instance d'Api Service
     */
    private static MeetingApiService sApiService = new FakeMeetingApiService();

    /**
     * Récupère l'ApiService en cours
     * @return : objet : api service
     */
    public static MeetingApiService getMeetingApiService() { return sApiService; }

    /**
     * Récupère une nouvelle instance d'Api Service
     * @return : objet : api service
     */
    public static MeetingApiService getMeetingApiServiceNewInstance() {
        sApiService = new FakeMeetingApiService();
    return sApiService; }

}
