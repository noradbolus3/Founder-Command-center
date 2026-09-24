package com.foundercommandcenter;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.android.controller.ActivityController;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 34)
public class MainActivityLaunchTest {
    @Test public void activityCreatesExecutiveShell() {
        ActivityController<MainActivity> controller = Robolectric.buildActivity(MainActivity.class);
        MainActivity activity = controller.create().start().resume().get();
        android.view.ViewGroup content = activity.findViewById(android.R.id.content);
        assertNotNull(content);
        assertNotNull(content.getChildAt(0));
        controller.pause().stop().destroy();
    }
}
