package com.github.bakuplayz.cropclick.utils;

import be.seeseemelk.mockbukkit.MockBukkit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class VersionUtilTests {


    @Before
    public void setUp() {
        MockBukkit.mock();
    }


    @After
    public void tearDown() {
        MockBukkit.unload();
    }


    @Test
    public void getServerVersion() {
        String version = VersionUtil.getServerVersion();
        assertAll("Should return a formatted and double parsable version number.",
                () -> assertNotNull(version),
                () -> assertEquals(version, "12.1")
        );
    }


    @Test
    public void between() {
        assertAll("Should return true, when the version is in the interval or equal to sent version.",
                () -> assertFalse(VersionUtil.between(0.0, 12.0)),
                () -> assertTrue(VersionUtil.between(12.0, 12.9)),
                () -> assertTrue(VersionUtil.between(12.0, 12.1))
        );
    }


    @Test
    public void supportsBeetroots() {
        assertAll("Should return true, due to 1.12 supporting beetroots.", VersionUtil::supportsBeetroots);
    }


    @Test
    public void supportsShulkers() {
        assertAll("Should return true, due to 1.12 supporting shulkers.", VersionUtil::supportsShulkers);
    }

}